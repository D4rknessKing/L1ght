package br.net.brjdevs.d4rk.l1ght.commands.music;

import br.net.brjdevs.d4rk.l1ght.music.AudioUtils;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class CmdStop {

    @Command(name="stop", description = "Stops the music player.", category = "Music", usage = "", perms = {L1ghtPerms.BASE, L1ghtPerms.DJ})
    public static void run(GuildMessageReceivedEvent event, String[] args) {
        if (AudioUtils.connections.get(event.getGuild().getId()) != null && AudioUtils.connections.get(event.getGuild().getId()).isRunning){
            if(AudioUtils.connections.get(event.getGuild().getId()).manager.getConnectedChannel() != event.getMember().getVoiceState().getChannel()){
                event.getChannel().sendMessage("**Error: **You're not connected to the voice channel i'm connected.").queue();
                return;
            }
            event.getChannel().sendMessage("**Stopping!**").queue();
            AudioUtils.connections.get(event.getGuild().getId()).stopQueue();
        }else{
            event.getChannel().sendMessage("**Error: **Nothing playing!").queue();
        }
    }

}
