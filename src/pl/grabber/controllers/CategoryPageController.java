package pl.grabber.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.grabber.db.DbManager;
import pl.grabber.managers.ContentManager;
import pl.grabber.objects.Article;
import pl.grabber.objects.Category;
import pl.grabber.objects.Page;

@WebServlet(name="CategoryPageController", urlPatterns={"/showCategory"})
public class CategoryPageController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private final static Logger logger= Logger.getLogger(CategoryPageController.class.getName());
	private ContentManager contentManager;

	public CategoryPageController() {
		super();
		
	}
	
	 public void init() throws ServletException
	 {
	      // Do required initialization
		 contentManager = ContentManager.getInstance();
	 }

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		/*List<String> categories = contentManager.loadCategoriesAll();*/
		
		RequestDispatcher rd = null;
		
		int paginationNumber = getIntValue(request,"p",0);
		int articlesOnPage = getIntValue(request,"view",25);
		Category category = loadCategoryFromParam(request,"cat");
		
		Page page = new Page();
		page.setPaginationNumber(paginationNumber);
		page.setArticlesOnPage(articlesOnPage);
		page.setCategory(category);
		
		ArrayList<Article> articles = new ArrayList<Article>();
		
		switch(category.getId()){
			case -1: System.out.println("category empty, GET ALL");  
					articles = contentManager.loadArticlesFromDbAllFor(page);
					break;
			case 0: System.out.println("category DONT EXIST"); break;
			default : System.out.println("category ok, LOAD FROM DB"); 
					  articles = contentManager.loadArticlesFromDbByCategoryFor(page);
					  break;
		}
		request.setAttribute("page", page);
		request.setAttribute("articles", articles);
		rd = request.getRequestDispatcher("/pages/categoryContentPage.jsp");
		
		rd.forward(request, response);
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		System.out.println("doGet");
		doPost(request,response);
	}
	
	
	private Category loadCategoryFromParam(HttpServletRequest request,String paramName){
		Category category = new Category();
		String cat = (String) request.getParameter(paramName);
		if(cat==null || cat.equals("")){
			logger.info("category empty!!!");
			category.setId(-1);
			category.setName("all");
		}else{
			category = contentManager.loadCategoryFromDb(cat);
		}
		
		logger.info(".loadCategoryFromParam()->");
		logger.info("category.id : " + category.getId() + "; name : " + category.getName());
		
		return category;
	}
	private int getIntValue(HttpServletRequest request, String paramName, int defaultValue){
		int result = defaultValue; 
		if(request.getParameter(paramName)!=null)
			result = Integer.valueOf(request.getParameter(paramName));
		return result;
	}

}