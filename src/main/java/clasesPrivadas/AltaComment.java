package clasesPrivadas;

public class AltaComment {
	private String comment;
	private String noteID;
    private String token; 
    
public AltaComment(){
		
	}

	public AltaComment(String comment, String noteID,String token){
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

	public Long getIdNote() {
	
		return Long.parseLong(noteID) ;
	}

	public void setIdNote(String noteID) {
		
		this.noteID = noteID ;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
