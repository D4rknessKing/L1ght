package br.net.brjdevs.d4rk.l1ght.listeners;

import br.net.brjdevs.d4rk.l1ght.commands.music.CmdSkip;
import br.net.brjdevs.d4rk.l1ght.music.AudioUtils;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildVoiceChannelListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        if(AudioUtils.connections.get(event.getGuild().getId()) != null && AudioUtils.connections.get(event.getGuild().getId()).manager.getConnectedChannel() == event.getChannelLeft()){
            if(AudioUtils.connections.get(event.getGuild().getId()).skipVotes.contains(event.getMember().getUser().getId())){
                AudioUtils.connections.get(event.getGuild().getId()).skipVotes.remove(event.getMember().getUser().getId());
                CmdSkip.check(event.getGuild(), event.getMember());
            }
        }
    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        if(AudioUtils.connections.get(event.getGuild().getId()) != null && AudioUtils.connections.get(event.getGuild().getId()).manager.getConnectedChannel() == event.getChannelLeft()){
            if(AudioUtils.connections.get(event.getGuild().getId()).skipVotes.contains(event.getMember().getUser().getId())){
                AudioUtils.connections.get(event.getGuild().getId()).skipVotes.remove(event.getMember().getUser().getId());
                CmdSkip.check(event.getGuild(), event.getMember());
            }
        }
    }
}
