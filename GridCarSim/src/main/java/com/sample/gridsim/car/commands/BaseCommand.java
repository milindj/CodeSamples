package com.sample.gridsim.car.commands;

import com.sample.gridsim.car.components.Car;
import com.sample.gridsim.exceptions.PositionNotInitialised;

/**
 * An abstract class to define common behavior of all our simulator commands.
 * 
 * @author milind
 *
 */
public abstract class BaseCommand implements Command {
	private Car car;

	/**
	 * Construct the command with the car to be execute upon as the input.
	 * 
	 * @param car
	 */
	public BaseCommand(Car car) {
		this.car = car;
	}

	/**
	 * Gets the car instance after validating its fit enough to execute commands on it.
	 * 
	 * @return
	 * @throws PositionNotInitialised
	 */
	public Car getCar() throws PositionNotInitialised {
		this.validateCar(this.car);
		return car;
	}

	/**
	 * Validate that the car is initialized properly
	 * 
	 * @param car
	 * @throws PositionNotInitialised
	 */
	protected void validateCar(Car car) throws PositionNotInitialised {
		if (car.getPosition() == null || car.getDirectionDegrees() == null) {
			throw new PositionNotInitialised();
		}
	}
}
