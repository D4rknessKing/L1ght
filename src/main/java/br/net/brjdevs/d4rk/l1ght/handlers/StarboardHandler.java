package br.net.brjdevs.d4rk.l1ght.handlers;

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

public class StarboardHandler {

    public static void onAdd(GuildMessageReactionAddEvent event) {
        HashMap<String, String> hashMap = GuildDataHandler.loadGuildStarboard(event.getGuild().getId());
        if(hashMap.get(event.getMessageId()) != null) {
            Message msg = event.getGuild().getTextChannelById(hashMap.get("channel")).getMessageById(hashMap.get(event.getMessageId())).complete();
            MessageEmbed embed = msg.getEmbeds().get(0);
            MessageEmbed newEmbed = new EmbedBuilder(embed).setTitle(":star: | "+getQuantity(event)).build();
            msg.editMessage(newEmbed).queue();
        }else{
            if(getQuantity(event) > 0) {
                Message msg = event.getChannel().getMessageById(event.getMessageId()).complete();
                MessageEmbed embed = new EmbedBuilder()
                        .setAuthor(msg.getAuthor().getName()+"#"+msg.getAuthor().getDiscriminator(), null, msg.getAuthor().getAvatarUrl())
                        .setTitle(":star: | "+getQuantity(event))
                        .setDescription(msg.getRawContent())
                        .setFooter("Message sent on #"+msg.getChannel().getName(), null)
                        .setTimestamp(msg.getCreationTime())
                        .setColor(new Color(255, 172, 51))
                        .build();
                event.getGuild().getTextChannelById(hashMap.get("channel")).sendMessage(embed).queue(e -> {
                    hashMap.put(event.getMessageId(), e.getId());
                    GuildDataHandler.saveGuildStarboard(event.getGuild().getId(), hashMap);
                });
            }
        }
    }

    public static void onRemove(GuildMessageReactionRemoveEvent event) {
        HashMap<String, String> hashMap = GuildDataHandler.loadGuildStarboard(event.getGuild().getId());
        if(hashMap.get(event.getMessageId()) == null) {
            return;
        }
        if (getQuantity(event) < 1) {
            Message msg = event.getGuild().getTextChannelById(hashMap.get("channel")).getMessageById(hashMap.get(event.getMessageId())).complete();
            msg.delete().queue(e -> {
                hashMap.remove(event.getMessageId());
                GuildDataHandler.saveGuildStarboard(event.getGuild().getId(), hashMap);
            });
        }else{
            Message msg = event.getGuild().getTextChannelById(hashMap.get("channel")).getMessageById(hashMap.get(event.getMessageId())).complete();
            MessageEmbed embed = msg.getEmbeds().get(0);
            MessageEmbed newEmbed = new EmbedBuilder(embed).setTitle(":star: | "+getQuantity(event)).build();
            msg.editMessage(newEmbed).queue();
        }
    }

    private static int getQuantity(GuildMessageReactionAddEvent event){
        Message msg = event.getChannel().getMessageById(event.getMessageId()).complete();
        for (MessageReaction r : msg.getReactions()) {
            if (r.getEmote().getName().equals("⭐")) {
                if (r.getUsers().complete().contains(msg.getAuthor())) {
                    return r.getCount()-1;
                }else{
                    return r.getCount();
                }
            }
        }
        return 0;
    }

    private static int getQuantity(GuildMessageReactionRemoveEvent event){
        Message msg = event.getChannel().getMessageById(event.getMessageId()).complete();
        for (MessageReaction r : msg.getReactions()) {
            if (r.getEmote().getName().equals("⭐")) {
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
