package com.bms.exception;

@SuppressWarnings("serial")
public class DeleteFailedException extends Exception{

	public DeleteFailedException(){
	}

	public DeleteFailedException(String message){
		super(message);
	}
	
}

