package br.net.brjdevs.d4rk.l1ght.commands.guild;

import br.net.brjdevs.d4rk.l1ght.handlers.customboard.Customboard;
import br.net.brjdevs.d4rk.l1ght.handlers.customboard.Emoji;
import br.net.brjdevs.d4rk.l1ght.handlers.data.GuildDataHandler;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.Paste;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import br.net.brjdevs.d4rk.l1ght.utils.command.SubCommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import java.util.HashMap;
import java.util.List;

public class CmdCustomboard {

    @Command(name="customboard", description = "With the customboard command, you can setup a reaction board of your choice using the emote that you want.", category = "Guild", usage="", perms = {L1ghtPerms.BASE})
    public static void run(GuildMessageReceivedEvent event, String[] args) {

        HashMap<String, Customboard> customboards = GuildDataHandler.loadGuildCustomboard(event.getGuild().getId());
        if(customboards.size() < 1) { event.getChannel().sendMessage("**Error: ** There's no available customboards in this guild!").queue(); return;}

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

    @SubCommand(name="create", description = "", usage = "(name) (emote) (channel) [min]", perms = {L1ghtPerms.BASE, L1ghtPerms.GUILD})
    public static void create(GuildMessageReceivedEvent event, String[] args) {

        HashMap<String, Customboard> customboards = GuildDataHandler.loadGuildCustomboard(event.getGuild().getId());

        if(customboards.keySet().contains(args[0])){
            event.getChannel().sendMessage("**Error: **There's already a customboard with this name.").queue(); return;
        }
        if(!Emoji.getEmojiList().contains(args[1])){
            event.getChannel().sendMessage("**Error: **Unknown emoji.").queue(); return;
        }

        TextChannel chn = null;
        try{
            chn = event.getJDA().getTextChannelById(args[2]);
        }catch(Exception ignored){}
        List<TextChannel> ata = event.getJDA().getTextChannelsByName(args[2], false);
        if (ata.size() >= 1) {
            chn = ata.get(0);
        }
        if (event.getMessage().getMentionedChannels().size() >= 1) {
                    chn = event.getMessage().getMentionedChannels().get(0);
        }
        if(chn == null){
            event.getChannel().sendMessage("**Error: **Couldn't find a channel that matches the arguments.").queue(); return;
        }
        if(args.length < 4){
            customboards.put(args[0], new Customboard(args[1], chn.getId(), 1));
        }else{
            int min;
            try{
                customboards.put(args[0], new Customboard(args[1], chn.getId(), Integer.valueOf(args[3])));
            }catch(Exception e){
                event.getChannel().sendMessage("**Error: **"+args[3]+" is not a valid number.").queue();
                e.printStackTrace();
                return;
            }
        }
        GuildDataHandler.saveGuildCustomboard(event.getGuild().getId(), customboards);
        event.getChannel().sendMessage("**Successfully created customboard!**").queue();

    }

    @SubCommand(name="remove", description = "", usage = "(name)", perms = {L1ghtPerms.BASE, L1ghtPerms.GUILD})
    public static void remove(GuildMessageReceivedEvent event, String[] args){

        HashMap<String, Customboard> customboards = GuildDataHandler.loadGuildCustomboard(event.getGuild().getId());

        if(!customboards.keySet().contains(args[0])){
            event.getChannel().sendMessage("**Error: **There's no customboard with this name.").queue();
        }else{
            customboards.remove(args[0]);
            GuildDataHandler.saveGuildCustomboard(event.getGuild().getId(), customboards);
            event.getChannel().sendMessage("**Successfully removed customboard!**").queue();
        }

    }

}
