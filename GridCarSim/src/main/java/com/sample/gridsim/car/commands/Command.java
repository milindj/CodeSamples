package com.sample.gridsim.car.commands;

import com.sample.gridsim.exceptions.GridSimException;

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
	 * @throws GridSimException
	 */
	public void execute() throws GridSimException;
}
