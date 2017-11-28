package br.net.brjdevs.d4rk.l1ght.handlers.feeds;

import br.net.brjdevs.d4rk.l1ght.L1ght;
import br.net.brjdevs.d4rk.l1ght.utils.Config;
import br.net.brjdevs.d4rk.l1ght.utils.FeedsType;
import br.net.brjdevs.d4rk.l1ght.utils.Paste;
import br.net.brjdevs.d4rk.l1ght.utils.Stats;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.utils.SimpleLog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class RedditHandler {

    private static List<String> entries = new ArrayList<>();
    private static List<RedditSubscription> subs = new ArrayList<>();
    private static List<String> subsString = new ArrayList<>();

    public static void start() {

        SimpleLog log = SimpleLog.getLog("Reddit-Stream");
        log.log(SimpleLog.Level.INFO,"Starting thread!");
        new Thread(() -> {
            log.log(SimpleLog.Level.INFO,"Started!");

            check();

            Timer timer = new Timer();
            TimerTask myTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        if(subsString.size() == 0) {
                            return;
                        }
                        List<String> newEntries = new ArrayList<>();

                        JSONObject json = Unirest
                                .get("https://www.reddit.com/r/" + String.join("%2B", subsString) + "/new/.json?limit=100")
                                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
                                .asJson().getBody().getObject();
                        for (Object o : json.getJSONObject("data").getJSONArray("children")) {
                            JSONObject jo = ((JSONObject) o).getJSONObject("data");
                            newEntries.add("https://www.reddit.com" + jo.getString("permalink") + ".json");
                        }

                        for (String s : newEntries) {
                            if (!entries.contains(s)) {
                                for (RedditSubscription r : subs) {
                                    if (s.split("/r/")[1].split("/")[0].equalsIgnoreCase(r.subreddit)) {
                                        JSONArray ja = Unirest
                                                .get(s)
                                                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
                                                .asJson()
                                                .getBody()
                                                .getArray();
                                        JSONObject jo = ja.getJSONObject(0).getJSONObject("data").getJSONArray("children").getJSONObject(0).getJSONObject("data");

                                        if((System.currentTimeMillis() / 1000) - jo.getLong("created_utc") < 150) {
                                            EmbedBuilder embed = new EmbedBuilder()
                                                    .setAuthor(jo.getString("title"), "https://www.reddit.com" + jo.getString("permalink"), "https://media.glassdoor.com/sqll/796358/reddit-squarelogo-1490630845152.png")
                                                    .setTitle("/u/" + jo.getString("author"), "http://reddit.com/u/" + jo.getString("author"))
                                                    .setFooter("/r/" + r.subreddit, null)
                                                    .setTimestamp(Instant.ofEpochSecond(jo.getLong("created_utc")).atZone(ZoneId.of("UTC")))
                                                    .setColor(new Color(255, 100, 0));
                                            String description = jo.getString("selftext");
                                            if (description.length() < 2) {
                                                String lnk = jo.getString("url").replace("http://", "https://");
                                                String ex = lnk.substring(lnk.lastIndexOf("."));
                                                if (ex.equals(".png") || ex.equals(".jpg") || ex.equals(".jpeg") || ex.equals(".gif")) {
                                                    embed.setImage(lnk);
                                                } else if (ex.equals(".gifv")) {
                                                    embed.setImage(lnk.replace(".gifv", ".gif"));
                                                } else if (lnk.startsWith("https://imgur.com/")) {
                                                    if (lnk.startsWith("https://imgur.com/a/")) {
                                                        try {
                                                            String hash = lnk.replace("https://imgur.com/a/", "").replace("/", "");
                                                            JSONObject j = Unirest.get("https://api.imgur.com/3/album/" + hash + "/images").header("Authorization", "Client-Id 6ae90a0a95800fa").asJson().getBody().getObject();
                                                            embed.setImage(j.getJSONArray("data").getJSONObject(0).getString("link"));
                                                            description = description + "\n**More images on: **" + lnk;
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                            description = description + "\n" + lnk;
                                                        }
                                                    } else if (lnk.startsWith("https://imgur.com/gallery/")) {
                                                        description = description + "\n" + jo.getString("url");
                                                    } else {
                                                        embed.setImage(lnk.substring(0, lnk.length()) + ".png");
                                                    }
                                                } else if (lnk.startsWith("https://gfycat.com/")) {
                                                    String ata = lnk.replace("https://gfycat", "https://thumbs.gfycat");
                                                    embed.setImage(ata.substring(0, ata.length()-1) + "-size_restricted.gif");
                                                } else {
                                                    description = description + "\n" + jo.getString("url");
                                                }
                                            }
                                            if (description.length() > 2046) {
                                                embed.setDescription(Paste.toHastebin(description));
                                            } else {
                                                embed.setDescription(description);
                                            }
                                            L1ght.jda.getGuildById(r.guildId).getTextChannelById(r.channelId).sendMessage(embed.build()).queue(e -> {
                                                log.log(SimpleLog.Level.INFO, "Got a match on /r/" + r.subreddit + ", at guild: " + e.getGuild().getName() + "(" + e.getGuild().getId() + "), on channel: #" + e.getChannel().getName() + "(" + e.getChannel().getId() + ")");
                                                Stats.redditMatches++;
                                            });
                                        }
                                    }
                                }
                            }
                        }

                        entries = newEntries;
                    }catch(UnirestException e){
                        log.log(SimpleLog.Level.WARNING, "Exception while requesting from Reddit: "+e.getMessage());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            };

            timer.schedule(myTask, 5000, 5000);

        }).start();

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
                    if (jo.getString("type").equals(FeedsType.REDDIT.name())) {
                        subs.add(new RedditSubscription(jo.getString("url"), f.getName().substring(0, f.getName().length()-5), jo.getString("channel")));
                        subsString.add(jo.getString("url"));
                    }
                }
            }catch(Exception ignored) {}
        }

        if(subsString.size() == 0) {
            return;
        }

        try{
            entries = new ArrayList<>();
            JSONObject json = Unirest
                    .get("https://www.reddit.com/r/"+String.join("%2B", subsString)+"/new/.json?limit=100")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
                    .asJson().getBody().getObject();
            for (Object o : json.getJSONObject("data").getJSONArray("children")) {
                JSONObject jo = ((JSONObject) o).getJSONObject("data");
                entries.add("https://www.reddit.com" + jo.getString("permalink") + ".json");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
