package clasesPrivadas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;


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


