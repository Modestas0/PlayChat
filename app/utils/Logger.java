package utils;

public class Logger {
    public static void debug(String message) {
        System.out.println("[debug] " + message);
    }

    public static void error(String message) {
        System.err.println("[error] " + message);
    }

    public static void error(String message, Exception execption) {
        System.err.println("[error] " + message);
        execption.printStackTrace();
    }
}
