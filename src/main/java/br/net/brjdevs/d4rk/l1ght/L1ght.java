package br.net.brjdevs.d4rk.l1ght;

import br.net.brjdevs.d4rk.l1ght.listeners.GuildMessageListener;
import br.net.brjdevs.d4rk.l1ght.utils.Config;
import br.net.brjdevs.d4rk.l1ght.utils.command.CommandRegistry;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

public class L1ght {

    static JDA jda;

    public static void main(String[] args) {

        CommandRegistry.registerCmds();
        Config.loadConfig();

        try {
            jda = new JDABuilder(AccountType.BOT)
                .setToken(Config.token)
                .addEventListener(new GuildMessageListener())
                .buildBlocking();
        }catch (Exception e){

        }

        if(Config.isStreaming) {
            jda.getPresence().setGame(Game.of(Config.defaultPlaying, "https://twitch.tv/ "));
        }else {
            jda.getPresence().setGame(Game.of(Config.defaultPlaying));
        }

    }

}
