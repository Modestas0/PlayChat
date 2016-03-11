package queries;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import database.UserTable;
import database.models.User;
import utils.BCrypt;
import com.google.inject.Singleton;

@Singleton
public class UserQuery {
    @Inject
    UserTable userTable;
    private Map<String, String> users = new HashMap<>();

    public boolean userExist(String username, String password) {
        if(username == null || password == null || password.length() == 0) {
            return false;
        }

        User user = userTable.getUser(username);
        if (user == null) {
            return false;
        }

        if (!BCrypt.checkpw(password, user.getPasswordHash())) {
            return false;
        }

        userTable.updateLastActive(user.getId());
        return true;
    }

    public boolean addUser(String username, String password) {
        if(username == null || password == null || password.length() == 0) {
            return false;
        }

        if(userTable.getUser(username) != null) {
            return false;
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        userTable.addUser(username, passwordHash);
        return true;
    }
}
