package com.sample.gridsim.exceptions;

import com.sample.gridsim.DirectionMap.Direction;

/**
 * Exception to represent a bad direction name, or the one which is not found in
 * {@link Direction}
 * 
 * @author Milind
 *
 */
public class InvalidDirectionException extends GridSimException {

	private static final long serialVersionUID = 2652271742542961676L;
	/**
	 * The invalid direction string which caused this exception.
	 */
	String invalidDirection;

	public InvalidDirectionException(String invalidDirection, IllegalArgumentException e) {
		super(e);
		this.invalidDirection = invalidDirection;
	}

	public InvalidDirectionException(String invalidDirection) {
		this.invalidDirection = invalidDirection;
	}

	/**
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return "The direction : " + this.invalidDirection + " is not valid.";
	}

}
