package controllers;

import play.*;
import play.mvc.*;
import views.html.*;


public class Contact extends Controller {

    public Result index() {
        return ok(contact.render());
    }

}
