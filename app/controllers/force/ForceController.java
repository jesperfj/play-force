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
    protected static final boolean ON_LOCALHOST      = APP_URI.startsWith("http://localhost");
    protected static final ApiConfig API_CONFIG = new ApiConfig()
        .setClientId(FORCE_OAUTH_KEY)
        .setClientSecret(FORCE_OAUTH_SECRET)
        .setRedirectURI(APP_URI+"/_auth");

    @Before
	public static void checkAuthenticated() {
        if(!ON_LOCALHOST && request.headers.get("x-forwarded-proto")!=null && !request.headers.get("x-forwarded-proto").values.contains("https")) {
            redirect(APP_URI+request.url);
            return;
        }
		if(session.get("force_auth")==null) {
			redirect(Auth.startOAuthWebServerFlow(new AuthorizationRequest()
		    	.apiConfig(getConfig())
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

    /**
     * Allows subclasses to override the ApiConfig as needed.
     */
    public ApiConfig getConfig(){
    	return API_CONFIG;
    }
}
