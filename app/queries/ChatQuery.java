package queries;

import com.google.inject.Singleton;
import models.MessageModel;
import utils.StringUtils;

import java.time.LocalDateTime;
import java.util.LinkedList;

@Singleton
public class ChatQuery {
    private LinkedList<MessageModel> messages = new LinkedList<>();

    /**
     * Get all messages ordered from oldest to newest
     * @return messages
     */
    public LinkedList<MessageModel> getMessages() {
        return messages;
    }

    /**
     * Get messages with higher id than provided
     * @param id from which id to show messages (non inclusive)
     * @return messages
     */
    public LinkedList<MessageModel> getMessagesAfter(int id) {
        LinkedList<MessageModel> after = new LinkedList<>();
        for(MessageModel message : messages) {
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
        MessageModel model = new MessageModel(messages.size(), LocalDateTime.now(), username, message);
        messages.add(model);
    }
}
