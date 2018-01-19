package org.bookstop.dataAccess;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class DA implements DataInterface{

	Connection conn = null;

	public DA() {
		// obtain CustomerDB data source from Tomcat's context
		Context context;
		try {
			context = new InitialContext();
			BasicDataSource ds;
			ds = (BasicDataSource) context.lookup("java:comp/env/jdbc/BookStopDatasource");
			conn = ds.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// use connection as you wishÅcbut close after usage! (this
		// is important for correct connection pool management
		// within Tomcat
	}

	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
