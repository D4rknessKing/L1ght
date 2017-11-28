package br.net.brjdevs.d4rk.l1ght.utils;

public class RandomUtils {

    public static int getPokemonTypeColor(String type) {
        int co = 0;
        switch (type) {
            case "normal":
                co = 11053176;
                break;
            case "fight":
                co = 12595240;
                break;
            case "flying":
                co = 11047152;
                break;
            case "poison":
                co = 10502304;
                break;
            case "ground":
                co = 14729320;
                break;
            case "rock":
                co = 12099640;
                break;
            case "bug":
                co = 11057184;
                break;
            case "ghost":
                co = 7362712;
                break;
            case "steel":
                co = 12105936;
                break;
            case "fire":
                co = 15761456;
                break;
            case "water":
                co = 6852848;
                break;
            case "grass":
                co = 7915600;
                break;
            case "electric":
                co = 16306224;
                break;
            case "psychic":
                co = 16275592;
                break;
            case "ice":
                co = 10016984;
                break;
            case "dragon":
                co = 7354616;
                break;
            case "dark":
                co = 7362632;
                break;
            case "fairy":
                co = 15636908;
                break;
        }
        return co;
    }

    public static String capitalizeFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    //Adrian porrinha para hora
    public static String getTime(long duration) {
        final long
                years = duration / 31104000000L,
                months = duration / 2592000000L % 12,
                days = duration / 86400000L % 30,
                hours = duration / 3600000L % 24,
                minutes = duration / 60000L % 60,
                seconds = duration / 1000L % 60;
        String uptime = (years == 0 ? "" : years + " Year" + cS(years) + ", ") + (months == 0 ? "" : months + " Month" + cS(months) + ", ")
                + (days == 0 ? "" : days + " Day" + cS(days) + ", ") + (hours == 0 ? "" : hours + " Hour" + cS(hours) + ", ")
                + (minutes == 0 ? "" : minutes + " Minute" + cS(minutes) + ", ") + (seconds == 0 ? "" : seconds + " Second" + cS(seconds) + ", ");

        uptime = replaceLast(uptime, ", ", "");
        uptime = replaceLast(uptime, ",", " and");

        return uptime;
    }

    //Adrian porrinha para hora
    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

    //Adrian porrinha para hora
    public static String cS(long value) {
        if(value == 1) { return "";}
        return "s";
    }

}
