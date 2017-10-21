package br.net.brjdevs.d4rk.l1ght.commands.guild;

import br.net.brjdevs.d4rk.l1ght.handlers.data.GuildDataHandler;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdStarboard implements Command{
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {
        if(args[0].toLowerCase().equals("disable")){
            if(GuildDataHandler.getGuildStarboardChannel(event.getGuild().getId()).equals("disabled")){
                event.getChannel().sendMessage("**Error: **Starboard already disabled!").queue();
                return;
            }
            GuildDataHandler.setGuildStarboardChannel(event.getGuild().getId(), "disabled");
            event.getChannel().sendMessage("**Successfully disabled starboard.**").queue();
        }else if(args[0].toLowerCase().equals("enable")){
            if(!GuildDataHandler.getGuildStarboardChannel(event.getGuild().getId()).equals("disabled")){
                event.getChannel().sendMessage("**Error: **Starboard already enabled in this guild!").queue();
                return;
            }
            GuildDataHandler.setGuildStarboardChannel(event.getGuild().getId(), event.getChannel().getId());
            event.getChannel().sendMessage("**Successfully enabled starboard in channel: ** #"+event.getChannel().getName()).queue();
        }else{
            event.getChannel().sendMessage("**Error: **Invalid option `" + args[0] + "`, please use `enable` or `disable`.").queue();
        }
    }

    @Override
    public String cmdName() {
        return "starboard";
    }

    @Override
    public String cmdDescription() {
        return "Setup the starboard system in the current channel.";
    }

    @Override
    public String cmdCategory() {
        return "Guild";
    }

    @Override
    public List<L1ghtPerms> cmdPerm() {
        return Arrays.asList(L1ghtPerms.BASE, L1ghtPerms.GUILD);
    }

    @Override
    public List<Pair<String, Boolean>> cmdArgs() {
        List<Pair<String, Boolean>> list = new ArrayList<>();
        list.add(new Pair<>("enable/disable", true));
        return list;
    }
}
