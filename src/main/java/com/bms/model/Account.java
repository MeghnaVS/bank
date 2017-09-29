package com.bms.model;

import javax.validation.constraints.*;

//import org.hibernate.validator.constraints.*;

public class Account {
	
	
	int id;
	
	@NotNull(message=" accountType is required!")
	String type;
	
	//@NotNull(message="Amount can not be left empty!")
	@Min(value=1000, message = "Minimum amount slould be 1000 INR! ")
	int amount;
	
	
	String status;
	
	//@NotNull(message="Customer ID Required!")
	@Min(value = 0)
	int customerId;
	
	public Account(){
		
	}

	public Account(String type, int amount,int customerId) {
		this.type = type;
		this.amount = amount;
		this.customerId = customerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", type=" + type + ", amount=" + amount
				+ ", status=" + status + ", customerId=" + customerId + "]";
	}

}
