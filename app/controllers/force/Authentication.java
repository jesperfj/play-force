package controllers.force;

import play.mvc.Controller;

import com.force.api.ApiConfig;
import com.force.api.ApiSession;
import com.force.api.Auth;
import com.force.api.AuthorizationResponse;

public class Authentication extends Controller {
	
    public static void index() {
    	ApiSession s = Auth.completeOAuthWebServerFlow(new AuthorizationResponse()
	        .apiConfig(new ApiConfig()
	            .setClientId(System.getenv("FORCE_OAUTH_KEY"))
	            .setClientSecret(System.getenv("FORCE_OAUTH_SECRET"))
	            .setRedirectURI(System.getenv("APP_URI")+"/_auth"))
	        .code(params.get("code")));

		session.put("force_auth", s.getAccessToken()+" "+s.getApiEndpoint());
    	
		redirect(params.get("state"));
    }

}
