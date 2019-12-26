package com.techelevator.brewery;

import java.util.Date;
import java.util.List;

import com.techelevator.brewer.Brewer;

public class Brewery {
	
	

	private long id;
	private String name;
	private String address;
	private String city;
	private int zipcode;
	private String description;
	private String image;
	private String phoneNumber;
	private String email;
	private String website;
	private String hoursOfOperation;
	
	
	public Brewery(String name, String address, String city, int zipcode, String description, String image,
			String phoneNumber, String email, String website, String hoursOfOperation) {
		super();
		this.name = name;
		this.address = address;
		this.city = city;
		this.zipcode = zipcode;
		this.description = description;
		this.image = image;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.website = website;
		this.hoursOfOperation = hoursOfOperation;
	}
	@Override
	public String toString() {
		return "Brewery [image=" + image + "]";
	}

	public Brewery() {

	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getHoursOfOperation() {
		return hoursOfOperation;
	}

	public void setHoursOfOperation(String hoursOfOperation) {
		this.hoursOfOperation = hoursOfOperation;
	}

	
	

}
