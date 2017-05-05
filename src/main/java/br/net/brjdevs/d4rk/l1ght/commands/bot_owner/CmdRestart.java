package br.net.brjdevs.d4rk.l1ght.commands.bot_owner;

import br.net.brjdevs.d4rk.l1ght.L1ght;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdRestart implements Command{
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {

        File currentJar = new File("pls work");

        try {
            currentJar = new File(L1ght.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        }catch(URISyntaxException e) {

        }
        if(!currentJar.getName().endsWith(".jar")) {
            event.getChannel().sendMessage("**Error: **Can't restart! .jar not found.").queue();
            return;
        }

        List<String> command = new ArrayList<String>();
        command.add("java");
        command.add("-jar");
        command.add(currentJar.getPath());

        event.getChannel().sendMessage("Buh-bye! :wave:").queue();

        ProcessBuilder builder = new ProcessBuilder(command);
        try{
            builder.start();
        }catch (IOException e) {

        }

        event.getJDA().shutdown();
        System.exit(0);

    }

    @Override
    public String cmdName() {
        return "restart";
    }

    @Override
    public String cmdDescription() {
        return "neither for this one.";
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
