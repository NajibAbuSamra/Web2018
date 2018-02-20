package org.bookstop.model;

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
