package br.net.brjdevs.d4rk.l1ght.commands.bot_owner;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class CmdShutdown implements Command {


    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {

        event.getChannel().sendMessage("Buh-bye! :wave:").queue();
        event.getJDA().shutdown();
        System.exit(0);

    }

    @Override
    public String cmdName() {
        return "shutdown";
    }

    @Override
    public String cmdDescription() {
        return "pls, we dont need a description for this one.";
    }

    @Override
    public String cmdCategory() {
        return "Bot Owner";
    }

    @Override
    public List<L1ghtPerms> cmdPerm() {
        return Arrays.asList(L1ghtPerms.BASE, L1ghtPerms.ADMIN);
    }

    @Override
    public List<Pair<String, Boolean>> cmdArgs() {
        return null;
    }
}
