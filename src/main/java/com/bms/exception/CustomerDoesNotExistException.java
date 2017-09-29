package com.bms.exception;

@SuppressWarnings("serial")
public class CustomerDoesNotExistException extends Exception{

	public CustomerDoesNotExistException(){
	}

	public CustomerDoesNotExistException(String message){
		super(message);
	}
	
}

