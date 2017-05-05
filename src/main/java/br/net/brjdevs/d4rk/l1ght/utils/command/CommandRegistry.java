package br.net.brjdevs.d4rk.l1ght.utils.command;

import br.net.brjdevs.d4rk.l1ght.commands.fun.*;
import br.net.brjdevs.d4rk.l1ght.commands.bot_owner.*;
import br.net.brjdevs.d4rk.l1ght.commands.info.*;
import br.net.brjdevs.d4rk.l1ght.commands.utils.*;

public class CommandRegistry {

    public static void registerCmds() {
        CommandRegister.register(new CmdLenny());
        CommandRegister.register(new CmdHelp());
        CommandRegister.register(new CmdPing());
        CommandRegister.register(new CmdInvite());
        CommandRegister.register(new CmdDoge());
        CommandRegister.register(new CmdYoda());
        CommandRegister.register(new CmdLua());
        CommandRegister.register(new CmdB64());
        CommandRegister.register(new CmdReg());
        CommandRegister.register(new CmdShutdown());
        CommandRegister.register(new CmdRestart());
        CommandRegister.register(new CmdUrban());
        CommandRegister.register(new CmdGuild());
        CommandRegister.register(new CmdRole());
        CommandRegister.register(new CmdMessage());
        CommandRegister.register(new CmdQuote());
        CommandRegister.register(new CmdUser());
    }

}
