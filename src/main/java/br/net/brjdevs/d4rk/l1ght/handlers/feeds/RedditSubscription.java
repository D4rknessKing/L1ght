package br.net.brjdevs.d4rk.l1ght.handlers.feeds;

public class RedditSubscription {

    public String subreddit;
    public String guildId;
    public String channelId;

    public RedditSubscription(String n, String g, String c) {
        subreddit = n;
        guildId = g;
        channelId = c;
    }
}
