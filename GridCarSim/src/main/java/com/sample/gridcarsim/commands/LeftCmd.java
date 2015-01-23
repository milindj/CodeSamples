package com.sample.gridcarsim.commands;

import com.sample.gridcarsim.components.Car;
import com.sample.gridcarsim.exceptions.CarSimException;

/**
 * Command to turn the car left.
 * 
 * @author milind
 *
 */
public class LeftCmd extends BaseCommand {

	/**
	 * Construct the command with the car to be execute upon as the input.
	 * 
	 * @param car
	 */
	public LeftCmd(Car car) {
		super(car);
	}

	/**
	 * Implementation to turn the car left by 90 degrees. (non-Javadoc)
	 * 
	 * @see com.sample.gridcarsim.Command#execute()
	 */
	public void execute() throws CarSimException {
		this.getCar().changeDirectionDegrees(90);
	}

}
