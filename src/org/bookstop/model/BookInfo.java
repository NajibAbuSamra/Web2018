package org.bookstop.model;

import java.util.ArrayList;

/**
 * A class used to give more information about a Book to the client side.
 * BookInfo holds the number of likes a book has, as well as a list of reviews for the book.
 * BookInfo fields are sent to the client side in json format when there is a request for a book.
 * @author najib
 * @see Book
 *
 */
public class BookInfo extends Book{
	private int likes;
	private ArrayList<Review> reviews;
	
	public BookInfo(int bookId, String name, String img, double price, String description, String link, int likes, ArrayList<Review> reviews) {
		super(bookId, name, img, price, description, link);
		this.setLikes(likes);
		this.reviews = new ArrayList<Review>();
		if(reviews != null) {
			for(Review r : reviews) {
				this.reviews.add(r);
			}
		}
		
	}

	public BookInfo(Book b, int likes, ArrayList<Review> reviews) {
		super(b);
		this.likes = likes;
		this.reviews = new ArrayList<Review>();
		if(reviews != null) {
			for(Review r : reviews) {
				this.reviews.add(r);
			}
		}
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public ArrayList<Review> getReviews() {
		return reviews;
	}
	
}
