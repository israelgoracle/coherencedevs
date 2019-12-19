package com.telefonica.tran.lib.userdetails.domain.exception;

public class IllegalSecurityProfileException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalSecurityProfileException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalSecurityProfileException(String message) {
		super(message);
	}

}
