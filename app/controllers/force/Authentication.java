package controllers.force;

import play.mvc.Controller;

import com.sforce.oauth.Auth;
import com.sforce.ws.ConnectorConfig;

public class Authentication extends Controller {

	static final private ConnectorConfig config;
	
	static {
		config = new ConnectorConfig();
		config.setConnectUri(System.getenv("FORCE_URL"));
		config.setOauthRedirectUri(System.getenv("APP_URI")+"/_auth");
	}
	
    public static void index() {
		ConnectorConfig c = Auth.completeOAuthWebServerFlow(config,params.get("code"));
		
		session.put("force_auth", c.getSessionId()+" "+c.getServiceHost());
    	
		redirect(params.get("state"));
    }

}
