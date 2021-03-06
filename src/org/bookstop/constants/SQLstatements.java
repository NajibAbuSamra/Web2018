package org.bookstop.constants;

import org.bookstop.dataAccess.DataContract;

/**
 * This class holds all the SQL DDL and DML commands that are used in the
 * DataAccess and at the time of initializing the DB (context listener).
 * 
 * Assumptions: Any field that was not specified in the project guidelines
 * as required or optional was considered optional for User.
 * @author najib
 *
 */
public final class SQLstatements {

	// Books
	public final static String CREATE_BOOKS_TABLE = "CREATE TABLE " + DataContract.BooksTable.TABLE_NAME + "("
			+ DataContract.BooksTable.COL_ID
			+ " INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
			+ DataContract.BooksTable.COL_NAME + " varchar(255) NOT NULL," + DataContract.BooksTable.COL_IMG
			+ " varchar(255) NOT NULL," + DataContract.BooksTable.COL_PRICE + " DOUBLE NOT NULL,"
			+ DataContract.BooksTable.COL_DESCRIPTION + " varchar(255) NOT NULL," + DataContract.BooksTable.COL_LINK
			+ " varchar(255) NOT NULL," + "PRIMARY KEY (" + DataContract.BooksTable.COL_ID + "))";
	public final static String INSERT_BOOK_STMT = "INSERT INTO " + DataContract.BooksTable.TABLE_NAME
			+ " VALUES(DEFAULT,?,?,?,?,?)";
	public final static String SELECT_ALL_BOOKS_STMT = "SELECT * FROM " + DataContract.BooksTable.TABLE_NAME;
	public final static String SELECT_BOOKS_BY_ID_STMT = "SELECT * FROM " + DataContract.BooksTable.TABLE_NAME
			+ " WHERE " + DataContract.BooksTable.COL_ID + " = ?";

	// Users
	public final static String CREATE_USERS_TABLE = "CREATE TABLE " + DataContract.UsersTable.TABLE_NAME + "("
			+ DataContract.UsersTable.COL_TYPE + " INTEGER NOT NULL," + DataContract.UsersTable.COL_USERNAME
			+ " varchar(10) UNIQUE NOT NULL," + DataContract.UsersTable.COL_EMAIL + " varchar(255),"
			+ DataContract.UsersTable.COL_ADDRESS + " varchar(255)," + DataContract.UsersTable.COL_PHONE
			+ " varchar(255)," + DataContract.UsersTable.COL_PASSWORD + " varchar(8) NOT NULL,"
			+ DataContract.UsersTable.COL_NICKNAME + " varchar(20) NOT NULL," + DataContract.UsersTable.COL_DESCRIPTION
			+ " varchar(50)," + DataContract.UsersTable.COL_PICTURE + " varchar(255))";
	public final static String INSERT_USER_STMT = "INSERT INTO " + DataContract.UsersTable.TABLE_NAME
			+ " VALUES(?,?,?,?,?,?,?,?,?)";
	public final static String SELECT_ALL_USERS_STMT = "SELECT * FROM " + DataContract.UsersTable.TABLE_NAME + " WHERE "
			+ DataContract.UsersTable.COL_TYPE + " = 0";
	public final static String SELECT_USER_BY_USERNAME_STMT = "SELECT * FROM " + DataContract.UsersTable.TABLE_NAME
			+ " WHERE " + DataContract.UsersTable.COL_USERNAME + "=?";
	public final static String DELETE_USER_BY_USERNAME_STMT = "DELETE FROM " + DataContract.UsersTable.TABLE_NAME
			+ " WHERE " + DataContract.UsersTable.COL_USERNAME + " = ?";

