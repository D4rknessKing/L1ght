package br.net.brjdevs.d4rk.l1ght.handlers.customboard;


import java.util.HashMap;

public class Customboard {

    private final String emote;
    private final String channelId;

    private int minimum;
    private HashMap<String, String> entries;

    public Customboard(String em, String ch, int min) {
        emote = em;
        channelId = ch;
        minimum = min;
    }

    public String getChannelID(){
        return channelId;
    }

    public String getEmote() {
        return emote;
    }

    public int getMinimum() {
        return minimum;
    }

    public Customboard setMinimum(int min) {
        minimum = min;
        return this;
    }

    public HashMap<String, String> getEntries() {
        return entries;
    }

    public Customboard setEntries(HashMap<String, String> hashMap) {
        entries = hashMap;
        return this;
    }
}
