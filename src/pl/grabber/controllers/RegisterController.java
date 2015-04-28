package pl.grabber.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.grabber.managers.UserManager;
import pl.grabber.objects.User;


@WebServlet(name="RegisterController", urlPatterns={"/register"})
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	UserManager userManager = UserManager.getInstance();

	public RegisterController() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			System.out.println("doPost polecia³");
			
			String userNameTakenError = "user name already taken";
			String userNameToShortError = "user name too short";
			String emailError = "email registered!";
			String invalidEmailError = "Invalid email";
			String passwordTooShortError = " password too short";
			String passwordDontMatchError = "passwords don't match";
			
			List<String> errors = new ArrayList<String>();
			
			String userName = (String) request.getParameter("username");
			if(userName.length() < 5){
				errors.add(userNameToShortError);
			}
			
			if(userManager.userInDatabase(userName)){
				errors.add(userNameTakenError);
			}
			
			String email = (String) request.getParameter("email");
			if(!email.contains("@")){
				errors.add(invalidEmailError);
			}
			
			if(userManager.userInDatabase(email)){
				errors.add(emailError);
			}
			
			String password = (String) request.getParameter("password");
			String passwordConfirm = (String) request.getParameter("confirm-password");
			
			if(password.length() < 5){
				errors.add(passwordTooShortError);
			}
			
			if(!password.equals(passwordConfirm)){
				errors.add(passwordDontMatchError);
			}
			
			
			System.out.println("register-form data :" + userName + ";" + email + ";" + password);
			
			
			
			RequestDispatcher rd = null;
			
			if(errors.size()>0){
				String[] errArr = new String[errors.size()];
				errArr = errors.toArray(errArr);
				for(String error : errArr){
					System.out.println("error :" + error);
				}
				request.setAttribute("errors", errArr);
				rd = request.getRequestDispatcher("/pages/register.jsp");
				
			}else{
				User newUser = new User();
				newUser.setUsername(userName);
				newUser.setEmail(email);
				newUser.setPassword(password);
				newUser.setSmsNotification(false);
				newUser.setEmailNotification(false);
				
				
				userManager.addNewUserToDb(newUser);
				
				rd = request.getRequestDispatcher("/pages/login.jsp");
				
				
			}
			rd.forward(request, response);
			
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			System.out.println("doGet polecia³");
			
			//HttpSession session = request.getSession();
			
			RequestDispatcher rd = null;
			rd = request.getRequestDispatcher("/pages/register.jsp");
			rd.forward(request, response);
			
	}
	
}

