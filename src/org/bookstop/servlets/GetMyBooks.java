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
import org.bookstop.model.BookInfo;
import org.bookstop.model.Review;
import org.bookstop.model.User;
import org.bookstop.model.UserLogin;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetMyBooks
 */
@WebServlet("/GetMyBooks")
public class GetMyBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetMyBooks() {
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
		// TODO Auto-generated method stub
		System.out.println("GetMyBooks Servlet");

		Logger logger = Logger.getLogger("GetMyBooksServlet");
		logger.log(Level.INFO, "doPost: Start...");

		Gson gson = new Gson();
		UserLogin user = null;
		try {
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = request.getReader().readLine()) != null) {
				sb.append(s);
			}

			user = (UserLogin) gson.fromJson(sb.toString(), UserLogin.class);
			logger.log(Level.INFO, "doPost: user info: uName:" + user.getuName() + " uPass:" + user.getuPass());

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
			User fullUser = da.selectUserByUsername(user.getuName());
			if(fullUser == null) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			} else if (fullUser.getPassword().matches(user.getuPass())) {
				logger.log(Level.INFO, "doPost: user found, password matched...");

				ArrayList<Book> books = da.getAllBooks();
				ArrayList<Integer> ownedBooksId = da.getOwnedBookIds(user.getuName());
				ArrayList<Book> ownedBooks = new ArrayList<Book>();
				if (ownedBooksId.isEmpty()) {
					ownedBooksId.add(0); // ids start with 1, so 0 means no book
				}
				for (Book b : books) {
					for (Integer id : ownedBooksId) {
						if (b.getBookId() == id) {
							ownedBooks.add(b);
							break;
						}
					}
				}
				ArrayList<BookInfo> booksInfo = new ArrayList<BookInfo>();
				for (Book b : ownedBooks) {
					int likes = da.countLikesByBookId(b.getBookId());
					ArrayList<Review> reviews = da.selectReviewsByBookId(b.getBookId(),true);
					booksInfo.add(new BookInfo(b, likes, reviews));
				}
				String json = new Gson().toJson(booksInfo);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			} else {
				logger.log(Level.SEVERE, "doPost: user found, PASSWORD MISMACHED!!!");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
			da.closeConnection();

		} catch (SQLException | NamingException e) {
			// log error
			logger.log(Level.SEVERE, "doPost: FAILED");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}

}
