package utils;

import play.Logger;
import play.mvc.Controller;

public class SessionUtils {
    private static final String USERNAME = "username";
    private static final String USER_ID = "user_id";
    private static final String LAST_ACTIVE = "last_active";

    private static final long SESSION_VALIDITY_TIME_MS = 1000L * 60L; // 1 minute

    public static boolean logIn(String username, int userId) {
        Controller.session(USERNAME, username);
        Controller.session(USER_ID, Integer.toString(userId));
        Controller.session(LAST_ACTIVE, Long.toString(System.currentTimeMillis()));
        return true;
    }

    public static boolean isLoggedIn() {
        String username = Controller.session(USERNAME);
        String lastActive = Controller.session(LAST_ACTIVE);

        if(username == null || lastActive == null) {
            return false;
        }

        long lastActiveMs;
        try {
            lastActiveMs = Long.valueOf(lastActive);
        } catch (NumberFormatException ex) {
            Logger.error("Session last active does not contain integer", ex);
            return false;
        }

        long now = System.currentTimeMillis();
        if(now - lastActiveMs > SESSION_VALIDITY_TIME_MS) {
            Controller.session().clear();
            return false;
        } else {
            Controller.session(LAST_ACTIVE, String.valueOf(now));
        }

        return true;
    }

    public static String getUsername() {
        String username = Controller.session(USERNAME);

        if(username == null) {
            return "";
        }

        return username;
    }

    public static Integer getUserId() {
        String id = Controller.session(USER_ID);

        if(id == null) {
            return null;
        }

        return Integer.valueOf(id);
    }
}
