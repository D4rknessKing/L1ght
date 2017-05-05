package br.net.brjdevs.d4rk.l1ght.utils.command;

import br.net.brjdevs.d4rk.l1ght.commands.bot_owner.CmdLua;
import br.net.brjdevs.d4rk.l1ght.commands.bot_owner.CmdRestart;
import br.net.brjdevs.d4rk.l1ght.commands.bot_owner.CmdShutdown;
import br.net.brjdevs.d4rk.l1ght.commands.fun.*;
import br.net.brjdevs.d4rk.l1ght.commands.utils.CmdHelp;
import br.net.brjdevs.d4rk.l1ght.commands.utils.CmdInvite;
import br.net.brjdevs.d4rk.l1ght.commands.utils.CmdPing;
import br.net.brjdevs.d4rk.l1ght.commands.utils.CmdB64;

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
    }

}
