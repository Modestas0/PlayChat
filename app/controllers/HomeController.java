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
        HomeModel model = new HomeModel();
        Form<HomeModel> form = formFactory.form(HomeModel.class).fill(model);

        return ok(index.render(form));
    }

    public Result login() {
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

        session("username", model.getUsername());
        return ok();
    }

    public Form<HomeModel> getForm() {
        return formFactory.form(HomeModel.class);
    }
}
