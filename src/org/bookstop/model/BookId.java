package org.bookstop.model;

/**
 * Helper class used to handle sent book ids from client.
 * During the work on the servlets we had difficulties working with Gson,
 * specifically converting basic types from json, so this class was used
 * to convert bookid from json.
 * @author najib
 *
 */
public class BookId {
	int bookid;

	public BookId(int bookid) {
		super();
		this.bookid = bookid;
	}

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	
	
}
