package controllers;

import java.util.List;

import model.Account;
import play.modules.force.Force;
import play.mvc.Controller;
import play.mvc.With;

import com.force.api.ForceApi;


@With(controllers.force.Secure.class)
public class Application extends Controller {

    public static void index() {
    	ForceApi api = Force.forceApi(session);
    	List<Account> accounts = api.query("SELECT name FROM Account",Account.class).getRecords();
        render(accounts);
    }

}
