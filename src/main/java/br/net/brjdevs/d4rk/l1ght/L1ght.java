package br.net.brjdevs.d4rk.l1ght;

import br.net.brjdevs.d4rk.l1ght.handlers.PrefixHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.feeds.RedditHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.feeds.TwitterHandler;
import br.net.brjdevs.d4rk.l1ght.listeners.GuildJoinLeaveListener;
import br.net.brjdevs.d4rk.l1ght.listeners.GuildMessageListener;
import br.net.brjdevs.d4rk.l1ght.listeners.GuildReactionListener;
import br.net.brjdevs.d4rk.l1ght.listeners.GuildVoiceChannelListener;
import br.net.brjdevs.d4rk.l1ght.music.AudioUtils;
import br.net.brjdevs.d4rk.l1ght.utils.Config;
import br.net.brjdevs.d4rk.l1ght.utils.LogHandler;
import br.net.brjdevs.d4rk.l1ght.utils.command.CommandRegister;
import br.net.brjdevs.d4rk.l1ght.utils.command.CommandRegistry;
import br.net.brjdevs.d4rk.l1ght.utils.command.RegisteredCommand;
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
        PrefixHandler.start();

        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(Config.token)
                    .addEventListener(new GuildMessageListener())
                    .addEventListener(new GuildReactionListener())
                    .addEventListener(new GuildVoiceChannelListener())
                    .addEventListener(new GuildJoinLeaveListener())
                    .buildBlocking();
        }catch (Exception e){

        }

        LogHandler.start();

        if(Config.isTwitterEnabled()){
            TwitterHandler.start();
        }
        if(Config.isRedditEnabled()){
            RedditHandler.start();
        }

        if(Config.isStreaming) {
            jda.getPresence().setGame(Game.of(Config.defaultPlaying, "https://twitch.tv/ "));
        }else {
            jda.getPresence().setGame(Game.of(Config.defaultPlaying));
        }


    }

}
