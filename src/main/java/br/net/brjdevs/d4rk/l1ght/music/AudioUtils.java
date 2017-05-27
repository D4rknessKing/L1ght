package br.net.brjdevs.d4rk.l1ght.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AudioUtils {

    public static AudioPlayerManager playerManager = new DefaultAudioPlayerManager();

    public static HashMap<String, AudioQueue> connections = new HashMap<>();
    public static List<SongSearchQuery> queryList = new ArrayList<>();

    public static void init() {
        AudioSourceManagers.registerRemoteSources(playerManager);
    }


}
