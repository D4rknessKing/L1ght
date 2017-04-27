package br.net.brjdevs.d4rk.l1ght.utils.command;

import java.util.HashMap;

public class CommandRegister {

    private static HashMap commandMap = new HashMap<String, Command>();

    public static void register(Command cmd) {
        commandMap.put(cmd.cmdName(), cmd);
    }

    public static HashMap<String, Command> getCommandMap() {
        return commandMap;
    }
}