	// Reviews
	public final static String CREATE_REVIEWS_TABLE = "CREATE TABLE " + DataContract.ReviewsTable.TABLE_NAME + "("
			+ DataContract.ReviewsTable.COL_ID
			+ " INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
			+ DataContract.ReviewsTable.COL_USERNAME + " varchar(10) NOT NULL," + DataContract.ReviewsTable.COL_NICKNAME
			+ " varchar(20) NOT NULL," + DataContract.ReviewsTable.COL_BOOKID + " INTEGER NOT NULL,"
			+ DataContract.ReviewsTable.COL_TEXT + " varchar(255) NOT NULL," + DataContract.ReviewsTable.COL_VERIFIED
			+ " INTEGER NOT NULL," + "PRIMARY KEY (" + DataContract.ReviewsTable.COL_ID + "),"
			+ "CONSTRAINT username_reviews_ref FOREIGN KEY (" + DataContract.ReviewsTable.COL_USERNAME + ") REFERENCES "
			+ DataContract.UsersTable.TABLE_NAME + "(" + DataContract.UsersTable.COL_USERNAME + ") ON DELETE CASCADE,"
			+ "CONSTRAINT bookid_reviews_ref FOREIGN KEY (" + DataContract.ReviewsTable.COL_BOOKID + ") REFERENCES "
			+ DataContract.BooksTable.TABLE_NAME + "(" + DataContract.BooksTable.COL_ID + ") ON DELETE CASCADE)";
	public final static String INSERT_REVIEW_STMT = "INSERT INTO " + DataContract.ReviewsTable.TABLE_NAME
			+ " VALUES(DEFAULT,?,?,?,?,?)";
	public final static String SELECT_REVIEWS_VERIFIED_BY_BOOKID_STMT = "SELECT * FROM "
			+ DataContract.ReviewsTable.TABLE_NAME + " WHERE " + DataContract.ReviewsTable.COL_BOOKID + " = ? AND "
			+ DataContract.ReviewsTable.COL_VERIFIED + " = 1";
	public final static String SELECT_REVIEWS_UNVERIFIED_BY_BOOKID_STMT = "SELECT * FROM "
			+ DataContract.ReviewsTable.TABLE_NAME + " WHERE " + DataContract.ReviewsTable.COL_BOOKID + " = ? AND "
			+ DataContract.ReviewsTable.COL_VERIFIED + " = 0";
	public final static String UPDATE_REVIEW_VERIFIED_BY_ID_STMT = "UPDATE " + DataContract.ReviewsTable.TABLE_NAME
			+ " SET " + DataContract.ReviewsTable.COL_VERIFIED + " = ? WHERE " + DataContract.ReviewsTable.COL_ID
			+ " =?";
	public final static String DELETE_REVIEW_BY_USERNAME_AND_BOOKID_STMT = "DELETE FROM "
			+ DataContract.ReviewsTable.TABLE_NAME + " WHERE " + DataContract.ReviewsTable.COL_USERNAME + " = ? AND "
			+ DataContract.ReviewsTable.COL_BOOKID + " = ?";
	public final static String SELECT_REVIEW_BY_ID_STMT = "SELECT * FROM " + DataContract.ReviewsTable.TABLE_NAME
			+ " WHERE " + DataContract.ReviewsTable.COL_ID + " = ?";

