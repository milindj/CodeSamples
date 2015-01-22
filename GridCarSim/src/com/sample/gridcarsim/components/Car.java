package com.sample.gridcarsim.components;

public class Car {
private Position position;
private Integer directionDegrees = 0;

public Integer getDirectionDegrees() {
	return directionDegrees;
}

public void changeDirectionDegrees(Integer directionDegrees) {
	if (directionDegrees< -360 || directionDegrees >360){
		//TODO throw exception
	}
	int directionSum = this.directionDegrees + directionDegrees;
	if(directionSum<0){
		directionSum = directionSum + 360;
	}else if(directionSum>=360){
		directionSum = directionSum - 360;
	}
	
	this.directionDegrees = directionSum;
}

public Position getPosition() {
	return position;
}

public void setPosition(Position position) {
	this.position = position;
}







}
