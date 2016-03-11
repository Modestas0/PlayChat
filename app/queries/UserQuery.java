package queries;

import com.google.inject.Inject;
import database.UserTable;
import database.models.User;
import utils.BCrypt;
import com.google.inject.Singleton;

@Singleton
public class UserQuery {
    @Inject
    UserTable userTable;

    /**
     * Checks if such user with such password exists
     * if exists, returns user id
     * if not, returs null
     *
     * @param username
     * @param password
     * @return
     */
    public Integer getUserId(String username, String password) {
        if (username == null || password == null || password.length() == 0) {
            return null;
        }

        User user = userTable.getUser(username);
        if (user == null) {
            return null;
        }

        if (!BCrypt.checkpw(password, user.getPasswordHash())) {
            return null;
        }

        userTable.updateLastActive(user.getId());
        return user.getId();
    }

    /**
     * Adds new user to database
     *
     * @param username
     * @param password
     * @return false if wrong parameters or user already exists, true otherwise
     */
    public boolean addUser(String username, String password) {
        if (username == null || password == null || password.length() == 0) {
            return false;
        }

        if (userTable.getUser(username) != null) {
            return false;
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        userTable.addUser(username, passwordHash);
        return true;
    }
}
