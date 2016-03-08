package controllers;

import com.google.inject.Inject;
import models.RegisterModel;
import org.h2.engine.Session;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import utils.SessionUtils;
import views.html.register;

public class RegisterController extends BaseController {
    @Inject
    private FormFactory formFactory;

    public Result register() {
        if(SessionUtils.isLoggedIn()) {
            return redirect(controllers.routes.ChatController.chat());
        }

        RegisterModel model = new RegisterModel();
        Form<RegisterModel> form = getForm().fill(model);

        return ok(register.render(form));
    }

    public Result submit() {
        if(SessionUtils.isLoggedIn()) {
            return redirect(controllers.routes.ChatController.chat());
        }

        RegisterModel model;
        Form<RegisterModel> form;

        try {
            form = getForm().bindFromRequest();
            model = form.get();
        } catch (Exception ex) {
            return error("", "Nepavyko užregistruoti!");
        }

        String username = model.getUsername();
        String password = model.getPassword();
        String passwordAgain = model.getPasswordAgain();

        if(username == null || !username.matches("^[a-zA-Z0-9._-]{3,16}$")) {
            return error(username, "Prisijungimo vardas turi būti nuo 3 iki 16 simbolių ilgio ir sudarytas iš raidžių, skaitmenų arba šių simbolių: . _ -");
        }

        if(password == null || !password.matches("^(?=.*\\d)(?=.*[a-zA-Z]).{6,}$")) {
            return error(username, "Slaptažodis turi būti sudarytas iš bent vienos raidės ir bent vieno skaičiaus ir turi būti bent 6 simbolių ilgio.");
        }

        if(!password.equals(passwordAgain)) {
            return error(username, "Slaptažodžiai nesutampa");
        }

        return ok();
    }

    private Form<RegisterModel> getForm() {
        return formFactory.form(RegisterModel.class);
    }

    private Result error(String username, String error) {
        RegisterModel model = new RegisterModel(username, error);
        Form<RegisterModel> form = getForm().fill(model);

        return ok(register.render(form));
    }
}
