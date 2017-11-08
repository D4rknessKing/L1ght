package br.net.brjdevs.d4rk.l1ght.commands.games;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import br.net.brjdevs.d4rk.l1ght.utils.command.SubCommand;
import com.mashape.unirest.http.Unirest;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

public class CmdSplatoon2 {

    @Command(name="splatoon2", description = "", category = "Games", usage = "null", perms = {L1ghtPerms.BASE})
    public static void splatoon2(GuildMessageReceivedEvent event, String[] args) {}

    @SubCommand(name = "current", description = "Shows what stages are currently playable in Splatoon2.", usage = "", perms = {L1ghtPerms.BASE})
    public static void current(GuildMessageReceivedEvent event, String[] args) {
        try{
            JSONObject splatinfo = Unirest.get("https://splatoon2.ink/data/schedules.json").asJson().getBody().getObject();
            JSONObject regular = splatinfo.getJSONArray("regular").getJSONObject(0);
            JSONObject regular2 = splatinfo.getJSONArray("regular").getJSONObject(1);
            JSONObject ranked = splatinfo.getJSONArray("gachi").getJSONObject(0);
            JSONObject ranked2 = splatinfo.getJSONArray("gachi").getJSONObject(1);
            JSONObject league = splatinfo.getJSONArray("league").getJSONObject(0);
            JSONObject league2 = splatinfo.getJSONArray("league").getJSONObject(1);
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("<:splat1:377891548853043231><:splat2:377891550044356612><:splat3:377891550367055872> Current Stages Information: ", null)
                    .setFooter("Information provided by: https://splatoon2.ink/", "https://splatoon2.ink/assets/img/apple-touch-icon.89707e.png")
                    .addField("<:splatregular:377866148869308427> Regular Battle: ", "**Now: "+regular.getJSONObject("rule").getString("name")+"**"+"\n"+regular.getJSONObject("stage_a").getString("name")+"\n"+regular.getJSONObject("stage_b").getString("name")+"\n\n"+"**Next: "+regular2.getJSONObject("rule").getString("name")+"**"+"\n"+regular2.getJSONObject("stage_a").getString("name")+"\n"+regular2.getJSONObject("stage_b").getString("name"), true)
                    .addField("<:splatranked:377866085250105355> Ranked Battle: ", "**Now: "+ranked.getJSONObject("rule").getString("name")+"**"+"\n"+ranked.getJSONObject("stage_a").getString("name")+"\n"+ranked.getJSONObject("stage_b").getString("name")+"\n\n"+"**Next: "+ranked2.getJSONObject("rule").getString("name")+"**"+"\n"+ranked2.getJSONObject("stage_a").getString("name")+"\n"+ranked2.getJSONObject("stage_b").getString("name"), true)
                    .addField("<:splatleague:377866085455757332> League Battle: ", "**Now: "+league.getJSONObject("rule").getString("name")+"**"+"\n"+league.getJSONObject("stage_a").getString("name")+"\n"+league.getJSONObject("stage_b").getString("name")+"\n\n"+"**Next: "+league2.getJSONObject("rule").getString("name")+"**"+"\n"+league2.getJSONObject("stage_a").getString("name")+"\n"+league2.getJSONObject("stage_b").getString("name"), true)
                    .addField("", "**Maps and modes update in "+Math.abs((regular.getLong("end_time")-(System.currentTimeMillis()/1000))/60)+" minutes.**", false)
                    .setColor(event.getMember().getColor())
                    .build();
            event.getChannel().sendMessage(embed).queue();
        }catch (Exception e){e.printStackTrace();}
    }

    @SubCommand(name = "salmon", description = "Shows the Salmon Run schedule and current gear.", usage = "", perms = {L1ghtPerms.BASE})
    public static void salmon(GuildMessageReceivedEvent event, String[] args) {
        try{
            JSONObject calendar = Unirest.get("https://splatoon2.ink/data/salmonruncalendar.json").asJson().getBody().getObject();
            JSONObject first = calendar.getJSONArray("schedules").getJSONObject(0);
            JSONObject second = calendar.getJSONArray("schedules").getJSONObject(1);
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("<:splat1:377891548853043231><:splat2:377891550044356612><:splat3:377891550367055872> Salmon Run Information: ", null)
                    .setFooter("Information provided by: https://splatoon2.ink/", "https://splatoon2.ink/assets/img/apple-touch-icon.89707e.png")
                    .setColor(event.getMember().getColor());
            if((System.currentTimeMillis()/1000) > first.getLong("start_time") && (System.currentTimeMillis()/1000) < first.getLong("end_time")){
                embed.addField("Open: Ends in "+Math.abs((first.getLong("end_time")-(System.currentTimeMillis()/1000))/60)+" minutes!", "**Stage: **"+first.getJSONObject("stage").getString("name")+"\n"+ "**Weapons: **"+"\n"
                        +" - "+first.getJSONArray("weapons").getJSONObject(0).getString("name")+"\n"
                        +" - "+first.getJSONArray("weapons").getJSONObject(1).getString("name")+"\n"
                        +" - "+first.getJSONArray("weapons").getJSONObject(2).getString("name")+"\n"
                        +" - "+first.getJSONArray("weapons").getJSONObject(3).getString("name")
                        ,true);
                embed.addBlankField(true);
                embed.addField("Next: Starts in "+Math.abs(((System.currentTimeMillis()/1000)-second.getLong("start_time"))/60)+" minutes!", "**Stage: **"+second.getJSONObject("stage").getString("name")+"\n"+ "**Weapons: **"+"\n"
                                +" - "+second.getJSONArray("weapons").getJSONObject(0).getString("name")+"\n"
                                +" - "+second.getJSONArray("weapons").getJSONObject(1).getString("name")+"\n"
                                +" - "+second.getJSONArray("weapons").getJSONObject(2).getString("name")+"\n"
                                +" - "+second.getJSONArray("weapons").getJSONObject(3).getString("name")
                        ,true);
            }else if((System.currentTimeMillis()/1000) < first.getLong("start_time")){
                embed.addField("Next: Starts in "+Math.abs(((System.currentTimeMillis()/1000)-first.getLong("start_time"))/60)+" minutes!", "**Stage: **"+first.getJSONObject("stage").getString("name")+"\n"+ "**Weapons: **"+"\n"
                                +" - "+first.getJSONArray("weapons").getJSONObject(0).getString("name")+"\n"
                                +" - "+first.getJSONArray("weapons").getJSONObject(1).getString("name")+"\n"
                                +" - "+first.getJSONArray("weapons").getJSONObject(2).getString("name")+"\n"
                                +" - "+first.getJSONArray("weapons").getJSONObject(3).getString("name")
                        ,true);
            }else{
                embed.addField("Next: Starts in "+Math.abs(((System.currentTimeMillis()/1000)-second.getLong("start_time"))/60)+" minutes!", "**Stage: **"+second.getJSONObject("stage").getString("name")+"\n"+ "**Weapons: **"+"\n"
                                +" - "+second.getJSONArray("weapons").getJSONObject(0).getString("name")+"\n"
                                +" - "+second.getJSONArray("weapons").getJSONObject(1).getString("name")+"\n"
                                +" - "+second.getJSONArray("weapons").getJSONObject(2).getString("name")+"\n"
                                +" - "+second.getJSONArray("weapons").getJSONObject(3).getString("name")
                        ,true);
            }
            event.getChannel().sendMessage(embed.build()).queue();
        }catch (Exception e){e.printStackTrace();}
    }

