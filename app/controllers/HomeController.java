package controllers;

import models.HomeModel;
import play.mvc.*;
import views.html.*;

public class HomeController extends Controller {

    public Result index() {
        HomeModel model = getModel();

        return ok(index.render(model));
    }

    private HomeModel getModel() {
        return new HomeModel();
    }

}
