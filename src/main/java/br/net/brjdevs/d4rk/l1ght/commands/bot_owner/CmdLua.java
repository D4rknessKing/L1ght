package br.net.brjdevs.d4rk.l1ght.commands.bot_owner;

import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import javafx.util.Pair;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdLua implements Command {
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {

        String code = String.join(" ", args);

        ScriptEngine se = new ScriptEngineManager().getEngineByName("luaj");
        se.put("event", event);

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

    @Override
    public String cmdName() {
        return "lua";
    }

    @Override
    public String cmdDescription() {
        return "lua code shit";
    }

    @Override
    public String cmdCategory() {
        return "Bot Owner";
    }

    @Override
    public List<L1ghtPerms> cmdPerm() {
        return Arrays.asList(L1ghtPerms.BASE, L1ghtPerms.ADMIN);
    }

    @Override
    public List<Pair<String, Boolean>> cmdArgs() {
        List<Pair<String, Boolean>> list = new ArrayList<>();
        list.add(new Pair<>("Text", true));
        return list;
    }
}
