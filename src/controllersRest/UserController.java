package controllersRest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import clases.Config;
import clases.User;
import clasesDAO.UserDAO;
import clasesPrivadas.Credential;

@RestController
public class UserController {
	@Autowired
	private UserDAO userDAO;

	public UserController(){
		
	}
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	
	@RequestMapping(value="/users",method = RequestMethod.GET ,headers="Accept=application/json")
	public ResponseEntity<ArrayList<User>> listAllUsers() {	
		ArrayList<User> users = userDAO.getAllWithoutOrder();
		if(users.isEmpty()){
			return new ResponseEntity<ArrayList<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<ArrayList<User>>(users, HttpStatus.OK);
	}


	@RequestMapping(value="/users/{id}", method = RequestMethod.GET , produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserById(@PathVariable("id") Long idUser) {
		User user =userDAO.get(idUser);
		if(user==null){
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	private Long searchSIU(Credential credential, String urlString){
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			String input = "{\"user\":\""+credential.getUser()+"\",\"pass\":\""+credential.getPass()+"\"}";
			OutputStream os = connection.getOutputStream();  
			os.write(input.getBytes());
			os.flush();
			if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
				BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
				String line= "";
				String response="";
				while ((line=br.readLine()) != null) { 
					response+=line; 
				}
				connection.disconnect();
				String[] temp = response.split("\"id\":");
				return(Long.valueOf(temp[1].split(",")[0]));
				}else if(connection.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND){
				return null;
			}
			 connection.disconnect();
			 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private Long searchSIU(Credential credential){
		Long result=searchSIU(credential,"http://localhost:8080/ejemploSIU/tryLoginS/");
		if(result!=null){
			return result;
		}
	    result=searchSIU(credential,"http://localhost:8080/ejemploSIU/tryLoginP/");
	    return result;
	}
	
	@RequestMapping(value="/tryLogin/", method = RequestMethod.POST)
	public ResponseEntity<User> getUserById(@RequestBody Credential credential) {
		System.out.println("inicio trylogin ponele");
		Boolean existeUser =userDAO.credentialsLogin(credential.getUser(), credential.getPass());
		User user;
		if(existeUser==false){
		   // user=searchSIU(credential);
			Long id=searchSIU(credential);
		    if(id==null){
		    	return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		    }
		    user = userDAO.getUserWithIDSIU(id);
		}else{
			user = userDAO.login(credential.getUser(), credential.getPass());
		}
		System.out.println("ultimo antes trylogin ponele");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
}
