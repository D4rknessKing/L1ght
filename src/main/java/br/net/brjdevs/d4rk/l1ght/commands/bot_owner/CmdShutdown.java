package br.net.brjdevs.d4rk.l1ght.commands.bot_owner;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class CmdShutdown{

    @Command(name="shutdown", description = "Shutdowns the bot.", category = "Bot Owner", usage = "", perms = {L1ghtPerms.BASE, L1ghtPerms.ADMIN})
    public static void run(GuildMessageReceivedEvent event, String[] args) {

        event.getChannel().sendMessage("Bye! :wave:").complete();
        event.getJDA().shutdown();
        System.exit(0);

    }

}
