package org.bookstop.model;

public class Review {
	private int id;
	private String username;
	private int bookID;
	private String text;
	private int verified;

	public Review(int id, String username, int bookID, int verified, String text) {
		super();
		this.id = id;
		this.username = username;
		this.bookID = bookID;
		this.verified = verified;
		this.text = text;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public int getVerified() {
		return verified;
	}

	public void setVerified(int verified) {
		this.verified = verified;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
