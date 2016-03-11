package queries;

import com.google.inject.Singleton;
import database.models.Message;
import utils.StringUtils;

import java.time.LocalDateTime;
import java.util.LinkedList;

@Singleton
public class ChatQuery {
    private LinkedList<Message> messages = new LinkedList<>();

    /**
     * Get all messages ordered from oldest to newest
     * @return messages
     */
    public LinkedList<Message> getMessages() {
        return messages;
    }

    /**
     * Get messages with higher id than provided
     * @param id from which id to show messages (non inclusive)
     * @return messages
     */
    public LinkedList<Message> getMessagesAfter(int id) {
        LinkedList<Message> after = new LinkedList<>();
        for(Message message : messages) {
            if(message.getId() > id) {
                after.add(message);
            }
        }
        return after;
    }

    /**
     * Add new message to chat
     * @param username who posted it
     * @param message what posted
     */
    public void addMessage(String username, String message) {
        message = StringUtils.escapeHTML(message);
        message = message.replace("\n", "<br>");
        Message model = new Message(messages.size(), LocalDateTime.now(), username, message);
        messages.add(model);
    }
}
