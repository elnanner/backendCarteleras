package initialize;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import clases.Admin;
import clases.Administrative;
import clases.Board;
import clases.Comment;
import clases.Config;
import clases.Note;
import clases.Professor;
import clases.Publisher;
import clases.Student;
import clases.SuscriptionManager;
import clasesDAO.AdminDAO;
import clasesDAO.BoardDAO;
import clasesDAO.CommentDAO;
import clasesDAO.GlobalConfigDAO;
import clasesDAO.NoteDAO;
import clasesDAO.SuscriptionManagerDAO;
import clasesDAO.UserDAO;


@Component
public class InitCarteleras {
	
	@Autowired 
	private AdminDAO adminDAO;
	
	@Autowired 
	private BoardDAO boardDAO;
	
	@Autowired 
	private CommentDAO commentDAO;
	
	@Autowired 
	private GlobalConfigDAO globalConfigDAO;
	
	@Autowired 
	private NoteDAO noteDAO;
	
	@Autowired 
	private SuscriptionManagerDAO suscriptionManagerDAO;
	
	@Autowired 
	private UserDAO userDAO;
	
	
	
	
	
	public InitCarteleras(){
		
	}
	
	public void init(){
		if(userDAO.getCount()==0){
		Config conf0=new Config("0");
		Config conf1=new Config("1");
		Config conf2=new Config("2");
		globalConfigDAO.persist(conf0);
		globalConfigDAO.persist(conf1);
		globalConfigDAO.persist(conf2);
		
		//SuscriptionManager manager=SuscriptionManager.getInstance();
		//suscriptionManagerDAO.persist(manager);
		
		
		
		Admin admin = new Admin("admin", "admin", conf0, "admin@mail.com");
		Student s1=new Student("alu", 1L, conf0);
	    userDAO.persist(admin);
	    userDAO.persist(s1);
	    userDAO.persist(new Student("alu2", 2L, conf0));
	    userDAO.persist(new Student("alu3", 3L, conf0));
	    userDAO.persist(new Student("alu4", 4L, conf0));
	   
	    userDAO.persist(new Administrative("adm", "adm", conf0, "adminstrative@mail.com"));
	    userDAO.persist(new Publisher("pub", "pub", conf0, "publicador@mail.com"));
	    userDAO.persist(new Professor("new", 6L, conf0));
	    
//movido
	    Board boardNoticias=new Board("Institucional", "Pizarra de las noticias institucionales de la facultad","institucional");
	    boardDAO.persist(boardNoticias);
	    
	    Board boardSubjectsFirstYear=new Board("Materias primer año", "Pizarra sobre  las noticias de las materias de primer año","primero");
	    Board adp=new Board("ADP", "Pizarra sobre  las noticias de adp","adp");
	    boardDAO.persist(adp);
	    boardSubjectsFirstYear.addBoard(adp);
	    boardDAO.persist(boardSubjectsFirstYear);
	    
	    Professor prof = new Professor("doc", 5L, conf0);
	    prof.addPermission(boardSubjectsFirstYear);
	    userDAO.persist(prof);
	   
	    
	    Board boardSubjectsSecondYear=new Board("Materias segundo año", "Pizarra sobre  las noticias de las materias de segundo año","segundo");
	    boardDAO.persist(boardSubjectsSecondYear);
	    
	    Board boardSubjectsThirdYear=new Board("Materias tercer año", "Pizarra sobre  las noticias de las materias de tercer año","tercero");
	    boardDAO.persist(boardSubjectsThirdYear);
	    
	    Board boardSubjectsFourthYear=new Board("Materias cuarto año", "Pizarra sobre  las noticias de las materias de cuarto año","cuarto");
	    boardDAO.persist(boardSubjectsFourthYear);
	    
	    Board boardSubjectsFifthYear=new Board("Materias quinto año", "Pizarra sobre  las noticias de las materias de quinto año","quinto");
	    boardDAO.persist(boardSubjectsFifthYear);
	    
	    Board boardLaboral=new Board("Pizarra con información laboral", "Ofertas de trabajo para los alumnos","ofertas");
	    boardDAO.persist(boardLaboral);
	    
	    Board boardEvents=new Board("Pizarra de eventos", "Pizarra sobre  las noticias de eventos que se den en la facultad o relacionados","eventos");
	    boardDAO.persist(boardEvents);
	    
	    Board boardLostProperty=new Board("Objetos perdidos", "Pizarra con los objetos perdidos","perdidos");
	    boardDAO.persist(boardLostProperty);
	    
	    Board board=new Board("Home", "pizarra home","home");
	   // board.addNote(note);
	    board.addBoard(boardNoticias);
	    board.addBoard(boardSubjectsFirstYear);
	    board.addBoard(boardSubjectsSecondYear);
	    board.addBoard(boardSubjectsThirdYear);
	    board.addBoard(boardSubjectsFourthYear);
	    board.addBoard(boardSubjectsFifthYear);
	    board.addBoard(boardLaboral);
	    board.addBoard(boardEvents);
	    board.addBoard(boardLostProperty);
	    boardDAO.persist(board);
	    
	   
	    Note note=new Note(false,new Date(), admin,"Welcome!", "Nota de bienvenida con sorpresa "); 
	    
	    Note note2=new Note(false,new Date(), admin,"El decano y la facu les dan la bienvenida bla bla", "Nota para los de primero ");
	    noteDAO.persist(note2);
	    boardSubjectsFirstYear.addNote(note2);
	    boardDAO.update(boardSubjectsFirstYear);
	    
	    Comment comment=new Comment("Comentario inicial ",admin,note);
	    Comment comment2=new Comment("Otro comentario ",s1,note);
	    commentDAO.persist(comment);
	    commentDAO.persist(comment2);
	    
	    
	    noteDAO.persist(note);
	    board.addNote(note);
	    
	    boardDAO.update(board);
	    
	    
	    
	   /* //para ver si crea mailNotifier
	    manager.addMailNotifier("mailUno@mail.com", boardNoticias);
	    manager.addMailNotifier("mailDos@mail.com", boardNoticias);
		FactoryDAO.getSuscriptionManager().update(manager);  //si usas persist explota, disatouched el mailNotif :p
	    */
	    
		}
	}

}
