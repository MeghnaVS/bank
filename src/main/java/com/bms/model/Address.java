package com.bms.model;


import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class Address {
	
	@NotEmpty(message = "Enter the Street name!")
	@Size(min = 4, max = 14, message = "Address should have character count between {2} & {1}!")
	String street;
	
	//@NotEmpty(message = "Select the City name!")
	@Size(min = 4, max = 14, message = "City should have character count between {2} & {1}!")
	String city;
	
	//@NotEmpty(message = "Select the State name!")
	@Size(min = 4, max = 14, message = "State should have character count between {2} & {1}!")
	String state;
	
	int customerId;	
	
	
	public Address() {
	
	}
	public Address(String street, String city, String state, int customerId) {
		
		this.street = street;
		this.city = city;
		this.state = state;
		this.customerId = customerId;
	}
	
	
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	@Override
	public String toString() {
		return "Address [street=" + street + ", city=" + city + ", state="
				+ state + ", customerId=" + customerId + "]";
	}
	
}


	
	
