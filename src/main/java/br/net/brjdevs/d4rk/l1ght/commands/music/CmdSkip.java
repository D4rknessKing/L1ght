package br.net.brjdevs.d4rk.l1ght.commands.music;

import br.net.brjdevs.d4rk.l1ght.music.AudioUtils;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;

public class CmdSkip implements Command{
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {

        if (AudioUtils.connections.get(event.getGuild().getId()) != null && AudioUtils.connections.get(event.getGuild().getId()).isRunning){
            if(AudioUtils.connections.get(event.getGuild().getId()).manager.getConnectedChannel() != event.getMember().getVoiceState().getChannel()){
                event.getChannel().sendMessage("**Error: **You're not connected to the voice channel i'm connected.").queue();
                return;
            }
            event.getChannel().sendMessage("**Skipping!**").queue();
            AudioUtils.connections.get(event.getGuild().getId()).player.stopTrack();
        }else{
            event.getChannel().sendMessage("**Error: **Nothing playing!").queue();
        }

    }

    @Override
    public String cmdName() {
        return "skip";
    }

    @Override
    public String cmdDescription() {
        return "Skips the current playing music.";
    }

    @Override
    public String cmdCategory() {
        return "Music";
    }

    @Override
    public List<L1ghtPerms> cmdPerm() {
        return Arrays.asList(L1ghtPerms.BASE, L1ghtPerms.MUSIC, L1ghtPerms.ADMIN);
    }

    @Override
    public List<Pair<String, Boolean>> cmdArgs() {
        return null;
    }
}
