package org.bookstop.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.bookstop.constants.AppConstants;
import org.bookstop.dataAccess.DA;
import org.bookstop.model.Book;
import org.bookstop.model.BookId;
import org.bookstop.model.User;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetLikersByBook
 */
@WebServlet("/GetLikersByBook")
public class GetLikersByBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetLikersByBook() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("GetLikersByBook Servlet");

		Logger logger = Logger.getLogger("GetLikersByBookServlet");
		logger.log(Level.INFO, "doPost: Start...");

		Gson gson = new Gson();
		// 	NOTE: it is possible to use the Book model to store the bookid given in the json.
		//	at the time of writing this servlet we had problems with Gson and the first solution
		//	that worked was to create a new model. We didn't have time to change things on the 
		//	client side and server side to match the Book model so Gson works.
		BookId bookid = null;
		try {
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = request.getReader().readLine()) != null) {
				sb.append(s);
			}
			bookid = (BookId) gson.fromJson(sb.toString(), BookId.class);
			logger.log(Level.INFO, "doPost: bookId: " + bookid);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (bookid == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		if(bookid.getBookid() < 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		try {

			// obtain CustomerDB data source from Tomcat's context
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource) context
					.lookup(getServletContext().getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
			Connection conn = ds.getConnection();
			logger.log(Level.INFO, "doPost: connection opened...");
			DA da = new DA(conn);

			Book b = da.selectBookById(bookid.getBookid());
			if (b == null) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			} else {

				ArrayList<String> usernames = da.selectUsernameFromLikesByBookId(bookid.getBookid());

				ArrayList<User> likers = new ArrayList<User>();
				// sending full user information (specifically username and password) is a
				// violation, so we clear out the password field)
				// this could have been achieved by not selecing the password column from the
				// sql table, but it's too late to change now...
				// we'll save this for the next iteration/optimizations
				for (String username : usernames) {
					User u = da.selectUserByUsername(username);
					u.setPassword("");
					likers.add(u);
				}
				String json = new Gson().toJson(likers);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				da.closeConnection();
			}
		} catch (SQLException | NamingException e) {
			// log error
			logger.log(Level.SEVERE, "doPost: FAILED");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}

}
