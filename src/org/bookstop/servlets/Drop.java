package org.bookstop.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
import org.bookstop.dataAccess.DataContract;

/**
 * Servlet implementation class Drop
 */
@WebServlet("/Drop")
public class Drop extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Drop() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger logger = Logger.getLogger("DropServlet");
		logger.log(Level.INFO, "doGet: attempting connection to DB...");
		try {

			// obtain CustomerDB data source from Tomcat's context
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource) context
					.lookup(getServletContext().getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
			Connection conn = ds.getConnection();
			logger.log(Level.INFO, "doGet: connection opened...");

			try {
				Statement stmt6 = conn.createStatement();
				stmt6.executeUpdate("DROP TABLE " + DataContract.ScrollPositionsTable.TABLE_NAME);
				// commit update
				conn.commit();
				stmt6.close();
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "Something went wrong" + e);
			}
			
			try {
				Statement stmt5 = conn.createStatement();
				stmt5.executeUpdate("DROP TABLE " + DataContract.TransactionsTable.TABLE_NAME);
				// commit update
				conn.commit();
				stmt5.close();
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "Something went wrong" + e);
			}

			try {
				Statement stmt4 = conn.createStatement();
				stmt4.executeUpdate("DROP TABLE " + DataContract.LikesTable.TABLE_NAME);
				// commit update
				conn.commit();
				stmt4.close();
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "Something went wrong" + e);
			}

			try {
				Statement stmt3 = conn.createStatement();
				stmt3.executeUpdate("DROP TABLE " + DataContract.ReviewsTable.TABLE_NAME);
				// commit update
				conn.commit();
				stmt3.close();
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "Something went wrong" + e);
			}

			try {
				Statement stmt2 = conn.createStatement();
				stmt2.executeUpdate("DROP TABLE " + DataContract.UsersTable.TABLE_NAME);
				// commit update
				conn.commit();
				stmt2.close();
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "Something went wrong" + e);
			}

			try {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("DROP TABLE " + DataContract.BooksTable.TABLE_NAME);
				// commit update
				conn.commit();
				stmt.close();
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "Something went wrong" + e);
			}
			conn.close();

		} catch (SQLException | NamingException e) {
			logger.log(Level.SEVERE, "Something went wrong");
		}
		
		logger.log(Level.INFO, "DONE<<<<<<<<<<<<<<<<<<<<<<<");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
