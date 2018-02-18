package org.bookstop.model;

import java.util.ArrayList;

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
