/**
 * 
 */
package com.sample.gridcarsim.commands;

import com.sample.gridcarsim.components.Car;
import com.sample.gridcarsim.components.Position;
import com.sample.gridcarsim.exceptions.CarSimException;

/**
 * @author milind
 *
 */
public class ForwardCmd extends BaseCommand {

	/**
	 * Default moving unit as 1.
	 */
	private Integer unit = 1;
	
	public ForwardCmd(Car car) {
		super(car);
	}
	
	/* (non-Javadoc)
	 * @see com.sample.gridcarsim.Command#execute()
	 */
	public void execute() throws CarSimException {
		Position carPosition = this.getCar().getPosition();
		Integer carDirection = this.getCar().getDirectionDegrees();
		// x = x + Ceil(cos(theta));
		carPosition.incrementX(unit * (int)Math.cos((Math.toRadians(carDirection))));
		// y = y + Ceil(sin(theta)); 
		carPosition.incrementY(unit * (int)Math.sin((Math.toRadians(carDirection))));
	}

}
