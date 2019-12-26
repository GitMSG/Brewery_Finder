package com.techelevator.beer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public interface BeerDao {

	public void addNewBeer(Beer newBeer);
	
	public List<Beer> getAllBeers();
	
	public void delete(Long id);
	
	public String getJsonStringFromUrl(URL url) throws MalformedURLException, IOException;
	
	public List<Beer> getBeersByBreweryId(String breweryId);
	
	public void updateBeer(String beerId, Beer beer);
	
}
