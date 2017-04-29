package br.net.brjdevs.d4rk.l1ght.handlers;

import br.net.brjdevs.d4rk.l1ght.handlers.data.GlobalDataHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.data.GuildDataHandler;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import java.util.List;


public class PermissionHandler {

    public static boolean handle(User user, Guild guild, List<L1ghtPerms> lperm) {

        if(GlobalDataHandler.hasGlobalPerm(user.getId(), lperm) != null) {
            return GlobalDataHandler.hasGlobalPerm(user.getId(), lperm);
        }else{
            return GuildDataHandler.hasGuildPerm(user.getId(), guild.getId(), lperm);
        }


    }

}
