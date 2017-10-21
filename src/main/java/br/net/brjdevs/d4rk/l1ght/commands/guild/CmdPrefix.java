package br.net.brjdevs.d4rk.l1ght.commands.guild;

import br.net.brjdevs.d4rk.l1ght.handlers.PrefixHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.data.GuildDataHandler;
import br.net.brjdevs.d4rk.l1ght.utils.Config;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdPrefix implements Command {

    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {
        if(args.length < 1) {
            List<String> prefixes = GuildDataHandler.getGuildPrefixes(event.getGuild().getId());
            if(prefixes.size() == 0){
                event.getChannel().sendMessage("**Current guild custom prefix: **`"+ Config.prefix + "`").queue();
            }else{
                String spref = "";
                for(String s : prefixes){
                    spref = spref+"`"+s+"` ";
                }
                if(prefixes.size() == 1){
                    event.getChannel().sendMessage("**Current guild custom prefix: **"+spref).queue();
                }else{
                    event.getChannel().sendMessage("**Current guild custom prefixes: **"+spref).queue();
                }
            }
        }else if(args[0].toLowerCase().equals("set")) {
            List<String> list = new ArrayList<>();
            for (String s : args) {
                if (!s.toLowerCase().equals("set")) {
                    list.add(s);
                }
            }
            GuildDataHandler.setGuildPrefixes(event.getGuild().getId(), list);
            PrefixHandler.check();
            event.getChannel().sendMessage("**Successfully changed custom prefix!**").queue();
        }else if(args[0].toLowerCase().equals("reset")) {
            GuildDataHandler.setGuildPrefixes(event.getGuild().getId(), new ArrayList<>());
            PrefixHandler.check();
            event.getChannel().sendMessage("**Successfully changed custom prefix!**").queue();
        }else{
            event.getChannel().sendMessage("**Error: **Invalid option `" + args[0] + "`, please use `set` or `reset`.").queue();
        }
    }

    @Override
    public String cmdName() {
        return "prefix";
    }

    @Override
    public String cmdDescription() {
        return "Config witch prefixes the bot will accept while in this guild.\nPSA: You can set multiple prefixes by inputting more than 1 argument. ";
    }

    @Override
    public String cmdCategory() {
        return "Guild";
    }

    @Override
    public List<L1ghtPerms> cmdPerm() {
        return Arrays.asList(L1ghtPerms.BASE, L1ghtPerms.GUILD);
    }

    @Override
    public List<Pair<String, Boolean>> cmdArgs() {
        List<Pair<String, Boolean>> list = new ArrayList<>();
        list.add(new Pair<>("set/reset", false));
        list.add(new Pair<>("prefix", false));
        return list;
    }
}
