package br.net.brjdevs.d4rk.l1ght.utils.command;

import br.net.brjdevs.d4rk.l1ght.commands.bot_owner.*;
import br.net.brjdevs.d4rk.l1ght.commands.fun.*;
import br.net.brjdevs.d4rk.l1ght.commands.games.*;
import br.net.brjdevs.d4rk.l1ght.commands.guild.*;
import br.net.brjdevs.d4rk.l1ght.commands.info.*;
import br.net.brjdevs.d4rk.l1ght.commands.music.*;
import br.net.brjdevs.d4rk.l1ght.commands.utils.*;

public class CommandRegistry {

    public static void registerCmds() {
        CommandRegister.register(CmdSplatoon2.class);
        CommandRegister.register(CmdB64.class);
        CommandRegister.register(CmdColor.class);
        CommandRegister.register(CmdHelp.class);
        CommandRegister.register(CmdInvite.class);
        CommandRegister.register(CmdPing.class);
        CommandRegister.register(CmdJava.class);
        CommandRegister.register(CmdLua.class);
        CommandRegister.register(Cmd8.class);
        CommandRegister.register(CmdDoge.class);
        CommandRegister.register(CmdLenny.class);
        CommandRegister.register(CmdPokedex.class);
        CommandRegister.register(CmdRcg.class);
        CommandRegister.register(CmdReg.class);
        CommandRegister.register(CmdUrban.class);
        CommandRegister.register(CmdYomomma.class);
        CommandRegister.register(CmdCustomboard.class);
        CommandRegister.register(CmdFeeds.class);
        CommandRegister.register(CmdPrefix.class);
        CommandRegister.register(CmdGuild.class);
        CommandRegister.register(CmdMessage.class);
        CommandRegister.register(CmdQuote.class);
        CommandRegister.register(CmdRole.class);
        CommandRegister.register(CmdUser.class);
        CommandRegister.register(CmdPlay.class);
        CommandRegister.register(CmdLyrics.class);
        CommandRegister.register(CmdQueue.class);
        CommandRegister.register(CmdSkip.class);
        CommandRegister.register(CmdStop.class);
        CommandRegister.register(CmdRestart.class);
        CommandRegister.register(CmdShutdown.class);
        CommandRegister.register(CmdPrefix.class);
        CommandRegister.register(CmdPerm.class);
        CommandRegister.register(CmdStats.class);
    }

}
