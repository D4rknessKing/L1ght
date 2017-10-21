package br.net.brjdevs.d4rk.l1ght.handlers.feeds;

import br.net.brjdevs.d4rk.l1ght.handlers.data.GuildDataHandler;
import br.net.brjdevs.d4rk.l1ght.utils.FeedsType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FeedsHandler {

    public static void addFeed(String guildId, String channelId, FeedsType type, String name, String url) {
        List<FeedSubscription> feed = GuildDataHandler.loadGuildFeeds(guildId);

        feed.add(new FeedSubscription(name, url, type, channelId));

        GuildDataHandler.saveGuildFeeds(guildId, feed);
        if(type == FeedsType.TWITTER) {
            TwitterHandler.check();
        }else if(type == FeedsType.REDDIT) {
            RedditHandler.check();
        }

    }

    public static void removeFeed(String guildId, String channelId, String name) {
        List<FeedSubscription> feed = GuildDataHandler.loadGuildFeeds(guildId);

        for(FeedSubscription fs : (List<FeedSubscription>) ((ArrayList) feed).clone()) {
            if (fs.name.equals(name)) {
                feed.remove(fs);
            }
        }
        GuildDataHandler.saveGuildFeeds(guildId, feed);

        TwitterHandler.check();
        RedditHandler.check();
    }


}
