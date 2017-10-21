package br.net.brjdevs.d4rk.l1ght.commands.bot_owner;

import br.net.brjdevs.d4rk.l1ght.handlers.PermissionHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.data.GlobalDataHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.data.GuildDataHandler;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import javafx.util.Pair;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.*;

public class CmdPerm implements Command {
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {

        if(args[0].toLowerCase().equals("add")) {

            if (args.length < 3) {
                event.getChannel().sendMessage("**Error: ** Missing arguments!").queue();  return;
            }
            if (!args[1].equalsIgnoreCase("global") && !args[1].equalsIgnoreCase("guild")) {
                event.getChannel().sendMessage("**Error: ** Unknown option! Must be either global or guild.").queue();  return;
            }

            L1ghtPerms perm;
            try{
                perm = L1ghtPerms.valueOf(args[2]);
            }catch(Exception e){
                event.getChannel().sendMessage("**Error: **Unknown perm!").queue(); return;
            }

            User usr = stringToUser(String.join("", Arrays.copyOfRange(args, 3, args.length)), event);
            if(usr == null) {
                event.getChannel().sendMessage("**Error: **Couldn't find a user that matches the arguments.").queue(); return;
            }

            if(args[1].equalsIgnoreCase("global")) {
                PermissionHandler.addGlobalPerm(usr.getId(), perm);
            }
            if(args[1].equalsIgnoreCase("guild")) {
                PermissionHandler.addGuildPerm(usr.getId(),event.getGuild().getId(), perm);
            }



        }else if(args[0].toLowerCase().equals("remove")) {

            if (args.length < 3) {
                event.getChannel().sendMessage("**Error: ** Missing arguments!").queue();  return;
            }
            if (!args[1].equalsIgnoreCase("global") && !args[1].equalsIgnoreCase("guild")) {
                event.getChannel().sendMessage("**Error: ** Unknown option! Must be either global or guild.").queue();  return;
            }

            L1ghtPerms perm;
            try{
                perm = L1ghtPerms.valueOf(args[2]);
            }catch(Exception e){
                event.getChannel().sendMessage("**Error: **Unknown perm!").queue(); return;
            }

            User usr = stringToUser(String.join("", Arrays.copyOfRange(args, 3, args.length)), event);
            if(usr == null) {
                event.getChannel().sendMessage("**Error: **Couldn't find a user that matches the arguments.").queue(); return;
            }

            if(args[1].equalsIgnoreCase("global")) {
                PermissionHandler.removeGlobalPerm(usr.getId(), perm);
            }
            if(args[1].equalsIgnoreCase("guild")) {
                PermissionHandler.removeGuildPerm(usr.getId(),event.getGuild().getId(), perm);
            }

        }else {

            User usr = stringToUser(String.join(" ",args), event);
            if(usr == null) {
                event.getChannel().sendMessage("**Error: **Couldn't find a user that matches the arguments.").queue();
                return;
            }

            String guild = "";
            HashMap<String, List<L1ghtPerms>> guildHashMap = GuildDataHandler.loadGuildPerms(event.getGuild().getId());

            if(guildHashMap.get(usr.getId()) == null) {
                guild = "Not yet generated.";
            }else {
                for (L1ghtPerms lp : guildHashMap.get(usr.getId())) {
                    guild = guild+lp.name()+", ";
                }
                guild = guild.substring(0, guild.length()-2);
            }

            String global = "";
            HashMap<String, List<L1ghtPerms>> globalHashMap = GlobalDataHandler.loadGlobalPerms();

            if(globalHashMap.get(usr.getId()) == null) {
                global = "Not generated.";
            }else {
                for (L1ghtPerms lp : globalHashMap.get(usr.getId())) {
                    global = global+lp.name()+", ";
                }
                global = global.substring(0, global.length()-2);
            }

            MessageEmbed embed = new EmbedBuilder()
                    .setAuthor(usr.getName()+"'s Permissions", null, usr.getAvatarUrl())
                    .setDescription(
                            "**Guild Permissions: **"+
                            "```"+guild+"```"+
                            "**Global Permissions: **"+
                            "```"+global+"```"
                    )
                    .setColor(event.getMember().getColor())
                    .setFooter("Requested by: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl())
                    .build();

            event.getChannel().sendMessage(embed).queue();

        }

    }

    @Override
    public String cmdName() {
        return "perm";
    }

    @Override
    public String cmdDescription() {
        return "Used to manipulate users perms.";
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
        list.add(new Pair<>("user or add/remove", true));
        list.add(new Pair<>("guild/global", false));
        list.add(new Pair<>("perm", false));
        list.add(new Pair<>("user", false));
        return list;
    }

    private User stringToUser(String shit, GuildMessageReceivedEvent event) {
        User usr = null;

        try{
            usr = event.getJDA().getUserById(shit);
        }catch(Exception ignored){}

        List ata = event.getJDA().getUsersByName(shit, false);
        if (ata.size() >= 1) {
            usr = (User) ata.get(0);
        }
        List ata2 = event.getGuild().getMembersByNickname(shit, false);
        if (ata2.size() >= 1) {
            usr = ((Member) ata2.get(0)).getUser();
        }

        if (event.getMessage().getMentionedUsers().size() >= 1) {
            usr = event.getMessage().getMentionedUsers().get(0);
        }

        return usr;
    }
}
