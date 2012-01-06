package controllers.force;

import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Controller;

import com.force.api.ApiConfig;
import com.force.api.ApiSession;
import com.force.api.ApiTokenException;
import com.force.api.Auth;
import com.force.api.AuthorizationRequest;
import com.force.api.ForceApi;

public class ForceController extends Controller {

	protected static final String APP_URI            = System.getenv("APP_URI");
    protected static final String FORCE_OAUTH_KEY    = System.getenv("FORCE_OAUTH_KEY");
    protected static final String FORCE_OAUTH_SECRET = System.getenv("FORCE_OAUTH_SECRET");

    protected static final ApiConfig API_CONFIG = new ApiConfig()
        .setClientId(FORCE_OAUTH_KEY)
        .setClientSecret(FORCE_OAUTH_SECRET)
        .setRedirectURI(APP_URI+"/_auth");


    @Before
	public static void checkAuthenticated() {
		if(session.get("force_auth")==null) {
			redirect(Auth.startOAuthWebServerFlow(new AuthorizationRequest()
		    	.apiConfig(API_CONFIG)
		    	.state(APP_URI+request.url)));
		}
	}
	
    @Catch(ApiTokenException.class)
    public static void catchBadApiToken(ApiTokenException e) {
        System.out.println("Caught API Token Exception "+e.getMessage());
        session.clear();
        redirect(APP_URI+request.url);
    }


    protected static ForceApi forceApi() {
        String[] parts = session.get("force_auth").split(" ");
        return new ForceApi(API_CONFIG,new ApiSession()
            .setAccessToken(parts[0])
            .setApiEndpoint(parts[1]));
    }

}
