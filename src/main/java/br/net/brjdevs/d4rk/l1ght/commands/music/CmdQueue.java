package br.net.brjdevs.d4rk.l1ght.commands.music;

import br.net.brjdevs.d4rk.l1ght.music.AudioUtils;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import java.util.List;

public class CmdQueue {

    @Command(name="queue", description = "Shows the music player queue for the current guild.", category = "Music", usage = "", perms = {L1ghtPerms.BASE})
    public static void run(GuildMessageReceivedEvent event, String[] args) {
        if (AudioUtils.connections.get(event.getGuild().getId()) != null && AudioUtils.connections.get(event.getGuild().getId()).isRunning){
            List<AudioTrack> queue = AudioUtils.connections.get(event.getGuild().getId()).queue;
            String finalQueue = "**Playing now: **`"+queue.get(0).getInfo().title+"`\n";
            int num = 1;
            for(AudioTrack at : queue) {
                if(num < 10){
                    if(queue.size() > num) {
                        finalQueue = finalQueue + "**["+num+"]:** `" + queue.get(num).getInfo().title + "`\n";
                        num++;
                    }else{
                        break;
                    }
                }else{
                    finalQueue = finalQueue + "**Warning: **Only showing 10 first songs from a total of `"+ String.valueOf(queue.size()-1) +"`";
                    break;
                }
            }
            MessageEmbed embed = new EmbedBuilder()
                    .setAuthor("Song queue of "+event.getGuild().getName(), null, event.getGuild().getIconUrl())
                    .setDescription(finalQueue)
                    .setFooter("Requested by: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl())
                    .setColor(event.getMember().getColor())
                    .build();
            event.getChannel().sendMessage(embed).queue();
        }else{
            event.getChannel().sendMessage("**Error: **Nothing playing!").queue();
        }
    }

}