    @SubCommand(name = "splatfest", description = "Shows information about confirmed Splatfests.", usage = "", perms = {L1ghtPerms.BASE})
    public static void splatfest(GuildMessageReceivedEvent event, String[] args) {
        try{
            JSONObject splatinfo = Unirest.get("https://splatoon2.ink/data/festivals.json").asJson().getBody().getObject();
            JSONArray na = splatinfo.getJSONObject("na").getJSONArray("festivals");
            JSONArray eu = splatinfo.getJSONObject("eu").getJSONArray("festivals");
            JSONArray jp = splatinfo.getJSONObject("jp").getJSONArray("festivals");
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("<:splat1:377891548853043231><:splat2:377891550044356612><:splat3:377891550367055872> Splatfest Information: ", null)
                    .setFooter("Information provided by: https://splatoon2.ink/", "https://splatoon2.ink/assets/img/apple-touch-icon.89707e.png")
                    .setColor(event.getMember().getColor());
            for(Object o : na){
                JSONObject jo = (JSONObject) o;
                if((System.currentTimeMillis()/1000) > jo.getJSONObject("times").getLong("start") && (System.currentTimeMillis()/1000) < jo.getJSONObject("times").getLong("end")) {
                    embed.addField(":flag_us: North America", "**Open: **Ends in "+Math.abs((jo.getJSONObject("times").getLong("end")-(System.currentTimeMillis()/1000))/60)+" minutes!", false);
                }else if((System.currentTimeMillis()/1000) < ((JSONObject) o).getJSONObject("times").getLong("start")){
                    embed.addField(":flag_us: North America", "**Next: **Starts in "+Math.abs(((System.currentTimeMillis()/1000)-jo.getJSONObject("times").getLong("start"))/60)+" minutes!", false);
                }
            }
            for(Object o : eu){
                JSONObject jo = (JSONObject) o;
                if((System.currentTimeMillis()/1000) > jo.getJSONObject("times").getLong("start") && (System.currentTimeMillis()/1000) < jo.getJSONObject("times").getLong("end")) {
                    embed.addField(":flag_eu: Europe", "**Open: **Ends in "+Math.abs((jo.getJSONObject("times").getLong("end")-(System.currentTimeMillis()/1000))/60)+" minutes!", false);
                }else if((System.currentTimeMillis()/1000) < ((JSONObject) o).getJSONObject("times").getLong("start")){
                    embed.addField(":flag_eu: Europe", "**Next: **Starts in "+Math.abs(((System.currentTimeMillis()/1000)-jo.getJSONObject("times").getLong("start"))/60)+" minutes!", false);
                }
            }
            for(Object o : jp){
                JSONObject jo = (JSONObject) o;
                if((System.currentTimeMillis()/1000) > jo.getJSONObject("times").getLong("start") && (System.currentTimeMillis()/1000) < jo.getJSONObject("times").getLong("end")) {
                    embed.addField(":flag_jp: Japan", "**Open: **Ends in "+Math.abs((jo.getJSONObject("times").getLong("end")-(System.currentTimeMillis()/1000))/60)+" minutes!", false);
                }else if((System.currentTimeMillis()/1000) < ((JSONObject) o).getJSONObject("times").getLong("start")){
                    embed.addField(":flag_jp: Japan", "**Next: **Starts in "+Math.abs(((System.currentTimeMillis()/1000)-jo.getJSONObject("times").getLong("start"))/60)+" minutes!", false);
                }
            }
            if(embed.build().getFields().size() < 1){
                embed.setDescription("**There aren't any splatfests announced or going on.**");
            }
            event.getChannel().sendMessage(embed.build()).queue();
        }catch (Exception e){e.printStackTrace();}
    }
}

