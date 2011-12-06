package play.modules.force;

import play.PlayPlugin;
import play.mvc.*;

public class ForceAuthPlugin extends PlayPlugin {

	@Override
	public void onApplicationStart() {
		System.out.println("ForceAuthPlugin called");
		Router.addRoute("GET", "/_auth", "controllers.force.Authentication.index");
	}	
	
	@Override
	public boolean rawInvocation(Http.Request request, Http.Response response) {
		System.out.println("Processing request: "+request.url);
		return false;
	}
}