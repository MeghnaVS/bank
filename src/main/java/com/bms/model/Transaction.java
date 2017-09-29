package com.bms.model;


import java.sql.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Transaction implements Comparable {
	
	int id;
	Date trnDate;

	String type;
	
	@NotNull(message="Mention Amount!")
	@Min(value=100,message="Tr Amount shoud be minimum 100 RS.")
	int amount;
	
	@NotNull(message="Mention Instrument No.!")
	//@Size(min=10, max=16,message="Size shud be between 10 and 16")
	int instrumentNo;
	
	String description;
	
	@NotNull(message="Mention Account ID!")
	int accountId;
	
	public Transaction(){
		
	}

	public Transaction( String type, int amount, int instrumentNo, String description, int accountId) {
		this.type = type;
		this.amount = amount;
		this.instrumentNo = instrumentNo;
		this.description = description;
		this.accountId = accountId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setTrnDate(Date date) {
		this.trnDate= date;
	}


	public Date getTrnDate() {
		return trnDate;
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

	public int getInstrumentNo() {
		return instrumentNo;
	}

	public void setInstrumentNo(int instrumentNo) {
		this.instrumentNo = instrumentNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	public int compareTo(Object date){
		return this.trnDate.compareTo((java.sql.Date)date);
	}
	
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", date=" + trnDate + ", type=" + type
				+ ", amount=" + amount + ", instrumentNo=" + instrumentNo
				+ ", description=" + description + ", accountId=" + accountId
				+ "]";
	}
	
	
}
