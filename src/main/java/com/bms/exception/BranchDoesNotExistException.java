package com.bms.exception;

@SuppressWarnings("serial")
public class BranchDoesNotExistException extends Exception {

	public BranchDoesNotExistException(String message){
		super(message);
	}
	
}
