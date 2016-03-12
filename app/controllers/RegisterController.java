package controllers;

import com.google.inject.Inject;
import models.HomeModel;
import models.RegisterModel;
import org.h2.engine.Session;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import queries.UserQuery;
import utils.SessionUtils;
import views.html.index;
import views.html.register;

public class RegisterController extends BaseController {
    @Inject
    private FormFactory formFactory;
    @Inject
    private UserQuery userQuery;

    public Result register() {
        if (SessionUtils.isLoggedIn()) {
            return redirect(controllers.routes.ChatController.chat());
        }

        RegisterModel model = new RegisterModel();
        Form<RegisterModel> form = getForm().fill(model);

        return ok(register.render(form));
    }

    public Result submit() {
        if (SessionUtils.isLoggedIn()) {
            return redirect(controllers.routes.ChatController.chat());
        }

        RegisterModel model;
        Form<RegisterModel> form;

        try {
            form = getForm().bindFromRequest();
            model = form.get();
        } catch (Exception ex) {
            return error("", "Invalid register form data");
        }

        String username = model.getUsername();
        String password = model.getPassword();
        String passwordAgain = model.getPasswordAgain();

        if (username == null || !username.matches("^[a-zA-Z0-9._-]{3,16}$")) {
            return error(username, "Username length must be from 3 to 16 symbols and may contain letters, digits or these symbols: . _ -");
        }

        if (password == null || !password.matches("^(?=.*\\d)(?=.*[a-zA-Z]).{6,}$")) {
            return error(username, "Password must contain at least one letter and one digit and must be at least 6 symbols in length");
        }

        if (!password.equals(passwordAgain)) {
            return error(username, "Passwords do not match");
        }

        if(!userQuery.addUser(username, password)) {
            return error(username, "Username is already used");
        }

        HomeModel homeModel = new HomeModel();
        homeModel.setUsername(username);
        homeModel.setSuccessMessage("Registration completed! Now you can log in");

        Form<HomeModel> homeForm = formFactory.form(HomeModel.class).fill(homeModel);
        return ok(index.render(homeForm));
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
