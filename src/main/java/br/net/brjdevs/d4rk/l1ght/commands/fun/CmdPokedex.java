package br.net.brjdevs.d4rk.l1ght.commands.fun;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.RandomUtils;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.util.Pair;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdPokedex implements Command {
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {
        try{
            JSONObject json = Unirest.get("http://pokeapi.co/api/v2/pokemon/"+args[0].toLowerCase()+"/").asJson().getBody().getObject();

            String types = "";
            for(Object jo : json.getJSONArray("types")){
                types = types+RandomUtils.capitalizeFirst(((JSONObject) jo).getJSONObject("type").getString("name"))+", ";
            }
            types = types.substring(0, types.length()-2);

            String stats = "";
            for(Object jo : json.getJSONArray("stats")){
                stats = stats+"**    "+RandomUtils.capitalizeFirst(((JSONObject) jo).getJSONObject("stat").getString("name"))+": **"+((JSONObject) jo).getInt("base_stat")+"\n";
            }
            stats = stats.substring(0, stats.length()-2);

            String appears = "";
            for(Object jo : json.getJSONArray("game_indices")){
                appears = appears+RandomUtils.capitalizeFirst(((JSONObject) jo).getJSONObject("version").getString("name"))+", ";
            }
            appears = appears.substring(0, appears.length()-2);

            String image = null;
            if(String.valueOf(json.getInt("id")).length() == 1){
                image = "http://assets.pokemon.com/assets/cms2/img/pokedex/detail/00"+json.getInt("id")+".png";
            }else if(String.valueOf(json.getInt("id")).length() == 2){
                image = "http://assets.pokemon.com/assets/cms2/img/pokedex/detail/0"+json.getInt("id")+".png";
            }else if(String.valueOf(json.getInt("id")).length() == 3){
                image = "http://assets.pokemon.com/assets/cms2/img/pokedex/detail/"+json.getInt("id")+".png";
            }

            MessageEmbed embed = new EmbedBuilder()
                    .setTitle(RandomUtils.capitalizeFirst(json.getString("name")) +" Information:")
                    .setDescription("**ID: **"+json.getInt("id")+"\n**Types: **"+types+"\n**Stats: **\n"+stats+"\n**Appears on: **"+appears)
                    .setColor(new Color(RandomUtils.getPokemonTypeColor(json.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name"))))
                    .setThumbnail(image)
                    .setFooter("Requested by: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl())
                    .build();

            event.getChannel().sendMessage(embed).queue();


        }catch (UnirestException e){
            event.getChannel().sendMessage("**An unknown error has occurred while doing the HTTP request to the PokeAPI**").queue();
            e.printStackTrace();
        }
    }

    @Override
    public String cmdName() {
        return "pokedex";
    }

    @Override
    public String cmdDescription() {
        return "Shows some information about the given pokemon.";
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
        list.add(new Pair<>("Pokemon Name/ID", true));
        return list;
    }



}
