/**
 * 
 */
package com.sample.gridsim.exceptions;

/**
 * An exception which represents the erroneous state when the car is made to put
 * in a position which is outside the grid's range.
 * 
 * @author Milind
 *
 */
public class OutOfGridRangeException extends GridSimException {

	private static final long serialVersionUID = -680518855833767198L;

	@Override
	public String getMessage() {
		return "Location invalid, it is outside the defined grid area.";
	}
}
