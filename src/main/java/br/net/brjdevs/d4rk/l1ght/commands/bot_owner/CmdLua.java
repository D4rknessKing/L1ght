package br.net.brjdevs.d4rk.l1ght.commands.bot_owner;

import br.net.brjdevs.d4rk.l1ght.handlers.PermissionHandler;
import br.net.brjdevs.d4rk.l1ght.music.AudioUtils;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;


public class CmdLua {

    @Command(name="lua", description = "Lua code evaluator.", category = "Bot Owner", usage = "(Code)", perms = {L1ghtPerms.BASE, L1ghtPerms.ADMIN, L1ghtPerms.EVAL})
    public static void run(GuildMessageReceivedEvent event, String[] args) {

        String code = String.join(" ", args);

        ScriptEngine se = new ScriptEngineManager().getEngineByName("luaj");
        se.put("event", event);
        se.put("jda", event.getJDA());
        se.put("permHandler", new PermissionHandler());
        se.put("audioConnections", AudioUtils.connections);


        try {
            Object result = se.eval(code);
            if(result == null) {
                event.getChannel().sendMessage(new EmbedBuilder()
                        .setTitle("Success: ", null)
                        .setColor(new Color(65280))
                        .setDescription("Executed without error and without return.")
                        .build()
                ).queue();
            }else{
                event.getChannel().sendMessage(new EmbedBuilder()
                        .setTitle("Success: ", null)
                        .setColor(new Color(65280))
                        .setDescription("Executed without error and with the following returns:"+"```\n"+result.toString()+"```")
                        .build()
                ).queue();
            }
        }catch (Exception e){
            event.getChannel().sendMessage(new EmbedBuilder()
                    .setTitle("Error: ", null)
                    .setColor(new Color(16711680))
                    .setDescription("Executed with the following error:"+"```\n"+e.getMessage()+"```")
                    .build()
            ).queue();
        }
    }

}
