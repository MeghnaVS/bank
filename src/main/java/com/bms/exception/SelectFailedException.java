package com.bms.exception;

@SuppressWarnings("serial")
public class SelectFailedException extends Exception {
	public SelectFailedException(String message){
		super(message);
	}
}
