package clasesDAO;

import org.springframework.stereotype.Component;

import clases.SuscriptionManager;
@Component
public interface SuscriptionManagerDAO extends GenericDAO<SuscriptionManager>{
	
	public SuscriptionManager getManager();

}
