package com.bms.exception;

@SuppressWarnings("serial")
public class AccountDoesNotExistException extends Exception{

	public AccountDoesNotExistException(){
	}

	public AccountDoesNotExistException(String message){
		super(message);
	}
	
}

