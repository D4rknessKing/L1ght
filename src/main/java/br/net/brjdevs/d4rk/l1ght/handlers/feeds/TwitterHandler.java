package br.net.brjdevs.d4rk.l1ght.handlers.feeds;


import br.net.brjdevs.d4rk.l1ght.L1ght;
import br.net.brjdevs.d4rk.l1ght.utils.Config;
import br.net.brjdevs.d4rk.l1ght.utils.FeedsType;
import br.net.brjdevs.d4rk.l1ght.utils.Stats;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.utils.SimpleLog;
import org.json.JSONObject;
import org.json.JSONArray;


import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwitterHandler {

    private static ConfigurationBuilder cb;
    private static TwitterStreamFactory streamFactory;
    private static TwitterStream stream;
    private static TwitterFactory factory;
    public static Twitter twitter;
    private static List<TwitterSubscription> subs;

    public static void start() {
        cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(Config.twitterConsumer)
          .setOAuthConsumerSecret(Config.twitterConsumerSecret)
          .setOAuthAccessToken(Config.twitterToken)
          .setOAuthAccessTokenSecret(Config.twitterTokenSecret);
        Configuration conf = cb.build();
        factory = new TwitterFactory(conf);
        streamFactory = new TwitterStreamFactory(conf);
        twitter = factory.getInstance();
        stream = streamFactory.getInstance();
        stream.addListener(new UserStreamAdapter() {
            @Override public void onStatus(Status status) {
                String image = null;
                MediaEntity[] md = status.getMediaEntities();
                if (md.length > 0) {
                    if (md[0].getType().equals("photo")) {
                        image = md[0].getMediaURLHttps();
                    }else if (md[0].getType().equals("animated_gif")) {
                        image = md[0].getMediaURLHttps();
                    }
                }

                for (TwitterSubscription ts : subs) {
                    if (ts.name.equals(status.getUser().getScreenName())) {
                        EmbedBuilder eb = new EmbedBuilder()
                                .setAuthor("@"+status.getUser().getScreenName(), "https://twitter.com/"+status.getUser().getScreenName()+"/status/"+status.getId(), "https://upload.wikimedia.org/wikipedia/de/thumb/9/9f/Twitter_bird_logo_2012.svg/154px-Twitter_bird_logo_2012.svg.png")
                                .setThumbnail(status.getUser().getProfileImageURL())
                                .setDescription(status.getText())
                                .setColor(new Color(29, 161, 242))
                                .setTimestamp(OffsetDateTime.now());
                        if(image != null) {
                            eb.setImage(image);
                        }
                        L1ght.jda.getGuildById(ts.guildId).getTextChannelById(ts.channelId).sendMessage(eb.build()).queue(e -> {
                            SimpleLog.getLog("Twitter-Stream").log(SimpleLog.Level.INFO, "Got a match on @"+ts.name+", at guild: "+e.getGuild().getName()+"("+e.getGuild().getId()+"), on channel: #"+e.getChannel().getName()+"("+e.getChannel().getId()+")");
                            Stats.twitterMatches++;
                        });
                    }
                }
            }
        });
        check();
        stream.user();
    }

    public static void check() {
        subs = new ArrayList<>();
        List<File> list = Arrays.asList(Config.getGuildDataFolder().listFiles());
        for (File f : list) {
            String jsons;
            try {
                jsons = new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())),"UTF-8");
            }catch (Exception e) {
                e.printStackTrace();
                jsons = "{}";
            }
            JSONObject json = new JSONObject(jsons);

            try{
                JSONArray feeds = json.getJSONArray("guildFeeds");
                for(Object o : feeds) {
                    JSONObject jo = (JSONObject) o;
                    if (jo.getString("type").equals(FeedsType.TWITTER.name())) {
                        subs.add(new TwitterSubscription(jo.getString("url"), f.getName().substring(0, f.getName().length()-5), jo.getString("channel")));
                    }
                }
            }catch(Exception ignored) {
            }
        }
        try {
            PagableResponseList<User> following = twitter.getFriendsList(twitter.getScreenName(), -1);
            List<String> followingString = new ArrayList<>();
            for (User u : following) {
                followingString.add(u.getScreenName());
            }
            List<String> tsFollowing = new ArrayList<>();
            for (TwitterSubscription ts : subs) {
                tsFollowing.add(ts.name);
                if(!followingString.contains(ts.name)) {
                    try{
                        TwitterHandler.twitter.createFriendship(ts.name);
                    }catch(TwitterException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
            for (String s : followingString) {
                if(!tsFollowing.contains(s)){
                    try{
                        TwitterHandler.twitter.destroyFriendship(s);
                    }catch(TwitterException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }catch(Exception ignored){

        }

    }
}
