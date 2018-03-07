package org.bookstop.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bookstop.constants.SQLstatements;
import org.bookstop.model.Book;
import org.bookstop.model.Like;
import org.bookstop.model.Review;
import org.bookstop.model.ScrollPosition;
import org.bookstop.model.Transaction;
import org.bookstop.model.User;

public class DA implements DataInterface {

	Connection conn = null;

	/**
	 * C'tor for DA (DataAccess) class, that takes a connection that was opened in the servlet.
	 * The connection is passed from the servlet because of context issues.
	 * @param conn	connection to the derby database.
	 */
	public DA(Connection conn) {
		this.conn = conn;
		// use connection as you wish but close after usage! (this
		// is important for correct connection pool management
		// within Tomcat
	}

	/**
	 * {@inheritDoc}
	 */
	public void closeConnection() {
		try {
			if(conn.isClosed() == false)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User selectUserByUsername(String username) {
		User user = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.SELECT_USER_BY_USERNAME_STMT);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new User(rs.getString(DataContract.UsersTable.COL_USERNAME),
						rs.getString(DataContract.UsersTable.COL_EMAIL),
						rs.getString(DataContract.UsersTable.COL_ADDRESS),
						rs.getString(DataContract.UsersTable.COL_PHONE),
						rs.getString(DataContract.UsersTable.COL_PASSWORD),
						rs.getString(DataContract.UsersTable.COL_NICKNAME),
						rs.getString(DataContract.UsersTable.COL_DESCRIPTION),
						rs.getString(DataContract.UsersTable.COL_PICTURE), rs.getInt(DataContract.UsersTable.COL_TYPE));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertUser(User user) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.INSERT_USER_STMT);
			pstmt.setInt(1, user.getType());
			pstmt.setString(2, user.getUsername());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getAddress());
			pstmt.setString(5, user.getPhone());
			pstmt.setString(6, user.getPassword());
			pstmt.setString(7, user.getNickname());
			pstmt.setString(8, user.getDescription());
			pstmt.setString(9, user.getPicture());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<User>();

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.SELECT_ALL_USERS_STMT);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				users.add(new User(rs.getString(DataContract.UsersTable.COL_USERNAME),
						rs.getString(DataContract.UsersTable.COL_EMAIL),
						rs.getString(DataContract.UsersTable.COL_ADDRESS),
						rs.getString(DataContract.UsersTable.COL_PHONE),
						rs.getString(DataContract.UsersTable.COL_PASSWORD),
						rs.getString(DataContract.UsersTable.COL_NICKNAME),
						rs.getString(DataContract.UsersTable.COL_DESCRIPTION),
						rs.getString(DataContract.UsersTable.COL_PICTURE),
						rs.getInt(DataContract.UsersTable.COL_TYPE)));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteUser(String username) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.DELETE_USER_BY_USERNAME_STMT);
			pstmt.setString(1, username);
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Book selectBookById(int id) {
		Book book = null;

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.SELECT_BOOKS_BY_ID_STMT);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				book = new Book(rs.getInt(DataContract.BooksTable.COL_ID),
						rs.getString(DataContract.BooksTable.COL_NAME), rs.getString(DataContract.BooksTable.COL_IMG),
						rs.getDouble(DataContract.BooksTable.COL_PRICE),
						rs.getString(DataContract.BooksTable.COL_DESCRIPTION),
						rs.getString(DataContract.BooksTable.COL_LINK));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return book;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Book> getAllBooks() {
		ArrayList<Book> books = new ArrayList<Book>();

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.SELECT_ALL_BOOKS_STMT);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				books.add(new Book(rs.getInt(DataContract.BooksTable.COL_ID),
						rs.getString(DataContract.BooksTable.COL_NAME), rs.getString(DataContract.BooksTable.COL_IMG),
						rs.getDouble(DataContract.BooksTable.COL_PRICE),
						rs.getString(DataContract.BooksTable.COL_DESCRIPTION),
						rs.getString(DataContract.BooksTable.COL_LINK)));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return books;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertReview(Review review) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.INSERT_REVIEW_STMT);
			pstmt.setString(1, review.getUsername());
			pstmt.setString(2, review.getNickname());
			pstmt.setInt(3, review.getBookID());
			pstmt.setString(4, review.getText());
			pstmt.setInt(5, Review.UNVERIFIED);
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateVerifiedReview(int verified, int id) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.UPDATE_REVIEW_VERIFIED_BY_ID_STMT);
			pstmt.setInt(1, verified);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteReviewByUsernameAndBookId(String username, int bookId) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.DELETE_REVIEW_BY_USERNAME_AND_BOOKID_STMT);
			pstmt.setString(1, username);
			pstmt.setInt(2, bookId);
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertLike(Like like) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.INSERT_LIKE_STMT);
			pstmt.setString(1, like.getUsername());
			pstmt.setInt(2, like.getBookid());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteLike(Like like) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.DELETE_LIKE_BY_USERNAME_AND_BOOKID_STMT);
			pstmt.setString(1, like.getUsername());
			pstmt.setInt(2, like.getBookid());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertTransaction(Transaction transaction) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.INSERT_TRANSACTION_STMT);
			pstmt.setString(1, transaction.getUsername());
			pstmt.setInt(2, transaction.getBookID());
			pstmt.setString(3, transaction.getCardCompany());
			pstmt.setString(4, transaction.getCardNumber());
			pstmt.setInt(5, transaction.getExpiryMonth());
			pstmt.setInt(6, transaction.getExpiryYear());
			pstmt.setString(7, transaction.getCvv());
			pstmt.setString(8, transaction.getFullName());
			pstmt.setString(9, transaction.getAddress());

			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Transaction> selectAllTransactions() {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.SELECT_ALL_TRANSACTIONS);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				transactions.add(new Transaction(rs.getString(DataContract.TransactionsTable.COL_USERNAME),
						rs.getInt(DataContract.TransactionsTable.COL_BOOKID),
						rs.getString(DataContract.TransactionsTable.COL_CARDCOMPANY),
						rs.getString(DataContract.TransactionsTable.COL_CARDNUMBER),
						rs.getInt(DataContract.TransactionsTable.COL_EXPIRYMONTH),
						rs.getInt(DataContract.TransactionsTable.COL_EXPIRYYEAR),
						rs.getString(DataContract.TransactionsTable.COL_CVV),
						rs.getString(DataContract.TransactionsTable.COL_FULLNAME),
						rs.getString(DataContract.TransactionsTable.COL_ADDRESS)));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return transactions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Transaction> selectTransactionsByUsername(String username) {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.SELECT_TRANSACTIONS_BY_USERNAME_STMT);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				transactions.add(new Transaction(rs.getString(DataContract.TransactionsTable.COL_USERNAME),
						rs.getInt(DataContract.TransactionsTable.COL_BOOKID),
						rs.getString(DataContract.TransactionsTable.COL_CARDCOMPANY),
						rs.getString(DataContract.TransactionsTable.COL_CARDNUMBER),
						rs.getInt(DataContract.TransactionsTable.COL_EXPIRYMONTH),
						rs.getInt(DataContract.TransactionsTable.COL_EXPIRYYEAR),
						rs.getString(DataContract.TransactionsTable.COL_CVV),
						rs.getString(DataContract.TransactionsTable.COL_FULLNAME),
						rs.getString(DataContract.TransactionsTable.COL_ADDRESS)));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return transactions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Integer> getOwnedBookIds(String username) {
		ArrayList<Integer> arr = new ArrayList<Integer>();

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.SELECT_TRANSACTIONS_BOOKID_BY_USERNAME_STMT);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				arr.add(rs.getInt(DataContract.TransactionsTable.COL_BOOKID));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arr;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int countLikesByBookId(int bookid) {
		int likes = -1; // default error
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.COUNT_LIKES_BY_BOOKID_STMT);
			pstmt.setInt(1, bookid);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				likes = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return likes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Review> selectReviewsByBookId(int bookid, boolean approved) {
		ArrayList<Review> reviews = new ArrayList<Review>();

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.SELECT_REVIEWS_UNVERIFIED_BY_BOOKID_STMT);
			pstmt.setInt(1, bookid);
			if(approved) {
				pstmt = conn.prepareStatement(SQLstatements.SELECT_REVIEWS_VERIFIED_BY_BOOKID_STMT);
				pstmt.setInt(1, bookid);
			}
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				reviews.add(new Review(rs.getInt(DataContract.ReviewsTable.COL_ID),
						rs.getString(DataContract.ReviewsTable.COL_USERNAME),
						rs.getString(DataContract.ReviewsTable.COL_NICKNAME),
						rs.getInt(DataContract.ReviewsTable.COL_BOOKID),
						rs.getInt(DataContract.ReviewsTable.COL_VERIFIED),
						rs.getString(DataContract.ReviewsTable.COL_TEXT)));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reviews;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<String> selectUsernameFromLikesByBookId(int bookid) {
		ArrayList<String> likers = new ArrayList<String>();

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.SELECT_LIKE_USERNAMES_BY_BOOKID_STMT);
			pstmt.setInt(1, bookid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				likers.add(rs.getString(1));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return likers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Transaction> selectTransactionsByBookid(int bookid) {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.SELECT_TRANSACTIONS_BY_BOOKID_STMT);
			pstmt.setInt(1, bookid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				transactions.add(new Transaction(rs.getString(DataContract.TransactionsTable.COL_USERNAME),
						rs.getInt(DataContract.TransactionsTable.COL_BOOKID),
						rs.getString(DataContract.TransactionsTable.COL_CARDCOMPANY),
						rs.getString(DataContract.TransactionsTable.COL_CARDNUMBER),
						rs.getInt(DataContract.TransactionsTable.COL_EXPIRYMONTH),
						rs.getInt(DataContract.TransactionsTable.COL_EXPIRYYEAR),
						rs.getString(DataContract.TransactionsTable.COL_CVV),
						rs.getString(DataContract.TransactionsTable.COL_FULLNAME),
						rs.getString(DataContract.TransactionsTable.COL_ADDRESS)));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return transactions;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Transaction selectTransactionByUsernameAndBookid(String username, int bookid) {
		Transaction t = null;

		try {
			PreparedStatement pstmt = conn
					.prepareStatement(SQLstatements.SELECT_TRANSACTION_BY_USERNAME_AND_BOOKID_STMT);
			pstmt.setString(1, username);
			pstmt.setInt(2, bookid);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				t = new Transaction(rs.getString(DataContract.TransactionsTable.COL_USERNAME),
						rs.getInt(DataContract.TransactionsTable.COL_BOOKID),
						rs.getString(DataContract.TransactionsTable.COL_CARDCOMPANY),
						rs.getString(DataContract.TransactionsTable.COL_CARDNUMBER),
						rs.getInt(DataContract.TransactionsTable.COL_EXPIRYMONTH),
						rs.getInt(DataContract.TransactionsTable.COL_EXPIRYYEAR),
						rs.getString(DataContract.TransactionsTable.COL_CVV),
						rs.getString(DataContract.TransactionsTable.COL_FULLNAME),
						rs.getString(DataContract.TransactionsTable.COL_ADDRESS));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return t;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertScrollPosition(ScrollPosition pos, boolean append) {
		try {
			PreparedStatement pstmt = null;
			if (!append) {
				pstmt = conn.prepareStatement(SQLstatements.INSERT_SCROLLPOSITION_STMT);
				pstmt.setString(1, pos.getUsername());
				pstmt.setInt(2, pos.getBookid());
				pstmt.setInt(3, pos.getYpos());
			}
			if (append) {
				pstmt = conn.prepareStatement(SQLstatements.UPDATE_YPOS_BY_USERNAME_AND_BOOKID_STMT);
				pstmt.setInt(1, pos.getYpos());
				pstmt.setString(2, pos.getUsername());
				pstmt.setInt(3, pos.getBookid());
	
			}

			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int selectYposByUsernameAndBookid(String username, int bookid) {
		int ypos = -1;

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.SELECT_YPOS_BY_USERNAME_AND_BOOKID_STMT);
			pstmt.setString(1, username);
			pstmt.setInt(2, bookid);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				ypos = rs.getInt(DataContract.ScrollPositionsTable.COL_YPOS);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ypos;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean likeExists(Like like) {
		boolean flag = false;
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQLstatements.SELECT_LIKE_STMT);
			pstmt.setString(1, like.getUsername());
			pstmt.setInt(2, like.getBookid());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = true;
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}





}
