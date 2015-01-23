package com.sample.gridcarsim.exceptions;

/**
 * A generic exception for car simulator which is extended by a set of other
 * specific car simulator exceptions.
 * 
 * @author Milind.
 *
 */
public abstract class CarSimException extends Exception {

	private static final long serialVersionUID = -2422551725941265840L;

	public CarSimException() {
		super();
	}

	public CarSimException(Throwable cause) {
		super(cause);
	}

}
