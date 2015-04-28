package pl.grabber.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import pl.grabber.objects.Article;
import pl.grabber.objects.ArticleAttributes;
import pl.grabber.objects.User;

public class DbManager {

	private static DbManager instance = null;
	private final static Logger logger= Logger.getLogger(DbManager.class.getName()); 
	
	String propertiesFile = "dbconnection.properties";
	Properties prop = new Properties();
	Connection connection;
	
	private DbManager(){
		loadPropertiesFromFile();
	}
	
	public static DbManager getInstance() {
		if(instance == null) {
			instance = new DbManager();
		}
		return instance;
	}
	
	public void initConnection(){
		
		try {
		     Class.forName("org.hsqldb.jdbc.JDBCDriver" );
		 } catch (Exception e) {
		     System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
		     e.printStackTrace();
		     return;
		 }
		
		String dbUser = prop.getProperty("dbuser");
		String dbPass = prop.getProperty("dbpassword");
		String db = prop.getProperty("database");
		String dbName = prop.getProperty("databaseName");
		
		try {
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://" + db + "/" + dbName, dbUser, dbPass);
			logger.info("Connection initialized");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("ERROR: failed to init sql conection : ");
			logger.warning("ERROR: failed to init sql conection : ");
			e.printStackTrace();
		}
		
	}
	
	public boolean isArticleAlreadyInDb(Article article){
			if(connection==null)
				initConnection();
			//System.out.println("checking article : " + article.getTitle());
			Article articleResult = resultSetToArticle(queryString("select * from articles where TITLE='"+article.getTitle()+"'"));
			boolean result = articleResult!=null;
			//System.out.println("result : " + result);
			return result;
		}
	
	private Article resultSetToArticle(ResultSet rs){
		Article article = null;
		ResultSetMetaData meta;
		if(rs!=null){
			try {
				meta = rs.getMetaData();
				int columnsAmount = meta.getColumnCount();
	
		          while (rs.next()) {
		        	  article = new Article();  
			              for (int i = 1; i <= columnsAmount; i++) {
			                String columnName = meta.getColumnName(i);    
			                String columnValue = rs.getString(i);
			                ArticleAttributes articleAtributes = ArticleAttributes.valueOf(columnName);
			                switch (articleAtributes) {
			                   case TITLE: article.setTitle(columnValue); break;
			                   case DESCRIPTION: article.setDescription(columnValue); break;
			                   case IMAGE: article.setImage(columnValue); break;
			                   case SOURCE: article.setSource(columnValue); break;
			                   case AUTHOR: article.setAuthor(columnValue); break;
			                   case SCORE: article.setScore(Integer.valueOf(columnValue)); break;
			                   case PUB_DATE: break;
			                   case ID: break;
			                   default: logger.warning("no attribute for : " + columnName); break;
			                }
			              }  
		            }
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return article;
	}
	
	public ResultSet queryString(String expression){
		if(connection==null)
			initConnection();
		
		Statement st = null;
        ResultSet rs = null;

        try {
			st = connection.createStatement();
			rs = st.executeQuery(expression);
			st.close();
			//logger.info("Query executed ok");
		} catch (SQLException e) {
			logger.warning("Query execution error");
			e.printStackTrace();
		}
        return rs;
	}
	public int queryStringIntResult(String expression){
		ResultSet rs = queryString(expression);
		int foundAmount = 0;
		try {
			if(rs.next()){
				foundAmount = rs.getInt("rowcount1");
				System.out.println("queryIntResult : " + foundAmount);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return foundAmount;
	}
	
	public PreparedStatement prepareStatement(String expression){
		if(connection==null)
			initConnection();
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement(expression);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return st;
		
	}
	
	
	public void insertArticleToDb(Article article){
		if(connection==null)
			initConnection();
		
		PreparedStatement st = null;
		String expression = "INSERT INTO Articles"
				+ "(title,description,image,source,author,score,pub_date) VALUES"
				+ "(?,?,?,?,?,?,?)";
		System.out.println("insertArticleToDb : ");
		System.out.println("title " + article.getTitle() +";" +
				"description " + article.getDescription() +";" +
				"image " + article.getImage() +";" +
				"source " + article.getSource() +";" +
				"author " + article.getAuthor() +";" +
				"score " + article.getScore() +";" 
				);
		
		
        try {
        	st = connection.prepareStatement(expression);
        	st.setString(1, article.getTitle());
        	st.setString(2, article.getDescription());
        	st.setString(3, article.getImage());
        	st.setString(4, article.getSource());
        	st.setString(5, article.getAuthor());
        	st.setInt(6, (int) article.getScore());
        	st.setTimestamp(7,getTimeStampForDate(article.getPublishDate()));
        	System.out.println(st.toString());
        	st.execute();
			st.close();
			//logger.info("Query executed ok");
		} catch (SQLException e) {
			logger.warning("Query execution error");
			e.printStackTrace();
		}
        
	}
	
	private java.sql.Timestamp getTimeStampForDate(Date date){
		return new java.sql.Timestamp(date.getTime());
	}
	
	public void printResultSet(ResultSet rs){
		ResultSetMetaData meta;
		try {
			meta = rs.getMetaData();
			int colmax = meta.getColumnCount();
			int               i;
	        Object            o = null;
	        
	        for (; rs.next(); ) {
	            for (i = 0; i < colmax; ++i) {
	                o = rs.getObject(i + 1);    // Is SQL the first column is indexed
	                // with 1 not 0
	                System.out.print(o.toString() + " ");
	            }
	            System.out.println(" ");
	        }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public void dump(ResultSet rs) throws SQLException {

        // the order of the rows in a cursor
        // are implementation dependent unless you use the SQL ORDER statement
        ResultSetMetaData meta   = rs.getMetaData();
        int               colmax = meta.getColumnCount();
        int               i;
        Object            o = null;

        // the result set is a cursor into the data.  You can only
        // point to one row at a time
        // assume we are pointing to BEFORE the first row
        // rs.next() points to next row and returns true
        // or false if there is no next row, which breaks the loop
        for (; rs.next(); ) {
            for (i = 0; i < colmax; ++i) {
                o = rs.getObject(i + 1);    // Is SQL the first column is indexed

                // with 1 not 0
                System.out.print(o.toString() + " ");
            }

            System.out.println(" ");
        }
    }   
	
	private void loadPropertiesFromFile(){
		
		InputStream input = null;
		try {
			//load file
			input  = getClass().getResourceAsStream(propertiesFile);
    		if(input==null){
	            System.out.println("Sorry, unable to find " + propertiesFile);
	            return;
			}
    		
			// load a properties file
			prop.load(input);	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
