package com.techelevator.review;

import java.util.List;

public interface ReviewDao {
	
	public void addReview(Review review);
	
	public List<Review> getReviewsByBeerId(String beerId);

}
