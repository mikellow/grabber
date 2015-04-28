package pl.grabber.managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

import pl.grabber.db.DbManager;
import pl.grabber.objects.User;

public class UserManager  extends HttpServlet {
	private static UserManager instance = null;
	private static final long serialVersionUID = 1L;
	private final static Logger logger= Logger.getLogger(DbManager.class.getName());
	
	DbManager dbManager;

	public UserManager() {
		dbManager = DbManager.getInstance();
	}
	
	public static UserManager getInstance() {
		if(instance == null) {
			instance = new UserManager();
		}
		return instance;
	}
	
	public boolean userInDatabase(String userLogin){
		boolean userExist = false;
		dbManager.initConnection();
		
			String queryStatementEmail = "select count(*)  AS rowcount1 from users where email ='" + userLogin+"'";
			boolean userWithEmailExist = dbManager.queryStringIntResult(queryStatementEmail) > 0;
			
			String queryStatementLogin = "select count(*)  AS rowcount1 from users where login ='" + userLogin+"'";
			boolean userWithLoginExist = dbManager.queryStringIntResult(queryStatementLogin) > 0;
			
		userExist =  userWithEmailExist  || userWithLoginExist;
		System.out.println(".userInDatabase() ->userExist :" + userExist);
		return userExist;
		
	}
	
	public void addNewUserToDb(User user){
		dbManager.initConnection();
		
		String queryStatement = "insert into users (login,email,passwd) values "
				+ "('"+user.getUsername()+"','"+ user.getEmail()+"','"+ user.getPassword()+"')";
		
		dbManager.queryString(queryStatement);
		
		System.out.println("user added !");
		
	}
	
	public User validateUser(String email, String password){
		
		dbManager.initConnection();
		String queryStatement = "select * from users where email ='" + email+"'";
		User user = getUserFromResultSet(dbManager.queryString(queryStatement),password);
		//System.out.println("articles from db : " + articles.size());
		return user;
	}
	
	public User getUserFromDb(String username){
		dbManager.initConnection();
		String queryStatementLogin = "select * from users where login='" + username +"'";
		User user = getUserFromResultSet(dbManager.queryString(queryStatementLogin));
		if(user == null){
			String queryStatementEmail = "select * from users where email='" + username +"'";
			user = getUserFromResultSet(dbManager.queryString(queryStatementEmail));	
		}
		return user;
		
	}
	
	public void updateUserLastLoginTime(User user){
		dbManager.initConnection();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
		Date date = new Date();
		System.out.println("lastLoginDate : " + date);
		user.setLastLoginDate(date);
				
		String expression = "UPDATE users SET last_login_date = ? WHERE login = ?";
		PreparedStatement st = dbManager.prepareStatement(expression);
		try {
			st.setTimestamp(1,new java.sql.Timestamp(date.getTime()));
			st.setString(2,user.getUsername());
			st.execute();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private User getUserFromResultSet(ResultSet rs){
		User user = null;
		ResultSetMetaData meta;
		try {
			  meta = rs.getMetaData();
	          while (rs.next()) {
		        	  user = new User();
		        	  user.setUsername(rs.getString(2));
		        	  user.setEmail(rs.getString(3));
		        	  
		        	  user.setSmsNotification(rs.getBoolean(5));
		        	  user.setEmailNotification(rs.getBoolean(6));
		        	  user.setLastLoginDate(rs.getTimestamp(7));
		        	  
		        	  logger.info("got user >> nick:" + user.getUsername() + "; email:" + user.getEmail() +";" ) ;
	          }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
	
	private User getUserFromResultSet(ResultSet rs,String password){
		User user = null;
		ResultSetMetaData meta;
		try {
			  meta = rs.getMetaData();
	          while (rs.next()) {
	        	  if(password.equals(rs.getString(4))){
		        	  user = getUserFromResultSet(rs);
		         }else{
		        	 logger.info("validate user failure" ) ;
		         }
	          }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
	
	

}
