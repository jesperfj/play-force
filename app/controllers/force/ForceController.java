package controllers.force;

import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Controller;

import com.force.api.ApiConfig;
import com.force.api.AuthorizationRequest.Display;
import com.force.api.AuthorizationRequest.Scope;

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
    protected static final ApiConfig API_CONFIG = new ApiConfig().setClientId(FORCE_OAUTH_KEY)
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
		    	.apiConfig(getApiConfig())
		    	.scope(params.get("scope")!=null?params.get("scope"):"id refresh_token")
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

    /**
     * Returns the apiConfig.Child classes can override this method to change
     * the apiConfig parameters.
     * 
     * @return ApiConfig - The configuration to be used for API calls
     */
    protected static ApiConfig getApiConfig(){
    	return API_CONFIG;
    }

    public static ForceApi api() {
        String[] parts = session.get("force_auth").split(" ");
        return new ForceApi(getApiConfig(),new ApiSession().setAccessToken(parts[0])
        												.setApiEndpoint(parts[1])
        					);
    }

}
