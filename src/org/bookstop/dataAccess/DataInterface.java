package org.bookstop.dataAccess;

import org.bookstop.model.User;

public interface DataInterface {
	
	public User getUserByUsername(String username);

	public void closeConnection();
}
