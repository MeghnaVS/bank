package com.bms.exception;

@SuppressWarnings("serial")
public class AccountException extends Exception{

	public AccountException(){
	}

	public AccountException(String message){
		super(message);
	}
	
}

