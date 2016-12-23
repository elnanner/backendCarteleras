package controllersRest;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import clases.Admin;

import clasesDAO.AdminDAO;


@RestController
public class AdminController {
	
	@Autowired
	private AdminDAO adminDAO;
	
	public AdminController(){
		
	}

	public AdminDAO getAdminDAO() {
		return adminDAO;
	}

	public void setAdminDAO(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}
	
	
	@RequestMapping(value="/admins",method = RequestMethod.GET ,headers="Accept=application/json")
	public ResponseEntity<ArrayList<Admin>> listAllAdmins() {	
		ArrayList<Admin> admins = adminDAO.getAllWithoutOrder();
		if(admins.isEmpty()){
			return new ResponseEntity<ArrayList<Admin>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<ArrayList<Admin>>(admins, HttpStatus.OK);
	}


	@RequestMapping(value="/admins/{id}", method = RequestMethod.GET , produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> getAdminById(@PathVariable("id") Long idAdmin) {
		Admin admin =adminDAO.get(idAdmin);
		if(admin==null){
			return new ResponseEntity<Admin>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Admin>(admin, HttpStatus.OK);
	}
	
	
	
	
}
