package br.net.brjdevs.d4rk.l1ght.listeners;

import br.net.brjdevs.d4rk.l1ght.handlers.customboard.Customboard;
import br.net.brjdevs.d4rk.l1ght.handlers.customboard.CustomboardHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.data.GuildDataHandler;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.List;

public class GuildReactionListener extends ListenerAdapter {

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        HashMap<String, Customboard> hashMap = GuildDataHandler.loadGuildCustomboard(event.getGuild().getId());
        for (String name : hashMap.keySet()) {
            if (hashMap.get(name).getEmote().equals(event.getReaction().getEmote().getName())) {
                CustomboardHandler.onAdd(event, name, hashMap.get(name));
            }
        }
    }

    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {
        HashMap<String, Customboard> hashMap = GuildDataHandler.loadGuildCustomboard(event.getGuild().getId());
        for (String name : hashMap.keySet()) {
            if (hashMap.get(name).getEmote().equals(event.getReaction().getEmote().getName())) {
                CustomboardHandler.onRemove(event, name, hashMap.get(name));
            }
        }
    }
}
