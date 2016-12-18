package clases;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@Entity
public class MailNotifier implements Observer {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
    
	/*@ElementCollection  //lo saco porque ya no es de elementos*/
	@OneToMany(fetch = FetchType.EAGER)
	private Collection</*String*/User> mails;
	
	public MailNotifier(){
		
	}
	
	public MailNotifier(Board board){
		board.addObserver(this);
		mails=new ArrayList<User>();
	}
	
	
	 
	public void addUser(User user){
		mails.add(user);
	}
	
	public void removeUser(User user){
		mails.remove(user);
	}
	 
	
	public void update(Observable board, Object noteParam) {
		//notifico por mail...
		Note note= (Note) noteParam;
		System.out.println("nota notificada.... "+note.getPublish());
	}


}
