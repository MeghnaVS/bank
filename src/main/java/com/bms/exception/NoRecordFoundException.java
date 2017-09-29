package com.bms.exception;

@SuppressWarnings("serial")
public class NoRecordFoundException extends Exception{

	public NoRecordFoundException(){
	}

	public NoRecordFoundException(String message){
		super(message);
	}
	
}

