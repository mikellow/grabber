package pl.grabber.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.grabber.managers.UserManager;
import pl.grabber.model.Authenticator;
import pl.grabber.objects.User;


@WebServlet(name="LoginController", urlPatterns={"/login"})
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	UserManager userManager = UserManager.getInstance();

	public LoginController() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			System.out.println("doPost polecia³");
			
			String userNameToShortError = "user name too short";
			String userNameDontExistError = "user doesn't exist";;
			String passwordInvalidError = "invalid password";
			String passwordTooShortError = " password too short";
			
			List<String> errors = new ArrayList<String>();
			
			String userName = (String) request.getParameter("username");
			if(userName.length() < 5){
				errors.add(userNameToShortError);
			}
			if(!userManager.userInDatabase(userName)){
				errors.add(userNameDontExistError);
			}
			
			String password = (String) request.getParameter("password");
			if(password.length() < 5){
				errors.add(passwordTooShortError);
			}
			
			if(userManager.validateUser(userName, password)!=null){
				errors.add(passwordInvalidError);
			}
			
			
			System.out.println("login-form data :" + userName + ";" + password);
			
			RequestDispatcher rd = null;
			
			if(errors.size()>0){
				String[] errArr = new String[errors.size()];
				errArr = errors.toArray(errArr);
				for(String error : errArr){
					System.out.println("error :" + error);
				}
				request.setAttribute("errors", errArr);
				rd = request.getRequestDispatcher("/pages/login.jsp");
				
			}else{
				User loggedUser =  userManager.getUserFromDb(userName);
				userManager.updateUserLastLoginTime(loggedUser);
				String rememberMeCheckbox =  request.getParameter("remember-me");
					
				HttpSession session = request.getSession();	
			    //session.setAttribute("user.mail", user.getEmail());
				session.setAttribute("loggedUser", loggedUser);
				if(rememberMeCheckbox != null){
					session.setMaxInactiveInterval(60 * 60); //60 min
					System.out.println("session set to  : " + 60 +"min");
				}
				
				//request.setAttribute("loggedUser", loggedUser);
				rd = request.getRequestDispatcher("/pages/user-page.jsp");
			}
			rd.forward(request, response);

	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			System.out.println("doGet polecia³");
			
			//HttpSession session = request.getSession();
			
			RequestDispatcher rd = null;
			rd = request.getRequestDispatcher("/pages/login.jsp");
			rd.forward(request, response);
			
	}
	
}

