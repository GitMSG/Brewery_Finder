package com.techelevator.brewer;

public class Brewer {

	private long id;
	private int user_id;
	private int brewery_id;
	private String experience;
	private String[] awards;
	
	public Brewer() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getBrewery_id() {
		return brewery_id;
	}

	public void setBrewery_id(int brewery_id) {
		this.brewery_id = brewery_id;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String[] getAwards() {
		return awards;
	}

	public void setAwards(String[] awards) {
		this.awards = awards;
	}
	
	
}
