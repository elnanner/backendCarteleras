package clasesDAO.JPAHibernate;

import org.springframework.stereotype.Repository;

import clases.Comment;
import clasesDAO.CommentDAO;
@Repository
	public class CommentDAOHibernate  extends GenericDAOJPAHibernate<Comment> implements CommentDAO{

		public CommentDAOHibernate(){
			super(Comment.class);
		}

}
