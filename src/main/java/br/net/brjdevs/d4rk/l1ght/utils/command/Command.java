package br.net.brjdevs.d4rk.l1ght.utils.command;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import com.sun.istack.internal.NotNull;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
