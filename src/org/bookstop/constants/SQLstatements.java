package org.bookstop.constants;

import org.bookstop.dataAccess.DataContract;

public final class SQLstatements {

	public final static String CREATE_BOOKS_TABLE = "CREATE TABLE " + DataContract.BooksTable.TABLE_NAME + "("
			+ DataContract.BooksTable.COL_ID + " INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)"
			+ DataContract.BooksTable.COL_NAME + " varchar(255) NOT NULL,"
			+ DataContract.BooksTable.COL_IMG + " varchar(255) NOT NULL,"
			+ DataContract.BooksTable.COL_PRICE + " DOUBLE NOT NULL,"
			+ DataContract.BooksTable.COL_DESCRIPTION + " varchar(255) NOT NULL,"
			+ DataContract.BooksTable.COL_LINK + "varchar(255) NOT NULL,"
			+ "CONSTRAINT primary_key PRIMARY KEY ("+DataContract.BooksTable.COL_ID+"))";


	public final static String INSERT_BOOK_STMT = "INSERT INTO " + DataContract.BooksTable.TABLE_NAME + " VALUES(?,?,?,?,?)";
	public final static String SELECT_ALL_BOOKS_STMT = "SELECT * FROM " + DataContract.BooksTable.TABLE_NAME;
	public final static String SELECT_BOOKS_BY_ID_STMT = "SELECT * FROM " + DataContract.BooksTable.TABLE_NAME + "WHERE " + DataContract.BooksTable.COL_ID + "=?";

}
