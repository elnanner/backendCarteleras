package clasesPrivadas;

import java.util.Collection;

import clases.Board;

public class UserDTO {
	private String name;
	private String type;
	private Collection<Board> favouriteBoards;
	
	public UserDTO(String name,String type, Collection<Board> favouriteBoard){
		this.name=name;
		this.type=type;
		favouriteBoards=favouriteBoard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Collection<Board> getFavouriteBoards() {
		return favouriteBoards;
	}

	public void setFavouriteBoards(Collection<Board> favouriteBoards) {
		this.favouriteBoards = favouriteBoards;
	}
	
}
