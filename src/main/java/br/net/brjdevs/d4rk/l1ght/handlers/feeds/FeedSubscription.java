package br.net.brjdevs.d4rk.l1ght.handlers.feeds;

import br.net.brjdevs.d4rk.l1ght.utils.FeedsType;

public class FeedSubscription {

    public String name;
    public String url;
    public FeedsType type;
    public String channelId;

    public FeedSubscription(String nm, String ur, FeedsType tp, String ch) {
        name = nm;
        url = ur;
        type = tp;
        channelId = ch;
    }
}
