package br.net.brjdevs.d4rk.l1ght.commands.utils;

import br.net.brjdevs.d4rk.l1ght.L1ght;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.RandomUtils;
import br.net.brjdevs.d4rk.l1ght.utils.Stats;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class CmdStats {

    //Adrian porrinha pra calcular cpu usage
    private static OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
    private static double lastProcessCpuTime = 0;
    private static long lastSystemTime = 0;

    @Command(name = "stats", description = "Shows some \"interesting\" statistics about the bot", category = "Utils", usage = "", perms = {L1ghtPerms.BASE})
    public static void run(GuildMessageReceivedEvent event, String[] args){

        //Adrian porrinha para calcular cpu usage
        lastSystemTime = System.nanoTime();
        lastProcessCpuTime = ((com.sun.management.OperatingSystemMXBean) os).getProcessCpuTime();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor("L1ght Stats", null, L1ght.jda.getSelfUser().getAvatarUrl());
        embed.setDescription("**Online for "+ RandomUtils.getTime(System.currentTimeMillis()-Stats.loginTime) +
                "**\nRAM Usage: " + String.format("%s MB", ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024*1024))) + "/" + String.format("%s MB", (Runtime.getRuntime().totalMemory() / (1024*1024))) +
                "\nCPU Usage: "+String.valueOf(calculateCpuUsage()).substring(0, 3)+" %");

        embed.addField("Entities:", "Guilds: "+L1ght.jda.getGuilds().size()+
                "\nUsers: "+L1ght.jda.getUsers().size()+
                "\nText Channels: "+L1ght.jda.getTextChannels().size()+
                "\nVoice Channels: "+L1ght.jda.getVoiceChannels().size()+
                "\nCategories: "+L1ght.jda.getCategories().size(),true);
        embed.addField("Usage:", "Messages sent: "+Stats.sendMessages+
                "\nMessages read: "+Stats.readMessages+
                "\nCommands Executed: "+Stats.executedCommands+
                "\nReddit Matches: "+Stats.redditMatches+
                "\nTwitter Matches: "+Stats.twitterMatches, true);

        embed.setColor(event.getMember().getColor());
        embed.setFooter("Requested by: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl());
        event.getChannel().sendMessage(embed.build()).queue();
    }

    //Adrian porrinha pra calcular cpu usage
    private static double calculateCpuUsage() {
        long systemTime = System.nanoTime();
        double processCpuTime = ((com.sun.management.OperatingSystemMXBean) os).getProcessCpuTime();

        double cpuUsage = (processCpuTime - lastProcessCpuTime) / ((double) (systemTime - lastSystemTime));

        lastSystemTime = systemTime;
        lastProcessCpuTime = processCpuTime;

        return cpuUsage / Runtime.getRuntime().availableProcessors();
    }

}
