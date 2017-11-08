package br.net.brjdevs.d4rk.l1ght.commands.utils;

import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class CmdInvite {

    @Command(name="invite", description = "Sends the OAuth authorization for adding L1ght in your guild and some other useful informations.", category = "Utils", usage = "", perms = {L1ghtPerms.BASE})
    public static void invite(GuildMessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage(
                "Hello! I'm L1ght and you can know more about my functions by using the -help command!\n" +
                "If you want me in your server, here goes my invitation link: https://discordapp.com/oauth2/authorize?client_id=" + event.getJDA().getSelfUser().getId() + "&scope=bot\n" +
                "If you want support, here goes my support guild: https://discord.gg/WvFU954\n"
        ).queue();
    }


}
