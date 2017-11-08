package br.net.brjdevs.d4rk.l1ght.commands.utils;


import br.net.brjdevs.d4rk.l1ght.utils.Config;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import br.net.brjdevs.d4rk.l1ght.utils.command.CommandRegister;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.RegisteredCommand;
import br.net.brjdevs.d4rk.l1ght.utils.command.RegisteredSubCommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.*;

public class CmdHelp {

    @Command(name="help", description = "Shows this.", category = "Utils", usage = "[command]", perms = {L1ghtPerms.BASE})
    public static void help(GuildMessageReceivedEvent event, String[] args) {
        HashMap<String, RegisteredCommand> hashMap = CommandRegister.getCommandMap();
        List<String> argsl = Arrays.asList(args);


        if(argsl.size() >= 1){
            if(hashMap.get(argsl.get(0)) == null) {
                event.getChannel().sendMessage("**Error: **Unknown Command").queue();
            }else{
                RegisteredCommand cmd = hashMap.get(argsl.get(0));

                String description = "";
                if(cmd.getDescription().length() > 0){
                    description = "**" + cmd.getDescription() + "**\n";
                }
                Field pcf = null;
                if(!cmd.getUsage().equals("null")){
                    String permission = "**Permission: **" + cmd.getPerms().toString();
                    pcf = new Field((Config.hardPrefix + cmd.getName() + " " + cmd.getUsage()).replace("null", ""), description  + permission, false);

                }

                String description2 = "";
                if(pcf == null && description.length() > 0){
                    description2 = description;
                }

                List<Field> scf = new ArrayList<>();
                if(cmd.getSubCommands() != null) {
                for(RegisteredSubCommand s : cmd.getSubCommands().values()){
                        String subdescription = "";
                        if(s.getDescription().length() > 0){
                            subdescription = "    **" + s.getDescription() + "**\n";
                        }
                        scf.add(new Field(Config.hardPrefix + cmd.getName() + " " + s.getName() + " " + s.getUsage(), subdescription + "**Permission: **" + s.getPerms().toString() , false));
                    }
                }


                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Command Information:", null)
                        .setFooter("Requested by: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl())
                        .setDescription(description2 + "**Category: **" + cmd.getCategory())
                        .setColor(event.getMember().getColor())
                        .addField(pcf);

                for(Field f : scf){
                    embed.addField(f);
                }

                event.getChannel().sendMessage(embed.build()).queue();

            }

        }else{
            HashMap<String, List<RegisteredCommand>> helpMap = new HashMap<>();

            for (String i : hashMap.keySet()) {
                if (!hashMap.get(i).getCategory().equals("")) {
                    helpMap.computeIfAbsent(hashMap.get(i).getCategory(), k -> new ArrayList<RegisteredCommand>());
                    helpMap.get(hashMap.get(i).getCategory()).add(hashMap.get(i));
                }
            }

            String description = "";
            for (String i : helpMap.keySet()) {
                description = description + "\n**" + i + "**: ";
                for (RegisteredCommand c : helpMap.get(i)) {
                    description = description + "`" + c.getName() + "`, ";
                }
                description = description.substring(0, description.length() - 2);
            }
            description = description+String.format("\n\nUsage exemple: %sb64 encode hello\nFor better information, do %shelp (command)", Config.hardPrefix, Config.hardPrefix);


            MessageEmbed embed = new EmbedBuilder()
                    .setAuthor("L1ght's Help", null, event.getJDA().getSelfUser().getAvatarUrl())
                    .setDescription(description)
                    .setFooter("Requested by: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl())
                    .setColor(event.getMember().getColor())
                    .build();

            event.getChannel().sendMessage(embed).queue();
        }
    }

}
