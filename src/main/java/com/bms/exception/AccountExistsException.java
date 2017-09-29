package com.bms.exception;

@SuppressWarnings("serial")
public class AccountExistsException extends Exception{
	public AccountExistsException(String message){
		super(message);
	}
	
}

