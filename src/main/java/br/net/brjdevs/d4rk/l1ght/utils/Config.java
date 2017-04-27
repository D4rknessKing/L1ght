package br.net.brjdevs.d4rk.l1ght.utils;

import br.net.brjdevs.d4rk.l1ght.L1ght;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {

    public static String prefix;
    public static String defaultPlaying;
    public static boolean isStreaming;

    public static String token;
    public static String mashapeKey;

    public static void loadConfig() {

        Path globalDataPath = Paths.get(new File(new File(L1ght.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile(), "config.json").getAbsolutePath());

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

        prefix = (String) json.get("prefix");
        defaultPlaying = (String) json.get("defaultPlaying");
        defaultPlaying = defaultPlaying.replace("%", prefix);
        isStreaming = (boolean) json.get("isStreaming");

        token = (String) json.get("token");
        mashapeKey = (String) json.get("mashapeKey");
    }


    public static void createConfig() {
        Path globalDataPath = Paths.get(new File(new File(L1ght.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile(), "config.json").getAbsolutePath());

        JSONObject jfile = new JSONObject();
        jfile.put("prefix", "");
        jfile.put("defaultPlaying", "");
        jfile.put("isStreaming", false);
        jfile.put("token", "");
        jfile.put("mashapeKey", "");


        try{
            Files.write(globalDataPath, jfile.toString().getBytes("UTF-8"));
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
