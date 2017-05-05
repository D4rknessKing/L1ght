package br.net.brjdevs.d4rk.l1ght.utils.command;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public interface Command {

    //What will happen
    void cmdRun(GuildMessageReceivedEvent event, String[] args);

    //Cmd name (and what will be executed)
    String cmdName();

    //Cmd description
    String cmdDescription();

    //Cmd category
    String cmdCategory();

    //Required bot permissions
    List<L1ghtPerms> cmdPerm();

    List<Pair<String, Boolean>> cmdArgs();
}
