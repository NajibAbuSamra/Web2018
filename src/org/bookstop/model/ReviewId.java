package org.bookstop.model;

/**
 * Helper class used to handle sent review ids from client.
 * During the work on the servlets we had difficulties working with Gson,
 * specifically converting basic types from json, so this class was used
 * to convert bookid from json.
 * @author najib
 *
 */
public class ReviewId {
	int reviewid;

	public ReviewId(int reviewid) {
		super();
		this.reviewid = reviewid;
	}

	public int getReviewid() {
		return reviewid;
	}

	public void setReviewid(int reviewid) {
		this.reviewid = reviewid;
	}
	
}
