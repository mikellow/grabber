package pl.grabber.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.grabber.managers.ContentManager;

@WebServlet(name="PageController", urlPatterns={"/PageController"})
public class PageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PageController() {
		super();
		
	}
	
	 public void init() throws ServletException
	 {
	      // Do required initialization
	 }

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ContentManager contentManager = ContentManager.getInstance();
		
		
		
		RequestDispatcher rd = null;

		String result = "success";
		
		if (result.equals("success")) {
			rd = request.getRequestDispatcher("/category-page.jsp");
			//User user = new User("siema", "siema2");
			//request.setAttribute("user", user);
			request.setAttribute("category", "Wykop");
			request.setAttribute("articles", contentManager.loadArticlesAll());
			
		} else {
			rd = request.getRequestDispatcher("/error.jsp");
		}
		rd.forward(request, response);
		/*
	      // Set response content type
	      response.setContentType("text/html");
	      String message = "Hello World";
	      // Actual logic goes here.
	      PrintWriter out = response.getWriter();
	      out.println("<h1>" + message + "</h1>");
	      */
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		System.out.println("doGet");
		doPost(request,response);
	}

}