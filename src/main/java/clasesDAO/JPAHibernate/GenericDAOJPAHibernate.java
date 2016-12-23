package clasesDAO.JPAHibernate;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import clasesDAO.GenericDAO;


@Transactional
public class GenericDAOJPAHibernate<T> implements GenericDAO<T> {

	protected Class<T> persistentClass;
	
	//@Autowired
	protected  /*static*/ EntityManager /*EntityManagerFactory*/ emf  /*=null*/;
	
	
	private void GenericDAOJPAHibernate(){

	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager em){
	this.emf = em;
	}
	public EntityManager getEntityManager() {
	return emf;
	}
	
	public GenericDAOJPAHibernate(Class clase) {
		persistentClass = clase;
		
		/*if(emf==null){
		  emf = Persistence.createEntityManagerFactory("miUP");
		}*/
    }
	
	
	
	
	public T update(T entity) {//este update notas y pizarras lo van a pisar...
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUP");
		
		T devolution=this.getEntityManager().merge(entity);
		/*EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		T devolution = em.merge(entity);
		etx.commit();
		em.close();*/
		return devolution;
	}

	
	public void delete(T entity) {
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUP");
		this.getEntityManager().remove(entity);
		/*
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = null;
		try {
			etx = em.getTransaction();
			etx.begin();
			em.remove(entity); //baja lgiica o fisica, revisar xD
			etx.commit();
		} catch (RuntimeException e) {
			if ( etx != null && etx.isActive() ) etx.rollback();
					throw e; // escribir en un log o mostrar un mensaje
		} finally {
			em.close();
		}
		
		*/
	}

	
	public boolean exists(Long id) {
		T search=get(id);
		if(search==null){
			return false;
		}else{
			return true;
		}
	}


	public T persist(T entity) {
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUP");
	//System.out.println(" el entity que spring deberia inicializar es... "+this.getEntityManager());
		this.getEntityManager().persist(entity);
		
		/*EntityManager em = emf.createEntityManager();
		EntityTransaction etx = null;
		try {
			etx = em.getTransaction();
			etx.begin();
		
		
			em.persist(entity);
			etx.commit();
		} catch (RuntimeException e) {
			if ( etx != null && etx.isActive() ) etx.rollback();
				throw e; // escribir en un log o mostrar un mensaje
		} finally {
			em.close();
		}

*/return entity;
	}


	public T get(Long id) {
		T result = null;
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUP");
		
		result=this.getEntityManager().find(persistentClass, id);
		/*EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		result=(T) em.find(persistentClass, id);
		etx.commit();
		em.close();	*/	
		return result;
	}
	
	
	public ArrayList<T> getAllWithoutOrder(){
		ArrayList<T> result = new ArrayList<T>();
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUP");
		
		result= (ArrayList<T>)(this.getEntityManager().createQuery("SELECT table FROM "+this.getPersistentClass().getSimpleName()  +" table")).getResultList();
		/*EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
	    result=(ArrayList<T>)(em.createQuery("SELECT table FROM "+this.getPersistentClass().getSimpleName()  +" table")).getResultList();
		etx.commit();
		em.close(); */
			
		return result;
	}
	
	//lo bajaria a los que tienen baja logica (user, board, comment y note)
	public ArrayList<T> getAllWithoutOrderAndNotLogicDelete(){
		ArrayList<T> result = new ArrayList<T>();
	//	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUP");
	
		   result=(ArrayList<T>)(this.getEntityManager().createQuery("SELECT table FROM "+this.getPersistentClass().getSimpleName()  +" table where delete=0")).getResultList();
		/*EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
	    result=(ArrayList<T>)(em.createQuery("SELECT table FROM "+this.getPersistentClass().getSimpleName()  +" table where delete=0")).getResultList();
		etx.commit();
		em.close(); */
		return result;
	}
	
	//lo bajaria a los que tienen baja logica (user, board, comment y note)
	public void logicDelete(Long id){
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUP");
		Query q=this.getEntityManager().createQuery("update "+this.getPersistentClass().getSimpleName()+" set delete = 1 where id = ? ");
		q.setParameter(1,id);
		q.getResultList();
		/*EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		Query q=em.createQuery("update "+this.getPersistentClass().getSimpleName()+" set delete = 1 where id = ? ");
		q.setParameter(1,id);
		q.getResultList();
		etx.commit();
		em.close();*/
	}
	
	
	
	
	public Integer getCount() {
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUP");
		   List result = (this.getEntityManager().createQuery("SELECT table FROM "+this.getPersistentClass().getSimpleName()  +" table")).getResultList();
		/*EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
	    List result = (em.createQuery("SELECT table FROM "+this.getPersistentClass().getSimpleName()  +" table")).getResultList();
		etx.commit();
		em.close();*/
		return result.size() ;
	}
	
	
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public void setPersistentClass(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}



}
