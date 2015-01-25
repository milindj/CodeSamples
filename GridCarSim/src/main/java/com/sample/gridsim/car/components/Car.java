package com.sample.gridsim.car.components;

import com.sample.gridsim.DirectionMap;
import com.sample.gridsim.Grid;
import com.sample.gridsim.Position;
import com.sample.gridsim.DirectionMap.Direction;
import com.sample.gridsim.exceptions.InvalidDirectionException;
import com.sample.gridsim.exceptions.OutOfGridRangeException;

/**
 * The car class which has functionalities to turn and move across the given
 * grid.
 * 
 * @author Milind
 *
 */
public class Car {
	private Position position;
	private Integer directionDegrees;
	private String gpsReport;
	private Grid grid;

	/**
	 * Constructor for the car class, it takes grid as an argument across which
	 * the car moves.
	 * 
	 * @param grid
	 */
	public Car(Grid grid) {
		this.grid = grid;
	}

	/**
	 * Changes direction of the car by given degrees. The direction change is in
	 * the range of 0-360deg. For turning left / right it is changed by +/-90
	 * deg.
	 * 
	 * @param directionDegrees
	 * @throws InvalidDirectionException
	 */
	public void changeDirectionDegrees(Integer directionDegrees) throws InvalidDirectionException {
		if (directionDegrees < -360 || directionDegrees > 360) {
			throw new InvalidDirectionException(directionDegrees + "degrees");
		}
		// Calculate direction.
		int directionSum = this.directionDegrees + directionDegrees;
		// Normalize it to 0 to 360.
		if (directionSum < 0) {
			directionSum = directionSum + 360;
		} else if (directionSum >= 360) {
			directionSum = directionSum - 360;
		}
		this.directionDegrees = directionSum;
	}

	/**
	 * Parses the string into a defined direction and set it into degrees for
	 * the car. The direction is defined in {@link DirectionMap} . Further it
	 * throws an invalid exception if the direction definition does not exist.
	 * 
	 * @param strDirection
	 * @throws InvalidDirectionException
	 */
	public void setDirection(String strDirection) throws InvalidDirectionException {
		try {
			Direction direction = Direction.valueOf(strDirection);
			this.directionDegrees = direction.degree();
		} catch (IllegalArgumentException e) {
			throw new InvalidDirectionException(strDirection, e);
		}

	}

	/**
	 * Gets the Direction of the car, it converts from degrees to a predefined
	 * direction.
	 * 
	 * @return the Direction
	 */
	public Direction getDirection() {
		return DirectionMap.degreeToDirection(this.directionDegrees);
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Validates and sets the new position of the car. Throws an exception if
	 * the position is out side the grid.
	 * 
	 * @param position
	 * @throws OutOfGridRangeException
	 */
	public void setPosition(Position position) throws OutOfGridRangeException {
		if (!this.grid.validateGridPosition(position)) {
			throw new OutOfGridRangeException();
		}
		this.position = position;
	}

	/**
	 * @return the directionDegrees
	 */
	public Integer getDirectionDegrees() {
		return directionDegrees;
	}

	/**
	 * @return the gpsReport
	 */
	public String getGpsReport() {
		return gpsReport;
	}

	/**
	 * @param gpsReport
	 *            the gpsReport to set
	 */
	public void setGpsReport(String gpsReport) {
		this.gpsReport = gpsReport;
	}

}
