package clases;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@Entity
public class Admin extends User {
	
	//admin no tiene las pizarras sobre los que tiene permisos
    //porque tiene permiso sobre todas, por los que nos parecia innecesario que 
	//tenga todas las pizarras. Por ese mismo motivo el canPublish() siempre devuelve true
	
	public Admin() {
		super();
		type="adm";
	}
	
	public Admin(String nameParam, String passParam,Config config,String mailParam) {
		super(nameParam, passParam,config,mailParam);
		type="adm";
	}
   
	public Boolean canPublish(){
		return true;
	}
	
}
