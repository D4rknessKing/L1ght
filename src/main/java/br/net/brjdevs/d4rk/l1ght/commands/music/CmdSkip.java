package br.net.brjdevs.d4rk.l1ght.commands.music;

import br.net.brjdevs.d4rk.l1ght.music.AudioUtils;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class CmdSkip {

    @Command(name="skip", description = "Votes for skipping the current song.", category = "Music", usage = "", perms = {L1ghtPerms.BASE})
    public static void run(GuildMessageReceivedEvent event, String[] args) {
        if (AudioUtils.connections.get(event.getGuild().getId()) != null && AudioUtils.connections.get(event.getGuild().getId()).isRunning){
            if(AudioUtils.connections.get(event.getGuild().getId()).manager.getConnectedChannel() != event.getMember().getVoiceState().getChannel()){
                event.getChannel().sendMessage("**Error: **You're not connected to the voice channel i'm connected.").queue();
                return;
            }

            int connectedIC = AudioUtils.connections.get(event.getGuild().getId()).manager.getConnectedChannel().getMembers().size()-1;

            if(AudioUtils.connections.get(event.getGuild().getId()).skipVotes.size() == 0){
                AudioUtils.connections.get(event.getGuild().getId()).skipVotes.add(event.getAuthor().getId());
            }else{
                if(AudioUtils.connections.get(event.getGuild().getId()).skipVotes.contains(event.getAuthor().getId())){
                    event.getChannel().sendMessage("**Error: **You've already voted.").queue();
                    return;
                }else{
                    AudioUtils.connections.get(event.getGuild().getId()).skipVotes.add(event.getAuthor().getId());
                }
            }
            if(AudioUtils.connections.get(event.getGuild().getId()).skipVotes.size() > connectedIC*0.6){
                event.getChannel().sendMessage("**Reached necessary number of skip votes, skipping!**").queue();
                AudioUtils.connections.get(event.getGuild().getId()).player.stopTrack();
            }else{
                event.getChannel().sendMessage(event.getMember().getAsMention()+"** voted for skipping the music , "+(Math.ceil(connectedIC*0.6)-AudioUtils.connections.get(event.getGuild().getId()).skipVotes.size())+" more skip votes needed!**").queue();
            }

        }else{
            event.getChannel().sendMessage("**Error: **Nothing playing!").queue();
        }
    }

    public static void check(Guild guild, Member member) {
        int connectedIC = AudioUtils.connections.get(guild.getId()).manager.getConnectedChannel().getMembers().size()-1;
        if(AudioUtils.connections.get(guild.getId()).skipVotes.size() > connectedIC*0.6){
            AudioUtils.connections.get(guild.getId()).event.getChannel().sendMessage("**Skip votes changed, reached necessary number of skip votes, skipping!**").queue();
            AudioUtils.connections.get(guild.getId()).player.stopTrack();
        }else{
            AudioUtils.connections.get(guild.getId()).event.getChannel().sendMessage("**Skip votes changed, "+(Math.ceil(connectedIC*0.6)-AudioUtils.connections.get(guild.getId()).skipVotes.size())+" more votes needed!**").queue();
        }
    }

}
