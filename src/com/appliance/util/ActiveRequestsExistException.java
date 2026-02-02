package com.appliance.util;

public class ActiveRequestsExistException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveRequestsExistException(String message) {
		super(message);
	}
}
