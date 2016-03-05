package controllers;

import models.HomeModel;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.google.inject.Inject;

public class HomeController extends Controller {
    @Inject
    private FormFactory formFactory;

    public Result index() {
        HomeModel model = getModel();
        Form<HomeModel> form = formFactory.form(HomeModel.class).fill(model);

        return ok(index.render(form));
    }

    public Result login() {
        return ok();
    }

    private HomeModel getModel() {
        return new HomeModel();
    }

}
