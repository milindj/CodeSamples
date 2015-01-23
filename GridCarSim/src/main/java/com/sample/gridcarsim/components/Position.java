package com.sample.gridcarsim.components;

/**
 * A simple POJO class representing an object position in x,y form on a given 2D
 * grid.
 * 
 * @author Milind
 *
 */
public class Position {
	Integer x, y;

	public Position(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public Integer getY() {
		return y;
	}
}
