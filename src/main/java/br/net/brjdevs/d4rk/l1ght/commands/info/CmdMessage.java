package br.net.brjdevs.d4rk.l1ght.commands.info;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import javafx.util.Pair;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdMessage implements Command {
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {

        String shit = String.join(" ",args);


        Message mem;
        try{
            mem = event.getChannel().getMessageById(shit).complete();
        }catch(ErrorResponseException e){
            event.getChannel().sendMessage("**Error: **Couldn't find a message that matches the arguments in this channel.").queue();
            return;
        }

        String edited = "";
        if(mem.getEditedTime() != null) {
            edited = mem.getEditedTime().format(DateTimeFormatter.ISO_DATE_TIME).replaceAll("[^0-9.:-]", " ");
        }else{
            edited = "Not edited.";
        }

        Color color;
        if(event.getGuild().getMember(mem.getAuthor()) != null) {
            color = event.getGuild().getMember(mem.getAuthor()).getColor();
        }else{
            color = event.getMember().getColor();
        }


        MessageEmbed embed = new EmbedBuilder()
                .addField("Message information: ", "**ID: **"+mem.getId(), true)
                .addField("Created by: ", mem.getAuthor().getAsMention(), true)
                .addField("Created at: ", mem.getCreationTime().format(DateTimeFormatter.ISO_DATE_TIME).replaceAll("[^0-9.:-]", " "), true)
                .addField("Edited at: ", edited, true)
                .addField("Content: ", mem.getRawContent(), false)
                .setThumbnail(mem.getAuthor().getAvatarUrl())
                .setFooter("Requested by: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl())
                .setColor(color)
                .build();

        event.getChannel().sendMessage(embed).queue();


    }

    @Override
    public String cmdName() {
        return "message";
    }

    @Override
    public String cmdDescription() {
        return "Gives some information about the given message.";
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
        list.add(new Pair<>("ID", true));
        return list;
    }
}
