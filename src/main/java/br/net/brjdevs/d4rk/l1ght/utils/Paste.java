package br.net.brjdevs.d4rk.l1ght.utils;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Paste {

    public static String toHastebin(String toSend) {
        try {
            String pasteToken = Unirest.post("https://hastebin.com/documents")
                    .header("User-Agent", "L1ght")
                    .header("Content-Type", "text/plain")
                    .body(toSend)
                    .asJson()
                    .getBody()
                    .getObject()
                    .getString("key");
            return "https://hastebin.com/" + pasteToken;
        } catch (UnirestException e) {
            e.printStackTrace();
            return "Caught an unexpected ``" + e.getMessage() + "``" + " while trying to upload paste, check logs";
        }
    }
}
