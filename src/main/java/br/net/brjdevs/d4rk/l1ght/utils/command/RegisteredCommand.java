package br.net.brjdevs.d4rk.l1ght.utils.command;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class RegisteredCommand {

    private String nam;
    private String dsc;
    private String ctg;
    private String use;
    private List<L1ghtPerms> prm;

    private Method cmd;
    private HashMap<String, RegisteredSubCommand> scm;

    public RegisteredCommand(String name, String description, String category, String usage, List<L1ghtPerms> perms, Method command, HashMap<String, RegisteredSubCommand> subCommands){
        nam = name;
        dsc = description;
        ctg = category;
        prm = perms;
        cmd = command;
        scm = subCommands;
        use = usage;
    }

    public String getName(){
        return nam;
    }

    public String getDescription(){
        return dsc;
    }

    public String getCategory(){
        return ctg;
    }

    public String getUsage() { return use; }

    public List<L1ghtPerms> getPerms(){
        return prm;
    }

    public Method getCommand(){
        return cmd;
    }

    public HashMap<String, RegisteredSubCommand> getSubCommands(){
        return scm;
    }



}
