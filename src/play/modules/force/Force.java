package play.modules.force;

import com.force.api.*;

public class Force {
	public static final ForceApi forceApi(play.mvc.Scope.Session session) {
		String[] parts = session.get("force_auth").split(" ");
		return new ForceApi(
			new ApiConfig()
				.setClientId(System.getenv("FORCE_OAUTH_KEY"))
				.setClientSecret(System.getenv("FORCE_OAUTH_SECRET"))
				.setRedirectURI(System.getenv("APP_URI")+"/_auth"),
			new ApiSession()
				.setAccessToken(parts[0])
				.setApiEndpoint(parts[1]));
	}
}
