package controllers;

import java.util.List;
import java.util.Map;

import model.Account;
import play.mvc.Controller;
import play.mvc.With;

import com.force.api.ForceApi;

import controllers.force.ForceController;


@With(ForceController.class)
public class Accounts extends Controller {

    private static ForceApi api() {
        return ForceController.api();
    }
    
    public static void typed() {
    	List<Account> accounts = api().query("SELECT name FROM Account",Account.class).getRecords();
        render(accounts);
    }
    
    public static void untyped() {
        List<Map> accounts = api().query("SELECT name FROM Account", Map.class).getRecords();
        render(accounts);
    }

}
