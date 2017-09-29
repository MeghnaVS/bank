package com.bms.exception;

@SuppressWarnings("serial")
public class TransactionNotFoundException extends Exception{

	public TransactionNotFoundException(){
	}

	public TransactionNotFoundException(String message){
		super(message);
	}
	
}

