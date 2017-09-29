package com.bms.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Offer {

	
	int id;
	
	@NotEmpty(message="Type can't be left blank!!")
	String type;
	
	
	String offerDate;
	
	
	String expiryDate;
	
	@NotEmpty(message="Type can't be left blank!!")
	String utilized;
	
	int customerId;

	public Offer() {
		super();
	}

	public Offer(String type, String offerDate, String expiryDate, String utilized,
			int customerId) {
		super();
		this.type = type;
		this.offerDate = offerDate;
		this.expiryDate = expiryDate;
		this.utilized = utilized;
		this.customerId = customerId;
	}

	public Offer(int id, String type, String offerDate, String expiryDate,
			String utilized, int customerId) {
		super();
		this.id = id;
		this.type = type;
		this.offerDate = offerDate;
		this.expiryDate = expiryDate;
		this.utilized = utilized;
		this.customerId = customerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@NotNull
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getOfferDate() {
		return offerDate;
	}

	public void setOfferDate(String offerDate) {
		this.offerDate = offerDate;
	}


	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	@NotNull
	public String getUtilized() {
		return utilized;
	}

	public void setUtilized(String utilized) {
		this.utilized = utilized;
	}

	@NotNull
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "Offer [id=" + id + ", type=" + type + ", offerDate="
				+ offerDate + ", expiryDate=" + expiryDate + ", utilized="
				+ utilized + ", customerId=" + customerId + "]";
	}

}
