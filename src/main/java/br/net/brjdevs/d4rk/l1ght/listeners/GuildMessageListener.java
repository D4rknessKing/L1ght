package br.net.brjdevs.d4rk.l1ght.listeners;

import br.net.brjdevs.d4rk.l1ght.handlers.MessageHandler;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildMessageListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        MessageHandler.handle(event);
    }


}
