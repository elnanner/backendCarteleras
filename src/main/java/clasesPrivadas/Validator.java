package clasesPrivadas;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validator {
	
	public static ArrayList<String> validarUsuario(String userName, String password,String email){
		ArrayList<String> result=new ArrayList<String>();
		result.add(0,"true");
		result.add(1,"");
		if(userName.isEmpty() || userName.trim().equals("")){
			result.set(0, "false");
			result.set(1, result.get(1)+"El nombre de usuario no puede ser vacio.");
			
			//verificar contra bd por ej
		}
		if(password.isEmpty() || password.trim().equals("")){
			result.set(0, "false");
			result.set(1, result.get(1)+"-El password no puede ser vacio.");
		}
		
		if(email.isEmpty() || email.trim().equals("")){
			result.set(0, "false");
			result.set(1, result.get(1)+"-El mail no puede ser vacio.");
		}else{
			//tipo mail
			 // Compiles the given regular expression into a pattern.
	        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	 
	        // Match the given input against this pattern
	        Matcher matcher = pattern.matcher(email);
	       if(!matcher.matches()){
	    	   result.set(0, "false");
				result.set(1, result.get(1)+"-El mail no cumple el formato"); //anotar formato
	       }
		}
		
		//mail unico por user
		
		return result;
	}
	
	
}
