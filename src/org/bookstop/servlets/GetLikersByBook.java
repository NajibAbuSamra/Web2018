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
import org.bookstop.model.BookInfo;
import org.bookstop.model.Review;
import org.bookstop.model.User;
import org.bookstop.model.UserLogin;

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
		// TODO Auto-generated constructor stub
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
		//TODO: try to user Book model, maybe it will partially fill the information, no need for the BookId model
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
		if (bookid == null || bookid.getBookid() == 0) {
			// TODO: check and handle error
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

			ArrayList<String> usernames = da.selectUsernameFromLikesByBookId(bookid.getBookid());

			ArrayList<User> likers = new ArrayList<User>();
			//TODO: sending full user information (specifically username and password) is a violation, so we clear out the password field)
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
			if (conn.isClosed() == false) {
				logger.log(Level.WARNING, "doPost: connection not closed after DA method, closing manually");
				conn.close();
			}

		} catch (SQLException | NamingException e) {
			// log error
			logger.log(Level.SEVERE, "doPost: FAILED");

		}
	}

}
