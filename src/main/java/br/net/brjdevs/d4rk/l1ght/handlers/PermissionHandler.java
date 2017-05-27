package br.net.brjdevs.d4rk.l1ght.handlers;

import br.net.brjdevs.d4rk.l1ght.handlers.data.GuildDataHandler;
import br.net.brjdevs.d4rk.l1ght.handlers.data.GlobalDataHandler;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;

import java.util.HashMap;
import java.util.List;

public class PermissionHandler {

    public static boolean hasPerm(User user, Guild guild, List<L1ghtPerms> lperm) {

        if(hasGlobalPerm(user.getId(), lperm) != null) {
            return hasGlobalPerm(user.getId(), lperm);
        }else{
            return hasGuildPerm(user.getId(), guild.getId(), lperm);
        }
    }

    public static boolean hasGuildPerm(String userId, String guildId, List<L1ghtPerms> permsList) {
        HashMap<String, List<L1ghtPerms>> hashMap = GuildDataHandler.loadGuildPerms(guildId);
        if(hashMap.get(userId) == null) {
            GuildDataHandler.createGuildUserPerm(userId, guildId);
            hashMap = GuildDataHandler.loadGuildPerms(guildId);
        }
        if(hashMap.get(userId).containsAll(permsList)) {
            return true;
        }else{
            return false;
        }
    }

    public static boolean hasGuildPerm(String userId, String guildId, L1ghtPerms perm) {
        HashMap<String, List<L1ghtPerms>> hashMap = GuildDataHandler.loadGuildPerms(guildId);
        if(hashMap.get(userId) == null) {
            GuildDataHandler.createGuildUserPerm(userId, guildId);
            hashMap = GuildDataHandler.loadGuildPerms(guildId);
        }
        if(hashMap.get(userId).contains(perm)) {
            return true;
        }else{
            return false;
        }
    }

    public static boolean hasGuildPerm(String userId, String guildId, String perm) {
        HashMap<String, List<L1ghtPerms>> hashMap = GuildDataHandler.loadGuildPerms(guildId);
        if(hashMap.get(userId) == null) {
            GuildDataHandler.createGuildUserPerm(userId, guildId);
            hashMap = GuildDataHandler.loadGuildPerms(guildId);
        }
        if(hashMap.get(userId).contains(L1ghtPerms.valueOf(perm))) {
            return true;
        }else{
            return false;
        }
    }

    public static void addGuildPerm(String userId, String guildId, L1ghtPerms lperm) {
        HashMap<String, List<L1ghtPerms>> hashMap = GuildDataHandler.loadGuildPerms(guildId);
        if(hashMap.get(userId) == null) {
            GuildDataHandler.createGuildUserPerm(userId, guildId);
            hashMap = GuildDataHandler.loadGuildPerms(guildId);
        }
        if(hashMap.get(userId).contains(lperm)) {
            return;
        }
        hashMap.get(userId).add(lperm);
        GuildDataHandler.saveGuildPerm(guildId, hashMap);
    }

    public static void addGuildPerm(String userId, String guildId, String lperm) {
        HashMap<String, List<L1ghtPerms>> hashMap = GuildDataHandler.loadGuildPerms(guildId);
        if(hashMap.get(userId) == null) {
            GuildDataHandler.createGuildUserPerm(userId, guildId);
            hashMap = GuildDataHandler.loadGuildPerms(guildId);
        }
        if(hashMap.get(userId).contains(L1ghtPerms.valueOf(lperm))) {
            return;
        }
        hashMap.get(userId).add(L1ghtPerms.valueOf(lperm));
        GuildDataHandler.saveGuildPerm(guildId, hashMap);
    }

    public static void removeGuildPerm(String userId, String guildId, L1ghtPerms lperm) {
        HashMap<String, List<L1ghtPerms>> hashMap = GuildDataHandler.loadGuildPerms(guildId);
        if(hashMap.get(userId) == null) {
            GuildDataHandler.createGuildUserPerm(userId, guildId);
            hashMap = GuildDataHandler.loadGuildPerms(guildId);
        }
        if(!hashMap.get(userId).contains(lperm)) {
            return;
        }
        hashMap.get(userId).remove(lperm);
        GuildDataHandler.saveGuildPerm(guildId, hashMap);
    }

