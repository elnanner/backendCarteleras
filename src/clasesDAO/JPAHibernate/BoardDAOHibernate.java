package clasesDAO.JPAHibernate;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import clases.Board;
import clasesDAO.BoardDAO;
@Repository

public class BoardDAOHibernate extends GenericDAOJPAHibernate<Board> implements BoardDAO{
	
	public BoardDAOHibernate(){
		super(Board.class);
	}
	
	public Boolean existBoard(){
		return true; //hacer
	}
	
	public Board getBoardCall(String name){
		Query q=this.getEntityManager().createQuery("SELECT b from "+Board.class.getSimpleName() +" b where name = ?1 ");
		q.setParameter(1,name);
		Board board=(Board) (q.getResultList().get(0)); //OJO, SIEMPRE PREGUNTAR ANTES SI EXISTE..
		/*EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUP");
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		Query q=em.createQuery("SELECT b from "+Board.class.getSimpleName() +" b where name = ?1 ");
		q.setParameter(1,name);
		Board board=(Board) (q.getResultList().get(0)); //OJO, SIEMPRE PREGUNTAR ANTES SI EXISTE..
		etx.commit();
		em.close();*/		
		return board;
	}
	
	public Board getBoardUrl(String url){
		Query q=getEntityManager().createQuery("SELECT b from "+Board.class.getSimpleName() +" b where url = ?1 ");
		q.setParameter(1,url);
		Board board=(Board) (q.getResultList().get(0)); //OJO, SIEMPRE PREGUNTAR ANTES SI EXISTE..
		/*EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUP");
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		Query q=em.createQuery("SELECT b from "+Board.class.getSimpleName() +" b where url = ?1 ");
		q.setParameter(1,url);
		Board board=(Board) (q.getResultList().get(0)); //OJO, SIEMPRE PREGUNTAR ANTES SI EXISTE..
		etx.commit();
		em.close();		*/
		return board;
	}
	
	
	//getBoardByName
	
	//getIdBoardName

}
