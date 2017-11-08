package br.net.brjdevs.d4rk.l1ght.commands.utils;

import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class CmdPing {

    @Command(name="ping", description = "Shows the ping between the Discord API gateway and the bot's server.", category = "Utils", usage = "", perms = {L1ghtPerms.BASE})
    public static void run(GuildMessageReceivedEvent event, String[] args) {
        long time = System.currentTimeMillis();
        event.getChannel().sendTyping().queue(success -> {
            long ping = System.currentTimeMillis() - time;
            event.getChannel().sendMessage("**Ping: **" + ping + "*ms*").queue();
        });
    }

}
