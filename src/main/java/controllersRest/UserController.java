package controllersRest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import clases.Admin;
import clases.Administrative;
import clases.Board;
import clases.Config;
import clases.Note;
import clases.Publisher;
import clases.User;
import clasesDAO.BoardDAO;
import clasesDAO.GlobalConfigDAO;
import clasesDAO.UserDAO;
import clasesPrivadas.CredentialDTO;
import clasesPrivadas.Token;
import clasesPrivadas.TokenManagerSecurity;
import clasesPrivadas.UserDTO;
import clasesPrivadas.rtaLoginDTO;

@RestController
public class UserController {
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private TokenManagerSecurity tokenManagerSecurity;
	
	@Autowired
	private GlobalConfigDAO globalConfigDAO;
	
	@Autowired 
	private BoardDAO boardDAO;

	public UserController(){
		
	}
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@RequestMapping(value="/deleteUser", method = RequestMethod.DELETE , produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> deleteUserById(HttpEntity<String> httpEntity /*@PathVariable("id") Long idBoard*/) {
		//ej {"idUser": "11"}
		//CONTROLAR QUE NO SE PUEDA BORRAR A SI MISMO CON TOKEN! :P 
		Gson gson = new Gson();
		String json = httpEntity.getBody();
		JsonObject dataJson = gson.fromJson(json, JsonObject.class);	
		Long idUser=dataJson.get("idUser").getAsLong();
		User userAuthorOperation=null;
		//System.out.println("baja de usuario el token es "+dataJson.get("token").getAsString());
		try{
			userAuthorOperation=tokenManagerSecurity.parseJWT(dataJson.get("token").getAsString());//ManagerToken.getDataFromToken(data.getToken());//userDAO.get(4L);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(idUser==userAuthorOperation.getId()){
			System.out.println("usteded no se puede borrar a si mismo");
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		User operatorRecoverFromBD=userDAO.get(userAuthorOperation.getId());
		if(operatorRecoverFromBD==null){
			System.out.println("error de id");
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (operatorRecoverFromBD.getDown()==true){
			System.out.println("usteded está dado de baja, no puede operar");
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(operatorRecoverFromBD.getType()!="adm"){
			System.out.println("usteded no tiene permisos para realizar esta operación");
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		User user =userDAO.get(idUser);
		//if(user==null || (user.getType().equals("alu") || user.getType().equals("doc"))){// NO PODES BORRAR alumnos ni profesores porque son del siu
		//	return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		//}
		user.setDown(true);
		userDAO.update(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/addFavourite", method = RequestMethod.POST , produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addFavouriteBoard(HttpEntity<String> httpEntity) {
		//ej {"idBoard": "23", "token":"token"}
		Gson gson = new Gson();
		String json = httpEntity.getBody();
		JsonObject dataJson = gson.fromJson(json, JsonObject.class);	
		Long idBoard=dataJson.get("idBoard").getAsLong();
		User userAuthorOperation=null;
		try{
			userAuthorOperation=tokenManagerSecurity.parseJWT(dataJson.get("token").getAsString());//ManagerToken.getDataFromToken(data.getToken());//userDAO.get(4L);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		User operatorRecoverFromBD=userDAO.get(userAuthorOperation.getId());
		if(operatorRecoverFromBD==null){
			System.out.println("error de id");
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (operatorRecoverFromBD.getDown()==true){
			System.out.println("usteded está dado de baja, no puede operar");
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		Board board =boardDAO.get(idBoard);
		if(board==null){
			System.out.println("error de id");
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(board.getDown()==true){
			System.out.println("usteded no puede agregar una pizarra dada de baja");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		
		operatorRecoverFromBD.addBoardInterest(board);
		try{
			userDAO.update(operatorRecoverFromBD);
		}catch(Exception e){
			System.out.println("seguramente clave duplicada");
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<User>(operatorRecoverFromBD, HttpStatus.OK);
	}
	
	@RequestMapping(value="/removeFavourite", method = RequestMethod.POST , produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> removeFavouriteBoard(HttpEntity<String> httpEntity) {
		//ej {"idBoard": "23", "token":"token"}
		Gson gson = new Gson();
		String json = httpEntity.getBody();
		JsonObject dataJson = gson.fromJson(json, JsonObject.class);	
		Long idBoard=dataJson.get("idBoard").getAsLong();
		User userAuthorOperation=null;
		try{
			userAuthorOperation=tokenManagerSecurity.parseJWT(dataJson.get("token").getAsString());
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		User operatorRecoverFromBD=userDAO.get(userAuthorOperation.getId());
		if(operatorRecoverFromBD==null){
			System.out.println("error de id");
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (operatorRecoverFromBD.getDown()==true){
			System.out.println("usteded está dado de baja, no puede operar");
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		Board board =boardDAO.get(idBoard);
		if(board==null){
			System.out.println("error de id");
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(board.getDown()==true){
			System.out.println("usteded no puede agregar una pizarra dada de baja");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		for(Board boardIteration:operatorRecoverFromBD.getFavouritesBoards()){
			System.out.println("board iteration "+boardIteration.getId()+" "+boardIteration.getName()+" id java "+boardIteration.toString());
		}
		System.out.println("la pizarra java es "+board.toString());
		System.out.println("antes de la baja tiene "+operatorRecoverFromBD.getFavouritesBoards());
		
		operatorRecoverFromBD.removeBoardInterest(board);
		System.out.println("despues de la baja tiene "+operatorRecoverFromBD.getFavouritesBoards());
		userDAO.update(operatorRecoverFromBD);
		return new ResponseEntity<User>(operatorRecoverFromBD, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getFavouriteBoards", method = RequestMethod.POST , produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Board>> getFaouriteBoards(HttpEntity<String> httpEntity) {
		//ej {"token":"token"}
		Gson gson = new Gson();
		String json = httpEntity.getBody();
		JsonObject dataJson = gson.fromJson(json, JsonObject.class);	
		User userAuthorOperation=null;
		try{
			userAuthorOperation=tokenManagerSecurity.parseJWT(dataJson.get("token").getAsString());
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			return new ResponseEntity<Collection<Board>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		User operatorRecoverFromBD=userDAO.get(userAuthorOperation.getId());
		if(operatorRecoverFromBD==null){
			System.out.println("error de id");
			return new ResponseEntity<Collection<Board>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (operatorRecoverFromBD.getDown()==true){
			System.out.println("usteded está dado de baja, no puede operar");
			return new ResponseEntity<Collection<Board>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Collection<Board>>(operatorRecoverFromBD.getFavouritesBoards(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/createUser", method = RequestMethod.POST , produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUser(HttpEntity<String> httpEntity ) {
		//ej {"user": "administrative", "pass": "ad", "mail": "mail@nuevoAdmin.com", "type": "administrative"}
		
		//NOTA: BUG CON CONFIG
		
		//verificar permisos
		Gson gson = new Gson();
		String json = httpEntity.getBody();
		JsonObject dataJson = gson.fromJson(json, JsonObject.class);
		
		//controlar tipos de datos,etc
		
	    System.out.println(" user recibido para agregar");
		
		String user=dataJson.get("user").getAsString();
		String pass=dataJson.get("pass").getAsString();
		String mail=dataJson.get("mail").getAsString();
		String type=dataJson.get("type").getAsString();
		Config conf0=globalConfigDAO.get(1L);
		if(type.equals("adm")){
			userDAO.persist(new Admin(user,pass,conf0,mail));
		}else if(type.equals("administrative")){
			userDAO.persist(new Administrative(user,pass,conf0,mail));
		}else if(type.equals("pub")){
			userDAO.persist(new Publisher(user,pass,conf0,mail));
		}else{
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<User>( HttpStatus.OK);
	}
	
	

@RequestMapping(value="/updateUser", method = RequestMethod.PUT , produces =MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<User> updateBoard(HttpEntity<String> httpEntity ) {
	//SOLO podes modificar los datos propios del usuario (nombre, password y mail), pero NO el id, la baja, la configuracion o el tipo
	//ej //ej {"id": "10","user": "administrative", "pass": "","passDos": "", "mail": "mail@nuevoAdmin.com"}
	
	//verificar permisos
	Gson gson = new GsonBuilder().serializeNulls().create();//new Gson();
	String json = httpEntity.getBody();
	JsonObject dataJson = gson.fromJson(json, JsonObject.class);
	
	
	//controlar tipos de datos,etc
	Long id=dataJson.get("id").getAsLong();
	String userName=dataJson.get("user").getAsString();
	String pass=dataJson.get("pass").getAsString();
	String passDos=dataJson.get("passDos").getAsString();
	String mail=dataJson.get("mail").getAsString();
	User user =userDAO.get(id);
	
	if(user==null || (userName.equals("")||mail.equals(""))){//si es nulo: puso mal el id, o no se recupero bien
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
	 
	 user.setName(userName);
	 if(!pass.equals("")&& pass.equals(passDos)){// Si no son vacios y son iguales entre si, supongo que los quiere cambiar
		 user.setPassword(pass);
	 }else if(!pass.equals("") || !passDos.equals("")){//si ambos son vacios es que no lo quiere cambiar. Pregunto por el opuesto pa retornar error
		 return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	 }
	 user.setMail(mail);
   
	userDAO.update(user);//actualizamos user
	
	return new ResponseEntity<User>(user, HttpStatus.OK);
}
	
	@RequestMapping(value="/users",method = RequestMethod.GET ,headers="Accept=application/json")
	public ResponseEntity<ArrayList<User>> listAllUsers() {	
		ArrayList<User> users = userDAO.getAllWithoutOrderAndNotLogicDelete();
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
	
	private Long searchSIU(CredentialDTO credential, String urlString){
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
	
	private Long searchSIU(CredentialDTO credential){
		Long result=searchSIU(credential,"http://localhost:8080/ejemploSIU/tryLoginS/");
		if(result!=null){
			return result;
		}
	    result=searchSIU(credential,"http://localhost:8080/ejemploSIU/tryLoginP/");
	    return result;
	}
	
	@RequestMapping(value="/tryLogin/", method = RequestMethod.POST)
	public ResponseEntity<rtaLoginDTO> getUserById(@RequestBody CredentialDTO credential) {
		//{"user": "prof1", "pass":"prof1"} 
		Boolean existeUser =userDAO.credentialsLogin(credential.getUser(), credential.getPass());
		User user;
		if(existeUser==false){
		   // user=searchSIU(credential);
			Long id=searchSIU(credential);
		    if(id==null){
		    	return new ResponseEntity<rtaLoginDTO>(HttpStatus.NOT_FOUND);
		    }
		    user = userDAO.getUserWithIDSIU(id);
		}else{
			user = userDAO.login(credential.getUser(), credential.getPass());
		}
		//return new ResponseEntity<User>(user, HttpStatus.OK);
		Token token;
		try {
			token = new Token(tokenManagerSecurity.createJWT(user));
			//rtaLogin r=new rtaLogin(token, user);
			rtaLoginDTO r=new rtaLoginDTO(token,new UserDTO(user.getName(),user.getType(),user.getFavouritesBoards()));
			return ResponseEntity.ok(r);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<rtaLoginDTO>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	
}
