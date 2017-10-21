package br.net.brjdevs.d4rk.l1ght.utils;

import br.net.brjdevs.d4rk.l1ght.L1ght;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {

    public static String prefix;
    public static String hardPrefix;
    public static String defaultPlaying;
    public static boolean isStreaming;

    public static String token;
    public static String mashapeKey;
    public static String vagalumeKey;

    public static String twitterConsumer;
    public static String twitterConsumerSecret;
    public static String twitterToken;
    public static String twitterTokenSecret;

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

        prefix = json.getString("prefix");
        hardPrefix = json.getString("hardPrefix");
        defaultPlaying = json.getString("defaultPlaying");
        defaultPlaying = defaultPlaying.replace("%", prefix);
        isStreaming = json.getBoolean("isStreaming");

        token = json.getString("token");
        mashapeKey = json.getString("mashapeKey");
        vagalumeKey = json.getString("vagalumeKey");

        twitterConsumer = json.getString("twitterConsumer");
        twitterConsumerSecret = json.getString("twitterConsumerSecret");
        twitterToken = json.getString("twitterToken");
        twitterTokenSecret = json.getString("twitterTokenSecret");
    }


    public static void createConfig() {
        Path globalDataPath = Paths.get(getConfigFile().getAbsolutePath());

        JSONObject jfile = new JSONObject();
        jfile.put("prefix", "");
        jfile.put("hardPrefix", "");
        jfile.put("defaultPlaying", "");
        jfile.put("isStreaming", false);
        jfile.put("token", "");
        jfile.put("mashapeKey", "");
        jfile.put("vagalumeKey", "");
        jfile.put("twitterConsumer", "");
        jfile.put("twitterConsumerSecret", "");
        jfile.put("twitterToken", "");
        jfile.put("twitterTokenSecret","");


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
