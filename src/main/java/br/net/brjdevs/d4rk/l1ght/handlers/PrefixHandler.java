package br.net.brjdevs.d4rk.l1ght.handlers;

import br.net.brjdevs.d4rk.l1ght.L1ght;
import br.net.brjdevs.d4rk.l1ght.utils.Config;
import net.dv8tion.jda.core.utils.SimpleLog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PrefixHandler {

    public static HashMap<String, List<String>> prefixes = new HashMap<>();

    public static void start() {

        SimpleLog log = SimpleLog.getLog("Prefix-Handler");
        log.log(SimpleLog.Level.INFO,"Starting thread!");

        check();

        new Thread(() -> {
           log.log(SimpleLog.Level.INFO, "Started!");
           Timer timer = new Timer();
           TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    log.log(SimpleLog.Level.INFO, "Running scheduled prefix check.");
                    check();
                }
           };
           timer.schedule(task, 300000, 300000);
        }).start();


    }

    public static void check() {
        prefixes = new HashMap<>();
        List<File> list = Arrays.asList(Config.getGuildDataFolder().listFiles());
        for (File f : list) {
            String jsons;
            try {
                jsons = new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())),"UTF-8");
            }catch (Exception e) {
                e.printStackTrace();
                jsons = "{}";
            }
            JSONObject json = new JSONObject(jsons);

            try{
                JSONArray prefs = json.getJSONArray("prefixes");
                List<String> gprefs = new ArrayList<>();
                for(Object o : prefs) {
                    gprefs.add(String.valueOf(o));
                }
                prefixes.put(f.getName().substring(0, f.getName().length()-5) , gprefs);
            }catch(Exception ignored) {}
        }

    }


}
