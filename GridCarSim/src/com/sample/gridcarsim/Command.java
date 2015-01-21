/**
 * 
 */
package com.sample.gridcarsim;

/**
 * @author milind
 *
 */
public interface Command {
	public void execute() throws OutOfGridRangeException;
}
