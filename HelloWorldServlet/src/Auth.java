import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.glassware.ListableMemoryCredentialStore;


public class Auth {
	public static ListableMemoryCredentialStore store = new ListableMemoryCredentialStore();
	  public static final String GLASS_SCOPE = "https://www.googleapis.com/auth/glass.timeline "
	      + "https://www.googleapis.com/auth/glass.location "
	      + "https://www.googleapis.com/auth/userinfo.profile";
	  
	  
	
	
}
