package br.net.brjdevs.d4rk.l1ght.commands.utils;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import br.net.brjdevs.d4rk.l1ght.utils.command.SubCommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Base64;

public class CmdB64 {

    @Command(name="b64", description = "Either encode or decode strings using the base64 encryption.", category = "Utils", usage = "null", perms = {L1ghtPerms.ADMIN})
    public static void b64(GuildMessageReceivedEvent event, String[] args) {
    }

    @SubCommand(name = "encode", description = "", usage = "(text)",  perms = {L1ghtPerms.BASE})
    public static void encode(GuildMessageReceivedEvent event, String[] args) {
        String fin = String.join(" ", args);
        String result = Base64.getEncoder().encodeToString(fin.getBytes());
        event.getChannel().sendMessage("**Text to base64: **"+result).queue();
    }

    @SubCommand(name = "decode", description = "", usage = "(text)", perms = {L1ghtPerms.BASE})
    public static void decode(GuildMessageReceivedEvent event, String[] args) {
        String fin = String.join(" ", args);
        try {
            byte[] bytes = Base64.getDecoder().decode(fin);
            String result = new String(bytes);
            event.getChannel().sendMessage("**Text from base64: **" + result).queue();
        }catch (IllegalArgumentException e){
            event.getChannel().sendMessage("**Error: **Unable to decode `"+fin+"` to base64. (Is it a base64 string?)").queue();
        }
    }

}
