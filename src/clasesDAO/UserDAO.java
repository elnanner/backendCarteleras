package clasesDAO;

import org.springframework.stereotype.Component;

import clases.User;
@Component
public interface UserDAO extends GenericDAO<User> {
	public Boolean credentialsLogin(String userName,String pass);
	public User login(String userName,String pass);
	public User getUserWithIDSIU(Long id);
}
