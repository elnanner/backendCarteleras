package clases;


import java.util.HashMap;

import java.util.Map;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
@XmlRootElement
@Entity
public class SuscriptionManager {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private  Long id;
	
	

	public static void setInstance(SuscriptionManager instance) {
		SuscriptionManager.instance = instance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany
	@Cascade({CascadeType.PERSIST,CascadeType.SAVE_UPDATE, CascadeType.MERGE})
	 private  Map<Board,MailNotifier> mailNotifiers;
    //private  Map<MailNotifier> mailNotifiers;
	
	
/*	@OneToMany
	@Column(nullable=false)
	 private  ArrayList<Board> boards;*/
	
    private static SuscriptionManager instance = null; //singleton
    

    
    protected SuscriptionManager() {
    	mailNotifiers=new HashMap<Board,MailNotifier>();
    	//boards=new ArrayList<Board>();
     }
    
    public static SuscriptionManager getInstance() {
        if(instance == null) {
           instance = new SuscriptionManager();
        }
        return instance;
     }
    
    
    public void addMailNotifier(User user,Board board){
    	if(! mailNotifiers.containsKey(board)){
    		mailNotifiers.put(board,new MailNotifier(board));
    		//mailNotifiers.add(new MailNotifier(board));
        }
    	mailNotifiers.get(board).addUser(user); 
    	
    	/*if(! boards.contains(board)){
    		boards.add(board);
    		mailNotifiers.add(new MailNotifier(board));
        }
    	mailNotifiers.get(boards.indexOf(board)).addMail(mail);  */  
    }
    
    public void removeMailNotifier(User user,Board board){
    	 if(mailNotifiers.containsKey(board)){
    		 mailNotifiers.get(board).removeUser(user);
    	 }
    	 
    	/*
    	 if(boards.contains(board)){
    		 mailNotifiers.get(boards.indexOf(board)).removeMail(mail);
         }
         */
    	
    }
	
}
