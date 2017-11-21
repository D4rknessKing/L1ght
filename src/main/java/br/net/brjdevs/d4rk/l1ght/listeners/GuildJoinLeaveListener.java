package br.net.brjdevs.d4rk.l1ght.listeners;

import br.net.brjdevs.d4rk.l1ght.utils.LogHandler;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.utils.SimpleLog;

public class GuildJoinLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        LogHandler.log.log(SimpleLog.Level.INFO, "Joined guild: "+event.getGuild().getName()+"("+event.getGuild().getId()+")");
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        LogHandler.log.log(SimpleLog.Level.INFO, "Left guild: "+event.getGuild().getName()+"("+event.getGuild().getId()+")");
    }
}
