package database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import database.models.Message;
import play.db.Database;
import utils.DateUtils;
import utils.Logger;

import java.sql.*;
import java.util.LinkedList;

@Singleton
public class MessageTable {
    @Inject
    Database db;

    private static final String selectAllMessages = "SELECT message.id AS id, " +
            "message.time AS time, " +
            "user.username AS username, " +
            "message.message AS message " +
            "FROM message, user " +
            "WHERE user.id = message.user_id " +
            "ORDER BY message.id ASC;";

    private static final String selectMessagesFromId = "SELECT message.id AS id, " +
            "message.time AS time, " +
            "user.username AS username, " +
            "message.message AS message " +
            "FROM message, user " +
            "WHERE message.id > ? " +
            "AND user.id = message.user_id " +
            "ORDER BY message.id ASC;";

    private static final String insertMessage = "INSERT INTO message (time, user_id, message) " +
            "VALUES (?, ?, ?);";

    public LinkedList<Message> getMessages() {
        LinkedList<Message> messages = new LinkedList<>();
        Connection connection = db.getConnection();
        Statement statement = null;
        ResultSet result = null;

        try {
            statement = connection.createStatement();
            result = statement.executeQuery(selectAllMessages);
            while (result.next()) {
                Message message = new Message();
                message.setId(result.getInt("id"));
                message.setTime(DateUtils.toLocalDateTime(result.getTimestamp("time")));
                message.setUsername(result.getString("username"));
                message.setMessage(result.getString("message"));
                messages.add(message);
            }
        } catch (SQLException ex) {
            Logger.error("Error in MessageTable.getMessages", ex);
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
                Logger.error("Error while closing connection in MessageTable.getMessages", ex);
            }
        }

        return messages;
    }

    public LinkedList<Message> getMessagesFromId(int id) {
        LinkedList<Message> messages = new LinkedList<>();
        Connection connection = db.getConnection();
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = connection.prepareStatement(selectMessagesFromId);
            statement.setInt(1, id);
            result = statement.executeQuery();
            while (result.next()) {
                Message message = new Message();
                message.setId(result.getInt("id"));
                message.setTime(DateUtils.toLocalDateTime(result.getTimestamp("time")));
                message.setUsername(result.getString("username"));
                message.setMessage(result.getString("message"));
                messages.add(message);
            }
        } catch (SQLException ex) {
            Logger.error("Error in MessageTable.getMessagesFromId", ex);
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
                Logger.error("Error while closing connection in MessageTable.getMessagesFromId", ex);
            }
        }

        return messages;
    }

    public void addMessage(int userId, String message) {
        Connection connection = db.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(insertMessage);
            statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            statement.setInt(2, userId);
            statement.setString(3, message);
            statement.execute();
        } catch (SQLException ex) {
            Logger.error("Error in MessageTable.addMessage", ex);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException ex) {
                Logger.error("Error while closing connection in MessageTable.addMessage", ex);
            }
        }
    }
}
