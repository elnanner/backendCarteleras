package controllersRest;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import clases.Comment;
import clases.Note;
import clases.User;
import clasesDAO.CommentDAO;
import clasesDAO.NoteDAO;
import clasesDAO.UserDAO;
import clasesPrivadas.AltaComment;
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
	public ResponseEntity<Note> addComment(@RequestBody AltaComment data) {
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
	
}
