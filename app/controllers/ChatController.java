package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.chat;

public class ChatController extends Controller {
    public Result chat() {

        return ok(chat.render());
    }
}
