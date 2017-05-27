package br.net.brjdevs.d4rk.l1ght.commands.bot_owner;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.JavaEval;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdJava implements Command {
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {

        String input = String.join(" ", args);
        JavaEval.eval(event, input);

    }

    @Override
    public String cmdName() {
        return "java";
    }

    @Override
    public String cmdDescription() {
        return "Java (Natan's Macumbas Certified) code evaluator.";
    }

    @Override
    public String cmdCategory() {
        return "Bot Owner";
    }

    @Override
    public List<L1ghtPerms> cmdPerm() {
        return Arrays.asList(L1ghtPerms.BASE, L1ghtPerms.EVAL, L1ghtPerms.ADMIN);
    }

    @Override
    public List<Pair<String, Boolean>> cmdArgs() {
        List<Pair<String, Boolean>> list = new ArrayList<>();
        list.add(new Pair<>("Code", true));
        return list;
    }
}
