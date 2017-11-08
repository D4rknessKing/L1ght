package br.net.brjdevs.d4rk.l1ght.commands.bot_owner;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.JavaEval;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class CmdJava {

    @Command(name="java", description = "Java (Natan's Macumbas Certified) code evaluator.", category = "Bot Owner", usage = "(Code)", perms = {L1ghtPerms.BASE, L1ghtPerms.ADMIN, L1ghtPerms.EVAL})
    public static void run(GuildMessageReceivedEvent event, String[] args) {

        String input = String.join(" ", args);
        JavaEval.eval(event, input);

    }

}
