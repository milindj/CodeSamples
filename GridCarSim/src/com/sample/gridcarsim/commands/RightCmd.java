/**
 * 
 */
package com.sample.gridcarsim.commands;

import com.sample.gridcarsim.components.Car;
import com.sample.gridcarsim.exceptions.CarSimException;
import com.sample.gridcarsim.exceptions.OutOfGridRangeException;

/**
 * @author milind
 *
 */
public class RightCmd extends BaseCommand {

	public RightCmd(Car car) {
		super(car);
	} 

	/**
	 * 
	 * @see com.sample.gridcarsim.commands.Command#execute()
	 */
	public void execute() throws CarSimException {
		this.getCar().changeDirectionDegrees(-90);
	}

}
