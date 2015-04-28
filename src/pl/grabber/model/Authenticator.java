package pl.grabber.model;

import javax.servlet.http.HttpSession;

import pl.grabber.managers.UserManager;
import pl.grabber.objects.User;

public class Authenticator {
	
	UserManager userManager = UserManager.getInstance();
	 
	public String authenticate(String email, String password) {
		
		System.out.println("hello, Authenticator.authenticate here");
		System.out.println("email : " + email + "; password : " + password);
		System.out.println("password : " + password);
		
		User user = userManager.validateUser(email, password);
		
		if(user!=null){
			return "success";
		} else {
			return "failure";
		}
	}
}