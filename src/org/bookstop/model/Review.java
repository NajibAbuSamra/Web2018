package org.bookstop.model;

public class Review {
	private String username;
	private int bookID;
	private String text;
	private int verified;
	
	public Review(String username, int bookID, int verified, String text) {
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
	public int isApproved() {
		return verified;
	}
	public void setApproved(int verified) {
		this.verified = verified;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
