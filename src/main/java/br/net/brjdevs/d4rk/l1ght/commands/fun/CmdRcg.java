package br.net.brjdevs.d4rk.l1ght.commands.fun;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import com.mashape.unirest.http.Unirest;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import java.awt.*;
import java.util.Random;

public class CmdRcg {

    @Command(name="rcg", description = "Generates a comic using Explosm's Random Comic Generator.", category = "Fun", usage="", perms = {L1ghtPerms.BASE})
    public static void run(GuildMessageReceivedEvent event, String[] args) {
        try{
            String rcg = Unirest.get("http://explosm.net/rcg/").asString().getBody();
            int indexStart = rcg.lastIndexOf("<div id=\"rcg-comic\">\n" +
                    "<img src=\"")+33;
            int indexEnd = rcg.lastIndexOf("\" alt=\"\" />");

            Random rand = new Random();
            Color color = null;
            if(rand.nextBoolean()){ color = Color.GREEN;
            }else{ color = Color.BLUE; }

            MessageEmbed embed = new EmbedBuilder()
                    .setAuthor("Random Comic, by: Explosm", "http://explosm.net/rcg/" , "https://yt3.ggpht.com/-nd8LyC3NoE4/AAAAAAAAAAI/AAAAAAAAAAA/-ZE_L6u0Q1Y/s900-c-k-no-mo-rj-c0xffffff/photo.jpg")
                    .setImage("https://"+rcg.substring(indexStart, indexEnd))
                    .setColor(color)
                    .setFooter("Requested by: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl())
                    .build();

            event.getChannel().sendMessage(embed).queue();
        }catch(Exception e){
            event.getChannel().sendMessage("**An unknown error has occurred while doing the HTTP request to the Explosm Random Comic Generator**").queue();
            e.printStackTrace();
        }
    }

}
