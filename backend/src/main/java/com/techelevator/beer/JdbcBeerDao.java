package com.techelevator.beer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcBeerDao implements BeerDao{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcBeerDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	 //	method to convert string into json
    //////////////////////////////////////////////////////////////////////////////////////////
    public String getJsonStringFromUrl(URL url) throws MalformedURLException, IOException {
    	HttpURLConnection serverConnection = (HttpURLConnection) url.openConnection();
    	serverConnection.setRequestMethod("GET");
    	if (serverConnection.getResponseCode() != 200) {
    		throw new RuntimeException("Failed : HTTP error code : " + serverConnection.getResponseCode());
    	}
    	BufferedReader responseContentReader = new BufferedReader(new InputStreamReader((serverConnection.getInputStream())));
    	String output = "";     
    	String jsonString = "";  	
    	while ((output = responseContentReader.readLine()) != null) { 
    		jsonString += output;                                      
    	} 
    	serverConnection.disconnect();   
    	return jsonString;               
    }

////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void addNewBeer(Beer newBeer) {
		String insertSql = "INSERT INTO beer " 
				+ "(brewery_id, name, description, abv, ibu, type, image, rating) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(insertSql,newBeer.getBrewery_id(), newBeer.getName(), newBeer.getDescription(), newBeer.getAbv(),
				newBeer.getIbu(), newBeer.getType(), newBeer.getImage(), newBeer.getRating());
	}
	public void delete(Long id) {
		String sql = "DELETE FROM beer where id = ?";
		jdbcTemplate.update(sql,id);
	}

	@Override
	public List<Beer> getAllBeers() {
		List<Beer> allBeers = new ArrayList<>();
		String sqlSelectAllBeers = "SELECT * FROM beer";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllBeers);
		while (results.next()) {
			allBeers.add(mapRowToBeer(results));
		}
		return allBeers;
	}
	
	public Beer mapRowToBeer(SqlRowSet results) {
		Beer resultBeer = new Beer();
		resultBeer.setBrewery_id(results.getString("brewery_id"));
		resultBeer.setId(results.getLong("id"));
		resultBeer.setName(results.getString("name"));
		resultBeer.setDescription(results.getString("description"));
		resultBeer.setAbv(results.getInt("abv"));
		resultBeer.setIbu(results.getInt("ibu"));
		resultBeer.setType(results.getString("type"));
		resultBeer.setImage(results.getString("image"));
		resultBeer.setRating(results.getDouble("rating"));
		return resultBeer;
	}

	@Override
	public List<Beer> getBeersByBreweryId(String breweryId) {
		// TODO Auto-generated method stub
		List<Beer> breweryBeerList = new ArrayList<Beer>();
		String sqlSelectBeersByBreweryId = "SELECT * from beer WHERE brewery_id = '" + breweryId + "'";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectBeersByBreweryId);
		while (results.next()) {
			breweryBeerList.add(mapRowToBeer(results));
		}
		return breweryBeerList;
	}

	@Override
	public void updateBeer(String beerId, Beer beer) {
		// TODO Auto-generated method stub
		int beerIdInt = Integer.parseInt(beerId);
		String updateSql = "UPDATE beer SET name = ?, abv = ?, ibu = ?, type = ?, description = ? "
						 + "WHERE id = ?";
		jdbcTemplate.update(updateSql,beer.getName(),beer.getAbv(),beer.getIbu(),beer.getType(),beer.getDescription(),beerIdInt);
		
	}

}