    public static void removeGuildPerm(String userId, String guildId, String lperm) {
        HashMap<String, List<L1ghtPerms>> hashMap = GuildDataHandler.loadGuildPerms(guildId);
        if(hashMap.get(userId) == null) {
            GuildDataHandler.createGuildUserPerm(userId, guildId);
            hashMap = GuildDataHandler.loadGuildPerms(guildId);
        }
        if(!hashMap.get(userId).contains(L1ghtPerms.valueOf(lperm))) {
            return;
        }
        hashMap.get(userId).remove(L1ghtPerms.valueOf(lperm));
        GuildDataHandler.saveGuildPerm(guildId, hashMap);
    }

    public static Boolean hasGlobalPerm(String userId, List<L1ghtPerms> permsList) {
        HashMap<String, List<L1ghtPerms>> hashMap = GlobalDataHandler.loadGlobalPerms();
        if(hashMap.get(userId) == null) {
            return null;
        }
        if(hashMap.get(userId).containsAll(permsList)) {
            return true;
        }else{
            return false;
        }
    }

    public static Boolean hasGlobalPerm(String userId, L1ghtPerms perm) {
        HashMap<String, List<L1ghtPerms>> hashMap = GlobalDataHandler.loadGlobalPerms();
        if(hashMap.get(userId) == null) {
            return null;
        }
        if(hashMap.get(userId).contains(perm)) {
            return true;
        }else{
            return false;
        }
    }

    public static Boolean hasGlobalPerm(String userId, String perm) {
        HashMap<String, List<L1ghtPerms>> hashMap = GlobalDataHandler.loadGlobalPerms();
        if(hashMap.get(userId) == null) {
            return null;
        }
        if(hashMap.get(userId).contains(L1ghtPerms.valueOf(perm))) {
            return true;
        }else{
            return false;
        }
    }

    public static void addGlobalPerm(String userId, L1ghtPerms lperm) {
        HashMap<String, List<L1ghtPerms>> hashMap = GlobalDataHandler.loadGlobalPerms();
        if(hashMap.get(userId) == null) {
            GlobalDataHandler.createGlobalUserPerm(userId);
            hashMap = GlobalDataHandler.loadGlobalPerms();
        }
        if(hashMap.get(userId).contains(lperm)) {
            return;
        }
        hashMap.get(userId).add(lperm);
        GlobalDataHandler.saveGlobalPerm(hashMap);
    }

    public static void addGlobalPerm(String userId, String lperm) {
        HashMap<String, List<L1ghtPerms>> hashMap = GlobalDataHandler.loadGlobalPerms();
        if(hashMap.get(userId) == null) {
            GlobalDataHandler.createGlobalUserPerm(userId);
            hashMap = GlobalDataHandler.loadGlobalPerms();
        }
        if(hashMap.get(userId).contains(L1ghtPerms.valueOf(lperm))) {
            return;
        }
        hashMap.get(userId).add(L1ghtPerms.valueOf(lperm));
        GlobalDataHandler.saveGlobalPerm(hashMap);
    }

    public static void removeGlobalPerm(String userId, L1ghtPerms lperm) {
        HashMap<String, List<L1ghtPerms>> hashMap = GlobalDataHandler.loadGlobalPerms();
        if(hashMap.get(userId) == null) {
            GlobalDataHandler.createGlobalUserPerm(userId);
            hashMap = GlobalDataHandler.loadGlobalPerms();
        }
        if(!hashMap.get(userId).contains(lperm)) {
            return;
        }
        hashMap.get(userId).remove(lperm);
        GlobalDataHandler.saveGlobalPerm(hashMap);
    }

    public static void removeGlobalPerm(String userId, String lperm) {
        HashMap<String, List<L1ghtPerms>> hashMap = GlobalDataHandler.loadGlobalPerms();
        if(hashMap.get(userId) == null) {
            GlobalDataHandler.createGlobalUserPerm(userId);
            hashMap = GlobalDataHandler.loadGlobalPerms();
        }
        if(!hashMap.get(userId).contains(L1ghtPerms.valueOf(lperm))) {
            return;
        }
        hashMap.get(userId).remove(L1ghtPerms.valueOf(lperm));
        GlobalDataHandler.saveGlobalPerm(hashMap);
    }

}
