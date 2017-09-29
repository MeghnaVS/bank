package com.bms.exception;

@SuppressWarnings("serial")
public class BankDoesNotExistException extends Exception {

	public BankDoesNotExistException(String message){
		super(message);
	}
	
}
