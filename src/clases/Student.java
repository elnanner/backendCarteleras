package clases;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@Entity
public class Student extends User {

	
    public Student(){
    	type="alu";
    }
    
	
	public Student(String nameParam, Long idSIUParam, Config config) {
		super(nameParam, idSIUParam,config);
		type="alu";
	}

}
