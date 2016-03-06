package controllers;

import models.HomeModel;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import utils.SessionUtils;
import views.html.chat;
import views.html.index;

import com.google.inject.Inject;

public class HomeController extends BaseController {
    @Inject
    private FormFactory formFactory;

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
            model = new HomeModel("", "Nepavyko prisijungti!");
            form = getForm().fill(model);
            return ok(index.render(form));
        }

        String username = model.getUsername();

        if(username == null || !username.matches("^[a-zA-Z0-9._-]{3,16}$")) {
            model.setLoginError("Prisijungimo vardas turi būti nuo 3 iki 16 simbolių ilgio ir sudarytas iš raidžių, skaitmenų arba šių simbolių: . _ -");
            form = getForm().fill(model);
            return ok(index.render(form));
        }

        SessionUtils.logIn(username);
        return redirect(controllers.routes.ChatController.chat());
    }

    public Form<HomeModel> getForm() {
        return formFactory.form(HomeModel.class);
    }
}
