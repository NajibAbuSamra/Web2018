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
import org.bookstop.model.Review;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetUnapprovedReviewsForBook
 */
@WebServlet("/GetUnapprovedReviewsForBook")
public class GetUnapprovedReviewsForBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetUnapprovedReviewsForBook() {
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
		System.out.println("GetUnapprovedReviewsForBook Servlet");

		Logger logger = Logger.getLogger("GetUnapprovedReviewsForBookServlet");
		logger.log(Level.INFO, "doPost: Start...");

		Gson gson = new Gson();
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
		if (bookid.getBookid() < 0) {
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
				ArrayList<Review> reviews = da.selectReviewsByBookId(bookid.getBookid(), false);

				String json = new Gson().toJson(reviews);
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
