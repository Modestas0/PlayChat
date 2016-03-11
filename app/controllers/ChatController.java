package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import database.models.Message;
import play.libs.Json;
import play.mvc.Result;
import queries.ChatQuery;
import utils.ControllerUtils;
import utils.SessionUtils;
import views.html.chat;

import com.google.inject.Inject;

import java.util.List;

public class ChatController extends BaseController {
    @Inject
    ChatQuery chatQuery;

    public Result chat() {
        if (!SessionUtils.isLoggedIn()) {
            return redirect(controllers.routes.HomeController.index());
        }

        return ok(chat.render());
    }

    public Result getMessages() {
        if (!SessionUtils.isLoggedIn()) {
            return forbidden("User is not logged in");
        }

        List<Message> messages;

        JsonNode json = ControllerUtils.getBodyAsJson();
        if (json == null) {
            return badRequest("Expecting json data");
        }

        JsonNode idNode = json.findValue("id");
        if (idNode == null) {
            messages = chatQuery.getMessages();
        } else {
            int id = idNode.asInt(-1);
            if (id < 0) {
                return badRequest("Expecting positive integer");
            }
            messages = chatQuery.getMessagesAfter(id);
        }

        JsonNode result = Json.toJson(messages);
        return ok(result);
    }

    public Result postMessage() {
        if (!SessionUtils.isLoggedIn()) {
            return forbidden("User is not logged in");
        }

        Integer userId = SessionUtils.getUserId();

        if (userId == null) {
            return forbidden("User is not logged in");
        }

        JsonNode json = ControllerUtils.getBodyAsJson();
        if (json == null) {
            return badRequest("Expecting json data");
        }

        JsonNode messageNode = json.findValue("message");
        if (messageNode == null) {
            return badRequest("Expecting message");
        }

        String message = messageNode.asText();
        if (message.isEmpty()) {
            return badRequest("Message cannot be empty");
        }

        chatQuery.addMessage(userId, message);

        JsonNode result = Json.newObject();
        return ok(result);
    }
}
