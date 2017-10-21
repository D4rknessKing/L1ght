package br.net.brjdevs.d4rk.l1ght.commands.fun;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import com.mashape.unirest.http.Unirest;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class CmdYomomma implements Command {
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {
        try {
            event.getChannel().sendMessage("`"+Unirest.get("http://api.yomomma.info/").asJson().getBody().getObject().getString("joke")+"`").queue();
        }catch(Exception e){
            event.getChannel().sendMessage("**An unknown error has occurred while doing the HTTP request to the YomamaAPI**").queue();
            e.printStackTrace();
        }
    }

    @Override
    public String cmdName() {
        return "yomomma";
    }

    @Override
    public String cmdDescription() {
        return "A command that throws insults at yomomma";
    }

    @Override
    public String cmdCategory() {
        return "Fun";
    }

    @Override
    public List<L1ghtPerms> cmdPerm() {
        return Arrays.asList(L1ghtPerms.BASE);
    }

    @Override
    public List<Pair<String, Boolean>> cmdArgs() {
        return null;
    }
}
