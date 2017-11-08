package br.net.brjdevs.d4rk.l1ght.commands.info;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;
import java.awt.*;

public class CmdQuote {

    @Command(name="quote", description = "Quotes the given message.", category = "Info", usage = "(ID)", perms = {L1ghtPerms.BASE})
    public static void run(GuildMessageReceivedEvent event, String[] args) {

        String shit = String.join(" ",args);


        Message mem;
        try{
            mem = event.getChannel().getMessageById(shit).complete();
        }catch(ErrorResponseException e){
            event.getChannel().sendMessage("**Error: **Couldn't find a message that matches the arguments in this channel.").queue();
            return;
        }

        Color color;
        if(event.getGuild().getMember(mem.getAuthor()) != null) {
            color = event.getGuild().getMember(mem.getAuthor()).getColor();
        }else{
            color = event.getMember().getColor();
        }


        MessageEmbed embed = new EmbedBuilder()
                .setAuthor("Message by: "+mem.getAuthor().getName(), null, mem.getAuthor().getAvatarUrl())
                .setDescription(mem.getRawContent())
                .setFooter("Message sent on " + "#" + mem.getChannel().getName(), null)
                .setTimestamp(mem.getCreationTime())
                .setColor(color)
                .build();

        event.getChannel().sendMessage(embed).queue();

    }

}
