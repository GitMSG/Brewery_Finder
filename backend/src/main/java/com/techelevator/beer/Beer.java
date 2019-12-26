package com.techelevator.beer;

public class Beer {
	
	private long id;
	private String brewery_id;
	private String name;
	private String description;
	private int abv;
	private int ibu;
	private String type;
	private String image;
	private double rating;
	
	public Beer() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Beer(String brewery_id, String name, String description, int abv, int ibu, String type, String image,
			double rating) {
		super();
		this.brewery_id = brewery_id;
		this.name = name;
		this.description = description;
		this.abv = abv;
		this.ibu = ibu;
		this.type = type;
		this.image = image;
		this.rating = rating;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBrewery_id() {
		return brewery_id;
	}

	public void setBrewery_id(String brewery_id) {
		this.brewery_id = brewery_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAbv() {
		return abv;
	}

	public void setAbv(int abv) {
		this.abv = abv;
	}

	public int getIbu() {
		return ibu;
	}

	public void setIbu(int ibu) {
		this.ibu = ibu;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	

}
