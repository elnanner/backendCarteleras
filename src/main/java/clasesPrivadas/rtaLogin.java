package clasesPrivadas;

import clases.User;

public class rtaLogin {

	private Token token;
	private User profile;
	
	public rtaLogin(Token t, User u){
		token=t;
		profile=u;
	}
	
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	public User getProfile() {
		return profile;
	}
	public void setProfile(User profile) {
		this.profile = profile;
	}
	
	
}
