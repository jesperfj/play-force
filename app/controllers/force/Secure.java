package controllers.force;

import play.mvc.Before;
import play.mvc.Controller;

import com.force.api.ApiConfig;
import com.force.api.Auth;
import com.force.api.AuthorizationRequest;

public class Secure extends Controller {

	@Before
	public static void checkAuthenticated() {
		if(session.get("force_auth")==null) {
			redirect(Auth.startOAuthWebServerFlow(new AuthorizationRequest()
		    	.apiConfig(new ApiConfig()
				    	.setClientId(System.getenv("FORCE_OAUTH_KEY"))
				    	.setRedirectURI(System.getenv("APP_URI")+"/_auth"))
			    .state(System.getenv("APP_URI")+request.url)));
		}
	}
}