	// Likes
	public final static String CREATE_LIKES_TABLE = "CREATE TABLE " + DataContract.LikesTable.TABLE_NAME + "("
			+ DataContract.LikesTable.COL_USERNAME + " VARCHAR(10) NOT NULL," + DataContract.LikesTable.COL_BOOKID
			+ " INTEGER NOT NULL," + "PRIMARY KEY (" + DataContract.LikesTable.COL_USERNAME + ","
			+ DataContract.LikesTable.COL_BOOKID + ")," + "CONSTRAINT username_likes_ref FOREIGN KEY ("
			+ DataContract.LikesTable.COL_USERNAME + ") REFERENCES " + DataContract.UsersTable.TABLE_NAME + "("
			+ DataContract.UsersTable.COL_USERNAME + ") ON DELETE CASCADE,"
			+ "CONSTRAINT bookid_likes_ref FOREIGN KEY (" + DataContract.LikesTable.COL_BOOKID + ") REFERENCES "
			+ DataContract.BooksTable.TABLE_NAME + "(" + DataContract.BooksTable.COL_ID + ") ON DELETE CASCADE)";
	public final static String INSERT_LIKE_STMT = "INSERT INTO " + DataContract.LikesTable.TABLE_NAME + " VALUES(?,?)";
	public final static String DELETE_LIKE_BY_USERNAME_AND_BOOKID_STMT = "DELETE FROM "
			+ DataContract.LikesTable.TABLE_NAME + " WHERE " + DataContract.LikesTable.COL_USERNAME + " = ? AND "
			+ DataContract.LikesTable.COL_BOOKID + " = ?";
	public final static String COUNT_LIKES_BY_BOOKID_STMT = "SELECT COUNT(*) FROM " + DataContract.LikesTable.TABLE_NAME
			+ " WHERE " + DataContract.LikesTable.COL_BOOKID + " = ?";
	public final static String SELECT_LIKE_USERNAMES_BY_BOOKID_STMT = "SELECT " + DataContract.LikesTable.COL_USERNAME
			+ " FROM " + DataContract.LikesTable.TABLE_NAME + " WHERE " + DataContract.LikesTable.COL_BOOKID + " = ?";
	public final static String SELECT_LIKE_STMT = "SELECT * FROM " + DataContract.LikesTable.TABLE_NAME + " WHERE "
			+ DataContract.LikesTable.COL_USERNAME + " = ? AND " + DataContract.LikesTable.COL_BOOKID + " = ?";

	// Transactions
	public final static String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE " + DataContract.TransactionsTable.TABLE_NAME
			+ "(" + DataContract.TransactionsTable.COL_ID
			+ " INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
			+ DataContract.TransactionsTable.COL_USERNAME + " VARCHAR(10) NOT NULL,"
			+ DataContract.TransactionsTable.COL_BOOKID + " INTEGER NOT NULL,"
			+ DataContract.TransactionsTable.COL_CARDCOMPANY + " VARCHAR(100) NOT NULL,"
			+ DataContract.TransactionsTable.COL_CARDNUMBER + " VARCHAR(19) NOT NULL," // 19 is the max number of credit
																						// card digits
			+ DataContract.TransactionsTable.COL_EXPIRYMONTH + " INTEGER NOT NULL,"
			+ DataContract.TransactionsTable.COL_EXPIRYYEAR + " INTEGER NOT NULL,"
			+ DataContract.TransactionsTable.COL_CVV + " VARCHAR(4) NOT NULL," // max length of cvv is 4
			+ DataContract.TransactionsTable.COL_FULLNAME + " VARCHAR(255) NOT NULL,"
			+ DataContract.TransactionsTable.COL_ADDRESS + " VARCHAR(255) NOT NULL," + "PRIMARY KEY ("
			+ DataContract.TransactionsTable.COL_ID + ")," + "CONSTRAINT username_trans_ref FOREIGN KEY ("
			+ DataContract.TransactionsTable.COL_USERNAME + ") REFERENCES " + DataContract.UsersTable.TABLE_NAME + "("
			+ DataContract.UsersTable.COL_USERNAME + ") ON DELETE CASCADE,"
			+ "CONSTRAINT bookid_trans_ref FOREIGN KEY (" + DataContract.TransactionsTable.COL_BOOKID + ") REFERENCES "
			+ DataContract.BooksTable.TABLE_NAME + "(" + DataContract.BooksTable.COL_ID + ") ON DELETE CASCADE)";
	public final static String INSERT_TRANSACTION_STMT = "INSERT INTO " + DataContract.TransactionsTable.TABLE_NAME
			+ " VALUES(DEFAULT,?,?,?,?,?,?,?,?,?)";
	public final static String SELECT_ALL_TRANSACTIONS = "SELECT * FROM " + DataContract.TransactionsTable.TABLE_NAME;
	public final static String SELECT_TRANSACTIONS_BY_USERNAME_STMT = "SELECT * FROM "
			+ DataContract.TransactionsTable.TABLE_NAME + " WHERE " + DataContract.TransactionsTable.COL_USERNAME
			+ " = ?";
	public final static String SELECT_TRANSACTIONS_BY_BOOKID_STMT = "SELECT * FROM "
			+ DataContract.TransactionsTable.TABLE_NAME + " WHERE " + DataContract.TransactionsTable.COL_BOOKID
			+ " = ?";
	public final static String SELECT_TRANSACTIONS_BOOKID_BY_USERNAME_STMT = "SELECT "
			+ DataContract.TransactionsTable.COL_BOOKID + " FROM " + DataContract.TransactionsTable.TABLE_NAME
			+ " WHERE " + DataContract.TransactionsTable.COL_USERNAME + " = ?";
	// TODO: not sure if needed
	public final static String SELECT_TRANSACTION_BY_USERNAME_AND_BOOKID_STMT = "SELECT * FROM "
			+ DataContract.TransactionsTable.TABLE_NAME + " WHERE " + DataContract.TransactionsTable.COL_USERNAME
			+ " = ? AND " + DataContract.TransactionsTable.COL_BOOKID + " = ?";

