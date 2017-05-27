package clasesDAO.JPAHibernate;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import org.springframework.transaction.annotation.Transactional;

import clasesDAO.GenericDAO;


@Transactional
public class GenericDAOJPAHibernate<T> implements GenericDAO<T> {

	protected Class<T> persistentClass;
	
	
	protected EntityManager  emf ;
	
	
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
    }
	
	
	
	
	public T update(T entity) {//este update notas y pizarras lo van a pisar...
		T devolution=this.getEntityManager().merge(entity);
		return devolution;
	}

	
	public void delete(T entity) {
		this.getEntityManager().remove(entity);
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
		this.getEntityManager().persist(entity);		
		return entity;
	}


	public T get(Long id) {
		T result = null;
		result=this.getEntityManager().find(persistentClass, id);
		return result;
	}
	
	
	public ArrayList<T> getAllWithoutOrder(){
		ArrayList<T> result = new ArrayList<T>();
		result= (ArrayList<T>)(this.getEntityManager().createQuery("SELECT table FROM "+this.getPersistentClass().getSimpleName()  +" table")).getResultList();
		return result;
	}
	
	//lo bajaria a los que tienen baja logica (user, board, comment y note)
	public ArrayList<T> getAllWithoutOrderAndNotLogicDelete(){
		ArrayList<T> result = new ArrayList<T>();
		result=(ArrayList<T>)(this.getEntityManager().createQuery("SELECT table FROM "+this.getPersistentClass().getSimpleName()  +" table where down=0")).getResultList();
		return result;
	}
	
	//lo bajaria a los que tienen baja logica (user, board, comment y note)
	public void logicDelete(Long id){
		Query q=this.getEntityManager().createQuery("update "+this.getPersistentClass().getSimpleName()+" set delete = 1 where id = ? ");
		q.setParameter(1,id);
		q.getResultList();
	}
	
	
	
	
	public Integer getCount() {
		List result = (this.getEntityManager().createQuery("SELECT table FROM "+this.getPersistentClass().getSimpleName()  +" table")).getResultList();
		return result.size() ;
	}
	
	
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public void setPersistentClass(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}



}
