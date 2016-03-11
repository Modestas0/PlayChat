package utils;

import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.mvc.Controller;

public class ControllerUtils {
    public static JsonNode getBodyAsJson() {
        JsonNode json = null;
        try {
            json = Controller.request().body().asJson();
        } catch (Exception ex) {
            Logger.error("Error getting json from body", ex);
        }
        return json;
    }
}
