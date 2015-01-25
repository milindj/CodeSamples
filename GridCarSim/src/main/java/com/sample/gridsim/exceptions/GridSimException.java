package com.sample.gridsim.exceptions;

/**
 * A generic exception for grid simulator, it is the base exception for other
 * specific simulator exceptions like {@link OutOfGridRangeException} and
 * {@link InvalidDirectionException}.
 * 
 * @author Milind.
 *
 */
public abstract class GridSimException extends Exception {

	private static final long serialVersionUID = -2422551725941265840L;

	public GridSimException() {
		super();
	}

	public GridSimException(Throwable cause) {
		super(cause);
	}

}
