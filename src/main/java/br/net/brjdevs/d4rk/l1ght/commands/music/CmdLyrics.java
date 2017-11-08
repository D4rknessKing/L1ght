package br.net.brjdevs.d4rk.l1ght.commands.music;

import br.net.brjdevs.d4rk.l1ght.music.AudioUtils;
import br.net.brjdevs.d4rk.l1ght.music.lyrics.LyricsSearchQuery;
import br.net.brjdevs.d4rk.l1ght.utils.Config;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import com.mashape.unirest.http.Unirest;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URLEncoder;
import java.util.Arrays;

public class CmdLyrics {

    @Command(name="lyrics", description = "Search for songs lyrics.", category = "Music", usage = "[-f] [song]", perms = {L1ghtPerms.BASE})
    public static void run(GuildMessageReceivedEvent event, String[] args) {

        if(args.length == 0){
            if (AudioUtils.connections.get(event.getGuild().getId()) != null && AudioUtils.connections.get(event.getGuild().getId()).isRunning){
                try{
                    JSONObject json = Unirest.get("https://api.vagalume.com.br/search.excerpt?apikey="+Config.vagalumeKey+"&q="+
                            URLEncoder.encode(AudioUtils.connections.get(event.getGuild().getId()).player.getPlayingTrack().getInfo().title, "UTF-8")+"&limit=5").asJsonAsync().get().getBody().getObject();
                    if(json.getJSONObject("response").getInt("numFound") == 0) {
                        event.getChannel().sendMessage("**Error: **Couldn't find lyrics!").queue();
                        return;
                    }
                    JSONArray array = json.getJSONObject("response").getJSONArray("docs");
                    new LyricsSearchQuery(event, array.getJSONObject(0), null, null, null, null, true).choose(1);
                }catch(Exception e){
                    event.getChannel().sendMessage("**Error: **"+e.getMessage()).queue();
                }
            }else{
                event.getChannel().sendMessage("**Error: **You're not listening to any music, and you haven't passed any arguments to search.").queue();
            }
        }else{
            if(args[0].equals("-f")) {
                if(args.length == 1) {
                    event.getChannel().sendMessage("**Error: **Missing arguments!\n" +
                            "**Correct usage: **"+Config.hardPrefix+"lyrics [-f] [song]").queue();
                    return;
                }
                String potato = String.join("%20", Arrays.copyOfRange(args, 1, args.length));
                try{
                    JSONObject json = Unirest.get("https://api.vagalume.com.br/search.excerpt?apikey="+Config.vagalumeKey+"&q="+potato+"&limit=5").asJsonAsync().get().getBody().getObject();
                    if(json.getJSONObject("response").getInt("numFound") == 0) {
                        event.getChannel().sendMessage("**Error: **Couldn't find lyrics!").queue();
                        return;
                    }
                    JSONArray array = json.getJSONObject("response").getJSONArray("docs");
                    new LyricsSearchQuery(event, array.getJSONObject(0), null, null, null, null, false).choose(1);
                }catch(Exception e){
                    event.getChannel().sendMessage("**Error: **"+e.getMessage()).queue();
                }
            }else{
                String potato = String.join("%20", args);
                try{
                    JSONObject json = Unirest.get("https://api.vagalume.com.br/search.excerpt?apikey="+Config.vagalumeKey+"&q="+potato+"&limit=5").asJsonAsync().get().getBody().getObject();
                    int found = json.getJSONObject("response").getInt("numFound");
                    if(found == 0) {
                        event.getChannel().sendMessage("**Error: **Couldn't find lyrics!").queue();
                        return;
                    }
                    JSONArray array = json.getJSONObject("response").getJSONArray("docs");
                    if(found >= 5) new LyricsSearchQuery(event, array.getJSONObject(0), array.getJSONObject(1), array.getJSONObject(2), array.getJSONObject(3), array.getJSONObject(4), false).start();
                    else if(found == 4) new LyricsSearchQuery(event, array.getJSONObject(0), array.getJSONObject(1), array.getJSONObject(2), array.getJSONObject(3), null, false).start();
                    else if(found == 3) new LyricsSearchQuery(event, array.getJSONObject(0), array.getJSONObject(1), array.getJSONObject(2),null, null, false).start();
                    else if(found == 2) new LyricsSearchQuery(event, array.getJSONObject(0), array.getJSONObject(1), null, null, null, false).start();
                    else if(found == 1) new LyricsSearchQuery(event, array.getJSONObject(0), null, null, null, null, false).start();
                    else event.getChannel().sendMessage("**Error: **Couldn't find lyrics!").queue();
                }catch(Exception e){
                    event.getChannel().sendMessage("**Error: **"+e.getMessage()).queue();
                }
            }
        }

    }
}
