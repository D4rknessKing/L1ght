package br.net.brjdevs.d4rk.l1ght.commands.guild;

import br.net.brjdevs.d4rk.l1ght.handlers.data.GuildDataHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.feeds.FeedSubscription;
import br.net.brjdevs.d4rk.l1ght.handlers.feeds.FeedsHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.feeds.TwitterHandler;
import br.net.brjdevs.d4rk.l1ght.utils.FeedsType;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.Paste;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import br.net.brjdevs.d4rk.l1ght.utils.command.SubCommand;
import com.mashape.unirest.http.Unirest;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONObject;
import twitter4j.ResponseList;
import twitter4j.User;
import java.util.ArrayList;
import java.util.List;

public class CmdFeeds {

    @Command(name="feeds", description = "With the `feeds` command you can setup feeds from a variety of different sites.\nCurrently available feeds: Twitter, Reddit", category = "Guild", usage="", perms = {L1ghtPerms.BASE})
    public static void run(GuildMessageReceivedEvent event, String[] args) {

        List<FeedSubscription> feedsList = GuildDataHandler.loadGuildFeeds(event.getGuild().getId());
        List<String> registered = new ArrayList<>();
        for (FeedSubscription fs : feedsList){
            registered.add(fs.name);
        }

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

    }

    @SubCommand(name="create", description = "", usage = "(name) (type) (url)", perms = {L1ghtPerms.BASE, L1ghtPerms.GUILD})
    public static void create(GuildMessageReceivedEvent event, String[] args){

        List<FeedSubscription> feedsList = GuildDataHandler.loadGuildFeeds(event.getGuild().getId());
        List<String> registered = new ArrayList<>();
        for (FeedSubscription fs : feedsList){
            registered.add(fs.name);
        }

        FeedsType type;
        try {
            type = FeedsType.valueOf(args[1].toUpperCase());
        }catch(Exception e) {
            event.getChannel().sendMessage("**Error: ** Unknown type!").queue();
            return;
        }

        if(registered.contains(args[0])){
            event.getChannel().sendMessage("**Error: ** There's already a feed with this name!").queue();
        }else{
            if(type == FeedsType.TWITTER) {
                try{
                    ResponseList<User> list = TwitterHandler.twitter.users().searchUsers(args[2], 1);
                    FeedsHandler.addFeed(event.getGuild().getId(), event.getChannel().getId(), type, args[0], list.get(0).getScreenName());
                    event.getChannel().sendMessage("**Successfully added twitter feed!**").queue();
                }catch(Exception e){
                    event.getChannel().sendMessage("**Error: **There's no twitter user with this name!").queue();
                }
            }else if(type == FeedsType.REDDIT) {
                try{
                    JSONObject json = Unirest
                            .get("https://www.reddit.com/r/"+args[2].toLowerCase()+"/.json")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
                            .asJson().getBody().getObject();
                    String test = json.getString("kind");
                    FeedsHandler.addFeed(event.getGuild().getId(), event.getChannel().getId(), type, args[0], args[2].toLowerCase());
                    event.getChannel().sendMessage("**Successfully added reddit feed!**").queue();
                }catch(Exception e){
                    event.getChannel().sendMessage("**Error: **There's no subreddit with this name!").queue();
                }
            }
        }

    }

    @SubCommand(name="remove", description = "", usage = "(name)", perms = {L1ghtPerms.BASE, L1ghtPerms.GUILD})
    public static void remove(GuildMessageReceivedEvent event, String[] args){

        List<FeedSubscription> feedsList = GuildDataHandler.loadGuildFeeds(event.getGuild().getId());
        List<String> registered = new ArrayList<>();
        for (FeedSubscription fs : feedsList){
            registered.add(fs.name);
        }

        if(!registered.contains(args[0])){
            event.getChannel().sendMessage("**Error: ** There's no available feed with this name!").queue();
        }else{
            FeedsHandler.removeFeed(event.getGuild().getId(), event.getChannel().getId(), args[0]);
            event.getChannel().sendMessage("**Successfully removed feed!**").queue();
        }

    }

}
