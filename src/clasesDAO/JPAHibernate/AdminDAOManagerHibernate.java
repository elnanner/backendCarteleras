package clasesDAO.JPAHibernate;

import org.springframework.stereotype.Repository;

import clases.Admin;
import clasesDAO.AdminDAO;

@Repository
public class AdminDAOManagerHibernate  extends GenericDAOJPAHibernate<Admin> implements AdminDAO{
	
	public AdminDAOManagerHibernate(){
		super(Admin.class);
	}

}
