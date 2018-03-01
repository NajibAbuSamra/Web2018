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
import org.bookstop.model.ScrollPosition;
import org.bookstop.model.User;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetScrollPosition
 */
@WebServlet("/GetScrollPosition")
public class GetScrollPosition extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetScrollPosition() {
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
		Logger logger = Logger.getLogger("GetScrollPosition");
		logger.log(Level.INFO, "doPost: attempting connection to DB...");

		Gson gson = new Gson();
		ScrollPosition scrollPosition = null;
		try {
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = request.getReader().readLine()) != null) {
				sb.append(s);
			}

			scrollPosition = (ScrollPosition) gson.fromJson(sb.toString(), ScrollPosition.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			// obtain CustomerDB data source from Tomcat's context
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource) context
					.lookup(getServletContext().getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
			Connection conn = ds.getConnection();
			logger.log(Level.INFO, "doPost: connection opened...");
			DA da = new DA(conn);

			scrollPosition.setYpos(
					da.selectYposByUsernameAndBookid(scrollPosition.getUsername(), scrollPosition.getBookid()));

			if (scrollPosition.getYpos() == -1) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND); // no ypos saved/found
				return;
			} else {
				String json = new Gson().toJson(scrollPosition.getYpos());
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}
			da.closeConnection();
			if (conn.isClosed() == false) {
				logger.log(Level.WARNING, "doPost: connection not closed after DA method, closing manually");
				conn.close();
			}

		} catch (SQLException | NamingException e) {
			// log error
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			logger.log(Level.SEVERE, "doPost: FAILED");
			e.printStackTrace();
			// TODO: handle errors
		}
	}

}
