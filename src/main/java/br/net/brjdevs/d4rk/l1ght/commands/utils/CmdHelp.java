package br.net.brjdevs.d4rk.l1ght.commands.utils;


import br.net.brjdevs.d4rk.l1ght.utils.Config;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import br.net.brjdevs.d4rk.l1ght.utils.command.CommandRegister;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import javafx.util.Pair;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.*;

public class CmdHelp implements Command {

    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {
        HashMap<String, Command> hashMap = CommandRegister.getCommandMap();
        List<String> argsl = Arrays.asList(args);


        if(argsl.size() >= 1){
            if(hashMap.get(argsl.get(0)) == null) {
                event.getChannel().sendMessage("Unknown Command").queue();
            }else{
                Command cmd = hashMap.get(argsl.get(0));

                String usage = "";
                if(cmd.cmdArgs() != null) {
                    for(Pair<String, Boolean> p : cmd.cmdArgs()) {
                        if (p.getValue()) {
                            usage = usage + " (" + p.getKey() + ") ";
                        } else {
                            usage = usage + " [" + p.getKey() + "] ";
                        }
                    }
                }

                String description = "**Name: **" + cmd.cmdName() + "\n" +
                        "**Description: **" + cmd.cmdDescription() + "\n" +
                        "**Category: **" + cmd.cmdCategory() + "\n" +
                        "**Permission: **" + cmd.cmdPerm().toString() + "\n" +
                        "**Usage: **" + "+" + cmd.cmdName() + usage;

                MessageEmbed embed = new EmbedBuilder()
                        .setTitle("Command Information:", null)
                        .setDescription(description)
                        .setFooter("Requested by: " + event.getAuthor().getName(), event.getAuthor().getAvatarUrl())
                        .setColor(event.getMember().getColor())
                        .build();

                event.getChannel().sendMessage(embed).queue();

            }

        }else{
            HashMap<String, List<Command>> helpMap = new HashMap<>();

            for (String i : hashMap.keySet()) {
                if (hashMap.get(i).cmdCategory() != null) {
                    helpMap.computeIfAbsent(hashMap.get(i).cmdCategory(), k -> new ArrayList<Command>());
                    helpMap.get(hashMap.get(i).cmdCategory()).add(hashMap.get(i));
                }
            }

            String description = "";
            for (String i : helpMap.keySet()) {
                description = description + "\n**" + i + "**: ";
                for (Command c : helpMap.get(i)) {
                    description = description + "`" + c.cmdName() + "`, ";
                }
                description = description.substring(0, description.length() - 2);
            }
            description = description+String.format("\n\nUsage exemple: %sb64 encode hello\nFor better information, do %shelp (command)", Config.prefix, Config.prefix);


            MessageEmbed embed = new EmbedBuilder()
                    .setAuthor("L1ght's Help", null, event.getJDA().getSelfUser().getAvatarUrl())
                    .setDescription(description)
                    .setFooter("Requested by: " + event.getAuthor().getName(), event.getAuthor().getAvatarUrl())
                    .setColor(event.getMember().getColor())
                    .build();

            event.getChannel().sendMessage(embed).queue();
        }
    }

    @Override
    public String cmdName() {
        return "help";
    }

    @Override
    public String cmdDescription() {
        return "Shows this.";
    }

    @Override
    public String cmdCategory() {
        return "Utils";
    }

    @Override
    public List<L1ghtPerms> cmdPerm() {
        return Arrays.asList(L1ghtPerms.BASE);
    }

    @Override
    public List<Pair<String, Boolean>> cmdArgs() {
        List<Pair<String, Boolean>> list = new ArrayList<>();
        list.add(new Pair<>("Command", false));
        return list;
    }
}
