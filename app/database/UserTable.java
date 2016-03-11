package database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import database.models.User;
import play.db.Database;
import utils.DateUtils;
import utils.Logger;

import java.sql.*;

@Singleton
public class UserTable {
    @Inject
    Database db;

    private static final String selectUser = "SELECT user.id AS id, " +
            "user.username AS username, " +
            "user.password AS password, " +
            "user.last_active AS last_active " +
            "FROM user " +
            "WHERE user.username = ?;";

    private static final String updateLastActive = "UPDATE user " +
            "SET user.last_active = ? " +
            "WHERE user.id = ?;";

    private static final String insertUser = "INSERT INTO user (username, password, last_active) " +
            "VALUES (?, ?, ?);";

    public User getUser(String username) {
        Connection connection = db.getConnection();
        PreparedStatement statement = null;
        ResultSet result = null;
        User user = null;

        try {
            statement = connection.prepareStatement(selectUser);
            statement.setString(1, username);
            result = statement.executeQuery();
            if (result.next()) {
                user = new User();
                user.setId(result.getInt("id"));
                user.setUsername(result.getString("username"));
                user.setPasswordHash(result.getString("password"));
                user.setLastActive(DateUtils.toLocalDateTime(result.getTimestamp("last_active")));
            }
        } catch (SQLException ex) {
            Logger.error("Error in UserTable.getUser", ex);
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException ex) {
                Logger.error("Error while closing connection in UserTable.getUser", ex);
            }
        }

        return user;
    }

    public void updateLastActive(int userId) {
        Connection connection = db.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(updateLastActive);
            statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            statement.setInt(2, userId);
            if (statement.executeUpdate() != 1) {
                Logger.error("userId not found [id=" + userId + "] and last_active not changed");
            }
        } catch (SQLException ex) {
            Logger.error("Error in UserTable.updateLastActive", ex);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException ex) {
                Logger.error("Error while closing connection in UserTable.updateLastActive", ex);
            }
        }
    }

    public void addUser(String username, String passwordHash) {
        Connection connection = db.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(insertUser);
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.execute();
        } catch (SQLException ex) {
            Logger.error("Error in UserTable.addUser", ex);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException ex) {
                Logger.error("Error while closing connection in UserTable.addUser", ex);
            }
        }
    }
}
