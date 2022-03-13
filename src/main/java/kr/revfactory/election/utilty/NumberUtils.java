package kr.revfactory.election.utilty;

public class NumberUtils {
    public static int toInt(String number) {
        return Integer.parseInt(number.replaceAll(",", ""));
    }
}
