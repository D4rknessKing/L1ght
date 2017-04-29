package br.net.brjdevs.d4rk.l1ght.commands.fun;

import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class CmdLenny implements Command {

    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("( ͡° ͜ʖ ͡°)").queue();
    }

    public String cmdName() {
        return "lenny";
    }

    public String cmdDescription() {
        return "( ͡° ͜ʖ ͡°)";
    }

    public String cmdCategory() {
        return null;
    }

    public List<L1ghtPerms> cmdPerm() {
        return Arrays.asList(L1ghtPerms.BASE);
    }

    @Override
    public List<Pair<String, Boolean>> cmdArgs() {
        return null;
    }

}
