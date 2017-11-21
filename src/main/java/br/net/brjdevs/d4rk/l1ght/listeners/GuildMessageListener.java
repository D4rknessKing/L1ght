package br.net.brjdevs.d4rk.l1ght.listeners;

import br.net.brjdevs.d4rk.l1ght.L1ght;
import br.net.brjdevs.d4rk.l1ght.handlers.CommandHandler;
import br.net.brjdevs.d4rk.l1ght.utils.Stats;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildMessageListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        CommandHandler.handle(event);
        Stats.readMessages++;
        if(event.getAuthor() == L1ght.jda.getSelfUser()){
            Stats.sendMessages++;
        }
    }


}
