package controllersRest;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import clases.Board;
import clases.Student;
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

}