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
import org.bookstop.model.BookId;
import org.bookstop.model.ReviewId;
import org.bookstop.model.User;

import com.google.gson.Gson;

/**
 * Servlet implementation class VerifyReview
 */
@WebServlet("/VerifyReview")
public class VerifyReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyReview() {
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
		System.out.println("VerifyReview Servlet");

		Logger logger = Logger.getLogger("VerifyReviewServlet");
		logger.log(Level.INFO, "doPost: Start...");

		Gson gson = new Gson();
		//TODO: try to user Book model, maybe it will partially fill the information, no need for the BookId model
		ReviewId id = null;
		try {
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = request.getReader().readLine()) != null) {
				sb.append(s);
			}
			id = (ReviewId) gson.fromJson(sb.toString(), ReviewId.class);
			logger.log(Level.INFO, "doPost: bookId: " + id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (id == null || id.getReviewid() == 0) {
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

			//TODO: check if there is a review with such id
			
			da.updateVerifiedReview(1, id.getReviewid());
			
			da.closeConnection();

		} catch (SQLException | NamingException e) {
			// log error
			logger.log(Level.SEVERE, "doPost: FAILED");

		}
	}

}
