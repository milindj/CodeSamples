package com.sample.gridcarsim.components;

import com.sample.gridcarsim.components.DirectionMap.Direction;
import com.sample.gridcarsim.exceptions.InvalidDirectionException;

public class Car {
	private Position position;
	private Integer directionDegrees;
	private String gpsReport;

	public Integer getDirectionDegrees() {
		return directionDegrees;
	}

	public void changeDirectionDegrees(Integer directionDegrees) throws InvalidDirectionException {
		if (directionDegrees < -360 || directionDegrees > 360) {
			throw new InvalidDirectionException(directionDegrees + "degrees");
		}
		int directionSum = this.directionDegrees + directionDegrees;
		if (directionSum < 0) {
			directionSum = directionSum + 360;
		} else if (directionSum >= 360) {
			directionSum = directionSum - 360;
		}
		this.directionDegrees = directionSum;
	}

	public void setDirection(String strDirection) throws InvalidDirectionException {
		 Direction direction = Direction.valueOf(strDirection);
		 if (direction == null){
			 throw new InvalidDirectionException(strDirection);
		 }else{
			 this.directionDegrees = direction.degree();
		 }
		
	}

	public Direction getDirection() {
		return DirectionMap.degreeToDirection(this.directionDegrees);
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
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
