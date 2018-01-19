package org.bookstop.model;

public class Book {
	private int bookId;
	private String name;
	private String img;
	private double price;
	private String description;
	private String link;
	
	

	public Book(int bookId, String name, String img, double price, String description, String link) {
		super();
		this.bookId = bookId;
		this.name = name;
		this.img = img;
		this.price = price;
		this.description = description;
		this.link = link;
	}

	public int getBookId() {
		return bookId;
	}
	
	public String getName() {
		return name;
	}

	public String getImg() {
		return img;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLink() {
		return link;
	}
	
	
}
