package play.modules.force;

import play.PlayPlugin;
import play.mvc.*;

public class ForceAuthPlugin extends PlayPlugin {

	@Override
	public void onApplicationStart() {
		Router.addRoute("GET", "/_auth", "controllers.force.Authentication.index");
	}	
	
}