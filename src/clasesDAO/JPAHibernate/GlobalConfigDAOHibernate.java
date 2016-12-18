package clasesDAO.JPAHibernate;

import org.springframework.stereotype.Repository;

import clases.Config;
import clasesDAO.GlobalConfigDAO;
@Repository
public class GlobalConfigDAOHibernate extends GenericDAOJPAHibernate<Config> implements GlobalConfigDAO{
	
	public GlobalConfigDAOHibernate(){
		super(Config.class);
	}

}
