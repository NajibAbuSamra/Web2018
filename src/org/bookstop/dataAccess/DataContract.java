package org.bookstop.dataAccess;

public final class DataContract {
	public static class UsersTable{
		public static final String TABLE_NAME = "users";
		public static final String COL_TYPE = "type";
		public static final String COL_USERNAME = "username";
		public static final String COL_EMAIL = "email";
		public static final String COL_ADDRESS = "address";
		public static final String COL_PHONE = "phone";
		public static final String COL_PASSWORD = "password";
		public static final String COL_NICKNAME = "nickname";
		public static final String COL_DESCRIPTION = "description";
		public static final String COL_PICTURE = "picture";
	}
	
	public static class BooksTable{
		public static final String TABLE_NAME = "books";
		public static final String COL_ID = "id";
		public static final String COL_NAME = "name";
		public static final String COL_IMG = "img";
		public static final String COL_PRICE = "price";
		public static final String COL_DESCRIPTION = "description";
		public static final String COL_LINK = "link";
	}
	
	public static class ReviewsTable{
		public static final String TABLE_NAME = "reviews";
		public static final String COL_ID = "id";
		public static final String COL_USERNAME = "username";
		public static final String COL_NICKNAME = "nickname";
		public static final String COL_BOOKID = "bookid";
		public static final String COL_TEXT = "text";
		public static final String COL_VERIFIED = "verified";
	}
	
	public static class LikesTable{
		public static final String TABLE_NAME = "likes";
		public static final String COL_USERNAME = "username";
		public static final String COL_BOOKID = "bookid";
	}
	
	public static class TransactionsTable{
		public static final String TABLE_NAME = "transactions";
		public static final String COL_ID = "id";
		public static final String COL_USERNAME = "username";
		public static final String COL_BOOKID = "bookid";
		public static final String COL_CARDCOMPANY = "company";
		public static final String COL_CARDNUMBER = "cardnumber";
		public static final String COL_EXPIRYMONTH = "expMonth";
		public static final String COL_EXPIRYYEAR = "expYear";
		public static final String COL_CVV = "cvv";
		public static final String COL_FULLNAME = "fullname";
	}
	
}
