package br.net.brjdevs.d4rk.l1ght.commands.fun;

import br.net.brjdevs.d4rk.l1ght.utils.Config;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import com.mashape.unirest.http.Unirest;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdYoda implements Command{


    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {

        String yoda;
        String sentence = String.join("+", args);

        try {
            yoda = Unirest.get("https://yoda.p.mashape.com/yoda?sentence=" + sentence)
                    .header("X-Mashape-Key", Config.mashapeKey)
                    .asStringAsync().get().getBody();
        }catch (Exception e) {
            event.getChannel().sendMessage("**Error: **"+e.getMessage()).queue();
            return;
        }

        event.getChannel().sendMessage("**Yoda says: **"+yoda).queue();

    }

    @Override
    public String cmdName() {
        return "yoda";
    }

    @Override
    public String cmdDescription() {
        return "Translate the given string to the yoda speak.";
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
        List<Pair<String, Boolean>> list = new ArrayList<>();
        list.add(new Pair<>("Text", true));
        return list;
    }
}
