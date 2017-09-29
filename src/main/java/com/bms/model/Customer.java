package com.bms.model;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class Customer {

	int customerId;

	@NotEmpty(message = "Name is required!")
	@Size(min = 4, max = 20, message = "Name length should be between {2} & {1}!")
	String customerName;

	@NotEmpty(message = "Can't be left blank !!")
	@Size(max=8,min = 4,message = "Password can have Max {1} and Min {2} characters!!")
	String password;

	@NotNull
	@Min(20)
	@Max(100)
	int age;

	@Min(value=1000000000, message="Can have only 10 digits!!")
	long phone;

	@Email
	@NotEmpty(message = "Can't be left blank !!")
	String email;

	@NotEmpty(message = "Please select a value !!")
	String type;

	@NotEmpty(message = "Please select a value !!")
	String ifsc;

	@Valid
	Address address;

	public Customer() {
		super();
	}

	public Customer(String customerName, String password, int age, long phone,
			String email, String type, String ifsc, Address address) {
		super();
		this.customerName = customerName;
		this.password = password;
		this.age = age;
		this.phone = phone;
		this.email = email;
		this.type = type;
		this.ifsc = ifsc;
		this.address = address;
	}

	public Customer(int customerId, String customerName, String password,
			int age, long phone, String email, String type, String ifsc,
			Address address) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.password = password;
		this.age = age;
		this.phone = phone;
		this.email = email;
		this.type = type;
		this.ifsc = ifsc;
		this.address = address;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName="
				+ customerName + ", password=" + password + ", age=" + age
				+ ", phone=" + phone + ", email=" + email + ", type=" + type
				+ ", ifsc=" + ifsc + ", address=" + address + "]";
	}

}