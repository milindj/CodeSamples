/**
 * 
 */
package com.sample.gridcarsim;

/**
 * @author milind
 *
 */
public class OutOfGridRangeException extends Exception {

	@Override
	public String getMessage() {
		return "Can not go to the desired location, it is outside the grid.";
	}

}
