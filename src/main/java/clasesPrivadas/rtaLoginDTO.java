package clasesPrivadas;

import clases.User;

public class rtaLoginDTO {

	private Token token;
	//private User profile;
	private UserDTO profile;
	
	/*public rtaLogin(Token t, User u){
		token=t;
		profile=u;
	}*/
	
	public rtaLoginDTO(Token t,UserDTO u){
		token=t;
		profile=u;
	}
	
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	
	
	
	
	
	public UserDTO getProfile() {
		return profile;
	}
	public void setProfile(UserDTO profile) {
		this.profile = profile;
	}
	
	/*
	public User getProfile() {
		return profile;
	}
	public void setProfile(User profile) {
		this.profile = profile;
	}*/
	
	
}
