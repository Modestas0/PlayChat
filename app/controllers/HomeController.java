package controllers;

import models.HomeModel;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import queries.UserQuery;
import utils.SessionUtils;
import views.html.index;

import com.google.inject.Inject;

public class HomeController extends BaseController {
    @Inject
    private FormFactory formFactory;
    @Inject
    private UserQuery userQuery;

    public Result index() {
        if(SessionUtils.isLoggedIn()) {
            return redirect(controllers.routes.ChatController.chat());
        }

        HomeModel model = new HomeModel();
        Form<HomeModel> form = formFactory.form(HomeModel.class).fill(model);

        return ok(index.render(form));
    }

    public Result login() {
        if(SessionUtils.isLoggedIn()) {
            return redirect(controllers.routes.ChatController.chat());
        }

        HomeModel model;
        Form<HomeModel> form;

        try {
            form = getForm().bindFromRequest();
            model = form.get();
        } catch(Exception ex) {
            model = new HomeModel("", "Invalid login form data");
            form = getForm().fill(model);
            return ok(index.render(form));
        }

        String username = model.getUsername();
        String password = model.getPassword();

        Integer userId = userQuery.getUserId(username, password);
        if(userId == null) {
            model = new HomeModel(username, "Wrong username or password");
            form = getForm().fill(model);
            return ok(index.render(form));
        }

        SessionUtils.logIn(username, userId);
        return redirect(controllers.routes.ChatController.chat());
    }

    public Form<HomeModel> getForm() {
        return formFactory.form(HomeModel.class);
    }
}
