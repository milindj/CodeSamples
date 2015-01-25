/**
 * 
 */
package com.sample.gridsim.car.commands;

import com.sample.gridsim.car.components.Car;
import com.sample.gridsim.exceptions.GridSimException;

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
	 * @see com.sample.gridsim.car.commands.Command#execute()
	 */
	public void execute() throws GridSimException {
		this.getCar().changeDirectionDegrees(-90);
	}

}
