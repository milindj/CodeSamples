/**
 * 
 */
package com.sample.gridcarsim.commands;

import com.sample.gridcarsim.components.Car;
import com.sample.gridcarsim.exceptions.CarSimException;

/**
 * Command to turn the car right.
 * 
 * @author milind
 *
 */
public class RightCmd extends BaseCommand {

	/**
	 * Construct the command with the car to be execute upon as the input.
	 * 
	 * @param car
	 */
	public RightCmd(Car car) {
		super(car);
	}

	/**
	 * Implementation to turn the car right by 90 degrees.
	 * 
	 * @see com.sample.gridcarsim.commands.Command#execute()
	 */
	public void execute() throws CarSimException {
		this.getCar().changeDirectionDegrees(-90);
	}

}
