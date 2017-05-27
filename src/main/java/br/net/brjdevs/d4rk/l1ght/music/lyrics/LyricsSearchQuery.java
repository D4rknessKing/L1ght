package br.net.brjdevs.d4rk.l1ght.music.lyrics;

import com.mashape.unirest.http.Unirest;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONObject;

public class LyricsSearchQuery {

    public String guildId;
    public String userId;
    private JSONObject js1;
    private JSONObject js2;
    private JSONObject js3;
    private JSONObject js4;
    private JSONObject js5;
    private GuildMessageReceivedEvent event;
    private Message msg;
    private boolean gss;

    public LyricsSearchQuery(GuildMessageReceivedEvent ent, JSONObject j1, JSONObject j2, JSONObject j3, JSONObject j4 , JSONObject j5, boolean guess) {
        event = ent;
        guildId = event.getGuild().getId();
        userId = event.getAuthor().getId();
        js1 = j1;
        js2 = j2;
        js3 = j3;
        js4 = j4;
        js5 = j5;
        if(js1 == null || js1 == JSONObject.NULL) js1 = new JSONObject().put("band", "Not Found").put("title", "Not Found").put("id", "null");
        if(js2 == null || js2 == JSONObject.NULL) js2 = new JSONObject().put("band", "Not Found").put("title", "Not Found").put("id", "null");
        if(js3 == null || js3 == JSONObject.NULL) js3 = new JSONObject().put("band", "Not Found").put("title", "Not Found").put("id", "null");
        if(js4 == null || js4 == JSONObject.NULL) js4 = new JSONObject().put("band", "Not Found").put("title", "Not Found").put("id", "null");
        if(js5 == null || js5 == JSONObject.NULL) js5 = new JSONObject().put("band", "Not Found").put("title", "Not Found").put("id", "null");
        if(guess){
            gss = true;
        }
    }

    public void start(){
        msg = event.getChannel().sendMessage("**Please choose one of the following: **\n" +
                "**[1]:** `"+ js1.getString("band") + " - " + js1.getString("title") + "`\n" +
                "**[2]:** `"+ js2.getString("band") + " - " + js2.getString("title") + "`\n" +
                "**[3]:** `"+ js3.getString("band") + " - " + js3.getString("title") + "`\n" +
                "**[4]:** `"+ js4.getString("band") + " - " + js4.getString("title") + "`\n" +
                "**[5]:** `"+ js5.getString("band") + " - " + js5.getString("title") + "`\n" +
                "Type 1-5 to choose one of the lyrics. Type anything else to cancel the query."
        ).complete();
        LyricsUtils.queryList.add(this);
    }

    public void choose(int i) {
        if(i == 1){
            if(!js1.getString("id").equals("null")) {
                proceed(js1.getString("id"));
            }else{
                msg.editMessage("**Error: **Unknown option, canceling query...").queue();
            }
        }else if(i == 2){
            if(!js2.getString("id").equals("null")) {
                proceed(js2.getString("id"));
            }else{
                msg.editMessage("**Error: **Unknown option, canceling query...").queue();
            }
        }else if(i == 3){
            if(!js3.getString("id").equals("null")) {
                proceed(js3.getString("id"));
            }else{
                msg.editMessage("**Error: **Unknown option, canceling query...").queue();
            }
        }else if(i == 4){
            if(!js4.getString("id").equals("null")) {
                proceed(js4.getString("id"));
            }else{
                msg.editMessage("**Error: **Unknown option, canceling query...").queue();
            }
        }else if(i == 5){
            if(!js5.getString("id").equals("null")) {
                proceed(js5.getString("id"));
            }else{
                msg.editMessage("**Error: **Unknown option, canceling query...").queue();
            }
        }else{
            msg.editMessage("**Error: **Unknown option, canceling query...").queue();
        }
        if(LyricsUtils.queryList.contains(this)) {
            LyricsUtils.queryList.remove(this);
        }
    }

    public void proceed(String id) {
        try{
            JSONObject lyrics = Unirest.get("https://api.vagalume.com.br/search.php?apikey=660a4395f992ff67786584e238f501aa&musid="+id).asJsonAsync().get().getBody().getObject();

            String thumb = lyrics.getJSONObject("art").getString("url").replace("https://www.vaga", "http://www.s2.vaga");
            thumb = thumb + "images/" + thumb.substring(thumb.lastIndexOf(".com.br/")+8);
            thumb = thumb.substring(0, thumb.length()-1) + ".jpg";

            MessageEmbed embed = new EmbedBuilder()
                    .setAuthor(
                            lyrics.getJSONObject("art").getString("name") + " - " + lyrics.getJSONArray("mus").getJSONObject(0).getString("name"),
                            null,
                            "https://pbs.twimg.com/profile_images/852191512444227584/RIPNzc9T.jpg")
                    .setTitle("Lyrics provided by https://www.vagalume.com.br", null)
                    .setThumbnail(thumb)
                    .setDescription(lyrics.getJSONArray("mus").getJSONObject(0).getString("text"))
                    .setColor(event.getMember().getColor())
                    .setFooter("Requested by: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl())
                    .build();
            if(msg != null) {
                msg.editMessage(embed).queue();
            }else if(gss){
                event.getChannel().sendMessage("**Warning: **Since you haven't passed any arguments, this lyric was chosen based on the title of the current playing track.").queue();
                event.getChannel().sendMessage(embed).queue();
            }else{
                event.getChannel().sendMessage(embed).queue();
            }
        }catch (Exception e){
            msg.editMessage("**Error: **"+e.getMessage()).queue();
        }
    }


}
