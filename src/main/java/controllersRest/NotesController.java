package controllersRest;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import clases.Board;
import clases.Note;
import clasesDAO.NoteDAO;

@RestController
public class NotesController {
	
	@Autowired
	private NoteDAO noteDAO;

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
		ArrayList<Note> notes = noteDAO.getAllWithoutOrder();
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

	
	
}