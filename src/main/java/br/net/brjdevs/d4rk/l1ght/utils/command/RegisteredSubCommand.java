package br.net.brjdevs.d4rk.l1ght.utils.command;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;

import java.lang.reflect.Method;
import java.util.List;

public class RegisteredSubCommand {

    private String nam;
    private String dsc;
    private String use;
    private List<L1ghtPerms> prm;

    private Method cmd;

    public RegisteredSubCommand(String name, String description, String usage, List<L1ghtPerms> perms, Method command){
        nam = name;
        dsc = description;
        use = usage;
        prm = perms;
        cmd = command;
    }

    public String getName(){
        return nam;
    }

    public String getDescription() {return dsc;}

    public String getUsage() {return use;}

    public List<L1ghtPerms> getPerms(){
        return prm;
    }

    public Method getCommand(){
        return cmd;
    }


}
