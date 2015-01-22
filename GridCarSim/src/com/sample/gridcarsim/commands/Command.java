/**
 * 
 */
package com.sample.gridcarsim.commands;

import com.sample.gridcarsim.exceptions.CarSimException;

/**
 * @author milind
 *
 */
public interface Command {
	public void execute() throws CarSimException;
}
