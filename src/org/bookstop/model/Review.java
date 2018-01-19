package org.bookstop.model;

public class Review {
	private String username;
	private int bookID;
	private String text;
	private boolean verified;
	
	public Review(String username, int bookID, boolean verified, String text) {
		super();
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
	public boolean isApproved() {
		return verified;
	}
	public void setApproved(boolean verified) {
		this.verified = verified;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
