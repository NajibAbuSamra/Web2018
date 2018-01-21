package org.bookstop.constants;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.reflect.TypeToken;

import org.bookstop.model.*;

public interface AppConstants {
	public final String BOOKS = "books";
	public final String BOOKS_FILE = BOOKS + ".json";
	public final String NAME = "name";
	public final Type BOOK_COLLECTION = new TypeToken<Collection<Book>>() {}.getType();
	//derby constants
	//TODO: might need to change DB_NAME and DB_DATASOURCE
	public final String DB_NAME = "DB_NAME";
	public final String DB_DATASOURCE = "DB_DATASOURCE";
	public final String PROTOCOL = "jdbc:derby:"; 
	public final String OPEN = "Open";
	public final String SHUTDOWN = "Shutdown";
}
