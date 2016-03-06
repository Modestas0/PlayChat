package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.MessageModel;
import play.libs.Json;
import play.mvc.Result;
import queries.ChatQuery;
import utils.ControllerUtils;
import views.html.chat;

import com.google.inject.Inject;

import java.util.List;

public class ChatController extends BaseController {
    @Inject
    ChatQuery chatQuery;

    public Result chat() {

        return ok(chat.render());
    }

    public Result getMessages() {
        List<MessageModel> messages;

        JsonNode json = ControllerUtils.getBodyAsJson();
        if (json == null) {
            return badRequest("Expecting json data");
        }

        JsonNode idNode = json.findValue("id");
        if (idNode == null) {
            messages = chatQuery.getMessages();
        } else {
            messages = chatQuery.getMessagesAfter(idNode.asInt());
        }

        JsonNode result = Json.toJson(messages);
        return ok(result);
    }

    public Result postMessage() {
        String username = session("username");

        JsonNode json = ControllerUtils.getBodyAsJson();
        if (json == null) {
            return badRequest("Expecting json data");
        }

        JsonNode messageNode = json.findValue("message");
        if (messageNode == null) {
            return badRequest("Expecting message");
        }

        String message = messageNode.asText();

        chatQuery.addMessage(username, message);

        JsonNode result = Json.newObject();
        return ok(result);
    }
}
