package br.net.brjdevs.d4rk.l1ght.handlers;

import br.net.brjdevs.d4rk.l1ght.utils.data.GlobalData;
import br.net.brjdevs.d4rk.l1ght.utils.data.GuildData;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import java.util.List;


public class PermissionHandler {

    public static boolean handle(User user, Guild guild, List<L1ghtPerms> lperm) {

        if(GlobalData.hasGlobalPerm(user.getId(), lperm) != null) {
            if (GlobalData.hasGlobalPerm(user.getId(), lperm)) {
                return true;
            }else{
                return false;
            }
        }else{
            if (GuildData.hasGuildPerm(user.getId(), guild.getId(), lperm)) {
                return true;
            }else{
                return false;
            }
        }


    }

}
