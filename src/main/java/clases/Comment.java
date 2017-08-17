package clases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
@XmlRootElement
@Entity
public class Comment {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Date commentDate;  //preguntar como hace hibernate para settear
	@Transient
	@JsonIgnore
	private DateFormat format;
	private String text;
	
	@ManyToOne
	@JoinColumn(name = "authorId")
	private User author;
	
	private Boolean down;
	

	
	
	public Comment(){
		format= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // yyyy/MM/dd
	}
	
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User authorParam) {
		author = authorParam;
	}

	public Comment(String textParam, User authorParam, Note note){
		text=textParam;
		author=authorParam;
		commentDate= new Date();
		format= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		down=false;
		note.addComment(this);
	}

	public String getCommentDate() {
		return format.format(commentDate);
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public DateFormat getFormat() {
		return format;
	}

	public void setFormat(DateFormat format) {
		this.format = format;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
