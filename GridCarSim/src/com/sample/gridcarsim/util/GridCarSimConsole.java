package com.sample.gridcarsim.util;

import java.io.IOException;

import com.sample.gridcarsim.components.GridCarSimulator;
import com.sample.gridcarsim.exceptions.CarSimException;
import com.sample.gridcarsim.exceptions.InvalidGridSizeException;

import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.ShellFactory;

public class GridCarSimConsole {

	public static final String QUICK_HELP = ("########   QUICK HELP   ########\n")
			+("##\n")
			+("##\n")
			+("## '?list' : for help on list of commands & descriptions\n")
			+("## '?help <cmd>' : for help on specific command\n")
			+("##\n")
			+("##\n")
			+("## 'exit' : to exit the application\n")
			+("##\n")
			+("## 'help' : to see this menu again \n");
	
	public GridCarSimConsole(GridCarSimulator gridCarSimulator) {
		this.gridCarSimulator = gridCarSimulator;
	}
	
	private GridCarSimulator gridCarSimulator;
	
	@Command(name = "help")
	public String help(){
		return QUICK_HELP;
	}

	@Command(name = "INIT", header = "..")
	public String init(@Param(name="x", description="X position of the car")Integer x,
			@Param(name="y", description="Y position of the car")Integer y,
			@Param(name="direction", description="Direction of the car(NORTH|SOUTH|EAST|WEST)")String direction) {
		try {
			this.gridCarSimulator.initPosition(x, y, direction);
		} catch (CarSimException e) {
			return "Error initialising the car; " + e.getMessage();
		}
		return "";
	}
	
	@Command(name = "FORWARD", header = "..")
	public String forward() {
		try {
			this.gridCarSimulator.moveForward();
		} catch (CarSimException e) {
			return "Error in moving the car; " + e.getMessage();
		}
		return "";
	}

	@Command(name = "LEFT", header = "..")
	public String left() {
		try {
			this.gridCarSimulator.turnLeft();
		} catch (CarSimException e) {
			return "Error in turning the car; " + e.getMessage();
		}
		return "";
	}
	
	@Command(name = "RIGHT", header = "..")
	public String right() {
		try {
			this.gridCarSimulator.turnRight();
		} catch (CarSimException e) {
			return "Error in turning the car; " + e.getMessage();
		}
		return "";
	}
	
	@Command(name = "GPS_REPORT", header = "..")
	public String gpsReport() {
		try {
			this.gridCarSimulator.gpsReport();
		} catch (CarSimException e) {
			return "Error in getting GPS report; " + e.getMessage();
		}
		return "";
	}
		
	@Command
	public void exit() { }

	public static void main(String[] args) {
		//Initialise the simulator.
		GridCarSimConsole gridCarSimConsole;
		try {
			gridCarSimConsole = new GridCarSimConsole(GridCarSimulator.getInstance());
			System.out.println(QUICK_HELP);
			//Let cliche manage the console.
				ShellFactory.createConsoleShell("CarSim",
						" Enter - ?list to get a list of commands", gridCarSimConsole)
						.commandLoop();
		} catch (InvalidGridSizeException|IOException e) {
			System.out.println("Failed to initialise the grid; " + e.getMessage());
		}
	}
}
