package br.net.brjdevs.d4rk.l1ght.handlers.feeds;


public class TwitterSubscription {

    public String name;
    public String guildId;
    public String channelId;

    public TwitterSubscription(String n, String g, String c) {
        name = n;
        guildId = g;
        channelId = c;
    }

}
