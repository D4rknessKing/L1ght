package br.net.brjdevs.d4rk.l1ght.commands.utils;

import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import br.net.brjdevs.d4rk.l1ght.utils.command.Command;
import javafx.util.Pair;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class CmdB64 implements Command{
    @Override
    public void cmdRun(GuildMessageReceivedEvent event, String[] args) {

        String fin = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        if(args[0].toLowerCase().equals("encode")) {
            String result = Base64.getEncoder().encodeToString(fin.getBytes());
            event.getChannel().sendMessage("**Text to base64: **"+result).queue();
        }else if(args[0].toLowerCase().equals("decode")) {
            try {
                byte[] bytes = Base64.getDecoder().decode(fin);
                String result = new String(bytes);
                event.getChannel().sendMessage("**Text from base64: **" + result).queue();
            }catch (IllegalArgumentException e){
                event.getChannel().sendMessage("**Error: **Unable to decode `"+fin+"` to base64. (Is it a base64 string?)").queue();
            }
        }else {
            event.getChannel().sendMessage("**Error: **Invalid option `"+args[0]+"`, please use `decode` or `encode`.").queue();
        }
    }

    @Override
    public String cmdName() {
        return "b64";
    }

    @Override
    public String cmdDescription() {
        return "Either encode or decode strings using the base64 encryption.";
    }

    @Override
    public String cmdCategory() {
        return "Utils";
    }

    @Override
    public List<L1ghtPerms> cmdPerm() {
        return Arrays.asList(L1ghtPerms.BASE);
    }

    @Override
    public List<Pair<String, Boolean>> cmdArgs() {
        List<Pair<String, Boolean>> list = new ArrayList<>();
        list.add(new Pair<>("Decode/Encode", true));
        list.add(new Pair<>("Text", true));
        return list;
    }
}
