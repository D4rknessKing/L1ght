package br.net.brjdevs.d4rk.l1ght.handlers;

import br.net.brjdevs.d4rk.l1ght.utils.Config;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import br.net.brjdevs.d4rk.l1ght.utils.command.CommandRegister;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MessageHandler {

    public static void handle(GuildMessageReceivedEvent event) {
        String rawContent = event.getMessage().getRawContent();

        HashMap<String, Command> commandMap = CommandRegister.getCommandMap();

        if (rawContent.startsWith(Config.prefix)) {
            rawContent = rawContent.substring(1);

            String[] args = rawContent.split(" ");

            for(String s : commandMap.keySet()) {

                if(Arrays.asList(args).get(0).equals(s)) {

                    Command cmd = commandMap.get(s);

                    args = Arrays.copyOfRange(args, 1, args.length);

                    if(PermissionHandler.handle(event.getAuthor(), event.getGuild(), cmd.cmdPerm())) {
                        List<Pair<String, Boolean>> requiredArgs = cmd.cmdArgs();
                        if(requiredArgs != null) {
                            int required = 0;
                            for(Pair<String, Boolean> p : requiredArgs) {
                                if(p.getValue()) {
                                    required++;
                                }
                            }
                            if(Arrays.asList(args).size() < required) {
                                String usage = "";
                                if(cmd.cmdArgs() != null) {
                                    for(Pair<String, Boolean> p : cmd.cmdArgs()) {
                                        if (p.getValue()) {
                                            usage = usage + " (" + p.getKey() + ") ";
                                        } else {
                                            usage = usage + " [" + p.getKey() + "] ";
                                        }
                                    }
                                }
                                event.getChannel().sendMessage("**Missing arguments!**" +
                                        "**Correct usage: **`"+usage+"`").queue();
                                return;
                            }else{
                                cmd.cmdRun(event, args);
                                return;
                            }
                        }else{
                            cmd.cmdRun(event, args);
                            return;
                        }
                    }else{
                        event.getChannel().sendMessage("You don't have all the required permissions: `"+cmd.cmdPerm().toString()+"`").queue();
                        return;
                    }

                }
            }
        }

    }

}
