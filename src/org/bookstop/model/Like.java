package org.bookstop.model;

public class Like {
	String username;
	int bookId;
	
	public Like(String username, int bookId) {
		super();
		this.username = username;
		this.bookId = bookId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	
}
