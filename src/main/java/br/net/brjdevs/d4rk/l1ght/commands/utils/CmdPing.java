package br.net.brjdevs.d4rk.l1ght.commands.utils;

import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class CmdPing implements Command {
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {
        long time = System.currentTimeMillis();
        event.getChannel().sendTyping().queue(success -> {
            long ping = System.currentTimeMillis() - time;
            event.getChannel().sendMessage("**Ping: **" + ping + "*ms*").queue();
        });
    }

    @Override
    public String cmdName() {
        return "ping";
    }

    @Override
    public String cmdDescription() {
        return "Shows the ping between the Discord API gateway and the bot's server.";
    }

    @Override
    public String cmdCategory() {
        return "Utils";
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
