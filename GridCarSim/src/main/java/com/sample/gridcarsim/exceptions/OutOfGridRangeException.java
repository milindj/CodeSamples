/**
 * 
 */
package com.sample.gridcarsim.exceptions;

/**
 * @author milind
 *
 */
public class OutOfGridRangeException extends CarSimException {

	private static final long serialVersionUID = -680518855833767198L;

	@Override
	public String getMessage() {
		return "Can not go to the desired location, it is outside the defined grid area.";
	}
}
