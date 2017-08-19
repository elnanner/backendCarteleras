package controllersRest;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import clases.Board;
import clases.Comment;
import clases.Note;
import clases.User;
import clasesDAO.BoardDAO;
import clasesDAO.CommentDAO;
import clasesDAO.NoteDAO;
import clasesDAO.UserDAO;
import clasesPrivadas.AltaCommentDTO;
import clasesPrivadas.TokenManagerSecurity;


@RestController
public class NotesController {
	
	@Autowired
	private NoteDAO noteDAO;
	
	@Autowired
	private CommentDAO commentDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private TokenManagerSecurity tokenManagerSecurity;
	
	@Autowired 
	private BoardDAO boardDAO;

	public NotesController(){
		
	}
	
	public NoteDAO getNoteDAO() {
		return noteDAO;
	}


	public void setNoteDAO(NoteDAO noteDAO) {
		this.noteDAO = noteDAO;
	}

	
	
	@RequestMapping(value="/notes",method = RequestMethod.GET ,headers="Accept=application/json")
	public ResponseEntity<ArrayList<Note>> listAllNotes() {	
		ArrayList<Note> notes = noteDAO.getAllWithoutOrderAndNotLogicDelete();
		if(notes.isEmpty()){
			return new ResponseEntity<ArrayList<Note>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<ArrayList<Note>>(notes, HttpStatus.OK);
	}


	@RequestMapping(value="/notes/{id}", method = RequestMethod.GET , produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Note> getNoteById(@PathVariable("id") Long idNote) {
		Note note =noteDAO.get(idNote);
		if(note==null){
			return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Note>(note, HttpStatus.OK);
	}
 
	@RequestMapping(value="/addComment", method = RequestMethod.POST , produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Note> addComment(@RequestBody AltaCommentDTO data) {
		System.out.println("alta comentario!! "+" "+data.getComment()+" id note:  "+data.getNoteID()+"  token "+data.getToken());
		if(data.getNoteID()==null){
			System.out.println("ES NULLLLLLLLLLLLLLLLLLLLLLLLLLL");
		}else{
			System.out.println(":DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
		}
		Note note =noteDAO.get(data.getNoteIDLong());
		if (note==null){
			return new ResponseEntity<Note>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//System.out.println(" el titulo de la nota es "+note.getTitle());
        User user=null;
		try{
			user=tokenManagerSecurity.parseJWT(data.getToken());//ManagerToken.getDataFromToken(data.getToken());//userDAO.get(4L);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			
			return new ResponseEntity<Note>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//System.out.println("name user es "+user.getName()+" and id "+user.getId());
		Comment coment=new Comment(data.getComment(),user,note);
		commentDAO.persist(coment);
		noteDAO.update(note);
		return new ResponseEntity<Note>(note, HttpStatus.OK);
	}
	
	@RequestMapping(value="/createNote", method = RequestMethod.POST , produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Board> createBoard(HttpEntity<String> httpEntity ) {
		//ej {"idBoard": "23", "title": "pruuuu","canComment": "0","publishText": "soy el texto de la nueva nota", "token":  "sqeeere"}
		
		//verificar permisos
		Gson gson = new GsonBuilder().serializeNulls().create();//new Gson();
		String json = httpEntity.getBody();
		JsonObject dataJson = gson.fromJson(json, JsonObject.class);
		
		//controlar tipos de datos,etc
		
	    long board = dataJson.get("idBoard").getAsLong();
		Board boardFather = boardDAO.get(board);
		if(boardFather==null){//si padre es nulo: puso mal el id
			return new ResponseEntity<Board>(HttpStatus.NOT_FOUND);
		}
		
		User author=null;
		try{
			author=tokenManagerSecurity.parseJWT(dataJson.get("token").getAsString());//ManagerToken.getDataFromToken(data.getToken());//userDAO.get(4L);
		
			if(!author.getType().equals("adm")){
				return new ResponseEntity<Board>(HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			return new ResponseEntity<Board>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		String title=dataJson.get("title").getAsString();
		Boolean canComment=dataJson.get("canComment").getAsBoolean();
		String publishText=dataJson.get("publishText").getAsString();
		
		
		
		Note newNote=new Note(canComment,new Date(),author,publishText,title);
		noteDAO.persist(newNote);
		boardFather.addNote(newNote);
		boardDAO.update(boardFather);
		
		return new ResponseEntity<Board>(boardFather, HttpStatus.OK);
	}
	

	@RequestMapping(value="/deleteNote", method = RequestMethod.DELETE , produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Note> deleteBoardById(HttpEntity<String> httpEntity) {
		//ej {"idNote": "26"}
		//verificar permisos
		Gson gson = new Gson();
		String json = httpEntity.getBody();
		JsonObject dataJson = gson.fromJson(json, JsonObject.class);
		
		Long idNote=dataJson.get("idNote").getAsLong();
	
	
		Note note =noteDAO.get(idNote);
		if(note==null){
			return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
		}
		note.setDown(true);
		noteDAO.update(note);
		return new ResponseEntity<Note>(note, HttpStatus.OK);
	}
	
	
}
