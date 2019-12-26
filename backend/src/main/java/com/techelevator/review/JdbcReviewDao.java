package com.techelevator.review;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcReviewDao implements ReviewDao{
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcReviewDao(DataSource datasource) {
		jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public void addReview(Review review) {
		// TODO Auto-generated method stub
		String insertsql = "INSERT INTO review "
				+ "(beer_id, rating, text, username) "
				+ "VALUES(?, ?, ?, ?)";
		jdbcTemplate.update(insertsql,review.getBeerId(),review.getRating(),review.getText(),review.getUsername());
	}

	@Override
	public List<Review> getReviewsByBeerId(String beerId) {
		// TODO Auto-generated method stub
		List<Review> reviewsByBeerId = new ArrayList<Review>();
		String sqlSelectReviewsByBeerId = "SELECT * FROM review WHERE beer_id = '" + beerId + "'";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectReviewsByBeerId);
		while(results.next()) {
			reviewsByBeerId.add(mapRowToReview(results));
		}
		return reviewsByBeerId;
	}
	
	private Review mapRowToReview(SqlRowSet results) {
		Review theReview = new Review();
		theReview.setBeerId(results.getString("beer_id"));
		theReview.setId(results.getLong("id"));
		theReview.setRating(results.getInt("rating"));
		theReview.setText(results.getString("text"));
		theReview.setUsername(results.getString("username"));
		return theReview;
	}

}
