package br.net.brjdevs.d4rk.l1ght.commands.fun;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import com.mashape.unirest.http.Unirest;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class CmdYomomma {

    @Command(name="yomomma", description = "A command that throws insults at yomomma", category = "Fun", usage="", perms = {L1ghtPerms.BASE})
    public static void run(GuildMessageReceivedEvent event, String[] args) {
        try {
            event.getChannel().sendMessage("`"+Unirest.get("http://api.yomomma.info/").asJson().getBody().getObject().getString("joke")+"`").queue();
        }catch(Exception e){
            event.getChannel().sendMessage("**An unknown error has occurred while doing the HTTP request to the YomamaAPI**").queue();
            e.printStackTrace();
        }
    }

}
