package br.net.brjdevs.d4rk.l1ght.listeners;

import br.net.brjdevs.d4rk.l1ght.handlers.StarboardHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.data.GuildDataHandler;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildReactionListener extends ListenerAdapter {

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if(event.getReaction().getEmote().getName().equals("⭐")){
            if(!GuildDataHandler.getGuildStarboardChannel(event.getGuild().getId()).equals("disabled")) {
                StarboardHandler.onAdd(event);
            }
        }
    }

    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {
        if(event.getReaction().getEmote().getName().equals("⭐")){
            if(!GuildDataHandler.getGuildStarboardChannel(event.getGuild().getId()).equals("disabled")) {
                StarboardHandler.onRemove(event);
            }
        }
    }
}
