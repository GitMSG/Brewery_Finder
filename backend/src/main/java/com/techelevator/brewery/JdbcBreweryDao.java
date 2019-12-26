package com.techelevator.brewery;

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
public class JdbcBreweryDao implements BreweryDao{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcBreweryDao(DataSource dataSource) {
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
	public void addNewBrewery(Brewery newBrewery) {
		String insertSql = "INSERT INTO brewery "
				+ "(name, address, city, zipcode, description, image, hours_of_operation, phone_number, email, website) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(insertSql, newBrewery.getName(), newBrewery.getAddress(), newBrewery.getCity(), newBrewery.getZipcode(),
				newBrewery.getDescription(), newBrewery.getImage(), newBrewery.getHoursOfOperation(), newBrewery.getPhoneNumber(), newBrewery.getEmail(), newBrewery.getWebsite());
	}
	
	@Override 
	public void updateExistingBrewery(long id, Brewery brewery) {
		jdbcTemplate.update("UPDATE brewery SET name=?, address=?, city=?, zipcode=?, description=?, image=?, hours_of_operation=?, phone_number=?, email=?, website=? WHERE id=?",
				brewery.getName(), brewery.getAddress(), brewery.getCity(), brewery.getZipcode(),
				brewery.getDescription(), brewery.getImage(), brewery.getHoursOfOperation(), 
				brewery.getPhoneNumber(), brewery.getEmail(), brewery.getWebsite());
	}
	
	@Override
	public List<Brewery> getAllBreweries() {
		List<Brewery> allBreweries = new ArrayList<>();
		String sqlSelectAllBreweries = "SELECT * FROM brewery";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllBreweries);
		while(results.next()) {
			allBreweries.add(mapRowToBrewery(results));
		}
		return allBreweries;
	}
	
	@Override 
	public Brewery getBreweryById(int id) {
		Brewery theBrewery = new Brewery();
		String sqlSelectBreweryById = "SELECT * FROM brewery WHERE id = '" + id + "'" ;
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectBreweryById);
		theBrewery = mapRowToBrewery(results);
		return theBrewery;
	}
	private Brewery mapRowToBrewery(SqlRowSet results) {
		Brewery resultBrewery = new Brewery();
		resultBrewery.setId(results.getInt("id"));
		resultBrewery.setName(results.getString("name"));
		resultBrewery.setAddress(results.getString("address"));
		resultBrewery.setCity(results.getString("city"));
		resultBrewery.setZipcode(results.getInt("zipcode"));
		resultBrewery.setDescription(results.getString("description"));
		resultBrewery.setImage(results.getString("image"));
		resultBrewery.setHoursOfOperation(results.getString("hours_of_operation"));
		resultBrewery.setPhoneNumber(results.getString("phone_number"));
		resultBrewery.setEmail(results.getString("email"));
		resultBrewery.setWebsite(results.getString("website"));
		return resultBrewery;
	}
	
	
}
