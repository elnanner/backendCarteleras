package clases;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Professor extends User {
		


//	@OneToMany
//	@ElementCollection
	@OneToMany(fetch = FetchType.EAGER)
	private Collection<Board> permissionsList;
	
	public Collection<Board> getPermissionsList() {
		return permissionsList;
	}

	public Professor(){
		type="doc";
	}
	
	public void addPermission(Board permissionParam) {
		permissionsList.add(permissionParam);
	}

	
	
	public Professor(String nameParam,Long idSIUParam,Config config) {
		super(nameParam, idSIUParam,config);
		permissionsList=new ArrayList<Board>();
		type="doc";
	}

}
