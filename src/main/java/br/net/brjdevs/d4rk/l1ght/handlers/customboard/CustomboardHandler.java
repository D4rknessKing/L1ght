package br.net.brjdevs.d4rk.l1ght.handlers.customboard;

import br.net.brjdevs.d4rk.l1ght.handlers.data.GlobalDataHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.data.GuildDataHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveEvent;

import java.awt.*;
import java.util.HashMap;

public class CustomboardHandler {

    public static void onAdd(GuildMessageReactionAddEvent event, String name , Customboard cb) {
        HashMap<String, Customboard> finalHashMap = GuildDataHandler.loadGuildCustomboard(event.getGuild().getId());

        HashMap<String, String> hashMap = cb.getEntries();
        if(hashMap.get(event.getMessageId()) != null) {
            Message msg = event.getGuild().getTextChannelById(cb.getChannelID()).getMessageById(hashMap.get(event.getMessageId())).complete();
            MessageEmbed embed = msg.getEmbeds().get(0);
            MessageEmbed newEmbed = new EmbedBuilder(embed).setTitle(cb.getEmote()+" | "+getQuantity(event, cb.getEmote())).build();
            msg.editMessage(newEmbed).queue();
        }else{
            if(getQuantity(event, cb.getEmote()) >= cb.getMinimum()) {
                Message msg = event.getChannel().getMessageById(event.getMessageId()).complete();
                MessageEmbed embed = new EmbedBuilder()
                        .setAuthor(msg.getAuthor().getName()+"#"+msg.getAuthor().getDiscriminator(), null, msg.getAuthor().getAvatarUrl())
                        .setTitle(cb.getEmote()+" | "+getQuantity(event, cb.getEmote()))
                        .setDescription(msg.getRawContent())
                        .setFooter("Message sent on #"+msg.getChannel().getName(), null)
                        .setTimestamp(msg.getCreationTime())
                        .setColor(new Color(255, 172, 51))
                        .build();
                event.getGuild().getTextChannelById(cb.getChannelID()).sendMessage(embed).queue(e -> {
                    hashMap.put(event.getMessageId(), e.getId());
                    cb.setEntries(hashMap);

                    finalHashMap.remove(name);
                    finalHashMap.put(name, cb);
                    GuildDataHandler.saveGuildCustomboard(event.getGuild().getId(), finalHashMap);
                });
            }
        }
    }

    public static void onRemove(GuildMessageReactionRemoveEvent event, String name, Customboard cb) {
        HashMap<String, Customboard> finalHashMap = GuildDataHandler.loadGuildCustomboard(event.getGuild().getId());

        HashMap<String, String> hashMap = cb.getEntries();
        if(hashMap.get(event.getMessageId()) == null) {
            return;
        }
        if (getQuantity(event, cb.getEmote()) < cb.getMinimum()) {
            Message msg = event.getGuild().getTextChannelById(cb.getChannelID()).getMessageById(hashMap.get(event.getMessageId())).complete();
            msg.delete().queue(e -> {
                hashMap.remove(event.getMessageId());
                cb.setEntries(hashMap);

                finalHashMap.remove(name);
                finalHashMap.put(name, cb);
                GuildDataHandler.saveGuildCustomboard(event.getGuild().getId(), finalHashMap);
            });
        }else{
            Message msg = event.getGuild().getTextChannelById(cb.getChannelID()).getMessageById(hashMap.get(event.getMessageId())).complete();
            MessageEmbed embed = msg.getEmbeds().get(0);
            MessageEmbed newEmbed = new EmbedBuilder(embed).setTitle(cb.getEmote()+" | "+getQuantity(event, cb.getEmote())).build();
            msg.editMessage(newEmbed).queue();
        }
    }

    private static int getQuantity(GuildMessageReactionAddEvent event, String emote){
        Message msg = event.getChannel().getMessageById(event.getMessageId()).complete();
        for (MessageReaction r : msg.getReactions()) {
            if (r.getEmote().getName().equals(emote)) {
                if (r.getUsers().complete().contains(msg.getAuthor())) {
                    return r.getCount()-1;
                }else{
                    return r.getCount();
                }
            }
        }
        return 0;
    }

    private static int getQuantity(GuildMessageReactionRemoveEvent event, String emote){
        Message msg = event.getChannel().getMessageById(event.getMessageId()).complete();
        for (MessageReaction r : msg.getReactions()) {
            if (r.getEmote().getName().equals(emote)) {
                if (r.getUsers().complete().contains(msg.getAuthor())) {
                    return r.getCount()-1;
                }else{
                    return r.getCount();
                }
            }
        }
        return 0;
    }
}
