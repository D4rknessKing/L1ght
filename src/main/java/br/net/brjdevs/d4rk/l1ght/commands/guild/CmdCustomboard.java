package br.net.brjdevs.d4rk.l1ght.commands.guild;

import br.net.brjdevs.d4rk.l1ght.handlers.customboard.Customboard;
import br.net.brjdevs.d4rk.l1ght.handlers.customboard.Emoji;
import br.net.brjdevs.d4rk.l1ght.handlers.data.GuildDataHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.feeds.FeedSubscription;
import br.net.brjdevs.d4rk.l1ght.utils.FeedsType;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.Paste;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import javafx.util.Pair;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CmdCustomboard implements Command{
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {

        HashMap<String, Customboard> customboards = GuildDataHandler.loadGuildCustomboard(event.getGuild().getId());

        if(args.length > 0) {
            if(args[0].toLowerCase().equals("create")){
                if(args.length < 3) {
                    event.getChannel().sendMessage("**Error: **Missing arguments!").queue(); return;
                }
                if(customboards.keySet().contains(args[1])){
                    event.getChannel().sendMessage("**Error: **There's already a customboard with this name.").queue(); return;
                }
                if(!Emoji.getEmojiList().contains(args[2])){
                    event.getChannel().sendMessage("**Error: **Unknown emoji.").queue(); return;
                }
                TextChannel chn = null;

                try{
                    chn = event.getJDA().getTextChannelById(args[3]);
                }catch(Exception ignored){}
                List<TextChannel> ata = event.getJDA().getTextChannelsByName(args[3], false);
                if (ata.size() >= 1) {
                    chn = ata.get(0);
                }
                if (event.getMessage().getMentionedChannels().size() >= 1) {
                    chn = event.getMessage().getMentionedChannels().get(0);
                }
                if(chn == null){
                    event.getChannel().sendMessage("**Error: **Couldn't find a channel that matches the arguments.").queue(); return;
                }
                if(args.length < 5){
                    customboards.put(args[1], new Customboard(args[2], chn.getId(), 1));
                }else{
                    int min;
                    try{
                        customboards.put(args[1], new Customboard(args[2], chn.getId(), Integer.valueOf(args[4])));
                    }catch(Exception e){
                        event.getChannel().sendMessage("**Error: **"+args[4]+" is not a valid number.").queue();
                    }
                }
                GuildDataHandler.saveGuildCustomboard(event.getGuild().getId(), customboards);
                event.getChannel().sendMessage("**Successfully created customboard!**").queue();
            }else if(args[0].toLowerCase().equals("remove")){
                if(args.length < 2){
                    event.getChannel().sendMessage("**Error: **Missing arguments!").queue(); return;
                }
                if(!customboards.keySet().contains(args[1])){
                    event.getChannel().sendMessage("**Error: **There's no customboard with this name.").queue(); return;
                }else{
                    customboards.remove(args[1]);
                    GuildDataHandler.saveGuildCustomboard(event.getGuild().getId(), customboards);
                    event.getChannel().sendMessage("**Successfully removed customboard!**").queue();
                }
            }else{
                event.getChannel().sendMessage("**Error: **Invalid option `" + args[0] + "`, please use `enable` or `disable`.").queue();
            }
        }else{
            if(customboards.size() < 1) {
                event.getChannel().sendMessage("**Error: ** There's no available customboards in this guild!").queue();
                return;
            }
            String cb = "";
            String cbEx = "";
            for (String name : customboards.keySet()) {
                if(cb.length() > 1800) {
                    cbEx = cbEx+customboards.get(name).getEmote()+" - **"+name+"** - <#"+customboards.get(name).getChannelID()+">\n";
                }else{
                    cb = cb+customboards.get(name).getEmote()+" - **"+name+"** - <#"+customboards.get(name).getChannelID()+">\n";
                }
            }
            if(!cbEx.equals("")) {
                cb = cb + "**Complete list: **"+
                        Paste.toHastebin(cb +cbEx);
            }
            MessageEmbed embed = new EmbedBuilder()
                    .setAuthor("Available customboards for: "+event.getGuild().getName() ,null, event.getGuild().getIconUrl())
                    .setDescription(cb)
                    .setFooter("Requested by: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl())
                    .setColor(event.getMember().getColor())
                    .build();
            event.getChannel().sendMessage(embed).queue();
        }


    }

    @Override
    public String cmdName() {
        return "customboard";
    }

    @Override
    public String cmdDescription() {
        return "With the customboard command, you can setup a reaction board of your choice using the emote that you want.";
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
        list.add(new Pair<>("create/remove", false));
        list.add(new Pair<>("name", false));
        list.add(new Pair<>("emote", false));
        list.add(new Pair<>("channel", false));
        list.add(new Pair<>("min", false));
        return list;
    }
}
