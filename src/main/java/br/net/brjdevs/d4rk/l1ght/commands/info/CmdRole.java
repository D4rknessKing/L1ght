package br.net.brjdevs.d4rk.l1ght.commands.info;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import javafx.util.Pair;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdRole implements Command {
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {

        String shit = String.join(" ",args);

        Role role = null;

        try{
            role = event.getGuild().getRoleById(shit);
        }catch(Exception ignored){}

        List ata = event.getGuild().getRolesByName(shit, false);
        if (ata.size() >= 1) {
            role = (Role) ata.get(0);
        }

        if(role == null) { event.getChannel().sendMessage("**Error: **Couldn't find a role that matches the arguments.").queue(); return; }

        String members = "";
        int mem = 0;
        for (Member m : event.getGuild().getMembers()) {
            if(m.getRoles().contains(role)) {
                members = members + m.getAsMention() + " ";
                mem++;
            }
        }

        String permissions = "";
        for (Permission p : role.getPermissions()) {
            permissions = permissions + p.getName() + ", ";
        }
        if(role.getPermissions().size() >= 1) permissions = permissions.substring(0, permissions.length()-2);

        MessageEmbed embed = new EmbedBuilder()
                .setDescription("**Role information for: **"+role.getAsMention())
                .addField(role.getName()+":", "**ID: **"+role.getId(), true)
                .addField("Created at: ", role.getCreationTime().format(DateTimeFormatter.ISO_DATE_TIME).replaceAll("[^0-9.:-]", " "), true)
                .addField("Members ("+mem+"): ", members, false)
                .addField("Permissions ("+role.getPermissions().size()+"): ", permissions, false)
                .setColor(role.getColor())
                .setFooter("Requested by: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl())
                .build();

        event.getChannel().sendMessage(embed).queue();

    }

    @Override
    public String cmdName() {
        return "role";
    }

    @Override
    public String cmdDescription() {
        return "Gives some information about the given role.";
    }

    @Override
    public String cmdCategory() {
        return "Info";
    }

    @Override
    public List<L1ghtPerms> cmdPerm() {
        return Arrays.asList(L1ghtPerms.BASE);
    }

    @Override
    public List<Pair<String, Boolean>> cmdArgs() {
        List<Pair<String, Boolean>> list = new ArrayList<>();
        list.add(new Pair<>("Role Name/ID", true));
        return list;
    }
}
