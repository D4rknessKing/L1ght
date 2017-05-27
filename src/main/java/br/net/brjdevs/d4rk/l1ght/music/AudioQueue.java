package br.net.brjdevs.d4rk.l1ght.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.ArrayList;
import java.util.List;

public class AudioQueue extends AudioEventAdapter {

    public AudioManager manager;
    public AudioPlayer player;
    public GuildMessageReceivedEvent event;

    public List<AudioTrack> queue = new ArrayList<>();
    public boolean isRunning = false;

    public AudioQueue(AudioManager am, AudioPlayer pl, GuildMessageReceivedEvent ch) {
        manager = am;
        player = pl;
        event = ch;
        player.addListener(this);
    }

    private void startQueue() {
        isRunning = true;
        player.startTrack(queue.get(0), false);
    }

    public void stopQueue() {
        int size = queue.size();
        while (queue.size() > 1) {
            queue.remove(1);
        }
        player.stopTrack();
    }

    public void toQueue(AudioTrack track) {
        queue.add(track);
        if(!isRunning) {
            startQueue();
        }
    }

    public void toQueue(AudioPlaylist track) {
        queue.addAll(track.getTracks());
        if(!isRunning) {
            startQueue();
        }
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        event.getChannel().sendMessage("**Now playing: **"+track.getInfo().title).queue();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if(queue.get(0) != null) queue.remove(0);
        if (endReason.mayStartNext || endReason == AudioTrackEndReason.STOPPED) {
            if(queue.size() > 0){
                player.startTrack(queue.get(0), false);
            }else{
                event.getChannel().sendMessage("**Disconnecting!** End of the queue.").queue();
                isRunning = false;
                player.destroy();
                manager.closeAudioConnection();
                AudioUtils.connections.remove(event.getGuild().getId());
            }
        }else{
            event.getChannel().sendMessage("**Disconnecting!**").queue();
            isRunning = false;
            player.destroy();
            manager.closeAudioConnection();
            AudioUtils.connections.remove(event.getGuild().getId());
        }
    }

}
