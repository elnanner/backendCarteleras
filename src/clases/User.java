package clases;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
@XmlRootElement
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	protected String name;
	
	protected Long idSIU;
	
	protected String password;
	@OneToMany(fetch = FetchType.EAGER)
	@Column(nullable=false)
	protected Collection<Board> favouritesBoards;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "configId")
	protected Config configuration;
	protected Integer state; //0 es activo, 1 baja, el resto veremos   
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "suscriptionManagerId")
	protected SuscriptionManager suscriptionManager;
	protected String mail;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	public Long getIdSIU() {
		return idSIU;
	}

	public void setIdSIU(Long idSIU) {
		this.idSIU = idSIU;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public SuscriptionManager getSuscriptionManager() {
		return suscriptionManager;
	}

	public void setSuscriptionManager(SuscriptionManager suscriptionManager) {
		this.suscriptionManager = suscriptionManager;
	}

	public Boolean getDown() {
		return down;
	}

	public void setDown(Boolean down) {
		this.down = down;
	}

	public void setFavouritesBoards(Collection<Board> favouritesBoards) {
		this.favouritesBoards = favouritesBoards;
	}

	protected Boolean down;
	
	protected String type; //parche rapidin para poder probar con lo viejo (las sub clases lo setean bien)
	
	public User(){
		
	}
	
	public Config getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Config configurationParam) {
		configuration = configurationParam;
	}

	public Collection<Board> getFavouritesBoards() {
		return favouritesBoards;
	}

	public void addBoard(Board boardParam) {
		favouritesBoards.add(boardParam);
	}
	
	public User(String nameParam, String passParam, Config config, String mailParam){
		name=nameParam;
		password=passParam;
		favouritesBoards=new ArrayList<Board>();
		configuration=config;
		state=0;  
		suscriptionManager=SuscriptionManager.getInstance();
		mail=mailParam;
		down=false;
		idSIU=null;
	}
	
	public User(String nameParam, Long idSIUParam, Config config ){
		name=nameParam;
		password=null;
		favouritesBoards=new ArrayList<Board>();
		configuration=config;
		state=0;  
		suscriptionManager=SuscriptionManager.getInstance();
		mail=null;
		down=false;
		idSIU=idSIUParam;
	}

	
	public void addBoardInterest(Board board){
		suscriptionManager.addMailNotifier(this, board);
		favouritesBoards.add(board);
	}

	public void removeBoardInterest(Board board){
		suscriptionManager.removeMailNotifier(this, board);
		favouritesBoards.remove(board);
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
