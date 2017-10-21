package br.net.brjdevs.d4rk.l1ght.commands.guild;

import br.net.brjdevs.d4rk.l1ght.handlers.data.GuildDataHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.feeds.FeedSubscription;
import br.net.brjdevs.d4rk.l1ght.handlers.feeds.FeedsHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.feeds.TwitterHandler;
import br.net.brjdevs.d4rk.l1ght.utils.FeedsType;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.Paste;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import com.mashape.unirest.http.Unirest;
import javafx.util.Pair;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONObject;
import twitter4j.ResponseList;
import twitter4j.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CmdFeeds implements Command {
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {

        List<FeedSubscription> feedsList = GuildDataHandler.loadGuildFeeds(event.getGuild().getId());
        List<String> registered = new ArrayList<>();
        for (FeedSubscription fs : feedsList){
            registered.add(fs.name);
        }

        if (args[0].toLowerCase().equals("add")) {
            if (args.length < 3) {
                event.getChannel().sendMessage("**Error: ** Missing arguments!").queue();
                return;
            }

            FeedsType type;
            try {
                type = FeedsType.valueOf(args[2].toUpperCase());
            }catch(Exception e) {
                event.getChannel().sendMessage("**Error: ** Unknown type!").queue();
                return;
            }

            if(registered.contains(args[1])){
                event.getChannel().sendMessage("**Error: ** There's already a feed with this name!").queue();
            }else{
                if(type == FeedsType.TWITTER) {
                    try{
                        ResponseList<User> list = TwitterHandler.twitter.users().searchUsers(args[3], 1);
                        FeedsHandler.addFeed(event.getGuild().getId(), event.getChannel().getId(), type, args[1], list.get(0).getScreenName());
                        event.getChannel().sendMessage("**Successfully added twitter feed!**").queue();
                    }catch(Exception e){
                        event.getChannel().sendMessage("**Error: **There's no twitter user with this name!").queue();
                    }
                }else if(type == FeedsType.REDDIT) {
                    try{
                        JSONObject json = Unirest
                                .get("https://www.reddit.com/r/"+args[3].toLowerCase()+"/.json")
                                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
                                .asJson().getBody().getObject();
                        String test = json.getString("kind");
                        FeedsHandler.addFeed(event.getGuild().getId(), event.getChannel().getId(), type, args[1], args[3].toLowerCase());
                        event.getChannel().sendMessage("**Successfully added reddit feed!**").queue();
                    }catch(Exception e){
                        event.getChannel().sendMessage("**Error: **There's no subreddit with this name!").queue();
                    }
                }

            }

        } else if (args[0].toLowerCase().equals("remove")) {
            if (args.length < 1) {
                event.getChannel().sendMessage("**Error: ** Missing arguments!").queue();  return;
            }

            if(!registered.contains(args[1])){
                event.getChannel().sendMessage("**Error: ** There's no available feed with this name!").queue();
            }else{
                FeedsHandler.removeFeed(event.getGuild().getId(), event.getChannel().getId(), args[1]);
                event.getChannel().sendMessage("**Successfully removed feed!**").queue();
            }

        } else if (args[0].toLowerCase().equals("list")){

            if(registered.size() < 1) {
                event.getChannel().sendMessage("**Error: ** There's no available feeds in this guild!").queue();
                return;
            }

            String feeds = "";
            String feedsExceded = "";
            for (FeedSubscription fs : feedsList) {
                if(feeds.length() > 1800) {
                    if (fs.type == FeedsType.TWITTER) {
                        feedsExceded = feedsExceded+"Twitter - **"+fs.name+"** - [@"+fs.url+"](https://twitter.com/"+fs.url+") - <#"+fs.channelId+">\n";
                    }else if (fs.type == FeedsType.REDDIT) {
                        feedsExceded = feedsExceded+"Reddit - **"+fs.name+"** - [/r/"+fs.url+"](https://reddit.com/r/"+fs.url+") - <#"+fs.channelId+">\n";
                    }
                }else{
                    if (fs.type == FeedsType.TWITTER) {
                        feeds = feeds+"<:twitter:353686862570717194> - **"+fs.name+"** - [@"+fs.url+"](https://twitter.com/"+fs.url+") - <#"+fs.channelId+">\n";
                    }else if (fs.type == FeedsType.REDDIT) {
                        feeds = feeds+"<:reddit:353934604677152775> - **"+fs.name+"** - [/r/"+fs.url+"](https://reddit.com/r/"+fs.url+") - <#"+fs.channelId+">\n";
                    }
                }
            }
            if(!feedsExceded.equals("")) {
                feeds = feeds + "**Complete list: **"+
                        Paste.toHastebin(
                        feeds
                                .replace("<:twitter:353686862570717194>", "Twitter")
                                .replace("<:reddit:353934604677152775>", "Reddit")
                                +feedsExceded
                        );
            }
            MessageEmbed embed = new EmbedBuilder()
                    .setAuthor("Available feeds for: "+event.getGuild().getName() ,null, event.getGuild().getIconUrl())
                    .setDescription(feeds)
                    .setFooter("Requested by: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl())
                    .setColor(event.getMember().getColor())
                    .build();
            event.getChannel().sendMessage(embed).queue();

        }else{
            event.getChannel().sendMessage("**Error: **Invalid option `" + args[0] + "`, please use `list`, `add`, or `remove`.").queue();
        }

    }


    @Override
    public String cmdName() {
        return "feeds";
    }

    @Override
    public String cmdDescription() {
        return "With the `feeds` command you can setup feeds from a variety of different sites.\nCurrently available feeds: Twitter, Reddit";
    }

    @Override
    public String cmdCategory() {
        return "Guild";
    }

    @Override
    public List<L1ghtPerms> cmdPerm() {
        return Arrays.asList(L1ghtPerms.BASE, L1ghtPerms.GUILD);
    }

    @Override
    public List<Pair<String, Boolean>> cmdArgs() {
        List<Pair<String, Boolean>> list = new ArrayList<>();
        list.add(new Pair<>("add/remove/list", true));
        list.add(new Pair<>("name", false));
        list.add(new Pair<>("type", false));
        list.add(new Pair<>("url", false));
        return list;
    }
}