	// ScrollPositions
	public final static String CREATE_SCROLLPOSITIONS_TABLE = "CREATE TABLE "
			+ DataContract.ScrollPositionsTable.TABLE_NAME + "(" + DataContract.ScrollPositionsTable.COL_USERNAME
			+ " VARCHAR(10) NOT NULL," + DataContract.ScrollPositionsTable.COL_BOOKID + " INTEGER NOT NULL,"
			+ DataContract.ScrollPositionsTable.COL_YPOS + " INTEGER NOT NULL," + "PRIMARY KEY ("
			+ DataContract.ScrollPositionsTable.COL_USERNAME + "," + DataContract.ScrollPositionsTable.COL_BOOKID + "),"
			+ "CONSTRAINT username_ypos_ref FOREIGN KEY (" + DataContract.ScrollPositionsTable.COL_USERNAME
			+ ") REFERENCES " + DataContract.UsersTable.TABLE_NAME + "(" + DataContract.UsersTable.COL_USERNAME
			+ ") ON DELETE CASCADE," + "CONSTRAINT bookid_ypos_ref FOREIGN KEY ("
			+ DataContract.ScrollPositionsTable.COL_BOOKID + ") REFERENCES " + DataContract.BooksTable.TABLE_NAME + "("
			+ DataContract.BooksTable.COL_ID + ") ON DELETE CASCADE)";
	public final static String INSERT_SCROLLPOSITION_STMT = "INSERT INTO "
			+ DataContract.ScrollPositionsTable.TABLE_NAME + " VALUES(?,?,?)";
	public final static String SELECT_YPOS_BY_USERNAME_AND_BOOKID_STMT = "SELECT "
			+ DataContract.ScrollPositionsTable.COL_YPOS + " FROM " + DataContract.ScrollPositionsTable.TABLE_NAME
			+ " WHERE " + DataContract.ScrollPositionsTable.COL_USERNAME + " = ? AND "
			+ DataContract.ScrollPositionsTable.COL_BOOKID + " = ?";
	public final static String UPDATE_YPOS_BY_USERNAME_AND_BOOKID_STMT = "UPDATE "
			+ DataContract.ScrollPositionsTable.TABLE_NAME + " SET " + DataContract.ScrollPositionsTable.COL_YPOS
			+ " = ? WHERE " + DataContract.ScrollPositionsTable.COL_USERNAME + " = ? AND "
			+ DataContract.ScrollPositionsTable.COL_BOOKID + " = ?";
}
