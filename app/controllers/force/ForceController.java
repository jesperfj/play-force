package controllers.force;

import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Controller;

import com.force.api.ApiConfig;
import com.force.api.ApiSession;
import com.force.api.ApiTokenException;
import com.force.api.Auth;
import com.force.api.AuthorizationRequest;
import com.force.api.AuthorizationRequest.Display;
import com.force.api.ForceApi;

public class ForceController extends Controller {

	protected static final String APP_URI            = System.getenv("APP_URI");
    protected static final String FORCE_OAUTH_KEY    = System.getenv("FORCE_OAUTH_KEY");
    protected static final String FORCE_OAUTH_SECRET = System.getenv("FORCE_OAUTH_SECRET");
    protected static final String ENDPOINT_PROD = "https://login.salesforce.com";
    protected static final ApiConfig API_CONFIG = new ApiConfig()
        .setClientId(FORCE_OAUTH_KEY)
        .setClientSecret(FORCE_OAUTH_SECRET)
        .setRedirectURI(APP_URI+"/_auth")
        //Set the 'loginEndpoint' parameter if passed in the 'request', else use the default.
    	.setLoginEndpoint((params.get("loginEndpoint") !=null) ? params.get("loginEndpoint") :ENDPOINT_PROD );

    @Before
	public static void checkAuthenticated() {
    	
		if(session.get("force_auth")==null) {
			redirect(Auth.startOAuthWebServerFlow(new AuthorizationRequest()
		    	.apiConfig(API_CONFIG)
		    	//Commented until "Scope" changes make it to force-rest-api 
		    	//.scope(params.get("scope")!=null?params.get("scope"):Scope.)
		    	.display(params.get("display")!=null?Display.valueOf(params.get("display")):Display.PAGE)
		    	.state(APP_URI+request.url)));
		}
	}
	
    @Catch(ApiTokenException.class)
    public static void catchBadApiToken(ApiTokenException e) {
        System.out.println("Caught API Token Exception "+e.getMessage());
        session.clear();
        redirect(APP_URI+request.url);
    }


    public static ForceApi api() {
        String[] parts = session.get("force_auth").split(" ");
        return new ForceApi(API_CONFIG,new ApiSession()
            .setAccessToken(parts[0])
            .setApiEndpoint(parts[1]));
    }

}
