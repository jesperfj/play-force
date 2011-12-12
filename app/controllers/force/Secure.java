package controllers.force;

import play.mvc.Before;
import play.mvc.Controller;

import com.sforce.oauth.Auth;
import com.sforce.oauth.OAuthParam;
import com.sforce.ws.ConnectorConfig;

public class Secure extends Controller {

	static final private ConnectorConfig config;
	
	static {
		config = new ConnectorConfig();
		config.setConnectUri(System.getenv("FORCE_URL"));
		config.setOauthRedirectUri(System.getenv("APP_URI")+"/_auth");
	}
	
	@Before
	public static void checkAuthenticated() {
		if(session.get("force_auth")==null) {
			redirect(Auth.startOAuthWebServerFlow(config,new OAuthParam()
						.state(System.getenv("APP_URI")+request.url)));
		}
	}
}
