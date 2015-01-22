package com.sample.gridcarsim.components;

public class Position {
	Integer x, y;

	public Position(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	public void incrementX(Integer count) {
		this.x = this.x + count;
	}

	public void incrementY(Integer count) {
		this.y = this.y + count;
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
