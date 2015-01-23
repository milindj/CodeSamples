package com.sample.gridcarsim.commands;

import com.sample.gridcarsim.exceptions.CarSimException;

/**
 * Interface for all the commands in our simulator.
 * 
 * @author milind
 *
 */
public interface Command {
	/**
	 * Executes this command in the simulator.
	 * 
	 * @throws CarSimException
	 */
	public void execute() throws CarSimException;
}
