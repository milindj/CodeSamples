package com.sample.gridcarsim.components;

import com.sample.gridcarsim.commands.ForwardCmd;
import com.sample.gridcarsim.commands.GpsReportCmd;
import com.sample.gridcarsim.commands.InitCmd;
import com.sample.gridcarsim.commands.LeftCmd;
import com.sample.gridcarsim.commands.RightCmd;
import com.sample.gridcarsim.exceptions.CarSimException;
import com.sample.gridcarsim.exceptions.InvalidGridSizeException;

public class GridCarSimulator {
	
	public enum Direction{
		NORTH(90),
		WEST(180),
		EAST(0),
		SOUTH(270);
		Direction(Integer degree){
			this.degree = degree;
		}
		private final Integer degree;
		public Integer degree(){
			return degree;
		}
	}

	private static GridCarSimulator instance;
	private Grid grid;
	private Car car;
	
	public static GridCarSimulator getInstance() throws InvalidGridSizeException {
		if (instance==null){
			return	GridCarSimulator.instance =  new GridCarSimulator();
		}else{
			return GridCarSimulator.instance;
		}
	}

	private GridCarSimulator() throws InvalidGridSizeException{
		this.grid = new Grid(10, 10, -10, -10); 
		this.car = new Car();
	}
	
	public void moveForward() throws CarSimException{
		ForwardCmd fwdCmd = new ForwardCmd(this.car);
		this.grid.execute(fwdCmd);
	}

	public void turnLeft() throws CarSimException{
		LeftCmd leftCmd = new LeftCmd(this.car);
		this.grid.execute(leftCmd);
	}
	
	public void turnRight() throws CarSimException{
		RightCmd rightCmd = new RightCmd(this.car);
		this.grid.execute(rightCmd);
	}
	
	public void initPosition(Integer x, Integer y, String direction) throws CarSimException{
		Position position = new Position(x,y);
		this.car.setPosition(position);
		InitCmd initCmd = new InitCmd(this.car);
		this.grid.execute(initCmd);
	}
	
	public String gpsReport() throws CarSimException{
		GpsReportCmd  gpsReportCmd = new GpsReportCmd(this.car);
		this.grid.execute(gpsReportCmd);
		//TODO pos , dir
		return this.car.getPosition().toString();		
	}
	
	public void setDirection(String direction) throws InvalidDirectionException {
		Direction.valueOf(direction).degree();
		Integer directionDegrees;
		switch (direction) {
		case "NORTH":
			directionDegrees = Direction.NORTH.degree;
			break;
		case "WEST":
			directionDegrees = 180;
			break;
		case "SOUTH":
			directionDegrees = 270;
			break;
		case "EAST":
			directionDegrees = 0;
			break;
		default:
			throw new InvalidDirectionException();
		}
	}
	
}
