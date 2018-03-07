package org.bookstop.dataAccess;

import java.util.ArrayList;

import org.bookstop.model.Book;
import org.bookstop.model.Like;
import org.bookstop.model.Review;
import org.bookstop.model.ScrollPosition;
import org.bookstop.model.Transaction;
import org.bookstop.model.User;

public interface DataInterface {
	
	//Book related methods
	
	/**
	 * Returns a Book from the database that matches the given id.
	 * @param id	ID number of the Book as assigned by the database Books Table
	 * @return		The Book that matches the given id, or null if not found.
	 * @see			SQLstatements
	 * @see			Book
	 */
	public Book selectBookById(int id);
	
	/**
	 * @deprecated 	As we don't use this, since an Admin shouldn't be able to buy books.
	 * @return		ArrayList of all Books in the database.
	 */
	public ArrayList<Book> getAllBooks();
	
	//User related methods
	/**
	 * Inserts a User into the database. Validity checks are done prior, such as username
	 * exists, a required field is null, etc.
	 * @param user	A User with all required fields, to be entered into the database.
	 */
	public void insertUser(User user);
	
	/**
	 * Returns a User from the databse (if exists) that matches the username given, or null in
	 * case no such User was found.
	 * @param username	that username of the user we want to select from the Users table.
	 * @return 			User from the Users table in the database that matches the given
	 * 					username, or null in case no such user was found.
	 */
	public User selectUserByUsername(String username);
	
	/**
	 * Returns all Users currently in the database that are not of type Admin.
	 * @return		ArrayList of all Users that are not of type Admin
	 * @see			AppConstants
	 */
	public ArrayList<User> getAllUsers();
	
	/**
	 * Removes a User from the database. Only an Admin should be calling the servlet that 
	 * calls this method, these checks are done in said servlet(s).
	 * @param username	the username of the User we want to delete.
	 */
	public void deleteUser(String username);
	
	//Review related methods
	/**
	 * Adds a Review to the database. Validity checks are done prior, such that
	 * required fields are not null, etc.
	 * @param review	Review to be inserted into the database
	 */
	public void insertReview(Review review);
	
	/**
	 * Updates the verified field of a Review with the given verified value.
	 * This allows also to "unverify" a Review, and supports future verification values (for
	 * future development).
	 * @param verified	value to be set for the verified field of a Review
	 * @param id		id of the review we want to update
	 * @see				Review
	 */
	public void updateVerifiedReview(int verified, int id);
	
	/**
	 * Removes a review from the database that was written by User with given username
	 * on a Book with given bookId. Check that such a Review exists in the database
	 * is done prior. 
	 * @param username	of a User
	 * @param bookId	of a Book
	 */
	public void deleteReviewByUsernameAndBookId(String username, int bookId);
	
	/**
	 * Returns all Reviews of a Book that matches given bookid, and that are approved
	 * or not, depending on the boolean value given.
	 * @param bookid	id of the Book.
	 * @param approved	status of Reviews we want.
	 * @return			ArrayList of Reviews as explained above.
	 */
	public ArrayList<Review> selectReviewsByBookId(int bookid, boolean approved);
	
	/**
	 * Returns a review from the database that matches the given id, if exists.
	 * @param id	of the review we want
	 * @return		Review with matched id, null otherwise
	 */
	public Review selectReviewById(int id);
	
	//Like related methods
	/**
	 * Adds a Like to the database. Validity checks are done prior, such as that an 
	 * identical doesn't exist in the Likes Table, that required fields are not null, etc. 
	 * @param like	the Like with all required information to be added to the database
	 */
	public void insertLike(Like like);
	
	/**
	 * Removes a Like from the database. Validity checks are done prior, such as Like exists,
	 * given Like has all required fields, etc.
	 * @param like	Like with required information.
	 */
	public void deleteLike(Like like);
	
	/**
	 * Check to see if a Like with given information exists in the Likes Table
	 * @param like	Like with information we want to check if exists.
	 * @return		true if such a Like exists, false otherwise.
	 */
	public boolean likeExists(Like like);
	
	/**
	 * Returns number of likes a Book has.
	 * @param bookid	id of a Book.
	 * @return			number of likes for a Book that matched given bookid.
	 */
	public int countLikesByBookId(int bookid);
	
	/**
	 * Returns the usernames of Users that liked a Book with given bookid
	 * @param bookid	id of a Book.
	 * @return			ArrayList of usernames.
	 */
	public ArrayList<String> selectUsernameFromLikesByBookId(int bookid);
	
	//Transaction related methods
	/**
	 * Adds a Transaction into the database. Validity checks are done prior.
	 * @param transaction
	 */
	public void insertTransaction(Transaction transaction);
	/**
	 * Returns all Transactions that exist in the database.
	 * CURRENTLY NOT IN USE, since we display transactions by Book, and not all Books at once.
	 * @return	all Transactions in the database.
	 */
	public ArrayList<Transaction> selectAllTransactions();
	
	/**
	 * Returns a list of all Transactions made by User with given username.
	 * CURRENTLY NOT IN USE.
	 * @param username	of a User.
	 * @return			ArrayList of all Transactions of said User.
	 */
	public ArrayList<Transaction> selectTransactionsByUsername(String username);
	
	/**
	 * Returns a list of all Transactions for a Book with given bookid.
	 * @param bookid	id of a Book.
	 * @return			ArrayList of all Transactions made for a Book with given bookid.
	 */
	public ArrayList<Transaction> selectTransactionsByBookid(int bookid);
	
	/**
	 * Returns a Transaction that was made by a User with username for a Book with bookid.
	 * @param username	of a User.
	 * @param bookid	id of a Book.
	 * @return			Transaction that matches given parameters, null otherwise.
	 */
	public Transaction selectTransactionByUsernameAndBookid(String username, int bookid);
	
	/**
	 * Returns all Book ids that have a matching username from the Transactions Table.
	 * @param username
	 * @return			ArrayList of the Book ids that were bought by a User
	 * 					with a matching username.
	 * @see				SQLstatements
	 * @see				Transaction
	 */
	public ArrayList<Integer> getOwnedBookIds(String username);
	
	//ScrollPosition related methods
	/**
	 * Adds a ScrollPosition to the database. If append is set to true, update an existing
	 * entry. Validity checks are done prior.
	 * @param pos		ScrollPosition to be inserted.
	 * @param append	flag, if false, inserts given pos as a new entry, otherwise updates 
	 * 					an existing one.
	 */
	public void insertScrollPosition(ScrollPosition pos, boolean append);
	
	/**
	 * Returns stored Ypos (number of pixels from top) from a ScrollPosition entry with matching
	 * username and bookid.
	 * @param username	of a User.
	 * @param bookid	id of a Book.
	 * @return			Ypos value from a matching entry (only one exists at most.)
	 */
	public int selectYposByUsernameAndBookid(String username, int bookid);
	
	//Util
	/**
	 * Used to close an open connection. Should be used to maintain correct connection pool
	 * management within Tomacat.
	 */
	public void closeConnection();
}
