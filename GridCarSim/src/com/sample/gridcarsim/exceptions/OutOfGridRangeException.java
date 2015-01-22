/**
 * 
 */
package com.sample.gridcarsim.exceptions;

/**
 * @author milind
 *
 */
public class OutOfGridRangeException extends CarSimException {
	@Override
	public String getMessage() {
		return "Can not go to the desired location, it is outside the grid area.";
	}
}
