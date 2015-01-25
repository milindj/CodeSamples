/**
 * 
 */
package com.sample.gridsim;

import com.sample.gridsim.exceptions.GridSimException;

/**
 * An interface which defines a basic contract of commands to move an object
 * around the grid.
 * 
 * @author Milind
 *
 */
public interface GridSimulator {

	/**
	 * Moves the car forward in the grid.
	 * 
	 * @throws GridSimException
	 */
	public void moveForward() throws GridSimException;

	/**
	 * Turns the car left.
	 * 
	 * @throws GridSimException
	 */
	public void turnLeft() throws GridSimException;

	/**
	 * Turns the car right.
	 * 
	 * @throws GridSimException
	 */
	public void turnRight() throws GridSimException;

	/**
	 * Initialized the position and direction of the car on the grid.
	 * 
	 * @param x
	 * @param y
	 * @param direction
	 * @throws GridSimException
	 */
	public void initPosition(Integer x, Integer y, String direction) throws GridSimException;

	/**
	 * Fetches the position and direction of the car on the grid in a string
	 * form.
	 * 
	 * @return
	 * @throws GridSimException
	 */
	public String gpsReport() throws GridSimException;
}
