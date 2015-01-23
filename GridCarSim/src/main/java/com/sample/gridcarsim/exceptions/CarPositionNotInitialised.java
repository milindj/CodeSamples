package com.sample.gridcarsim.exceptions;

/**
 * An exception representing the state where a command was issues without
 * initializing the car.
 * 
 * @author Milind
 *
 */
public class CarPositionNotInitialised extends CarSimException {

	private static final long serialVersionUID = 1978492660536632763L;

	@Override
	public String getMessage() {
		return "Car position not initialised, use init first.";
	}

}
