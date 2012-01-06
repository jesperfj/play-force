package controllers;

import java.util.List;

import model.Account;

import com.force.api.ForceApi;

import controllers.force.ForceController;


public class Application extends ForceController {

    public static void index() {
    	List<Account> accounts = forceApi().query("SELECT name FROM Account",Account.class).getRecords();
        render(accounts);
    }

}
