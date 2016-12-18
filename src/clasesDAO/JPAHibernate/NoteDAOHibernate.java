package clasesDAO.JPAHibernate;

import org.springframework.stereotype.Repository;

import clases.Note;
import clasesDAO.NoteDAO;

@Repository
public class NoteDAOHibernate  extends GenericDAOJPAHibernate<Note> implements NoteDAO{

		public NoteDAOHibernate(){
			super(Note.class);
		}

}
