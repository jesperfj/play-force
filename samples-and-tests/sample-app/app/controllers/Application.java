package controllers;

import java.io.IOException;

import play.modules.force.Force;
import play.mvc.Controller;
import play.mvc.With;

import com.sforce.rest.RestApiException;
import com.sforce.rest.RestConnection;


@With(controllers.force.Secure.class)
public class Application extends Controller {

    public static void index() {
    	RestConnection rest = Force.restApi(session);
    	try {
			rest.describeGlobal();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RestApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        render();
    }

}
