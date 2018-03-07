package org.bookstop.constants;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.reflect.TypeToken;

import org.bookstop.model.*;

public interface AppConstants {
	public final String BOOKS = "books";
	public final String BOOKS_FILE = BOOKS + ".json";
	public final String USERS = "users";
	public final String USERS_FILE = USERS + ".json";
	public final String LIKES = "likes";
	public final String LIKES_FILE = LIKES + ".json";
	public final String REVIEWS = "reviews";
	public final String REVIEWS_FILE = REVIEWS + ".json";
	public final String NAME = "name";
	public final Type BOOK_COLLECTION = new TypeToken<Collection<Book>>() {}.getType();
	public final Type USER_COLLECTION = new TypeToken<Collection<User>>() {}.getType();
	public final Type REVIEW_COLLECTION = new TypeToken<Collection<Review>>() {}.getType();
	public final Type LIKE_COLLECTION = new TypeToken<Collection<Like>>() {}.getType();
	//derby constants
	//TODO: might need to change DB_NAME and DB_DATASOURCE
	public final String DB_NAME = "DB_NAME";
	public final String DB_DATASOURCE = "DB_DATASOURCE";
	public final String PROTOCOL = "jdbc:derby:"; 
	public final String OPEN = "Open";
	public final String SHUTDOWN = "Shutdown";
	
	public final int USERTYPE_USER = 0;
	public final int USERTYPE_ADMIN = 1;
	
}
