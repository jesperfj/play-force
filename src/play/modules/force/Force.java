package play.modules.force;

import com.sforce.rest.*;
import com.sforce.ws.ConnectorConfig;

public class Force {
	public static final RestConnection restApi(play.mvc.Scope.Session session) {
		try {
			String[] parts = session.get("force_auth").split(" ");
			ConnectorConfig c = new ConnectorConfig();
			c.setOauthKey(System.getenv("FORCE_OAUTH_KEY"));
			c.setOauthSecret(System.getenv("FORCE_OAUTH_SECRET"));
			c.setOauthRedirectUri(System.getenv("APP_URI")+"/_auth");
			c.setSessionId(parts[0]);
			c.setServiceHost(parts[1]);
			c.setRestEndpoint(parts[1]+"/services/data/v23.0/");
			return new RestConnectionImpl(c);
		}
		catch(RestApiException e) {
			throw new RuntimeException(e);
		}
	}
}
