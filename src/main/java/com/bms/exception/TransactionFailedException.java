package com.bms.exception;

@SuppressWarnings("serial")
public class TransactionFailedException extends Exception{

	public TransactionFailedException(){
	
	}

	public TransactionFailedException(String message){
		super(message);
	}
	
}

