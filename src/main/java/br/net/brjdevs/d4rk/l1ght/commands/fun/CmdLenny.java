package br.net.brjdevs.d4rk.l1ght.commands.fun;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class CmdLenny {

    @Command(name="lenny", description = "( ͡° ͜ʖ ͡°)", category = "", usage="", perms = {L1ghtPerms.BASE})
    public static void run(GuildMessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("( ͡° ͜ʖ ͡°)").queue();
    }

}
