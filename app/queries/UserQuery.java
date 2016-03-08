package queries;

import java.util.HashMap;
import java.util.Map;
import utils.BCrypt;
import com.google.inject.Singleton;

@Singleton
public class UserQuery {
    private Map<String, String> users = new HashMap<>();

    public boolean userExist(String username, String password) {
        if(username == null || password == null || password.length() == 0) {
            return false;
        }
        if (!users.containsKey(username)) {
            return false;
        }
        if (!BCrypt.checkpw(password, users.get(username))) {
            return false;
        }
        return true;
    }

    public boolean addUser(String username, String password) {
        if(username == null || password == null || password.length() == 0) {
            return false;
        }
        if(users.containsKey(username)) {
            return false;
        }
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        users.put(username, passwordHash);
        return true;
    }
}
