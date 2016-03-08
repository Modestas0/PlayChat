package controllers;

import com.google.inject.Inject;
import models.RegisterModel;
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

        return ok();
    }

    private Form<RegisterModel> getForm() {
        return formFactory.form(RegisterModel.class);
    }
}
