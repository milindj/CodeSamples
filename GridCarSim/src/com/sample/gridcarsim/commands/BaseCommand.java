package com.sample.gridcarsim.commands;

import com.sample.gridcarsim.components.Car;
import com.sample.gridcarsim.exceptions.CarPositionNotInitialised;

public abstract class BaseCommand implements Command{
	private Car car;
	
	public Car getCar() throws CarPositionNotInitialised {
		if (this.car.getPosition() == null){
			throw new CarPositionNotInitialised();
		}
		return car;
	}

	public BaseCommand(Car car) {
		this.car = car;
	}
}
