package com.sample.gridsim.exceptions;

/**
 * An exception representing the state where a command was issued without
 * initializing the object on the grid.
 * 
 * @author Milind
 *
 */
public class PositionNotInitialised extends GridSimException {

	private static final long serialVersionUID = 1978492660536632763L;

	@Override
	public String getMessage() {
		return "Car position not initialised, use init first.";
	}

}
