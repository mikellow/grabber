package pl.grabber.test;

import java.sql.SQLException;
import java.util.logging.Logger;

import pl.grabber.db.DbManager;

public class DbConnectionTest {
	
	private final static Logger logger= Logger.getLogger(DbManager.class.getName());

	public static void main(String[] args) throws SQLException {

		DbManager dbManager = DbManager.getInstance();
				  logger.info("dbManager initialized");
				  dbManager.initConnection();
				  logger.info("dbManager.connection initialized");
				  //dbManager.printResultSet(dbManager.query("select * from articles"));
				  dbManager.queryString("select * from articles");
				  logger.info("dbManager.query executed");
	}
}
