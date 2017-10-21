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
}
