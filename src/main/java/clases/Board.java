package clases;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
@XmlRootElement
@Entity
public class Board extends Observable{

	
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String description;
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@ElementCollection
	//@JoinTable(name="board_board",joinColumns=
	//@JoinColumn(name="idBoardFather", referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="idBoardChild", referencedColumnName="id"))
	private Collection<Board> boardList;
	@ManyToMany(fetch = FetchType.EAGER)
	@ElementCollection
	//@JoinTable(name="board_note",joinColumns=
	//@JoinColumn(name="idBoard", referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="idNote", referencedColumnName="id"))
	private Collection<Note> noteList;
	
	protected Boolean down;
	
	@JsonIgnore
	protected String url;

	
	public Boolean getDown() {
		return down;
	}

	public void setDown(Boolean delete) {
		this.down = delete;
	}

	public Board(){
		
	}
	
	public Board(String nameParam, String descriptionParam,String urlParam) {
		name = nameParam;
		description = descriptionParam;
		boardList = new ArrayList<Board>();
		noteList = new ArrayList<Note>();
		down=false;
		url=urlParam;
	}

	//debería usarse este
	public Board(String nameParam, String descriptionParam) {
		name = nameParam;
		description = descriptionParam;
		boardList = new ArrayList<Board>();
		noteList = new ArrayList<Note>();
		down=false;
		url=null;
	}
	

	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void addBoard(Board boardParam){
		boardList.add(boardParam);
	}
	
	public void removeBoard(Board boardParam){
		boardList.remove(boardParam);
	}
	
	public Collection<Board> getBoardList() {
	//public ArrayList<Board> getBoardList() {
		return boardList;
	}


	public void setBoardList(ArrayList<Board> boardList) {
		this.boardList = boardList;
	}

	//public ArrayList<Note> getNoteList() {
  	public Collection<Note> getNoteList() {
  		return  noteList;
		//return (ArrayList<Note>) noteList;
	}


	public void addNote(Note noteParam) {
		noteList.add(noteParam);
		setChanged();
	    notifyObservers(noteParam);
	}
	
	public void removeNote(Note noteParam) {
		noteList.remove(noteParam);
	}
	
	
}


