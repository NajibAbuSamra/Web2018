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
import org.bookstop.model.Like;
import org.bookstop.model.User;

import com.google.gson.Gson;

/**
 * Servlet implementation class RemoveUser
 */
@WebServlet("/RemoveUser")
public class RemoveUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("RemoveUser Servlet");

		Logger logger = Logger.getLogger("RemoveUserServlet");
		logger.log(Level.INFO, "doPost: Start...");

		Gson gson = new Gson();
		User u = null;
		try {
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = request.getReader().readLine()) != null) {
				sb.append(s);
			}

			u = (User) gson.fromJson(sb.toString(), User.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (u == null) {
			logger.log(Level.SEVERE, "doPost: USER FROM JSON IS NULL!!!!!");
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

			User temp = da.selectUserByUsername(u.getUsername());

			if (temp == null) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			} else {
				da.deleteUser(u.getUsername());
			}

			da.closeConnection();
			if (conn.isClosed() == false) {
				conn.close();
			}
		} catch (SQLException | NamingException e) {
			// log error
			logger.log(Level.SEVERE, "doPost: FAILED");

		}
	}

}
