package org.bookstop.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
import org.bookstop.model.Like;
import org.bookstop.model.User;

import com.google.gson.Gson;

/**
 * Servlet implementation class RemoveLike
 */
@WebServlet("/RemoveLike")
public class RemoveLike extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RemoveLike() {
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
		System.out.println("RemoveLike Servlet");

		Logger logger = Logger.getLogger("RemoveLikeServlet");
		logger.log(Level.INFO, "doPost: Start...");

		Gson gson = new Gson();
		Like l = null;
		try {
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = request.getReader().readLine()) != null) {
				sb.append(s);
			}

			l = (Like) gson.fromJson(sb.toString(), Like.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (l == null) {
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

			User u = da.selectUserByUsername(l.getUsername());
			Book b = da.selectBookById(l.getBookid());

			if(u == null) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			} else if (b == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			} else if (da.likeExists(l) == false) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			} else {
				da.deleteLike(l);
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
