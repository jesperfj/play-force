package controllers.force;

import play.mvc.Controller;

import com.force.api.ApiConfig;
import com.force.api.ApiSession;
import com.force.api.Auth;
import com.force.api.AuthorizationResponse;

public class Authentication extends Controller {

    public static void index() {
		ApiSession apiSession = Auth.completeOAuthWebServerFlow(new AuthorizationResponse()
			.apiConfig(new ApiConfig()
				.setClientId(System.getenv("CLIENT_ID"))
				.setClientSecret(System.getenv("CLIENT_SECRET"))
				.setRedirectURI(System.getenv("APP_URI")+"/_auth"))
			.code(params.get("code"))
			.state(params.get("state")));

		session.put("force_auth", apiSession.getAccessToken()+" "+apiSession.getApiEndpoint());
    	
		redirect(params.get("state"));
    }

}
