package br.net.brjdevs.d4rk.l1ght.commands.fun;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdReg implements Command{
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {

        String fin = String.join(" ", args);
        String[] tbl = fin.toLowerCase().split("");

        List<String> theend = new ArrayList<>();
        for (String i : tbl) {
            if(i.equals("a")) theend.add(":regional_indicator_a: ");
            else if(i.equals("b")) theend.add(":regional_indicator_b: ");
            else if(i.equals("c")) theend.add(":regional_indicator_c: ");
            else if(i.equals("d")) theend.add(":regional_indicator_d: ");
            else if(i.equals("e")) theend.add(":regional_indicator_e: ");
            else if(i.equals("f")) theend.add(":regional_indicator_f: ");
            else if(i.equals("g")) theend.add(":regional_indicator_g: ");
            else if(i.equals("h")) theend.add(":regional_indicator_h: ");
            else if(i.equals("i")) theend.add(":regional_indicator_i: ");
            else if(i.equals("j")) theend.add(":regional_indicator_j: ");
            else if(i.equals("k")) theend.add(":regional_indicator_k: ");
            else if(i.equals("l")) theend.add(":regional_indicator_l: ");
            else if(i.equals("m")) theend.add(":regional_indicator_m: ");
            else if(i.equals("n")) theend.add(":regional_indicator_n: ");
            else if(i.equals("o")) theend.add(":regional_indicator_o: ");
            else if(i.equals("p")) theend.add(":regional_indicator_p: ");
            else if(i.equals("q")) theend.add(":regional_indicator_q: ");
            else if(i.equals("r")) theend.add(":regional_indicator_r: ");
            else if(i.equals("s")) theend.add(":regional_indicator_s: ");
            else if(i.equals("t")) theend.add(":regional_indicator_t: ");
            else if(i.equals("u")) theend.add(":regional_indicator_u: ");
            else if(i.equals("v")) theend.add(":regional_indicator_v: ");
            else if(i.equals("w")) theend.add(":regional_indicator_w: ");
            else if(i.equals("x")) theend.add(":regional_indicator_x: ");
            else if(i.equals("y")) theend.add(":regional_indicator_y: ");
            else if(i.equals("z")) theend.add(":regional_indicator_z: ");
            else if(i.equals("0")) theend.add(":zero: ");
            else if(i.equals("1")) theend.add(":one: ");
            else if(i.equals("2")) theend.add(":two: ");
            else if(i.equals("3")) theend.add(":three: ");
            else if(i.equals("4")) theend.add(":four: ");
            else if(i.equals("5")) theend.add(":five: ");
            else if(i.equals("6")) theend.add(":six: ");
            else if(i.equals("7")) theend.add(":seven: ");
            else if(i.equals("8")) theend.add(":eight: ");
            else if(i.equals("9")) theend.add(":nine: ");
            else if(i.equals(" ")) theend.add("    ");
            else theend.add(":interrobang:");
        }

        String ready = String.join("", theend);
        if(ready.length() > 1999) event.getChannel().sendMessage("**Error: **The final result is too big!").queue();
        else event.getChannel().sendMessage(ready).queue();


    }

    @Override
    public String cmdName() {
        return "reg";
    }

    @Override
    public String cmdDescription() {
        return "Converts your input to regional indicators.";
    }

    @Override
    public String cmdCategory() {
        return "Fun";
    }

    @Override
    public List<L1ghtPerms> cmdPerm() {
        return Arrays.asList(L1ghtPerms.BASE);
    }

    @Override
    public List<Pair<String, Boolean>> cmdArgs() {
        List<Pair<String, Boolean>> list = new ArrayList<>();
        list.add(new Pair<>("Text", true));
        return list;
    }
}
