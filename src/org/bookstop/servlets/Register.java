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
import org.bookstop.model.User;

import com.google.gson.Gson;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
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
		Logger logger = Logger.getLogger("RegisterServlet");
		logger.log(Level.INFO, "doPost: attempting connection to DB...");

		Gson gson = new Gson();
		User user = null;
		try {
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = request.getReader().readLine()) != null) {
				sb.append(s);
			}

			user = (User) gson.fromJson(sb.toString(), User.class);
			logger.log(Level.INFO, "doPost: user info: username:" + user.getUsername() + " type:" + user.getType());

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

			User temp = null;
			if (user.getUsername() == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			temp = da.selectUserByUsername(user.getUsername());

			if (temp != null) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN); // user exists with that username
				return;
			}

			if (user.getNickname() == null || user.getNickname().isEmpty() || user.getNickname().length() > 20
					|| user.getPassword() == null || user.getPassword().isEmpty() || user.getPassword().length() > 8
					|| user.getEmail() == null
					|| ((user.getEmail().isEmpty() == false) && (user.getEmail().contains("@") == false))) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			da.insertUser(user);
			da.closeConnection();

		} catch (SQLException | NamingException e) {
			// log error
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			logger.log(Level.SEVERE, "doPost: FAILED");
			e.printStackTrace();
		}
		return; // By default the response will be 200 "OK"
	}

}
