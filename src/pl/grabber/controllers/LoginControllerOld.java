package pl.grabber.controllers;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.grabber.model.Authenticator;
import pl.grabber.objects.User;


@WebServlet(name="LoginControllerOld", urlPatterns={"/LoginController"})
public class LoginControllerOld extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginControllerOld() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		RequestDispatcher rd = null;

		Authenticator authenticator = new Authenticator();
		String result = authenticator.authenticate(email, password);
		if (result.equals("success")) {
			System.out.println("kuuuurwa");
			rd = request.getRequestDispatcher("/pages/user-page.jsp");
			HttpSession session = request.getSession();
			User user = new User(email, password);
			
			
		    //session.setAttribute("user.mail", user.getEmail());
			session.setAttribute("USERNAME", "DUPA");
			request.setAttribute("user", user);
		} else {
			rd = request.getRequestDispatcher("/error.jsp");
		}
		rd.forward(request, response);
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			System.out.println("doGet poelcial");
			Boolean logout = Boolean.valueOf(request.getParameter("logout"));
			System.out.println("logout : " + logout);
			
			RequestDispatcher rd = null;
			
			if(logout){
				HttpSession session = request.getSession();
				session.invalidate();
				rd = request.getRequestDispatcher("/index.jsp");
				
			}
			rd.forward(request, response);
		 //session.invalidate()
		
		
	}
	
}

