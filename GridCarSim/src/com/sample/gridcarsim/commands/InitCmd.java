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
public class InitCmd extends BaseCommand {
	
	Position initPosition;
	String initDirection;
	
	public InitCmd(Car car, Position initPosition, String initDirection) {
		super(car);
		this.initPosition = initPosition;
		this.initDirection = initDirection;
	}

	/* (non-Javadoc)
	 * @see com.sample.gridcarsim.Command#execute()
	 */
	public void execute() throws CarSimException {
		Car car = this.getCar();
		car.setPosition(this.initPosition); 
		car.setDirection(this.initDirection);
	}

}
