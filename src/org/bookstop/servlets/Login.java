package org.bookstop.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
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
			User user = da.getUserByUsername(uName);
			if (user.getPassword().matches(uPass)) {
				logger.log(Level.INFO, "doGet: user found, password matched...");
				String json = new Gson().toJson(user);
			    response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().write(json);
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
			while((s = request.getReader().readLine()) != null) {
				sb.append(s);
			}
			
			user = (UserLogin) gson.fromJson(sb.toString(), UserLogin.class);
			logger.log(Level.INFO, "doGet: user info: uName:"+user.getuName()+" uPass:"+user.getuPass());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(user == null) {
			//TODO: check and handle error
			return;
		}
		try {

			// obtain CustomerDB data source from Tomcat's context
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource) context
					.lookup(getServletContext().getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
			Connection conn = ds.getConnection();
			logger.log(Level.INFO, "doGet: connection opened...");
			DA da = new DA(conn);
			User u = da.getUserByUsername(user.getuName());
			if (u.getPassword().matches(user.getuPass())) {
				logger.log(Level.INFO, "doGet: user found, password matched...");
				String json = new Gson().toJson(user);
			    response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().write(json);
			}
			
		} catch (SQLException | NamingException e) {
			// log error
			logger.log(Level.SEVERE, "doGet: FAILED");

		}
		
	}

}
