package br.net.brjdevs.d4rk.l1ght.commands.info;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import javafx.util.Pair;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class CmdGuild implements Command {
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {

        Guild g = event.getGuild();

        int online = 0;
        for (Member m : g.getMembers()){
            if(m.getOnlineStatus().equals(OnlineStatus.ONLINE)) {
                online++;
            }
        }

        String emojis = "";
        for (Emote em : g.getEmotes()) {
            emojis = emojis + em.getAsMention() + " ";
        }

        String roles = "";
        for (Role ro : g.getRoles()) {
            roles = roles + ro.getAsMention() + " ";
        }

        MessageEmbed embed = new EmbedBuilder()
                .setAuthor("Guild information:", null, g.getIconUrl())
                .setThumbnail(g.getIconUrl())
                .addField(g.getName(), "**ID: **"+g.getId(), false)
                .addField("Online/Unique Members: ", String.valueOf(online) + g.getMembers().size(), true)
                .addField("Voice/Text Channels: ", String.valueOf(g.getVoiceChannels().size()) + "/" + String.valueOf(g.getTextChannels().size()), true)
                .addField("Created at: ", g.getCreationTime().format(DateTimeFormatter.ISO_DATE_TIME).replaceAll("[^0-9.:-]", " "), true)
                .addField("Owner: ", g.getOwner().getUser().getName() + "#" + g.getOwner().getUser().getDiscriminator(), true)
                .addField("Region: ", g.getRegion().getName(), true)
                .addField("Roles ("+String.valueOf(g.getRoles().size())+"): ", roles , false)
                .addField("Emojis ("+g.getEmotes().size()+"): ", emojis, false)
                .setFooter("Requested by: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl())
                .setColor(event.getMember().getColor())
                .build();

        event.getChannel().sendMessage(embed).queue();


    }

    @Override
    public String cmdName() {
        return "guild";
    }

    @Override
    public String cmdDescription() {
        return "Gives some information about the current guild.";
    }

    @Override
    public String cmdCategory() {
        return "Info";
    }

    @Override
    public List<L1ghtPerms> cmdPerm() {
        return Arrays.asList(L1ghtPerms.BASE);
    }

    @Override
    public List<Pair<String, Boolean>> cmdArgs() {
        return null;
    }
}
