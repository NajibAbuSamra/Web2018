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
import org.bookstop.model.Transaction;
import org.bookstop.model.User;
import org.bookstop.model.UserLogin;

import com.google.gson.Gson;

/**
 * Servlet implementation class BuyBook
 */
@WebServlet("/BuyBook")
public class BuyBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BuyBook() {
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
		System.out.println("BuyBooks Servlet");

		Logger logger = Logger.getLogger("BuyBookServlet");
		logger.log(Level.INFO, "doPost: Start...");

		Gson gson = new Gson();
		Transaction t = null;
		try {
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = request.getReader().readLine()) != null) {
				sb.append(s);
			}

			t = (Transaction) gson.fromJson(sb.toString(), Transaction.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (t == null) {
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

			User u = da.selectUserByUsername(t.getUsername());

			if (u == null) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			} else {
				Transaction temp = da.selectTransactionByUsernameAndBookid(t.getUsername(), t.getBookID());
				if (temp != null) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				} else {
					da.insertTransaction(t);
				}
			}

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
