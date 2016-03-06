package utils;

import play.mvc.Controller;

public class SessionUtils {
    public static boolean logIn(String username) {
        Controller.session("username", username);
        return true;
    }

    public static boolean isLoggedIn() {
        return Controller.session("username") != null;
    }

    public static String getUsername() {
        String username = Controller.session("username");
        if(username == null) {
            username = "";
        }
        return username;
    }
}
