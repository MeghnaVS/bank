package com.bms.exception;

@SuppressWarnings("serial")
public class SaveFailedException extends Exception {
	public SaveFailedException(String message){
		super(message);
	}
}
