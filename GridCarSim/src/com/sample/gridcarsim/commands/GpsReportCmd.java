package com.sample.gridcarsim.commands;

import com.sample.gridcarsim.components.Car;
import com.sample.gridcarsim.components.Position;
import com.sample.gridcarsim.exceptions.CarSimException;

public class GpsReportCmd extends BaseCommand {

	public GpsReportCmd(Car car) {
		super(car);
	}

	public void execute() throws CarSimException {
		Position carPosition = this.getCar().getPosition();
		String gpsReport = "Output: " + carPosition.getX() + ", " + carPosition.getY() 
				+ ", " + this.getCar().getDirection();
		this.getCar().setGpsReport(gpsReport);
	}

}
