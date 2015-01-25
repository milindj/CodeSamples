package com.sample.gridsim.car.components;

import com.sample.gridsim.Grid;
import com.sample.gridsim.GridSimulator;
import com.sample.gridsim.Position;
import com.sample.gridsim.car.commands.ForwardCmd;
import com.sample.gridsim.car.commands.GpsReportCmd;
import com.sample.gridsim.car.commands.InitCmd;
import com.sample.gridsim.car.commands.LeftCmd;
import com.sample.gridsim.car.commands.RightCmd;
import com.sample.gridsim.exceptions.GridSimException;

/**
 * The main grid car simulator class which performs various function to move a
 * car around the grid.
 * 
 * @author milind
 *
 */
public class GridCarSimImpl implements GridSimulator{
	private static GridCarSimImpl carSimInstance;
	private Grid grid;
	private Car car;

	/**
	 * Singleton instance, we do not want multiple simulators kicked of on a
	 * single console.
	 * 
	 * @return
	 */
	public static GridCarSimImpl getInstance() {
		if (carSimInstance == null) {
			carSimInstance = new GridCarSimImpl();
			return carSimInstance;
		} else {
			return GridCarSimImpl.carSimInstance;
		}
	}

	/**
	 * Initialized it with a default grid size of (10, 10, -10, -10)
	 */
	private GridCarSimImpl() {
		this.grid = new Grid(10, 10, -10, -10);
		this.car = new Car(grid);
	}

	/**
	 * Moves the car forward in the grid.
	 * 
	 * @throws GridSimException
	 */
	public void moveForward() throws GridSimException {
		ForwardCmd fwdCmd = new ForwardCmd(this.car);
		this.grid.execute(fwdCmd);
	}

	/**
	 * Turns the car left.
	 * 
	 * @throws GridSimException
	 */
	public void turnLeft() throws GridSimException {
		LeftCmd leftCmd = new LeftCmd(this.car);
		this.grid.execute(leftCmd);
	}

	/**
	 * Turns the car right.
	 * 
	 * @throws GridSimException
	 */
	public void turnRight() throws GridSimException {
		RightCmd rightCmd = new RightCmd(this.car);
		this.grid.execute(rightCmd);
	}

	/**
	 * Initialized the position and direction of the car on the grid.
	 * @param x
	 * @param y
	 * @param direction
	 * @throws GridSimException
	 */
	public void initPosition(Integer x, Integer y, String direction) throws GridSimException {
		Position position = new Position(x, y);
		InitCmd initCmd = new InitCmd(this.car, position, direction);
		this.grid.execute(initCmd);
	}

	/**
	 * Fetches the position and direction of the car on the grid in a string form.
	 * @return
	 * @throws GridSimException
	 */
	public String gpsReport() throws GridSimException {
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
