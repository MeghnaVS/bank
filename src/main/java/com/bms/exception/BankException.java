package com.bms.exception;

@SuppressWarnings("serial")
public class BankException extends Exception{

	public BankException(){
	}

	public BankException(String message){
		super(message);
	}
	
}

