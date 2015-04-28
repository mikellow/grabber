package pl.grabber.managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import pl.grabber.db.DbManager;
import pl.grabber.objects.Article;
import pl.grabber.objects.ArticleAttributes;
import pl.grabber.objects.Category;
import pl.grabber.objects.Page;

public class ContentManager {
	private static ContentManager instance = null;	
	private final static Logger logger= Logger.getLogger(DbManager.class.getName());
	
	private int articlesPerPage = 18;
	
	DbManager dbManager;

	private ContentManager(){
		dbManager = DbManager.getInstance();
	}
	
	public static ContentManager getInstance() {
		if(instance == null) {
			instance = new ContentManager();
		}
		return instance;
	}
	
	/* dont touch  xD */
	public Category loadCategoryFromDb(String categoryName){
		dbManager.initConnection();
	
		String expression = "select id,category_name from CATEGORIES where category_name = ?";
		PreparedStatement st = dbManager.prepareStatement(expression);
		try {
			st.setString(1, categoryName);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ResultSet rs;
		Category category = new Category();
		category.setName(categoryName);
		try {
			rs = st.executeQuery();
			while (rs.next()) {
				int catId = rs.getInt("ID");
				String catName = rs.getString("CATEGORY_NAME");
				System.out.println("catId: " + catId + "; catName: " + catName);
				category.setId(catId);
				category.setName(catName);
			}
		}catch (SQLException e) {
			logger.warning("Query execution error");
			e.printStackTrace();
		}
		/*
		if(category == null && categoryName.equals("all")){
			category = new Category();
			category.setName("all");
		}
		*/
		return category;
		
	}
	
	public ArrayList<Article> loadArticlesFromDbAllFor(Page page){
		ArrayList<Article> articles = new ArrayList<Article>();
		
		int articlesPerPage = page.getArticlesOnPage();
		int pageNumber = page.getPaginationNumber();
		
		dbManager.initConnection();
		String expression = new StringBuilder()
			.append("select ID,TITLE,DESCRIPTION,IMAGE,SOURCE,AUTHOR,SCORE,PUB_DATE from ARTICLES ")
			.append("order by pub_date desc limit ? offset ?")
			.toString();
		
		PreparedStatement st = dbManager.prepareStatement(expression);
		
		try {
			st.setInt(1, articlesPerPage);
			st.setInt(2, pageNumber);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ResultSet rs;

		try {
			rs = st.executeQuery();
			articles = resultSetToArticles(rs);
			/*
			while (rs.next()) {
				int articleId = rs.getInt("ID");
				String articleTitle = rs.getString("TITLE");
				System.out.println("articleId: " + articleId + "; articleTitle: " + articleTitle);
			}
			*/
		}catch (SQLException e) {
			logger.warning("Query execution error");
			e.printStackTrace();
		}

		System.out.println("articles from db : " + articles.size());
		return articles;
			
	}
	
	public ArrayList<Article> loadArticlesFromDbByCategoryFor(Page page){
		ArrayList<Article> articles = new ArrayList<Article>();
		
		if(page.getCategory()==null){
			System.out.println("category is null :O");
			return articles;
		}
		
		int categoryId = page.getCategory().getId();
		int articlesPerPage = page.getArticlesOnPage();
		int pageNumber = page.getPaginationNumber();
		System.out.println("categoryId : " + categoryId);
		System.out.println("articlesPerPage : " + articlesPerPage);
		System.out.println("pageNumber : " + pageNumber);
		
		dbManager.initConnection();
		String expression = new StringBuilder()
			.append("select ID,TITLE,DESCRIPTION,IMAGE,SOURCE,AUTHOR,SCORE,PUB_DATE from ARTICLES ")
			.append("inner join (")
			.append("select A_ID from (")
			.append("select ID,TAG_NAME from TAGS ")
			.append("INNER JOIN CATEGORY_TAG ON CATEGORY_TAG.T_ID = TAGS.ID ")
			.append("where CATEGORY_TAG.C_ID = ? ")
			.append(") TAGS_FOR_CATEGORY ")
			.append("inner join TAG_ARTICLE ")
			.append("on TAGS_FOR_CATEGORY.id = TAG_ARTICLE.t_id ")
			.append(") ART_TAG ")
			.append("on ARTICLES.id = ART_TAG.A_ID order by pub_date desc limit ? offset ?")
			.toString();
		
		//System.out.println("expression :" + expression);
		
		PreparedStatement st = dbManager.prepareStatement(expression);
		
		try {
			System.out.println("setting categoryId : " + categoryId);
			st.setInt(1, categoryId);
			st.setInt(2, articlesPerPage);
			st.setInt(3, pageNumber);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ResultSet rs;

		try {
			rs = st.executeQuery();
			articles = resultSetToArticles(rs);
			/*
			while (rs.next()) {
				int articleId = rs.getInt("ID");
				String articleTitle = rs.getString("TITLE");
				System.out.println("articleId: " + articleId + "; articleTitle: " + articleTitle);
			}
			*/
		}catch (SQLException e) {
			logger.warning("Query execution error");
			e.printStackTrace();
		}

		System.out.println("articles from db : " + articles.size());
		return articles;
	}

	
	
	public void loadFromDbTagsAll(){
		dbManager.initConnection();
		
		String expression = "select id,tag_name from tags";
		PreparedStatement st = dbManager.prepareStatement(expression);
		ResultSet rs;
		try {
			rs = st.executeQuery();
			while (rs.next()) {
				int tagId = rs.getInt("ID");
				String tagName = rs.getString("TAG_NAME");
				System.out.println("tagId: " + tagId + "; tagName: " + tagName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loadFromDbCategoriesAll(){
		dbManager.initConnection();
		
		String expression = "select id,category_name from categories";
		PreparedStatement st = dbManager.prepareStatement(expression);
		ResultSet rs;
		try {
			rs = st.executeQuery();
			while (rs.next()) {
				int catId = rs.getInt("ID");
				String tagName = rs.getString("CATEGORY_NAME");
				System.out.println("catId: " + catId + "; categoryName: " + tagName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Article> loadArticlesAll(){
		ArrayList<Article> articles = new ArrayList<Article>();
		dbManager.initConnection();
		articles = resultSetToArticles(dbManager.queryString("select * from articles"));
		System.out.println("articles from db : " + articles.size());
		return articles;
	}
	
	public ArrayList<Article> loadArticlesWithCategory(String category){
		
		ArrayList<Article> articles = new ArrayList<Article>();
		dbManager.initConnection();
		articles = resultSetToArticles(dbManager.queryString("select * from articles"));
		System.out.println("articles from db : " + articles.size());
		return articles;
	}
	
	public ArrayList<String> loadCategoriesAll(){
		ArrayList<String> categories = new ArrayList<String>();
		dbManager.initConnection();
		categories = resultSetToArrayList(dbManager.queryString("select * from categories"));
		return categories;
	
	}
	
	public Article loadArticleWithId(String articleId){
		Article article = new Article();
		String queryStatement = "select * from articles where id="+articleId;
		article = resultSetToArticles(dbManager.queryString(queryStatement)).get(0);
		return article;
	}
	
	public ArrayList<Article> loadArticlesForPage(int pageNumber){
		ArrayList<Article> articles = new ArrayList<Article>();
		String queryStatement = "select * from articles order by pub_date desc limit " + articlesPerPage + " offset " + articlesPerPage*(pageNumber-1);
		
		articles = resultSetToArticles(dbManager.queryString(queryStatement));
		System.out.println("articles from db : " + articles.size());
		return articles;
	}
	public int getLastPageNumber(){
		String queryStatement = "select count(*) as rowsNumber from articles";
		ResultSet rs = dbManager.queryString(queryStatement);
		int rowsNumber = 0;
		try {
			rs.next();
			rowsNumber = rs.getInt("rowsNumber") ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int lastPageNumber = (int) Math.ceil((double)rowsNumber/(double)articlesPerPage);
		return lastPageNumber;
	}
	
	private ArrayList<Article> resultSetToArticles(ResultSet rs){
		ArrayList<Article> articles = new ArrayList<Article>();

		ResultSetMetaData meta;
		try {
			meta = rs.getMetaData();
			int columnsAmount = meta.getColumnCount();

	          while (rs.next()) {
	        	  Article article = new Article();
		              for (int i = 1; i <= columnsAmount; i++) {
		                //if (i > 1) System.out.print(",  ");
		                String columnName = meta.getColumnName(i);
		                //System.out.print("column: " + columnName);    
		                String columnValue = rs.getString(i);
		                //System.out.print(columnValue);
		                
		                ArticleAttributes articleAtributes = ArticleAttributes.valueOf(columnName);
		                switch (articleAtributes) {
		                   case ID: article.setId(columnValue); break;
		                   case TITLE: article.setTitle(columnValue); break;
		                   case DESCRIPTION: article.setDescription(columnValue); break;
		                   case IMAGE: article.setImage(columnValue); break;
		                   case SOURCE: article.setSource(columnValue); break;
		                   case AUTHOR: article.setAuthor(columnValue); break;
		                   case SCORE: article.setScore(Integer.valueOf(columnValue)); break;
		                   case PUB_DATE: article.setPublishDate(sqlTimestampToDate(columnValue));break;
		                   default: logger.warning("no attribute for : " + columnName);	break;
		                }
		              }
		             articles.add(article);
	              //System.out.println("");  
	            }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return articles;
	}
	
	private ArrayList<String>  resultSetToArrayList(ResultSet rs){
	ArrayList<String> result= new ArrayList<String>();
	try {
          while (rs.next()) {
        	  String columnValue = rs.getString(2);
        	  System.out.println("arrayValue : " + columnValue);
        	  result.add(columnValue);
          }
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return result;
}
	
	private Date sqlTimestampToDate(String timestampString){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(timestampString);
			/*
			System.out.println("Date : "  + timestampString);
			System.out.println("parsedDate : "  + parsedDate);
			*/
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parsedDate;
	}

}
