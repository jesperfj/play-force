package play.modules.force;

import com.force.api.*;

public class Force {
	public static final DataApi dataApi(play.mvc.Scope.Session session) {
		String[] parts = session.get("force_auth").split(" ");
		return new DataApi(
			new ApiConfig()
				.setClientId(System.getenv("CLIENT_ID"))
				.setClientSecret(System.getenv("CLIENT_SECRET"))
				.setRedirectURI(System.getenv("APP_URI")+"/_auth"),
			new ApiSession()
				.setAccessToken(parts[0])
				.setApiEndpoint(parts[1]));
				

	}
}
