package br.net.brjdevs.d4rk.l1ght.handlers;

import br.net.brjdevs.d4rk.l1ght.music.lyrics.LyricsSearchQuery;
import br.net.brjdevs.d4rk.l1ght.music.lyrics.LyricsUtils;
import br.net.brjdevs.d4rk.l1ght.music.SongSearchQuery;
import br.net.brjdevs.d4rk.l1ght.music.AudioUtils;
import br.net.brjdevs.d4rk.l1ght.utils.Config;
import br.net.brjdevs.d4rk.l1ght.utils.Logger;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import br.net.brjdevs.d4rk.l1ght.utils.command.CommandRegister;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MessageHandler {

    public static void handle(GuildMessageReceivedEvent event) {

        if (!event.getChannel().canTalk()) {
            return;
        }

        String rawContent = event.getMessage().getRawContent();

        HashMap<String, Command> commandMap = CommandRegister.getCommandMap();

        for(SongSearchQuery as : AudioUtils.queryList) {
            if(as.guildId.equals(event.getGuild().getId()) && as.userId.equals(event.getAuthor().getId())) {
                try{
                    int value = Integer.valueOf(rawContent);
                    as.choose(value);
                    return;
                }catch (Exception e){
                    as.choose(0);
                    return;
                }
            }
        }

        for(LyricsSearchQuery as : LyricsUtils.queryList) {
            if(as.guildId.equals(event.getGuild().getId()) && as.userId.equals(event.getAuthor().getId())) {
                try{
                    int value = Integer.valueOf(rawContent);
                    as.choose(value);
                    return;
                }catch (Exception e){
                    as.choose(0);
                    return;
                }
            }
        }

        String prefix = null;

        if(rawContent.startsWith(Config.hardPrefix)) {
            prefix = Config.hardPrefix;
        }else if(PrefixHandler.prefixes.get(event.getGuild().getId()) != null) {
            if(PrefixHandler.prefixes.get(event.getGuild().getId()).size() == 0){
                prefix = Config.prefix;
            }else{
                for (String p : PrefixHandler.prefixes.get(event.getGuild().getId())) {
                    if (rawContent.startsWith(p)) {
                        prefix = p;
                    }
                }
            }
        }else if (rawContent.startsWith(Config.prefix)) {
            prefix = Config.prefix;
        }

        if(prefix != null && rawContent.startsWith(prefix)) {

            if(rawContent.length() < prefix.length()+1){
                return;
            }

            rawContent = rawContent.substring(prefix.length());

            String[] args = rawContent.split(" ");

            for (String s : commandMap.keySet()) {

                if (Arrays.asList(args).get(0).equals(s)) {

                    Command cmd = commandMap.get(s);

                    args = Arrays.copyOfRange(args, 1, args.length);

                    if (PermissionHandler.hasPerm(event.getAuthor(), event.getGuild(), cmd.cmdPerm())) {
                        List<Pair<String, Boolean>> requiredArgs = cmd.cmdArgs();
                        if (requiredArgs != null) {
                            int required = 0;
                            for (Pair<String, Boolean> p : requiredArgs) {
                                if (p.getValue()) {
                                    required++;
                                }
                            }
                            if (Arrays.asList(args).size() < required) {
                                String usage = "";
                                if (cmd.cmdArgs() != null) {
                                    for (Pair<String, Boolean> p : cmd.cmdArgs()) {
                                        if (p.getValue()) {
                                            usage = usage + " (" + p.getKey() + ") ";
                                        } else {
                                            usage = usage + " [" + p.getKey() + "] ";
                                        }
                                    }
                                }
                                usage = prefix + cmd.cmdName() + " " + usage;
                                event.getChannel().sendMessage("**Error: **Missing arguments!\n" +
                                        "**Correct usage: **`" + usage + "`").queue();
                                return;
                            } else {
                                Logger.logCmd(prefix, cmd, event);
                                cmd.cmdRun(event, args);
                                return;
                            }
                        } else {
                            Logger.logCmd(prefix, cmd, event);
                            cmd.cmdRun(event, args);
                            return;
                        }
                    } else {
                        event.getChannel().sendMessage("You don't have all the required permissions: `" + cmd.cmdPerm().toString() + "`").queue();
                        return;
                    }

                }
            }

        }
    }

}
