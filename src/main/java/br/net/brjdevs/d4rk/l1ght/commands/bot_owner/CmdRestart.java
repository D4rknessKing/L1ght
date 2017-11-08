package br.net.brjdevs.d4rk.l1ght.commands.bot_owner;

import br.net.brjdevs.d4rk.l1ght.L1ght;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class CmdRestart{


    @Command(name="restart", description = "Restarts the bot.", category = "Bot Owner", usage = "", perms = {L1ghtPerms.BASE, L1ghtPerms.ADMIN})
    public static void run(GuildMessageReceivedEvent event, String[] args) {

        File currentJar = new File("pls work");

        try {
            currentJar = new File(L1ght.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {

        }
        if (!currentJar.getName().endsWith(".jar")) {
            event.getChannel().sendMessage("**Error: **Can't restart! .jar not found.").queue();
            return;
        }

        List<String> command = new ArrayList<String>();
        command.add("java");
        command.add("-jar");
        command.add(currentJar.getPath());

        event.getChannel().sendMessage("Restarting! :wave:").complete();

        ProcessBuilder builder = new ProcessBuilder(command);
        try {
            builder.start();
        } catch (IOException e) {

        }

        event.getJDA().shutdown();
        System.exit(0);

    }

}
