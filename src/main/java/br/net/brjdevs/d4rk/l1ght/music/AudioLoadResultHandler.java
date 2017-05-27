package br.net.brjdevs.d4rk.l1ght.music;

import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class AudioLoadResultHandler implements com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler {

    public AudioTrack track;
    public AudioPlaylist playlist;
    public String result;
    public String error;

    @Override
    public void trackLoaded(AudioTrack audioTrack) {
        result = "Track";
        error = "Unexpected Track";
        track = audioTrack;
    }

    @Override
    public void playlistLoaded(AudioPlaylist audioPlaylist) {
        result = "Playlist";
        error = "Unexpected Playlist";
        playlist = audioPlaylist;
    }

    @Override
    public void noMatches() {
        result = "None";
        error = "Couldn't find any song!";
    }

    @Override
    public void loadFailed(FriendlyException e) {
        result = "Error";
        error = e.getMessage();
    }
}
