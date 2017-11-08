package br.net.brjdevs.d4rk.l1ght.commands.guild;

import br.net.brjdevs.d4rk.l1ght.handlers.PrefixHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.data.GuildDataHandler;
import br.net.brjdevs.d4rk.l1ght.utils.Config;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import br.net.brjdevs.d4rk.l1ght.utils.command.SubCommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdPrefix {

    @Command(name="prefix", description = "Config which prefixes the bot will accept while in this guild.\nPSA: You can set multiple prefixes by inputting more than 1 argument. ", category = "Guild", usage="", perms = {L1ghtPerms.BASE})
    public static void run(GuildMessageReceivedEvent event, String[] args) {
        List<String> prefixes = GuildDataHandler.loadGuildPrefixes(event.getGuild().getId());
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
    }

    @SubCommand(name="set", description = "", usage = "(prefixes)", perms = {L1ghtPerms.BASE, L1ghtPerms.GUILD})
    public static void set(GuildMessageReceivedEvent event, String[] args) {
        GuildDataHandler.saveGuildPrefixes(event.getGuild().getId(), Arrays.asList(args));
        PrefixHandler.check();
        event.getChannel().sendMessage("**Successfully changed custom prefix!**").queue();
    }

    @SubCommand(name="reset", description = "", usage = "", perms = {L1ghtPerms.BASE, L1ghtPerms.GUILD})
    public static void reset(GuildMessageReceivedEvent event, String[] args) {
        GuildDataHandler.saveGuildPrefixes(event.getGuild().getId(), new ArrayList<>());
        PrefixHandler.check();
        event.getChannel().sendMessage("**Successfully changed custom prefix!**").queue();
    }

}
