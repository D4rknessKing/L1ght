package br.net.brjdevs.d4rk.l1ght.commands.utils;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;

public class CmdColor  {

    @Command(name = "color", description = "Gives you information about a given color", category = "Utils", usage = "(hex/red) [green] [blue]", perms = {L1ghtPerms.BASE})
    public static void color(GuildMessageReceivedEvent event, String[] args) {

        JSONObject json = null;

        if(args.length == 1 && args[0].length() == 6) {
            try{
                json = Unirest.get("http://www.thecolorapi.com/id?hex="+args[0]).asJson().getBody().getObject();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(args.length == 3){
            int r;
            int g;
            int b;

            try{
                r = Integer.valueOf(args[0]);
                g = Integer.valueOf(args[1]);
                b = Integer.valueOf(args[2]);

                if(!(r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255)){
                    json = Unirest.get(String.format("http://www.thecolorapi.com/id?rgb=(%s,%s,%s)", r, g, b)).asJson().getBody().getObject();
                }

            }catch(Exception e){
                if(e instanceof UnirestException) {
                    e.printStackTrace();
                }
            }
        }else{
            event.getChannel().sendMessage("**Error: **Invalid arguments!").queue();
            return;
        }

        if(json != null){
            JSONObject rgb = json.getJSONObject("rgb");
            String desc =
                    "**Name: **"+json.getJSONObject("name").getString("value")+"\n"+
                    "**Hex Code: **"+json.getJSONObject("hex").getString("value")+"\n"+
                    "**RGB: **"+json.getJSONObject("rgb").getString("value").substring(3)+"\n"+
                    "**HSL: **"+json.getJSONObject("hsl").getString("value").substring(3)+"\n"+
                    "**HSV: **"+json.getJSONObject("hsv").getString("value").substring(3);

            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("Color information:")
                    .setDescription(desc)
                    .setColor(new Color(rgb.getInt("r"), rgb.getInt("g"), rgb.getInt("b")))
                    .setFooter("Requested by: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl())
                    .build();
            event.getChannel().sendMessage(embed).queue();
        }else{
            event.getChannel().sendMessage("**Error: **Unkwnown color!").queue();
        }

    }

}
