/**
 * 
 */
package com.sample.gridcarsim.commands;

import com.sample.gridcarsim.components.Car;
import com.sample.gridcarsim.exceptions.CarPositionNotInitialised;

/**
 * @author milind
 *
 */
public class LeftCmd extends BaseCommand {

	public LeftCmd(Car car) {
		super(car);
	} 

	/* (non-Javadoc)
	 * @see com.sample.gridcarsim.Command#execute()
	 */
	public void execute() throws CarPositionNotInitialised {
		this.getCar().changeDirectionDegrees(90);
	}

}
