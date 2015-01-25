package com.sample.gridsim.car.commands;

import com.sample.gridsim.Position;
import com.sample.gridsim.car.components.Car;
import com.sample.gridsim.exceptions.GridSimException;

/**
 * Command to generate and store the GPS report.
 * 
 * @author milind
 *
 */
public class GpsReportCmd extends BaseCommand {

	/**
	 * Construct the command with the car to be execute upon as the input.
	 * 
	 * @param car
	 */
	public GpsReportCmd(Car car) {
		super(car);
	}

	/**
	 * Implementation to generate and store the GPS report.
	 */
	public void execute() throws GridSimException {
		Position carPosition = this.getCar().getPosition();
		String gpsReport = "Output: " + carPosition.getX() + ", " 
							+ carPosition.getY() + ", " + this.getCar().getDirection();
		this.getCar().setGpsReport(gpsReport);
	}
}
