package com.bms.exception;

@SuppressWarnings("serial")
public class UpdateFailedException extends Exception{

	public UpdateFailedException(){
	}

	public UpdateFailedException(String message){
		super(message);
	}
	
}

