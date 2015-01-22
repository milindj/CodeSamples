package com.sample.gridcarsim.exceptions;


public class InvalidDirectionException extends CarSimException {
	
	private static final long serialVersionUID = 2652271742542961676L;
	String invalidDirection; 
	
	public InvalidDirectionException(String invalidDirection) {
		this.invalidDirection = invalidDirection;
	}

	/**
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return "The direction : " + this.invalidDirection + " is not valid; " + super.getMessage();
	}

	
	
}
