package org.bookstop.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bookstop.constants.SQLstatements;
import org.bookstop.model.User;

public class DA implements DataInterface{

	Connection conn = null;

	public DA(Connection conn) {
		this.conn = conn;
		// use connection as you wish but close after usage! (this
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

	public User getUserByUsername(String username) {
		User user = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.SELECT_USER_BY_USERNAME_STMT);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				user = new User(rs.getString(DataContract.UsersTable.COL_USERNAME),
						rs.getString(DataContract.UsersTable.COL_EMAIL),
						rs.getString(DataContract.UsersTable.COL_ADDRESS),
						rs.getString(DataContract.UsersTable.COL_PHONE),
						rs.getString(DataContract.UsersTable.COL_PASSWORD),
						rs.getString(DataContract.UsersTable.COL_NICKNAME),
						rs.getString(DataContract.UsersTable.COL_DESCRIPTION),
						rs.getString(DataContract.UsersTable.COL_PICTURE),
						rs.getInt(DataContract.UsersTable.COL_TYPE));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

}
