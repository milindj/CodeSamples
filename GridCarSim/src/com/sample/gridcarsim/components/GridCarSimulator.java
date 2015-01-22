package com.sample.gridcarsim.components;

import com.sample.gridcarsim.commands.ForwardCmd;
import com.sample.gridcarsim.commands.GpsReportCmd;
import com.sample.gridcarsim.commands.InitCmd;
import com.sample.gridcarsim.commands.LeftCmd;
import com.sample.gridcarsim.commands.RightCmd;
import com.sample.gridcarsim.exceptions.CarSimException;
import com.sample.gridcarsim.exceptions.InvalidDirectionException;
import com.sample.gridcarsim.exceptions.InvalidGridSizeException;

public class GridCarSimulator {
	private static GridCarSimulator carSimInstance;
	private Grid grid;
	private Car car;
	
	public static GridCarSimulator getInstance() throws InvalidGridSizeException {
		if (carSimInstance==null){
			carSimInstance = new GridCarSimulator();
			return carSimInstance;
		}else{
			return GridCarSimulator.carSimInstance;
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
		InitCmd initCmd = new InitCmd(this.car, position, direction);
		this.grid.execute(initCmd);
	}
	
	public String gpsReport() throws CarSimException{
		GpsReportCmd  gpsReportCmd = new GpsReportCmd(this.car);
		this.grid.execute(gpsReportCmd);
		return this.car.getGpsReport();		
	}
	
}
