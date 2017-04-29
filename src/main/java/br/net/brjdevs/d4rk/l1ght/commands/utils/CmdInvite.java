package br.net.brjdevs.d4rk.l1ght.commands.utils;

import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class CmdInvite implements Command {

    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage(
                "Hello! I'm L1ght and you can know more about my functions by using the -help command!\n" +
                "If you want me in your server, here goes my invitation link: https://discordapp.com/oauth2/authorize?client_id=" + event.getJDA().getSelfUser().getId() + "&scope=bot\n" +
                "If you want support, here goes my support guild: https://discord.gg/WvFU954\n"
        ).queue();
    }

    public String cmdName() {
        return "invite";
    }

    public String cmdDescription() {
        return "Sends the OAuth authorization for adding L1ght in your guild and some other useful informations.";
    }

    public String cmdCategory() {
        return "Utils";
    }

    public List<L1ghtPerms> cmdPerm() {
        return Arrays.asList(L1ghtPerms.BASE);
    }

    @Override
    public List<Pair<String, Boolean>> cmdArgs() {
        return null;
    }

}
