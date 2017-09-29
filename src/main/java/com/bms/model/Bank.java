package com.bms.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class Bank {

	int bankId;

	@NotEmpty(message = "Name is required")
	@Size(min = 3, max = 20, message = "Name length should be between {2} & {1}!")
	String name;

	@NumberFormat(style = Style.NUMBER)
	int strength;

	@NotEmpty(message = "Type is required!")
	String type;

	public Bank() {

	}

	public Bank(String name, int strength, String type) {

		this.name = name;
		this.strength = strength;
		this.type = type;
	}

	public Bank(int bankId, String name, int strength, String type) {

		this.bankId = bankId;
		this.name = name;
		this.strength = strength;
		this.type = type;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/*
	 * @Override public String toString() { return "Bank [bankId=" + bankId +
	 * ", name=" + name + ", strength=" + strength + ", type=" + type + "]"; }
	 */

}
