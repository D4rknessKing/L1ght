package br.net.brjdevs.d4rk.l1ght.utils;

import br.net.brjdevs.d4rk.l1ght.L1ght;
import br.net.brjdevs.d4rk.l1ght.utils.command.RegisteredCommand;
import br.net.brjdevs.d4rk.l1ght.utils.command.RegisteredSubCommand;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.utils.SimpleLog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    public static void logCmd(String prefix, RegisteredCommand cmd, GuildMessageReceivedEvent event) {
        TextChannel tc = L1ght.jda.getTextChannelById("255081000785281025");
        DateFormat dateFormat = new SimpleDateFormat("[dd.MM.yyyy] [HH:mm:ss]: ");
        SimpleLog log = SimpleLog.getLog("L1ght");
        tc.sendMessage(String.format("%s`%s(%s)` executed: `%s%s`, at guild: `%s(%s)`, on channel: `#%s(%s)`",
                dateFormat.format(new Date()),
                event.getAuthor().getName(),
                event.getAuthor().getId(),
                prefix,
                cmd.getName(),
                event.getGuild().getName(),
                event.getGuild().getId(),
                event.getChannel().getName(),
                event.getChannel().getId())).queue();
        log.log(SimpleLog.Level.INFO, String.format("%s(%s) executed: %s%s, at guild: %s(%s), on channel: #%s(%s)",
                event.getAuthor().getName(),
                event.getAuthor().getId(),
                prefix,
                cmd.getName(),
                event.getGuild().getName(),
                event.getGuild().getId(),
                event.getChannel().getName(),
                event.getChannel().getId()));
    }

    public static void logCmd(String prefix, RegisteredSubCommand cmd, GuildMessageReceivedEvent event) {
        TextChannel tc = L1ght.jda.getTextChannelById("255081000785281025");
        DateFormat dateFormat = new SimpleDateFormat("[dd.MM.yyyy] [HH:mm:ss]: ");
        SimpleLog log = SimpleLog.getLog("L1ght");
        tc.sendMessage(String.format("%s`%s(%s)` executed: `%s%s`, at guild: `%s(%s)`, on channel: `#%s(%s)`",
                dateFormat.format(new Date()),
                event.getAuthor().getName(),
                event.getAuthor().getId(),
                prefix,
                cmd.getName(),
                event.getGuild().getName(),
                event.getGuild().getId(),
                event.getChannel().getName(),
                event.getChannel().getId())).queue();
        log.log(SimpleLog.Level.INFO, String.format("%s(%s) executed: %s%s, at guild: %s(%s), on channel: #%s(%s)",
                event.getAuthor().getName(),
                event.getAuthor().getId(),
                prefix,
                cmd.getName(),
                event.getGuild().getName(),
                event.getGuild().getId(),
                event.getChannel().getName(),
                event.getChannel().getId()));
    }
}
