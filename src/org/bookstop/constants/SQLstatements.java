package org.bookstop.constants;

import org.bookstop.dataAccess.DataContract;

public final class SQLstatements {

	//Books
	public final static String CREATE_BOOKS_TABLE = "CREATE TABLE " + DataContract.BooksTable.TABLE_NAME + "("
			+ DataContract.BooksTable.COL_ID + " INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
			+ DataContract.BooksTable.COL_NAME + " varchar(255) NOT NULL,"
			+ DataContract.BooksTable.COL_IMG + " varchar(255) NOT NULL,"
			+ DataContract.BooksTable.COL_PRICE + " DOUBLE NOT NULL,"
			+ DataContract.BooksTable.COL_DESCRIPTION + " varchar(255) NOT NULL,"
			+ DataContract.BooksTable.COL_LINK + "varchar(255) NOT NULL,"
			+ "PRIMARY KEY ("+DataContract.BooksTable.COL_ID+"))";

	public final static String INSERT_BOOK_STMT = "INSERT INTO " + DataContract.BooksTable.TABLE_NAME + " VALUES(?,?,?,?,?)";
	public final static String SELECT_ALL_BOOKS_STMT = "SELECT * FROM " + DataContract.BooksTable.TABLE_NAME;
	public final static String SELECT_BOOKS_BY_ID_STMT = "SELECT * FROM " + DataContract.BooksTable.TABLE_NAME + "WHERE " + DataContract.BooksTable.COL_ID + "=?";

	//Users
	public final static String CREATE_USERS_TABLE = "CREATE TABLE " + DataContract.UsersTable.TABLE_NAME + "("
			+ DataContract.UsersTable.COL_TYPE + " INTEGER NOT NULL,"
			+ DataContract.UsersTable.COL_USERNAME + " varchar(10) UNIQUE NOT NULL,"
			+ DataContract.UsersTable.COL_EMAIL + " varchar(255),"
			+ DataContract.UsersTable.COL_ADDRESS + " varchar(255),"
			+ DataContract.UsersTable.COL_PHONE + " varchar(255),"
			+ DataContract.UsersTable.COL_PASSWORD + " varchar(8) NOT NULL,"
			+ DataContract.UsersTable.COL_NICKNAME + " varchar(20) NOT NULL,"
			+ DataContract.UsersTable.COL_DESCRIPTION + " varchar(50),"
			+ DataContract.UsersTable.COL_PICTURE + " varchar(255),"
			+ "PRIMARY KEY ("+DataContract.UsersTable.COL_USERNAME+"))";
	
	public final static String INSERT_USER_STMT = "INSERT INTO " + DataContract.UsersTable.TABLE_NAME + " VALUES(?,?,?,?,?,?,?,?,?)";
	public final static String SELECT_USER_NICKNAME_STMT = "SELECT * FROM " + DataContract.UsersTable.TABLE_NAME + " WHERE " + DataContract.UsersTable.COL_NICKNAME + "=?";
	
	//Reviews
	public final static String CREATE_REVIEWS_TABLE = "CREATE TABLE " + DataContract.ReviewsTable.TABLE_NAME + "("
			+ DataContract.ReviewsTable.COL_ID + " INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
			+ DataContract.ReviewsTable.COL_USERNAME + " VARCHAR(10) NOT NULL,"
			+ DataContract.ReviewsTable.COL_BOOKID + " INTEGER NOT NULL,"
			+ DataContract.ReviewsTable.COL_TEXT + " VARCHAR(255) NOT NULL,"
			+ DataContract.ReviewsTable.COL_VERIFIED + "BIT NOT NULL,"
			+ "PRIMARY KEY ("+DataContract.ReviewsTable.COL_ID+"),"
			+ "CONSTRAINT username_ref FOREIGN KEY ("+DataContract.ReviewsTable.COL_USERNAME+") REFERENCES "+DataContract.UsersTable.TABLE_NAME+"("+DataContract.UsersTable.COL_USERNAME+") ON DELETE CASCADE,"
			+ "CONSTRAINT bookid_ref FOREIGN KEY ("+DataContract.ReviewsTable.COL_BOOKID+") REFERENCES "+DataContract.BooksTable.TABLE_NAME+"("+DataContract.BooksTable.COL_ID+") ON DELETE CASCADE)";
	
	public final static String INSERT_REVIEW_STMT = "INSERT INTO " + DataContract.ReviewsTable.TABLE_NAME + " VALUES(?,?,?,?)";
	public final static String UPDATE_REVIEW_VERIFIED_STM = "UPDATE " + DataContract.ReviewsTable.TABLE_NAME + " SET " + DataContract.ReviewsTable.COL_VERIFIED + " = ?"; 
	
	//Likes
	public final static String CREATE_LIKES_TABLE = "CREATE TABLE " + DataContract.LikesTable.TABLE_NAME + "("
			+DataContract.LikesTable.COL_USERNAME + " VARCHAR(10) NOT NULL,"
			+DataContract.LikesTable.COL_BOOKID + " INTEGER NOT NULL,"
			+ "CONSTRAINT username_ref FOREIGN KEY ("+DataContract.LikesTable.COL_USERNAME+") REFERENCES "+DataContract.UsersTable.TABLE_NAME+"("+DataContract.UsersTable.COL_USERNAME+") ON DELETE CASCADE,"
			+ "CONSTRAINT bookid_ref FOREIGN KEY ("+DataContract.LikesTable.COL_BOOKID+") REFERENCES "+DataContract.BooksTable.TABLE_NAME+"("+DataContract.BooksTable.COL_ID+") ON DELETE CASCADE)";
	
	public final static String INSERT_LIKE_STMT = "INSERT INTO " + DataContract.LikesTable.TABLE_NAME + " VALUES(?,?)";

	//Transactions
	public final static String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE " + DataContract.TransactionsTable.TABLE_NAME + "("
			+DataContract.TransactionsTable.COL_ID + " INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
			+DataContract.TransactionsTable.COL_USERNAME + " VARCHAR(10) NOT NULL,"
			+DataContract.TransactionsTable.COL_BOOKID + " INTEGER NOT NULL,"
			+DataContract.TransactionsTable.COL_CARDCOMPANY + " VARCHAR(100) NOT NULL,"
			+DataContract.TransactionsTable.COL_CARDNUMBER + " VARCHAR(19) NOT NULL," //19 is the max number of credit card digits
			+DataContract.TransactionsTable.COL_EXPIRYMONTH + " INTEGER(2) NOT NULL,"
			+DataContract.TransactionsTable.COL_EXPIRYYEAR + " INTEGER(2) NOT NULL,"
			+DataContract.TransactionsTable.COL_CVV + " INTEGER(4) NOT NULL," //max length of cvv is 4
			+DataContract.TransactionsTable.COL_FULLNAME + " VARCHAR(255) NOT NULL,"
			+ "CONSTRAINT username_ref FOREIGN KEY ("+DataContract.TransactionsTable.COL_USERNAME+") REFERENCES "+DataContract.UsersTable.TABLE_NAME+"("+DataContract.UsersTable.COL_USERNAME+") ON DELETE CASCADE,"
			+ "CONSTRAINT bookid_ref FOREIGN KEY ("+DataContract.TransactionsTable.COL_BOOKID+") REFERENCES "+DataContract.BooksTable.TABLE_NAME+"("+DataContract.BooksTable.COL_ID+") ON DELETE CASCADE)";

	public final static String INSERT_TRANSACTION_STMT = "INSERT INTO " + DataContract.TransactionsTable.TABLE_NAME + " VALUES(?,?,?,?,?,?,?,?)";
}
