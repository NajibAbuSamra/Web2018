package org.bookstop.dataAccess;

import java.util.ArrayList;

import org.bookstop.model.Book;
import org.bookstop.model.Like;
import org.bookstop.model.Review;
import org.bookstop.model.Transaction;
import org.bookstop.model.User;

public interface DataInterface {
	
	//Book
	public Book selectBookById(int id);
	public ArrayList<Book> getAllBooks();
	public ArrayList<Integer> getOwnedBookIds(String username);
	
	//User
	public void insertUser(User user);
	public User selectUserByUsername(String username);
	public ArrayList<User> getAllUsers();
	
	//Reviews
	public void insertReview(Review review);
	public void updateVerifiedReview(int verified);
	public void deleteReviewByUsernameAndBookId(String username, int bookId);
	
	//Likes
	public void insertLike(Like like);
	public void deleteLike(Like like);
	
	//Transactions
	public void insertTransaction(Transaction transaction);
	public ArrayList<Transaction> selectAllTransactions();
	public ArrayList<Transaction> selectTransactionsByUsername(String username);
	
	//Util
	public void closeConnection();
}
