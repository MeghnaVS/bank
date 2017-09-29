package com.bms.exception;

@SuppressWarnings("serial")
public class InsertFailedException extends Exception{

	public InsertFailedException(){
	}

	public InsertFailedException(String message){
		super(message);
	}
	
}

