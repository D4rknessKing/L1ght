package br.net.brjdevs.d4rk.l1ght;

import br.net.brjdevs.d4rk.l1ght.handlers.PrefixHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.feeds.RedditHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.feeds.TwitterHandler;
import br.net.brjdevs.d4rk.l1ght.listeners.GuildMessageListener;
import br.net.brjdevs.d4rk.l1ght.listeners.GuildReactionListener;
import br.net.brjdevs.d4rk.l1ght.music.AudioUtils;
import br.net.brjdevs.d4rk.l1ght.utils.Config;
import br.net.brjdevs.d4rk.l1ght.utils.command.CommandRegistry;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

import java.util.ArrayList;
import java.util.List;

public class L1ght {

    public static JDA jda;

    public static void main(String[] args) {

        CommandRegistry.registerCmds();
        Config.loadConfig();
        AudioUtils.init();
        //System.setProperty("ui4j.headless", "true");
        PrefixHandler.start();

        try {
            jda = new JDABuilder(AccountType.BOT)
                .setToken(Config.token)
                .addEventListener(new GuildMessageListener())
                .addEventListener(new GuildReactionListener())
                .buildBlocking();
        }catch (Exception e){

        }

        TwitterHandler.start();
        RedditHandler.start();

        if(Config.isStreaming) {
            jda.getPresence().setGame(Game.of(Config.defaultPlaying, "https://twitch.tv/ "));
        }else {
            jda.getPresence().setGame(Game.of(Config.defaultPlaying));
        }


    }

}
