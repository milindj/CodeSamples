package com.sample.gridcarsim.commands;

import com.sample.gridcarsim.components.Car;
import com.sample.gridcarsim.components.Position;
import com.sample.gridcarsim.exceptions.CarSimException;

/**
 * Command to move the car forward.
 * @author milind
 *
 */
public class ForwardCmd extends BaseCommand {

	/**
	 * Default moving unit as 1.
	 */
	private Integer unit = 1;

	/**
	 * Construct the command with the car to be execute upon as the input.
	 * 
	 * @param car
	 */
	public ForwardCmd(Car car) {
		super(car);
	}
	
	/**
	 * Implementation of the logic to move the car front.
	 * @see com.sample.gridcarsim.Command#execute()
	 */
	public void execute() throws CarSimException {
		Position carPosition = this.getCar().getPosition();
		Integer carDirection = this.getCar().getDirectionDegrees();
		// x = x + cos(theta);
		Integer newX = carPosition.getX() + (unit * (int) Math.cos((Math.toRadians(carDirection))));
		// y = y + (sin(theta);
		Integer newY = carPosition.getY() + (unit * (int) Math.sin((Math.toRadians(carDirection))));
		Position newPosition = new Position(newX, newY);
		this.getCar().setPosition(newPosition);
	}

}
