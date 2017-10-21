package br.net.brjdevs.d4rk.l1ght.commands.fun;

import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import javafx.util.Pair;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.Color;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CmdDoge implements Command {
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {

        String ata = String.join("/", args);
        Random random = new Random();

        try{
            ata = URLEncoder.encode(ata, "UTF-8").replace("%2F", "/");
        }catch (Exception e) {

        }

        MessageEmbed embed = new EmbedBuilder()
                .setAuthor("Dogified!", null, "http://i1.kym-cdn.com/entries/icons/facebook/000/013/564/aP2dv.jpg")
                .setImage("http://dogr.io/"+ata+".png?split=false")
                .setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                .setFooter("Requested by: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl())
                .build();
        event.getChannel().sendMessage(embed).queue();

    }

    @Override
    public String cmdName() {
        return "doge";
    }

    @Override
    public String cmdDescription() {
        return "Generate an awesome doge styled meme using your input!";
    }

    @Override
    public String cmdCategory() {
        return "Fun";
    }

    @Override
    public List<L1ghtPerms> cmdPerm() {
        return Arrays.asList(L1ghtPerms.BASE);
    }

    @Override
    public List<Pair<String, Boolean>> cmdArgs() {
        List<Pair<String, Boolean>> list = new ArrayList<>();
        list.add(new Pair<>("Text", true));
        return list;
    }
}
