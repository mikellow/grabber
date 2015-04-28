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


@WebServlet(name="UserPageController", urlPatterns={"/showUserPage"})
public class userPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	UserManager userManager = UserManager.getInstance();

	public userPageController() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			System.out.println("doPost polecia³");
	
			RequestDispatcher rd = null;
			HttpSession session = request.getSession();
			if (null == session.getAttribute("loggedUser")){
				System.out.println("loggedUser null :O");
				rd = request.getRequestDispatcher("/index.jsp");
			}else{
				System.out.println("loggedUser !=null , go to  user-page.jsp :O");
				rd = request.getRequestDispatcher("/pages/user-page.jsp");
			}
			rd.forward(request, response);

	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			System.out.println("doGet polecia³");
			
				doPost(request,response);					
	}
	
}

