package org.bookstop.model;

public class ScrollPosition {
	String username;
	int bookid;
	int ypos;
	public ScrollPosition(String username, int bookid, int ypos) {
		super();
		this.username = username;
		this.bookid = bookid;
		this.ypos = ypos;
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
	public int getYpos() {
		return ypos;
	}
	public void setYpos(int ypos) {
		this.ypos = ypos;
	}
	
	
}
