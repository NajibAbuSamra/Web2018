package org.bookstop.dataAccess;

import java.util.ArrayList;

import org.bookstop.model.Book;
import org.bookstop.model.Like;
import org.bookstop.model.Review;
import org.bookstop.model.ScrollPosition;
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
	public void deleteUser(String username);
	
	//Reviews
	public void insertReview(Review review);
	public void updateVerifiedReview(int verified);
	public void deleteReviewByUsernameAndBookId(String username, int bookId);
	public ArrayList<Review> selectReviewsByBookId(int bookid);
	
	//Likes
	public void insertLike(Like like);
	public void deleteLike(Like like);
	public int countLikesByBookId(int bookid);
	public ArrayList<String> selectUsernameFromLikesByBookId(int bookid);
	
	//Transactions
	public void insertTransaction(Transaction transaction);
	public ArrayList<Transaction> selectAllTransactions();
	public ArrayList<Transaction> selectTransactionsByUsername(String username);
	public ArrayList<Transaction> selectTransactionsByBookid(int bookid);
	public Transaction selectTransactionByUsernameAndBookid(String username, int bookid);
	
	//ScrollPositions
	public void insertScrollPosition(ScrollPosition pos, boolean append);
	public int selectYposByUsernameAndBookid(String username, int bookid);
	
	//Util
	public void closeConnection();
}
