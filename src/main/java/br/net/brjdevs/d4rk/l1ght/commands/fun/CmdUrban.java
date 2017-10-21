package br.net.brjdevs.d4rk.l1ght.commands.fun;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import com.mashape.unirest.http.Unirest;
import javafx.util.Pair;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdUrban implements Command {
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {

        String ata = String.join("%20", args);
        JSONObject urban;

        try{
        urban = Unirest.get("http://api.urbandictionary.com/v0/define?term="+ata)
                .asJsonAsync().get().getBody().getObject();
        }catch (Exception e) {
            event.getChannel().sendMessage("**Error: **"+e.getMessage()).queue();
            return;
        }

        urban = ((JSONArray) urban.get("list")).getJSONObject(0);

        MessageEmbed embed = new EmbedBuilder()
                .setAuthor("Urban dictionary result for: "+urban.get("word"), urban.getString("permalink"), "https://lh5.googleusercontent.com/-rY97dP0iEo0/AAAAAAAAAAI/AAAAAAAAAGA/xm1HYqJXdMw/s0-c-k-no-ns/photo.jpg")
                .setTitle("by: "+urban.getString("author"), "http://www.urbandictionary.com/author.php?author="+urban.getString("author").replace(" ", "%20"))
                .setDescription(urban.getString("definition")+"\n")
                .addField("Examples:", urban.getString("example"), false)
                .addField(":thumbsup:", urban.get("thumbs_up").toString(), true)
                .addField(":thumbsdown:", urban.get("thumbs_down").toString(), true)
                .setFooter("Requested by: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl())
                .setColor(event.getMember().getColor())
                .build();

        event.getChannel().sendMessage(embed).queue();

    }

    @Override
    public String cmdName() {
        return "urban";
    }

    @Override
    public String cmdDescription() {
        return "Search for stuff in the Urban Dictionary";
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
        List<Pair<String, Boolean>> args =  new ArrayList<>();
        args.add(new Pair<>("Search", true));
        return args;
    }
}
