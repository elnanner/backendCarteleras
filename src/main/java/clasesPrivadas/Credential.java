package clasesPrivadas;




public class Credential {

	
	
	
	
	
	private String user;
	private String pass;
	
	public Credential(){
		
	}

	public Credential(String usuarioParam, String claveParam){
		user = usuarioParam;
		pass = claveParam;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String usuario) {
		this.user = usuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String clave) {
		this.pass = clave;
	}

	

}


