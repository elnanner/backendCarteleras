package controllersRest;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.JsonObject;

import clases.Board;

import clasesDAO.BoardDAO;

@RestController
public class BoardsController {
	
@Autowired 
private BoardDAO boardDAO;


public BoardsController(){  }
public BoardDAO getBoardDAO() {
	return boardDAO;
}
public void setBoardDAO(BoardDAO boardDAO) {
	this.boardDAO = boardDAO;
}




//Recupero todas las pizarras
@RequestMapping(value="/boards",method = RequestMethod.GET ,headers="Accept=application/json")
public ResponseEntity<ArrayList<Board>> listAllBoards() {	
	ArrayList<Board> boards = boardDAO.getAllWithoutOrder();
	if(boards.isEmpty()){
		return new ResponseEntity<ArrayList<Board>>(HttpStatus.NO_CONTENT);
	}
	return new ResponseEntity<ArrayList<Board>>(boards, HttpStatus.OK);
}


@RequestMapping(value="/boards/{id}", method = RequestMethod.GET , produces =MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Board> getBoardById(@PathVariable("id") Long idBoard) {
	Board board =boardDAO.get(idBoard);
	if(board==null){
		return new ResponseEntity<Board>(HttpStatus.NOT_FOUND);
	}
	return new ResponseEntity<Board>(board, HttpStatus.OK);
}


@RequestMapping(value="/deleteBoard", method = RequestMethod.DELETE , produces =MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Board> deleteBoardById(HttpEntity<String> httpEntity /*@PathVariable("id") Long idBoard*/) {
	//ej {"idBoard": "26"}
	//verificar permisos
	Gson gson = new Gson();
	String json = httpEntity.getBody();
	JsonObject dataJson = gson.fromJson(json, JsonObject.class);
	
	Long idBoard=dataJson.get("idBoard").getAsLong();
	
	
	Board board =boardDAO.get(idBoard);
	if(board==null || idBoard==26L){// NO PODES BORRAR LA PIZARRA 26 (la home) restricción del sistema
		return new ResponseEntity<Board>(HttpStatus.NOT_FOUND);
	}
	board.setDown(true);
	boardDAO.update(board);
	return new ResponseEntity<Board>(board, HttpStatus.OK);
	//return new ResponseEntity<Board>(board, HttpStatus.OK);
}


@RequestMapping(value="/createBoard", method = RequestMethod.POST , produces =MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Board> createBoard(HttpEntity<String> httpEntity ) {
	//ej {"idFatherBoard": "26", "name": "newBoard", "description": "soy una nueva pizarra"}
	
	//verificar permisos
	Gson gson = new GsonBuilder().serializeNulls().create();//new Gson();
	String json = httpEntity.getBody();
	JsonObject dataJson = gson.fromJson(json, JsonObject.class);
	
	//controlar tipos de datos,etc
	
    long idFatherBoard = dataJson.get("idFatherBoard").getAsLong();
	Board boardFather = boardDAO.get(idFatherBoard);
	if(boardFather==null){//si padre es nulo: puso mal el id
		return new ResponseEntity<Board>(HttpStatus.NOT_FOUND);
	}
	
	String nameBoard=dataJson.get("name").getAsString();
	String descriptionBoard=dataJson.get("description").getAsString();
	
	Board newBoard=new Board(nameBoard,descriptionBoard,"urlBasura");
	boardDAO.persist(newBoard);//persistimos pizarra nueva
	
    //agregamos el hijo al padre
	boardFather.addBoard(newBoard);
	boardDAO.update(boardFather);

	return new ResponseEntity<Board>(newBoard, HttpStatus.OK);
}


@RequestMapping(value="/updateBoard", method = RequestMethod.POST , produces =MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Board> updateBoard(HttpEntity<String> httpEntity ) {
	//SOLO podes modificar los datos propios de la pizarra (nombre, descripcion y pizarras hijas), pero NO el id o la baja
	//ej (idpadre peude ser null {"idFatherBoard": "26", "name": "newBoard", "description": "soy una nueva pizarra"}
	
	//verificar permisos
	Gson gson = new GsonBuilder().serializeNulls().create();//new Gson();
	String json = httpEntity.getBody();
	JsonObject dataJson = gson.fromJson(json, JsonObject.class);
	
	
	//controlar tipos de datos,etc
	Long idBoard=dataJson.get("idBoard").getAsLong();
	Board board =boardDAO.get(idBoard);
	
	if(board==null){//si es nulo: puso mal el id, o no se recupero bien
		return new ResponseEntity<Board>(HttpStatus.NOT_FOUND);
	}
	
	 
	 board.setName(dataJson.get("name").getAsString());
	 board.setDescription(dataJson.get("description").getAsString());
	
   
	boardDAO.update(board);//actualizamos pizarra
	
	return new ResponseEntity<Board>(board, HttpStatus.OK);
}



}