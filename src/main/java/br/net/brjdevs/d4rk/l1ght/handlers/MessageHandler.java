package br.net.brjdevs.d4rk.l1ght.handlers;

import br.net.brjdevs.d4rk.l1ght.commands.games.CmdSplatoon2;
import br.net.brjdevs.d4rk.l1ght.music.lyrics.LyricsSearchQuery;
import br.net.brjdevs.d4rk.l1ght.music.lyrics.LyricsUtils;
import br.net.brjdevs.d4rk.l1ght.music.SongSearchQuery;
import br.net.brjdevs.d4rk.l1ght.music.AudioUtils;
import br.net.brjdevs.d4rk.l1ght.utils.Config;
import br.net.brjdevs.d4rk.l1ght.utils.command.CommandRegister;
import br.net.brjdevs.d4rk.l1ght.utils.command.RegisteredCommand;
import br.net.brjdevs.d4rk.l1ght.utils.command.RegisteredSubCommand;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MessageHandler {

    public static void handle(GuildMessageReceivedEvent event) {

        if (!event.getChannel().canTalk()) {
            return;
        }

        String rawContent = event.getMessage().getRawContent();

        HashMap<String, RegisteredCommand> commandMap = CommandRegister.getCommandMap();

        for (SongSearchQuery as : AudioUtils.queryList) {
            if (as.guildId.equals(event.getGuild().getId()) && as.userId.equals(event.getAuthor().getId())) {
                try {
                    int value = Integer.valueOf(rawContent);
                    as.choose(value);
                    return;
                } catch (Exception e) {
                    as.choose(0);
                    return;
                }
            }
        }

        for (LyricsSearchQuery as : LyricsUtils.queryList) {
            if (as.guildId.equals(event.getGuild().getId()) && as.userId.equals(event.getAuthor().getId())) {
                try {
                    int value = Integer.valueOf(rawContent);
                    as.choose(value);
                    return;
                } catch (Exception e) {
                    as.choose(0);
                    return;
                }
            }
        }

        String prefix = null;

        if (rawContent.startsWith(Config.hardPrefix)) {
            prefix = Config.hardPrefix;
        } else if (PrefixHandler.prefixes.get(event.getGuild().getId()) != null) {
            if (PrefixHandler.prefixes.get(event.getGuild().getId()).size() == 0) {
                prefix = Config.prefix;
            } else {
                for (String p : PrefixHandler.prefixes.get(event.getGuild().getId())) {
                    if (rawContent.startsWith(p)) {
                        prefix = p;
                    }
                }
            }
        } else if (rawContent.startsWith(Config.prefix)) {
            prefix = Config.prefix;
        }

        if (prefix != null && rawContent.startsWith(prefix)) {

            if (rawContent.length() < prefix.length() + 1) {
                return;
            }

            rawContent = rawContent.substring(prefix.length());

            String[] args = rawContent.split("\\s+");


            if(commandMap.keySet().contains(args[0])){

                RegisteredCommand cmd = commandMap.get(args[0]);

                args = Arrays.copyOfRange(args, 1, args.length);

                if (cmd.getSubCommands() != null && cmd.getSubCommands().size() > 0 && args.length > 0) {
                    if(cmd.getSubCommands().keySet().contains(args[0])){

                        RegisteredSubCommand s = cmd.getSubCommands().get(args[0]);
                        args = Arrays.copyOfRange(args, 1, args.length);

                        //Here we run the subcommand

                        List<Boolean> requiredArgs = Arrays.stream(s
                                .getUsage()
                                .replaceAll("[^()\\[\\]]", "")
                                .replace("()", "true\n")
                                .replace("[]", "false\n")
                                .split("\n"))
                                .map(Boolean::valueOf)
                                .collect(Collectors.toList()
                                );

                        for(int i = 0 ; i < requiredArgs.size() ; i++){
                            if(requiredArgs.get(i) && args.length < i+1){
                                event.getChannel().sendMessage("**Error: **Missing arguments!" + "\n" + "**Correct usage: **"+prefix+cmd.getName()+" "+s.getName()+" "+s.getUsage()).queue();
                                return;
                            }
                        }

                        if (PermissionHandler.hasPerm(event.getAuthor(), event.getGuild(), s.getPerms())) {
                            try{
                                s.getCommand().invoke(null, event, args);
                            }catch (Exception e) {e.printStackTrace();}
                        }else{
                            event.getChannel().sendMessage("**Error: **You don't have all the required permissions: `" + s.getPerms().toString() + "`").queue();
                        }

                        return;

                    }
                }

                if(cmd.getUsage().equals("null")){
                    String availables = "";
                    for(RegisteredSubCommand s : cmd.getSubCommands().values()){
                        availables = availables+"`"+s.getName()+"`, ";
                    }
                    availables = availables.substring(0, availables.length()-2);
                    if(args.length > 0){
                        event.getChannel().sendMessage("**Unknown option `"+args[0]+"`, available ones are: "+availables+"**").queue();
                    }else{
                        event.getChannel().sendMessage("**Unknown option, available ones are: "+availables+"**").queue();
                    }
                    return;
                }

                //Here we run the command

                List<Boolean> requiredArgs = Arrays.stream(cmd
                        .getUsage()
                        .replaceAll("[^()\\[\\]]", "")
                        .replace("()", "true\n")
                        .replace("[]", "false\n")
                        .split("\n"))
                        .map(Boolean::valueOf)
                        .collect(Collectors.toList()
                        );

                for(int i = 0 ; i < requiredArgs.size() ; i++){
                    if(requiredArgs.get(i) && args.length < i+1){
                        event.getChannel().sendMessage("**Error: **Missing arguments!" + "\n" + "**Correct usage: **"+prefix+cmd.getName()+" "+cmd.getUsage()).queue();
                        return;
                    }
                }

                if (PermissionHandler.hasPerm(event.getAuthor(), event.getGuild(), cmd.getPerms())) {
                    try{
                        cmd.getCommand().invoke(null, event, args);
                    }catch (Exception e) {e.printStackTrace();}
                }else{
                event.getChannel().sendMessage("**Error: **You don't have all the required permissions: `" + cmd.getPerms().toString() + "`").queue();
                }

            }


        }

    }

}
