package pl.grabber.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.grabber.managers.ContentManager;

@WebServlet(name="ShowPageController", urlPatterns={"/showPage"})
public class ShowPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShowPageController() {
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

		
		String articleId = (String) request.getParameter("id");
		if(articleId==null){
			rd = request.getRequestDispatcher("/error.jsp");
		}else{
			rd = request.getRequestDispatcher("/show-page.jsp");
			//request.setAttribute("category", category);
			request.setAttribute("article", contentManager.loadArticleWithId(articleId));
		}
		
		rd.forward(request, response);
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		System.out.println("doGet");
		doPost(request,response);
	}

}