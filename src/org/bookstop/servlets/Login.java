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
import org.bookstop.model.UserLogin;

import com.google.gson.Gson;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uName = request.getParameter("uName");
		String uPass = request.getParameter("uPass");
		Logger logger = Logger.getLogger("LoginServlet");
		logger.log(Level.INFO, "doGet: attempting connection to DB...");
		try {

			// obtain CustomerDB data source from Tomcat's context
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource) context
					.lookup(getServletContext().getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
			Connection conn = ds.getConnection();
			logger.log(Level.INFO, "doGet: connection opened...");
			DA da = new DA(conn);
			User user = da.selectUserByUsername(uName);
			if (user == null) {
				logger.log(Level.WARNING, "doGet: user NOT found");
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			} else if (user.getPassword().matches(uPass)) {
				logger.log(Level.INFO, "doGet: user found, password matched...");
				String json = new Gson().toJson(user);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			} else {
				logger.log(Level.WARNING, "doGet: user found, password MISMATCHED!!!");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			da.closeConnection();
			if (conn.isClosed() == false) {
				logger.log(Level.WARNING, "doPost: connection not closed after DA method, closing manually");
				conn.close();
			}
		} catch (SQLException | NamingException e) {
			// log error
			logger.log(Level.SEVERE, "doGet: FAILED");

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Login Servlet");

		Logger logger = Logger.getLogger("LoginServlet");
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
			User fullUser = da.selectUserByUsername(user.getuName());
			if (fullUser == null) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
			else if (fullUser.getPassword().matches(user.getuPass())) {
				logger.log(Level.INFO, "doPost: user found, password matched...");
				String json = new Gson().toJson(fullUser);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}
			else {
				logger.log(Level.WARNING, "doPost: user found, password MISMATCHED!!!");
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
			da.closeConnection();

		} catch (SQLException | NamingException e) {
			// log error
			logger.log(Level.SEVERE, "doGet: FAILED");

		}

	}

}
