package com.techelevator.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.authentication.AuthProvider;
import com.techelevator.authentication.UnauthorizedException;
import com.techelevator.beer.Beer;
import com.techelevator.beer.BeerDao;
import com.techelevator.brewery.Brewery;
import com.techelevator.brewery.BreweryDao;
import com.techelevator.model.User;
import com.techelevator.model.UserDao;
import com.techelevator.review.Review;
import com.techelevator.review.ReviewDao;

/**
 * ApiController
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiController {

    @Autowired
    private AuthProvider authProvider;
    
    @Autowired
    private BreweryDao breweryDao;
    
    @Autowired 
    private BeerDao beerDao;
    
    @Autowired
    private ReviewDao reviewDao;
    
    @Autowired
    private UserDao userDao;
   
   
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String authorizedOnly() throws UnauthorizedException {
        /*
        You can lock down which roles are allowed by checking
        if the current user has a role.
        
        In this example, if the user does not have the admin role
        we send back an unauthorized error.
        */
        if (!authProvider.userHasRole(new String[] { "admin" })) {
            throw new UnauthorizedException();
        }
        return "Success";
    }
    
    ////////////////////GET BREWERY FROM API
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(path = "/breweries", method = RequestMethod.GET)  
    public String getApiBreweries() throws IOException {					
    	URL url = new URL("https://sandbox-api.brewerydb.com/v2/breweries/?key=9fa9f5e3d682fb2e23c1d79f5bd1b763");  
    	String jsonString = breweryDao.getJsonStringFromUrl(url);   
    	return jsonString;     	
    } 
    
    @RequestMapping(path = "/breweryLocation/{id}", method = RequestMethod.GET)  
	public String getBreweryLocation(@PathVariable String id) throws IOException {	
		URL url = new URL("https://sandbox-api.brewerydb.com/v2/brewery/"+id+"/locations/?key=9fa9f5e3d682fb2e23c1d79f5bd1b763");
		String jsonString = breweryDao.getJsonStringFromUrl(url);   
		return jsonString;     	
	} 
    
    @RequestMapping(path = "/getDbBreweries", method = RequestMethod.GET) 
    public List<Brewery> showBreweryList() {
    	List<Brewery> allBreweries = breweryDao.getAllBreweries();
    	return allBreweries;
    }
    
    
    ////////////////////			GET BEERS FROM API
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(path = "/beers", method = RequestMethod.GET)  
	public String getAllBeers() throws IOException {					
		URL url = new URL("https://sandbox-api.brewerydb.com/v2/beers/?key=9fa9f5e3d682fb2e23c1d79f5bd1b763");  
		String jsonString = beerDao.getJsonStringFromUrl(url);   
		return jsonString;     	
	} 
    
    @RequestMapping(path = "/breweryBeers/{id}", method = RequestMethod.GET)  
	public String getBreweryBeers(@PathVariable String id) throws IOException {	
		URL url = new URL("https://sandbox-api.brewerydb.com/v2/brewery/"+id+"/beers/?key=9fa9f5e3d682fb2e23c1d79f5bd1b763");
		String jsonString = beerDao.getJsonStringFromUrl(url);   
		return jsonString;     	
	} 
    
    
    @RequestMapping(path = "/brewery/{id}", method = RequestMethod.GET)
    public Brewery getBreweryById(@PathVariable int id) throws IOException {
    	Brewery brewery = breweryDao.getBreweryById(id);
    	return brewery;
    }
    
    @RequestMapping(path = "/dbBeers", method = RequestMethod.GET) 
    public List<Beer> getDbBeers() {
    	List<Beer> allDbBeers = beerDao.getAllBeers();
    	return allDbBeers;
    }
    
    @RequestMapping(path = "/dbBeers/{beerId}", method = RequestMethod.PUT)
    public void updateBeer(@Valid @RequestBody Beer beer, @PathVariable String beerId) {
    	beerDao.updateBeer(beerId, beer);
    }

    @RequestMapping(path = "/addBeer", method = RequestMethod.POST) 
    public void addNewBeer(@Valid @RequestBody Beer beer) {
    	Beer newBeer = new Beer(beer.getBrewery_id(), beer.getName(), beer.getDescription(), beer.getAbv(), beer.getIbu(), beer.getType(), beer.getImage(), beer.getRating());
    	beerDao.addNewBeer(newBeer);
    }
    @RequestMapping(path="/deleteBeer/{id}", method=RequestMethod.DELETE)
    public void delete(@Valid @PathVariable Long id) { 
    	beerDao.delete(id);	
    }
    
    //controller to show new brewery input page
    @RequestMapping(path = "/newBrewery", method = RequestMethod.GET)
    public String showNewBreweryPage(ModelMap map, HttpSession session) throws UnauthorizedException {
    	if(session.getAttribute("login").equals(false)) {
    		return "redirect:/login";
    	}
    	if (!authProvider.userHasRole(new String[] { "admin" })) {
    		throw new UnauthorizedException();
    	}
    	return "newBrewery";
    }
    
    //controller to handle actual input of new brewery to database
    @RequestMapping(path = "/newBrewery", method = RequestMethod.POST)
    public void postNewBrewery(@Valid @RequestBody Brewery brewery) {
    	Brewery newBrewery = new Brewery(brewery.getName(), brewery.getAddress(), brewery.getCity(), brewery.getZipcode(), brewery.getDescription(), brewery.getImage(),  brewery.getPhoneNumber(), brewery.getEmail(), brewery.getWebsite(), brewery.getHoursOfOperation());
    	breweryDao.addNewBrewery(newBrewery);
    	
    }
    
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void updateBrewery(@PathVariable int id, @Valid @RequestBody Brewery brewery, BindingResult result) throws BreweryNotFoundException {
    	Brewery breweryToUpdate = breweryDao.getBreweryById(id);
    	if(breweryToUpdate != null) {
    		breweryDao.updateExistingBrewery(id, brewery);
    	} else {
    		throw new BreweryNotFoundException(id, "Brewery not found!");
    	}
    }
    
    @RequestMapping(path = "/addReview", method = RequestMethod.POST)
    public void addReview(@Valid @RequestBody Review review) {
    	
    	Review newReview = new Review();
    	newReview.setBeerId(review.getBeerId());
    	newReview.setRating(review.getRating());
    	newReview.setText(review.getText());
    	newReview.setUsername(review.getUsername());
    	reviewDao.addReview(newReview);
    }
    
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers(){
    	List<User> allUsers = userDao.getAllUsers();
    	return allUsers;
    }
    
    @RequestMapping(path = "/changeUserRole", method = RequestMethod.POST)
    public void changeUserRole(@RequestBody User user) {
    	userDao.changeRole(user.getUsername(), user.getRole());
    }
    
    //controller for returning beers from database(created beers)
    @RequestMapping(path = "/breweryBeerList/{breweryId}", method = RequestMethod.GET)
    public List<Beer> getBreweryBeerList(@PathVariable String breweryId) {
    	List<Beer> beersByBreweryId = new ArrayList<Beer>();
    	beersByBreweryId = beerDao.getBeersByBreweryId(breweryId);
    	return beersByBreweryId;
    }
    
    @RequestMapping(path = "/getReviewsByBeerId/{beerId}", method = RequestMethod.GET)
    public List<Review> getReviewsByBeerId(@PathVariable String beerId) throws IOException {
    	List<Review> reviewsByBeerId = new ArrayList<Review>();
    	reviewsByBeerId = reviewDao.getReviewsByBeerId(beerId);
    	return reviewsByBeerId;
    }
    
    
    
}







