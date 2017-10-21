package br.net.brjdevs.d4rk.l1ght.handlers.data;

import br.net.brjdevs.d4rk.l1ght.L1ght;

import br.net.brjdevs.d4rk.l1ght.utils.Config;
import br.net.brjdevs.d4rk.l1ght.utils.L1ghtPerms;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GlobalDataHandler {

    public static void createGlobalUserPerm(String userId) {
        HashMap<String, List<L1ghtPerms>> hashMap = loadGlobalPerms();
        hashMap.put(userId, Arrays.asList(L1ghtPerms.BASE));
        saveGlobalPerm(hashMap);
    }

    public static HashMap<String, List<L1ghtPerms>> loadGlobalPerms() {
        JSONObject jfile = loadGlobalData();

        try{
            jfile.get("globalPerms");
        }catch(JSONException e){
            return new HashMap<>();
        }

        HashMap<String, List<L1ghtPerms>> fperms = new HashMap<>();

        for (String s : ((JSONObject) jfile.get("globalPerms")).keySet()) {
            List<L1ghtPerms> list = new ArrayList<>();
            for (Object i : ((JSONObject) jfile.get("globalPerms")).getJSONArray(s)) {
                list.add(L1ghtPerms.valueOf((String) i));
            }
            fperms.put(s, list);
        }
        return fperms;
    }

    public static void saveGlobalPerm(HashMap<String, List<L1ghtPerms>> hashMap) {

        JSONObject perm = new JSONObject();

        for (String s : hashMap.keySet()) {
            JSONArray array = new JSONArray();
            for (L1ghtPerms p : hashMap.get(s)) {
                array.put(p.toString());
            }
            perm.put(s, array);
        }

        saveGlobalData(perm);
    }

    public static JSONObject loadGlobalData() {
        Path globalDataPath = Paths.get(Config.getGlobalDataFile().getAbsolutePath());

        if(Files.notExists(globalDataPath)) {
            createGlobalData();
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

    public static void saveGlobalData(JSONObject perm) {
        Path globalDataPath = Paths.get(Config.getGlobalDataFile().getAbsolutePath());

        JSONObject jfile = new JSONObject();

        if (perm != null) {
            jfile.put("globalPerms", perm);
        }else{
            try{
                jfile.put("globalPerms", loadGlobalData().get("globalPerms"));
            }catch(JSONException ignored){}
        }

        try{
            Files.write(globalDataPath, jfile.toString().getBytes("UTF-8"));
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void createGlobalData() {
        Path globalDataPath = Paths.get(Config.getGlobalDataFile().getAbsolutePath());

        JSONObject jfile = new JSONObject();

        try{
            Files.write(globalDataPath, jfile.toString().getBytes("UTF-8"));
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
