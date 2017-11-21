package br.net.brjdevs.d4rk.l1ght.utils;

import br.net.brjdevs.d4rk.l1ght.L1ght;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.utils.SimpleLog.*;
import net.dv8tion.jda.core.utils.SimpleLog;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LogHandler {

    public static SimpleLog log = SimpleLog.getLog("L1ght");

    public static void start(){
        if(Config.logChannel == null || L1ght.jda.getTextChannelById(Config.logChannel) == null){
            log.warn("Can't log to discord, unknown channel.");
            return;
        }
        DateFormat dateFormat = new SimpleDateFormat("[HH:mm:ss]");
        TextChannel tc = L1ght.jda.getTextChannelById("255081000785281025");
        LogListener listener = new LogListener() {
            @Override
            public void onLog(SimpleLog simpleLog, Level level, Object o) {
                if(level != Level.TRACE && level != Level.DEBUG){
                    tc.sendMessage(dateFormat.format(new Date()) + " [" + level.getTag() + "] [" + simpleLog.name + "] "+o.toString()).queue();
                }
            }

            @Override
            public void onError(SimpleLog simpleLog, Throwable throwable) {
                tc.sendMessage(dateFormat.format(new Date()) + " [Error] [" + simpleLog.name + "] " + throwable.getMessage() + " at " + throwable.getClass().getName() + ". \nSee log for more details.").queue();
            }
        };
        SimpleLog.addListener(listener);
        log.log(Level.INFO, "Started logging to Discord!");
    }
}
