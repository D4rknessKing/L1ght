package br.net.brjdevs.d4rk.l1ght.handlers.data;

import br.net.brjdevs.d4rk.l1ght.L1ght;
import br.net.brjdevs.d4rk.l1ght.handlers.feeds.FeedSubscription;
import br.net.brjdevs.d4rk.l1ght.utils.Config;
import br.net.brjdevs.d4rk.l1ght.utils.FeedsType;
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

public class GuildDataHandler {

    //Permission Stuff
    public static void createGuildUserPerm(String userId, String guildId) {
        HashMap<String, List<L1ghtPerms>> hashMap = loadGuildPerms(guildId);
        hashMap.put(userId, Arrays.asList(L1ghtPerms.BASE));
        saveGuildPerm(guildId, hashMap);
    }

    public static HashMap<String, List<L1ghtPerms>> loadGuildPerms(String guildId) {
        JSONObject jfile = loadGuildData(guildId);

        try{
            jfile.get("guildPerms");
        }catch(JSONException e){
            return new HashMap<>();
        }

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

        saveGuildData(guildId, perm, null, null, null);
    }

    //Feeds Stuff
    public static List<FeedSubscription> loadGuildFeeds(String guildId) {
        JSONObject jfile = loadGuildData(guildId);

        try{
            jfile.get("guildFeeds");
        }catch(JSONException e){
            return new ArrayList<>();
        }

        List<FeedSubscription> feeds = new ArrayList<>();

        for(Object j : ((JSONArray) jfile.get("guildFeeds"))) {
            JSONObject jo = (JSONObject) j;
            feeds.add(
                    new FeedSubscription(
                            jo.getString("name"),
                            jo.getString("url"),
                            FeedsType.valueOf(jo.getString("type")),
                            jo.getString("channel")
                    )
            );
        }

        return feeds;

    }

    public static void saveGuildFeeds (String guildId, List<FeedSubscription> list) {

        JSONArray jfeeds = new JSONArray();

        for(FeedSubscription fs : list) {
            JSONObject jo = new JSONObject();
            jo.put("name", fs.name);
            jo.put("url", fs.url);
            jo.put("type", fs.type.name());
            jo.put("channel", fs.channelId);
            jfeeds.put(jo);
        }

        saveGuildData(guildId, null, jfeeds, null, null);

    }

    //Prefix Stuff
    public static List<String> getGuildPrefixes(String guildId) {
        JSONObject jfile = loadGuildData(guildId);
        List<String> list = new ArrayList<>();

        try{
            jfile.get("prefixes");
        }catch(JSONException e){
            return new ArrayList<>();
        }

        for(Object o : jfile.getJSONArray("prefixes")) {
            list.add(String.valueOf(o));
        }
        return list;
    }

    public static void setGuildPrefixes(String guildId, List<String> list) {
        JSONArray ja = new JSONArray();
        for (String s : list) {
            ja.put(s);
        }
        saveGuildData(guildId, null, null, null, ja);
    }

    //Starboard Stuff
    public static String getGuildStarboardChannel(String guildId) {
        JSONObject jfile = loadGuildData(guildId);

        try{
            return jfile.getJSONObject("starboard").getString("channel");
        }catch(Exception e){
            return "disabled";
        }
    }

    public static void setGuildStarboardChannel(String guildId, String channelId) {
        JSONObject jfile = loadGuildData(guildId);
        JSONObject jo;

        jo = new JSONObject().put("channel", channelId);

        saveGuildData(guildId, null, null, jo, null);
    }

    public static HashMap<String, String> loadGuildStarboard(String guildId) {
        JSONObject jfile = loadGuildData(guildId);

        try{
            jfile.get("starboard");
        }catch(JSONException e){
            return new HashMap<>();
        }

        HashMap<String, String> star = new HashMap<>();

        for (String s : ((JSONObject) jfile.get("starboard")).keySet()) {
            star.put(s, jfile.getJSONObject("starboard").getString(s));
        }
        return star;
    }

    public static void saveGuildStarboard(String guildId, HashMap<String, String> hashMap) {

        JSONObject star = new JSONObject();

        for (String s : hashMap.keySet()) {
            star.put(s, hashMap.get(s));
        }

        saveGuildData(guildId, null, null, star, null);
    }

    //Stuff?
    private static JSONObject loadGuildData(String guildId) {
        Path globalDataPath = Paths.get(Config.getGuildDataFile(guildId).getAbsolutePath());

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

    private static void saveGuildData(String guildId, JSONObject perm, JSONArray feeds, JSONObject starboard, JSONArray prefixes) {
        Path guildDataPath = Paths.get(Config.getGuildDataFile(guildId).getAbsolutePath());

        JSONObject jfile = new JSONObject();

        if (prefixes != null) {
            jfile.put("prefixes", prefixes);
        }else{
            try{
                jfile.put("prefixes", loadGuildData(guildId).get("prefixes"));
            }catch(JSONException ignored){}
        }

        if (starboard != null) {
            jfile.put("starboard", starboard);
        }else{
            try{
                jfile.put("starboard", loadGuildData(guildId).get("starboard"));
            }catch(JSONException ignored){}
        }

        if (perm != null) {
            jfile.put("guildPerms", perm);
        }else{
            try{
                jfile.put("guildPerms", loadGuildData(guildId).get("guildPerms"));
            }catch(JSONException ignored){}
        }

        if (feeds != null) {
            jfile.put("guildFeeds", feeds);
        }else{
            try{
                jfile.put("guildFeeds", loadGuildData(guildId).get("guildFeeds"));
            }catch(JSONException ignored){}
        }

        try{
            Files.write(guildDataPath, jfile.toString().getBytes("UTF-8"));
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private static void createGuildData(String guildId) {
        Path globalDataPath = Paths.get(Config.getGuildDataFile(guildId).getAbsolutePath());

        JSONObject jfile = new JSONObject();

        try{
            Files.write(globalDataPath, jfile.toString().getBytes("UTF-8"));
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
