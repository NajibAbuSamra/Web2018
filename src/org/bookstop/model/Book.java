package org.bookstop.model;
/**
 * This class is used to handle book data stored in the database.
 * Book model is NOT sent in JSON responses from the server, as when we designed
 * the system a book would also provide information on number of likes and reviews
 * it had, which is why we created BooksInfo class.
 * @author najib
 * @see BooksInfo
 *
 */
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

	public Book(Book b) {
		super();
		this.bookId = b.getBookId();
		this.name = b.getName();
		this.img = b.getImg();
		this.price = b.getPrice();
		this.description = b.getDescription();
		this.link = b.getLink();
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
