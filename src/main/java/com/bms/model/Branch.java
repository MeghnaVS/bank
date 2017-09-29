package com.bms.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;


public class Branch {
	
	
	String ifsc;
	
	@NotEmpty(message="Name is Required")
	@Size(min=4,max=20,message="Name length should be between {2} & {1}!")
	String name;
	
	@NotEmpty(message="Address is required!")
	String address;
	
	
	String status;
	
	int bankId;
	
	public Branch() {
	}
		
	public Branch(String name, String address, String status, int bankId) {
		this.name = name;
		this.address = address;
		this.status = status;
		this.bankId = bankId;
	}


	public Branch(String ifsc, String name, String address, String status,int bankId) {
		this.ifsc = ifsc;
		this.name = name;
		this.address = address;
		this.status = status;
		this.bankId = bankId;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	@Override
	public String toString() {
		return "Branch [ifsc=" + ifsc + ", name=" + name + ", address="
				+ address + ", status=" + status + ", bankId=" + bankId + "]";
	}
	
	
}
	
	
	
	
	
	
	
	
	
	
	

