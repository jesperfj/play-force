package controllers;

import com.force.api.Identity;

import controllers.force.ForceController;


public class Application extends ForceController {

    public static void index() {
        Identity identity = api().getIdentity();
        
        render(identity);
    }
    
}
