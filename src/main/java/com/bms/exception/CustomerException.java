package com.bms.exception;

@SuppressWarnings("serial")
public class CustomerException extends Exception{

	public CustomerException(){
	}

	public CustomerException(String message){
		super(message);
	}
	
}

