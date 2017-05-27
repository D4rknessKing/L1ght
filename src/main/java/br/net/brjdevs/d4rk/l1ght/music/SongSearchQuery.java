package br.net.brjdevs.d4rk.l1ght.music;

import br.net.brjdevs.d4rk.l1ght.commands.music.CmdPlay;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class SongSearchQuery {

    public String guildId;
    public String userId;
    private AudioTrack at1;
    private AudioTrack at2;
    private AudioTrack at3;
    private String atitle1;
    private String atitle2;
    private String atitle3;
    private GuildMessageReceivedEvent event;
    private Message msg;

    public SongSearchQuery(GuildMessageReceivedEvent gm , AudioTrack t1, AudioTrack t2, AudioTrack t3, Message mg) {
        event = gm;
        guildId = event.getGuild().getId();
        userId = event.getAuthor().getId();
        at1 = t1;
        at2 = t2;
        at3 = t3;
        msg = mg;
        if(at1 == null){
            atitle1 = "None";
        }else{
            atitle1 = at1.getInfo().title;
        }
        if(at2 == null){
            atitle2 = "None";
        }else{
            atitle2 = at2.getInfo().title;
        }
        if(at3 == null){
            atitle3 = "None";
        }else{
            atitle3 = at3.getInfo().title;
        }
    }

    public void start(){
        msg.editMessage("**Please choose one of the following: **\n" +
                "**[1]:** `"+ atitle1 + "`\n" +
                "**[2]:** `"+ atitle2 + "`\n" +
                "**[3]:** `"+ atitle3 + "`\n" +
                "Type 1,2 or 3 to choose one of the songs. Type anything else to cancel the query."
        ).queue();
        AudioUtils.queryList.add(this);
    }

    public void choose(int i) {
        if(i == 1){
            if(at1 != null) {
                msg.editMessage("**Successfully added: **`"+at1.getInfo().title+"`**  to the queue.**").queue();
                AudioUtils.connections.get(guildId).toQueue(at1);
            }else{
                msg.editMessage("**Error: **Unknown option, canceling query...").queue();
                CmdPlay.canceled(event);
            }
        }else if(i == 2){
            if(at2 != null) {
                msg.editMessage("**Successfully added: **`"+at2.getInfo().title+"`** to the queue.**").queue();
                AudioUtils.connections.get(guildId).toQueue(at2);
            }else{
                msg.editMessage("**Error: **Unknown option, canceling query...").queue();
                CmdPlay.canceled(event);
            }
        }else if(i == 3){
            if(at3 != null) {
                msg.editMessage("**Successfully added: **`"+at3.getInfo().title+"`** to the queue.**").queue();
                AudioUtils.connections.get(guildId).toQueue(at3);
            }else{
                msg.editMessage("**Error: **Unknown option, canceling query...").queue();
                CmdPlay.canceled(event);
            }
        }else{
            msg.editMessage("**Error: **Unknown option, canceling query...").queue();
            CmdPlay.canceled(event);
        }

        AudioUtils.queryList.remove(this);
    }

}
