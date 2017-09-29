package com.bms.exception;

@SuppressWarnings("serial")
public class LowBalanceException extends Exception {
	public LowBalanceException(){
		
	}
	public LowBalanceException(String message) {
		super(message);
	}
	
}
