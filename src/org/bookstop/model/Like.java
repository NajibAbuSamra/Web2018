package org.bookstop.model;

/**
 * This class is used to handle like data stored in the database.
 * Like fields are received and sent in json requests/responds from the client.
 * @author najib
 *
 */
public class Like {
	String username;
	int bookid;
	
	public Like(String username, int bookid) {
		super();
		this.username = username;
		this.bookid = bookid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	
}
