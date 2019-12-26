package com.techelevator.brewery;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public interface BreweryDao {

	
	public void addNewBrewery(Brewery newBrewery);
	
	public List<Brewery> getAllBreweries();
	
	public Brewery getBreweryById(int id);

	void updateExistingBrewery(long id, Brewery brewery);

	public String getJsonStringFromUrl(URL url) throws MalformedURLException, IOException ;
	
	
}
