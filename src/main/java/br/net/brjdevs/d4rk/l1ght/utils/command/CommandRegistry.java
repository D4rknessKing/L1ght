package br.net.brjdevs.d4rk.l1ght.utils.command;

import br.net.brjdevs.d4rk.l1ght.commands.*;

public class CommandRegistry {

    public static void registerCmds() {
        CommandRegister.register(new CmdLenny());
        CommandRegister.register(new CmdHelp());
        CommandRegister.register(new CmdPing());
        CommandRegister.register(new CmdInvite());
        CommandRegister.register(new CmdDoge());
        CommandRegister.register(new CmdYoda());
        CommandRegister.register(new CmdLua());

    }

}
