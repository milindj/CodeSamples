package com.sample.gridcarsim;

import com.sample.gridcarsim.commands.ForwardCmd;
import com.sample.gridcarsim.commands.GpsReportCmd;
import com.sample.gridcarsim.commands.InitCmd;
import com.sample.gridcarsim.commands.LeftCmd;
import com.sample.gridcarsim.commands.RightCmd;
import com.sample.gridcarsim.components.Car;
import com.sample.gridcarsim.components.Grid;
import com.sample.gridcarsim.components.Position;
import com.sample.gridcarsim.exceptions.CarSimException;

/**
 * The main grid car simulator class which performs various function to move a
 * car around the grid.
 * 
 * @author milind
 *
 */
public class GridCarSimulator {
	private static GridCarSimulator carSimInstance;
	private Grid grid;
	private Car car;

	/**
	 * Singleton instance, we do not want multiple simulators kicked of on a
	 * single console.
	 * 
	 * @return
	 */
	public static GridCarSimulator getInstance() {
		if (carSimInstance == null) {
			carSimInstance = new GridCarSimulator();
			return carSimInstance;
		} else {
			return GridCarSimulator.carSimInstance;
		}
	}

	/**
	 * Initialized it with a default grid size of (10, 10, -10, -10)
	 */
	private GridCarSimulator() {
		this.grid = new Grid(10, 10, -10, -10);
		this.car = new Car(grid);
	}

	/**
	 * Moves the car forward in the grid.
	 * 
	 * @throws CarSimException
	 */
	public void moveForward() throws CarSimException {
		ForwardCmd fwdCmd = new ForwardCmd(this.car);
		this.grid.execute(fwdCmd);
	}

	/**
	 * Turns the car left.
	 * 
	 * @throws CarSimException
	 */
	public void turnLeft() throws CarSimException {
		LeftCmd leftCmd = new LeftCmd(this.car);
		this.grid.execute(leftCmd);
	}

	/**
	 * Turns the car right.
	 * 
	 * @throws CarSimException
	 */
	public void turnRight() throws CarSimException {
		RightCmd rightCmd = new RightCmd(this.car);
		this.grid.execute(rightCmd);
	}

	/**
	 * Initialized the position and direction of the car on the grid.
	 * @param x
	 * @param y
	 * @param direction
	 * @throws CarSimException
	 */
	public void initPosition(Integer x, Integer y, String direction) throws CarSimException {
		Position position = new Position(x, y);
		InitCmd initCmd = new InitCmd(this.car, position, direction);
		this.grid.execute(initCmd);
	}

	/**
	 * Fetches the position and direction of the car on the grid in a string form.
	 * @return
	 * @throws CarSimException
	 */
	public String gpsReport() throws CarSimException {
		GpsReportCmd gpsReportCmd = new GpsReportCmd(this.car);
		this.grid.execute(gpsReportCmd);
		return this.car.getGpsReport();
	}

	/**
	 * returns the car instance associated with this simulator.
	 * @return
	 */
	public Car getCar() {
		return car;
	}

}
