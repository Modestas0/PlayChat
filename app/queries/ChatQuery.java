package queries;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import database.MessageTable;
import database.models.Message;
import utils.StringUtils;

import java.util.LinkedList;

@Singleton
public class ChatQuery {
    @Inject
    MessageTable messageTable;

    /**
     * Get all messages ordered from oldest to newest
     * @return messages
     */
    public LinkedList<Message> getMessages() {
        return messageTable.getMessages();
    }

    /**
     * Get messages with higher id than provided
     * @param id from which id to show messages (non inclusive)
     * @return messages
     */
    public LinkedList<Message> getMessagesAfter(int id) {
        return messageTable.getMessagesFromId(id);
    }

    /**
     * Add new message to chat
     * @param userId id of user who posted it
     * @param message what user posted
     */
    public void addMessage(int userId, String message) {
        message = StringUtils.escapeHTML(message);
        message = message.replace("\n", "<br>");
        messageTable.addMessage(userId, message);
    }
}
