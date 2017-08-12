package clasesPrivadas;

public class AltaComment {
	private String comment;
	private String noteID;
	private String token; 
    
public AltaComment(){
		
	}

	public AltaComment(String comment, String noteID, String token){
		this.comment = comment;
		this.noteID = noteID;
		this.token=token;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	 public String getNoteID() {
			return noteID;
		}
	 
	 public void setNoteID(String noteID) {
			this.noteID = noteID;
		}

	/*
	public String getIdNote() {
	
		//return Long.parseLong(noteID) ;
		//return Long.parseLong(String.valueOf(noteID));
		return i;
	}

	
	public void setIdNote(String i) {
		
		this.i = i ;
	}*/

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
