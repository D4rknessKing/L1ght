package br.net.brjdevs.d4rk.l1ght.commands.fun;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import com.mashape.unirest.http.Unirest;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Cmd8 implements Command {
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {
        Random rand = new Random();

        String[] magic8ball = {
                "It is certain",
                "It is decidedly so",
                "Without a doubt",
                "Yes - definitely",
                "You may rely on it",
                "As I see it, yes",
                "Most likely",
                "Yes",
                "Signs point to yes",
                "Reply hazy, try again",
                "Ask again later",
                "Better not tell you now",
                "Cannot predict now",
                "Concentrate and ask again",
                "Don't count on it",
                "My reply is no",
                "My sources say no",
                "Very doubtful"};

        event.getChannel().sendMessage("**The :8ball:ball says: `"+magic8ball[rand.nextInt(magic8ball.length)]+"`**").queue();
    }

    @Override
    public String cmdName() {
        return "8ball";
    }

    @Override
    public String cmdDescription() {
        return "Answer any given question, using its trustworthy knowledge.";
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
        list.add(new Pair<>("Question", false));
        return list;
    }
}
