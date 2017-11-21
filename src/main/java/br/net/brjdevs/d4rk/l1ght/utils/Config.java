package br.net.brjdevs.d4rk.l1ght.utils;

import br.net.brjdevs.d4rk.l1ght.L1ght;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {

    public static String prefix = null;
    public static String hardPrefix = null;
    public static String defaultPlaying = null;
    public static boolean isStreaming = false;
    public static String logChannel = null;

    public static String token = null;
    public static String mashapeKey = null;
    public static String vagalumeKey = null;

    public static String twitterConsumer = null;
    public static String twitterConsumerSecret = null;
    public static String twitterToken = null;
    public static String twitterTokenSecret = null;

    private static boolean redditStream = false;

    public static void loadConfig() {

        Path globalDataPath = Paths.get(getConfigFile().getAbsolutePath());

        if(Files.notExists(globalDataPath)) {
            createConfig();
        }

        String jsons;
        try {
            jsons = new String(Files.readAllBytes(globalDataPath),"UTF-8");
        }catch (Exception e) {
            System.out.println(e);
            jsons = "{}";
        }

        JSONObject json = new JSONObject(jsons);

        try{prefix = json.getString("prefix");}catch(Exception ignored){}
        try{hardPrefix = json.getString("hardPrefix");}catch(Exception ignored){}
        try{ defaultPlaying = json.getString("defaultPlaying");}catch(Exception ignored){}
        try{defaultPlaying = defaultPlaying.replace("%", prefix);}catch(Exception ignored){}
        try{isStreaming = json.getBoolean("isStreaming");}catch(Exception ignored){}
        try{logChannel = json.getString("logChannel");}catch(Exception ignored){}

        try{token = json.getString("token");}catch(Exception ignored){}
        try{mashapeKey = json.getString("mashapeKey");}catch(Exception ignored){}
        try{vagalumeKey = json.getString("vagalumeKey");}catch(Exception ignored){}

        try{twitterConsumer = json.getString("twitterConsumer");}catch(Exception ignored){}
        try{twitterConsumerSecret = json.getString("twitterConsumerSecret");}catch(Exception ignored){}
        try{twitterToken = json.getString("twitterToken");}catch(Exception ignored){}
        try{twitterTokenSecret = json.getString("twitterTokenSecret");}catch(Exception ignored){}

        try{redditStream = json.getBoolean("redditStream");}catch(Exception ignored){}
    }

    public static boolean isTwitterEnabled() {
        if((twitterTokenSecret.length() > 5 || twitterTokenSecret == null) ||
           (twitterToken.length() > 5 || twitterToken == null) ||
           (twitterConsumerSecret.length() > 5 || twitterConsumerSecret == null) ||
           (twitterConsumer.length() > 5 || twitterConsumer == null)
        ){ return true; }else{ return false; }
    }

    public static boolean isRedditEnabled() {
        return redditStream;
    }


    public static void createConfig() {
        Path globalDataPath = Paths.get(getConfigFile().getAbsolutePath());

        JSONObject jfile = new JSONObject();
        jfile.put("prefix", "");
        jfile.put("hardPrefix", "");
        jfile.put("defaultPlaying", "");
        jfile.put("isStreaming", false);
        jfile.put("logChannel", "");
        jfile.put("token", "");
        jfile.put("mashapeKey", "");
        jfile.put("vagalumeKey", "");
        jfile.put("twitterConsumer", "");
        jfile.put("twitterConsumerSecret", "");
        jfile.put("twitterToken", "");
        jfile.put("twitterTokenSecret","");
        jfile.put("redditStream", false);


        try{
            Files.write(globalDataPath, jfile.toString().getBytes("UTF-8"));
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static File getGuildDataFolder() {
        if(System.getProperty("os.name").startsWith("Windows")){
            return new File(new File(L1ght.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile(), "\\guildData");
        }else{
            return new File(new File(L1ght.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile(), "/guildData");
        }
    }

    public static File getGuildDataFile(String guildId) {
        if(System.getProperty("os.name").startsWith("Windows")){
            return new File(new File(L1ght.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile(), "\\guildData\\"+guildId+".json");
        }else{
            return new File(new File(L1ght.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile(), "/guildData/"+guildId+".json");
        }
    }

    public static File getGlobalDataFile() {
        return new File(new File(L1ght.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile(), "globalData.json");
    }

    public static File getConfigFile() {
        return new File(new File(L1ght.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile(), "config.json");
    }
}
