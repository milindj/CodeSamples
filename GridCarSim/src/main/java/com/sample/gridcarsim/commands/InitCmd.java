package com.sample.gridcarsim.commands;

import com.sample.gridcarsim.components.Car;
import com.sample.gridcarsim.components.Position;
import com.sample.gridcarsim.exceptions.CarPositionNotInitialised;
import com.sample.gridcarsim.exceptions.CarSimException;

/**
 * Command to initialized the car on the grid.
 * @author milind
 *
 */
public class InitCmd extends BaseCommand {
	
	Position initPosition;
	String initDirection;
	
	/**
	 * Construct the init command, to initiate the input car 
	 * to the input position and direction.
	 * 
	 * @param car
	 * @param initPosition
	 * @param initDirection
	 */
	public InitCmd(Car car, Position initPosition, String initDirection) {
		super(car);
		this.initPosition = initPosition;
		this.initDirection = initDirection;
	}

	/**
	 * We do not validate the pre-command condition of the car being initiated.
	 * This is because we are initializing the car here.
	 */
	@Override
	protected void validateCar(Car car) throws CarPositionNotInitialised {};

	/**
	 * Implementation to initialized the car on the grid.
	 * @see com.sample.gridcarsim.Command#execute()
	 */
	public void execute() throws CarSimException {
		Car car = this.getCar();
		car.setPosition(this.initPosition); 
		car.setDirection(this.initDirection);
	}

}
