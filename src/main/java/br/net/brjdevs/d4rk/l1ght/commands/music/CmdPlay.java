package br.net.brjdevs.d4rk.l1ght.commands.music;

import br.net.brjdevs.d4rk.l1ght.music.*;
import br.net.brjdevs.d4rk.l1ght.utils.Config;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import javafx.util.Pair;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdPlay implements Command{
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {

        if (event.getMember().getVoiceState().getAudioChannel() == null) { event.getChannel().sendMessage("**Error: **You're not connected to any voice channel.").queue(); return; }

        if (AudioUtils.connections.get(event.getGuild().getId()) == null){
            if (event.getGuild().getSelfMember().hasPermission(event.getMember().getVoiceState().getChannel(), Permission.VOICE_CONNECT) && event.getGuild().getSelfMember().hasPermission(event.getMember().getVoiceState().getChannel(), Permission.VOICE_SPEAK)) {
                AudioPlayer player = AudioUtils.playerManager.createPlayer();
                AudioManager am = event.getGuild().getAudioManager();
                am.setSendingHandler(new AudioPlayerSendHandler(player));
                am.openAudioConnection(event.getMember().getVoiceState().getChannel());
                AudioQueue tr = new AudioQueue(am, player, event);
                AudioUtils.connections.put(event.getGuild().getId(), tr);
            }else{
                event.getChannel().sendMessage("**Error: **I have no permission to Join/Speak in that channel.").queue();
                return;
            }

        }else if(AudioUtils.connections.get(event.getGuild().getId()).manager.getConnectedChannel() != event.getMember().getVoiceState().getChannel()){
            event.getChannel().sendMessage("**Error: **You're not connected to the voice channel i am connected.").queue(); return;
        }

        if(args[0].equals("-f")) {
            if (args.length == 1) {
                String usage = "";
                if (this.cmdArgs() != null) {
                    for (Pair<String, Boolean> p : this.cmdArgs()) {
                        if (p.getValue()) {
                            usage = usage + " (" + p.getKey() + ") ";
                        } else {
                            usage = usage + " [" + p.getKey() + "] ";
                        }
                    }
                }
                usage = Config.prefix + this.cmdName() + " " + usage;
                event.getChannel().sendMessage("**Error: **Missing arguments!\n" +
                        "**Correct usage: **`" + usage + "`").queue();
                return;
            }

            String shit = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

            AudioLoadResultHandler alr = new AudioLoadResultHandler();
            Message msg = event.getChannel().sendMessage("**Searching...**").complete();
            try {
                AudioUtils.playerManager.loadItem("ytsearch:" + shit, alr).get();
            } catch (Exception e) {
                alr.result = "Error";
                alr.error = e.getMessage();
            }
            if (alr.result.equals("Playlist")) {
                int found = alr.playlist.getTracks().size();
                if(found >= 1) {
                    msg.editMessage("**Successfully added: **`"+alr.playlist.getTracks().get(0).getInfo().title+"`**  to the queue.**").queue();
                    AudioUtils.connections.get(event.getGuild().getId()).toQueue(alr.playlist.getTracks().get(0));
                } else {
                    msg.editMessage("**Error: **Couldn't find song!").queue();
                    canceled(event);
                }
            }else{
                msg.editMessage("**Error: **" + alr.error).queue();
                canceled(event);
            }

        }else if(args[0].startsWith("https")) {

            if(args[0].startsWith("https://www.youtube.com/playlist")) {
                Message msg = event.getChannel().sendMessage("**Playlist Detected!** Loading..").complete();

                AudioLoadResultHandler alr = new AudioLoadResultHandler();
                try {
                    AudioUtils.playerManager.loadItem(args[0], alr).get();
                } catch (Exception e) {
                    alr.result = "Error";
                    alr.error = e.getMessage();
                }
                if (alr.result.equals("Playlist")) {
                    int found = alr.playlist.getTracks().size();
                    if(found >= 1) {
                        msg.editMessage("**Successfully added: **`"+alr.playlist.getTracks().size()+" songs`**  to the queue.**").queue();
                        AudioUtils.connections.get(event.getGuild().getId()).toQueue(alr.playlist);
                    } else {
                        msg.editMessage("**Error: **Couldn't find song!").queue();
                        canceled(event);
                    }
                }else{
                    msg.editMessage("**Error: **" + alr.error).queue();
                    canceled(event);
                }
            }else if(args[0].startsWith("https://www.youtube.com/watch")){
                Message msg = event.getChannel().sendMessage("**Song Detected!** Loading..").complete();

                AudioLoadResultHandler alr = new AudioLoadResultHandler();
                try {
                    AudioUtils.playerManager.loadItem(args[0], alr).get();
                } catch (Exception e) {
                    alr.result = "Error";
                    alr.error = e.getMessage();
                }
                if (alr.result.equals("Track")) {
                    msg.editMessage("**Successfully added: **`"+alr.track.getInfo().title+"`**  to the queue.**").queue();
                    AudioUtils.connections.get(event.getGuild().getId()).toQueue(alr.track);
                }else{
                    msg.editMessage("**Error: **" + alr.error).queue();
                    canceled(event);
                }
            }else{
                event.getChannel().sendMessage("**Error: **Unknown type of link!").complete();
                canceled(event);
            }


        }else{

            String shit = String.join(" ", args);

            AudioLoadResultHandler alr = new AudioLoadResultHandler();
            Message msg = event.getChannel().sendMessage("**Searching...**").complete();
            try {
                AudioUtils.playerManager.loadItem("ytsearch:" + shit, alr).get();
            } catch (Exception e) {
                alr.result = "Error";
                alr.error = e.getMessage();
            }
            if (alr.result.equals("Playlist")) {
                int found = alr.playlist.getTracks().size();
                if (found >= 3) new SongSearchQuery(event, alr.playlist.getTracks().get(0), alr.playlist.getTracks().get(1), alr.playlist.getTracks().get(2), msg).start();
                else if (found == 2) new SongSearchQuery(event, alr.playlist.getTracks().get(0), alr.playlist.getTracks().get(1), null, msg).start();
                else if (found == 1) new SongSearchQuery(event, alr.playlist.getTracks().get(0), null, null, msg).start();
                else {
                    msg.editMessage("**Error: **Couldn't find song!").queue();
                    canceled(event);
                }
            }else{
                msg.editMessage("**Error: **" + alr.error).queue();
                canceled(event);
            }
        }
    }

    public static void canceled(GuildMessageReceivedEvent event) {
        if(AudioUtils.connections.get(event.getGuild().getId()) != null && !AudioUtils.connections.get(event.getGuild().getId()).isRunning){
            AudioUtils.connections.get(event.getGuild().getId()).manager.closeAudioConnection();
            AudioUtils.connections.remove(event.getGuild().getId());
        }
    }

    @Override
    public String cmdName() {
        return "play";
    }

    @Override
    public String cmdDescription() {
        return "Plays the given music.";
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
        List<Pair<String, Boolean>> list = new ArrayList<>();
        list.add(new Pair<>("-f", false));
        list.add(new Pair<>("Song", true));
        return list;
    }
}
