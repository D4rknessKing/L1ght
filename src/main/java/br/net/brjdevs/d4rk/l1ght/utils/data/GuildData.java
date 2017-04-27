package br.net.brjdevs.d4rk.l1ght.utils.data;

import br.net.brjdevs.d4rk.l1ght.L1ght;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GuildData {

    public static boolean hasGuildPerm(String userId, String guildId, List<L1ghtPerms> permsList) {
        HashMap<String, List<L1ghtPerms>> hashMap = loadGuildPerms(guildId);
        if(hashMap.get(userId) == null) {
            createGuildUserPerm(userId, guildId);
            hashMap = loadGuildPerms(guildId);
        }
        if(hashMap.get(userId).containsAll(permsList)) {
            return true;
        }else{
            return false;
        }
    }

    public static void addGuildPerm(String userId, String guildId, L1ghtPerms lperm) {
        HashMap<String, List<L1ghtPerms>> hashMap = loadGuildPerms(guildId);
        if(hashMap.get(userId) == null) {
            createGuildUserPerm(userId, guildId);
            hashMap = loadGuildPerms(guildId);
        }
        if(hashMap.get(userId).contains(lperm)) {
            return;
        }
        hashMap.get(userId).add(lperm);
        saveGuildPerm(guildId, hashMap);
    }

    public static void removeGuildPerm(String userId, String guildId, L1ghtPerms lperm) {
        HashMap<String, List<L1ghtPerms>> hashMap = loadGuildPerms(guildId);
        if(hashMap.get(userId) == null) {
            createGuildUserPerm(userId, guildId);
            hashMap = loadGuildPerms(guildId);
        }
        if(!hashMap.get(userId).contains(lperm)) {
            return;
        }
        hashMap.get(userId).remove(lperm);
        saveGuildPerm(guildId, hashMap);
    }

    public static void createGuildUserPerm(String userId, String guildId) {
        HashMap<String, List<L1ghtPerms>> hashMap = loadGuildPerms(guildId);
        hashMap.put(userId, Arrays.asList(L1ghtPerms.BASE));
        saveGuildPerm(guildId, hashMap);
    }

    public static HashMap<String, List<L1ghtPerms>> loadGuildPerms(String guildId) {
        JSONObject jfile = loadGuildData(guildId);

        HashMap<String, List<L1ghtPerms>> fperms = new HashMap<>();

        for (String s : ((JSONObject) jfile.get("guildPerms")).keySet()) {
            List<L1ghtPerms> list = new ArrayList<>();
            for (Object i : ((JSONObject) jfile.get("guildPerms")).getJSONArray(s)) {
                list.add(L1ghtPerms.valueOf((String) i));
            }
            fperms.put(s, list);
        }
        return fperms;
    }

    public static void saveGuildPerm(String guildId, HashMap<String, List<L1ghtPerms>> hashMap) {

        JSONObject perm = new JSONObject();

        for (String s : hashMap.keySet()) {
            JSONArray array = new JSONArray();
            for (L1ghtPerms p : hashMap.get(s)) {
                array.put(p.toString());
            }
            perm.put(s, array);
        }

        saveGuildData(guildId, perm);
    }

    public static JSONObject loadGuildData(String guildId) {
        Path globalDataPath = Paths.get(new File(new File(L1ght.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile(), "\\guildData\\"+guildId+".json").getAbsolutePath());

        if(Files.notExists(globalDataPath)) {
            createGuildData(guildId);
        }

        String jsons;
        try {
            jsons = new String(Files.readAllBytes(globalDataPath),"UTF-8");
        }catch (Exception e) {
            System.out.println(e);
            jsons = "{}";
        }
        return new JSONObject(jsons);
    }

    public static void saveGuildData(String guildId, JSONObject perm) {
        Path globalDataPath = Paths.get(new File(new File(L1ght.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile(), "\\guildData\\"+guildId+".json").getAbsolutePath());

        JSONObject jfile = new JSONObject();
        jfile.put("guildPerms", perm);

        try{
            Files.write(globalDataPath, jfile.toString().getBytes("UTF-8"));
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void createGuildData(String guildId) {
        Path globalDataPath = Paths.get(new File(new File(L1ght.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile(), "\\guildData\\"+guildId+".json").getAbsolutePath());

        JSONObject jfile = new JSONObject();
        jfile.put("guildPerms", new JSONObject());

        try{
            Files.write(globalDataPath, jfile.toString().getBytes("UTF-8"));
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
