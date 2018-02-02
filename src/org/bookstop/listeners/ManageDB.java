package org.bookstop.listeners;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import org.bookstop.constants.AppConstants;
import org.bookstop.constants.SQLstatements;
import org.bookstop.model.Book;
import org.bookstop.model.User;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * Application Lifecycle Listener implementation class ManageDB
 *
 */
@WebListener
public class ManageDB implements ServletContextListener {

	/**
	 * Default constructor.
	 */
	public ManageDB() {
		// TODO Auto-generated constructor stub
	}

	// utility that checks whether a table already exists
	private boolean tableAlreadyExists(SQLException e) {
		boolean exists = false;
		if (e.getSQLState().equals("X0Y32")) {
			exists = true;
		}
		
		return exists;
	}
	
	private void createTable(Connection conn, String stm) throws SQLException{
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(stm);
		// commit update
		conn.commit();
		stmt.close();
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {

		ServletContext cntx = event.getServletContext();

		// shut down database
		try {
			// obtain CustomerDB data source from Tomcat's context and shutdown
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource) context
					.lookup(cntx.getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.SHUTDOWN);
			ds.getConnection();
			ds = null;
		} catch (SQLException | NamingException e) {
			cntx.log("Error shutting down database", e);
		}
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		Logger logger = Logger.getLogger("ManageDB");
		logger.log(Level.INFO, "contextInitialized: Start...");
		ServletContext cntx = event.getServletContext();

		try {

			// obtain CustomerDB data source from Tomcat's context
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource) context
					.lookup(cntx.getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
			Connection conn = ds.getConnection();
			logger.log(Level.INFO, "contextInitialized: Connection to db opened...");
			boolean created = false;
			try {
				// create Books table
				createTable(conn,SQLstatements.CREATE_BOOKS_TABLE);
				//create Users table
				createTable(conn,SQLstatements.CREATE_USERS_TABLE);
				//create Reviews table
				createTable(conn, SQLstatements.CREATE_REVIEWS_TABLE);
				//create Likes table
				createTable(conn,SQLstatements.CREATE_LIKES_TABLE);
				//create Transactions table
				createTable(conn, SQLstatements.CREATE_TRANSACTIONS_TABLE);
				
			} catch (SQLException e) {
				// check if exception thrown since table was already created (so we created the
				// database already
				// in the past
				logger.log(Level.SEVERE, "contextInitialized: sql exception while creating tables");
				//TODO: for debug, remove when done
				//cntx.log("Error during database initialization", e);
				created = tableAlreadyExists(e);
				if (!created) {
					//TODO: in case of error should delete all created tables. Add below
					logger.log(Level.SEVERE, "contextInitialized: Error, something happened...");
					throw e;// re-throw the exception so it will be caught in the
					// external try..catch and recorded as error in the log
				}
			}

			// if no database exist in the past - further populate its records in the table
			if (!created) {
				logger.log(Level.INFO, "contextInitialized: Tables created, populating with books...");
				//TODO: for debugging, remove when done
				if(conn==null) {
					logger.log(Level.INFO, "contextInitialized:EXITING*********************");
					return;
				}
				// populate books table with book data from json file
				Collection<Book> books = loadBooks(
						cntx.getResourceAsStream(File.separator + AppConstants.BOOKS_FILE));
				PreparedStatement pstmt = conn.prepareStatement(SQLstatements.INSERT_BOOK_STMT);
				for (Book book : books) {
					//TODO: create json file and set proper attributes so the db is created correctly
					pstmt.setString(1, book.getName());
					pstmt.setString(2, book.getImg());
					pstmt.setDouble(3, book.getPrice());
					pstmt.setString(4, book.getDescription());
					pstmt.setString(5, book.getLink());
					pstmt.executeUpdate();
				}

				// commit update
				conn.commit();
				// close statements
				pstmt.close();
				
				//TODO: create admin user
				User admin = new User("admin","najib.as1990@gmail.com","Address","049813963","Passw0rd","Overlord","He who rules them all.",null,1);
				PreparedStatement pstmt2 = conn.prepareStatement(SQLstatements.INSERT_USER_STMT);
				pstmt2.setInt(1, admin.getType());
				pstmt2.setString(2, admin.getUsername());
				pstmt2.setString(3, admin.getEmail());
				pstmt2.setString(4, admin.getAddress());
				pstmt2.setString(5, admin.getPhone());
				pstmt2.setString(6, admin.getPassword());
				pstmt2.setString(7, admin.getNickname());
				pstmt2.setString(8, admin.getDescription());
				pstmt2.setString(9, admin.getPicture());
				pstmt2.executeUpdate();
				
				// commit update
				conn.commit();
				// close statements
				pstmt2.close();
				
				//TODO: at this time no further pre-configuration is needed
			}
			
			logger.log(Level.INFO, "contextInitialized: PRE-CONFIGURATION COMPLETED SUCCESFULLY");

			// close connection
			conn.close();

		} catch (IOException | SQLException | NamingException e) {
			// log error
			cntx.log("Error during database initialization", e);
		}
	}
	
	/**
	 * Loads books data from json file that is read from the input stream into 
	 * a collection of Book objects
	 * @param is input stream to json file
	 * @return collection of books
	 * @throws IOException
	 */
	private Collection<Book> loadBooks(InputStream is) throws IOException{
		
		//wrap input stream with a buffered reader to allow reading the file line by line
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder jsonFileContent = new StringBuilder();
		//read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null){
			jsonFileContent.append(nextLine);
		}

		Gson gson = new Gson();
		//this is a require type definition by the Gson utility so Gson will 
		//understand what kind of object representation should the json file match
		Type type = new TypeToken<Collection<Book>>(){}.getType();
		Collection<Book> books = gson.fromJson(jsonFileContent.toString(), type);
		//close
		br.close();	
		return books;

	}

}
