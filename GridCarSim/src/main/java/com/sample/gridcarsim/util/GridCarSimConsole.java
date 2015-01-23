package com.sample.gridcarsim.util;

import java.io.IOException;

import com.sample.gridcarsim.GridCarSimulator;
import com.sample.gridcarsim.exceptions.CarSimException;

import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.ShellFactory;

public class GridCarSimConsole {

	public static final String HELP_MENU = ("---------------- Help Menu ---------------- \n")
			+("\n")
			+("Commands \t\t| Description\n")
			+("---------------------\t--------------\n")
			+("?list \t\t\t| Get the list of all commands.\n")
			+("?help <cmd> \t\t| Help on the entered command\n")
			+("help \t\t\t| Help menu \n")
			+("exit \t\t\t| Exit the simulator\n")
			+("INIT x y direction \t| Initialize the car position. \n")
			+("FORWARD \t\t| Move the car forward by one unit \n")
			+("LEFT \t\t\t| Turn left \n")
			+("RIGHT \t\t\t| Turn right \n")
			+("GPS_REPORT \t\t| Print position and direction \n");
		
	public GridCarSimConsole(GridCarSimulator gridCarSimulator) {
		this.gridCarSimulator = gridCarSimulator;
	}
	
	private GridCarSimulator gridCarSimulator;
	
	@Command(name = "help")
	public String help(){
		return HELP_MENU;
	}

	@Command(name = "INIT")
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
	
	@Command(name = "FORWARD")
	public String forward() {
		try {
			this.gridCarSimulator.moveForward();
		} catch (CarSimException e) {
			return "Error in moving the car; " + e.getMessage();
		}
		return "";
	}

	@Command(name = "LEFT")
	public String left() {
		try {
			this.gridCarSimulator.turnLeft();
		} catch (CarSimException e) {
			return "Error in turning the car; " + e.getMessage();
		}
		return "";
	}
	
	@Command(name = "RIGHT")
	public String right() {
		try {
			this.gridCarSimulator.turnRight();
		} catch (CarSimException e) {
			return "Error in turning the car; " + e.getMessage();
		}
		return "";
	}
	
	@Command(name = "GPS_REPORT")
	public String gpsReport() {
		try {
			return this.gridCarSimulator.gpsReport();
		} catch (CarSimException e) {
			return "Error in getting GPS report; " + e.getMessage();
		}
	}
		
	@Command
	public void exit() { }

	public static void main(String[] args) {
		//Initialise the simulator.
		GridCarSimConsole gridCarSimConsole;
		try {
			gridCarSimConsole = new GridCarSimConsole(GridCarSimulator.getInstance());
			System.out.println(HELP_MENU);
			//Let cliche manage the console.
				ShellFactory.createConsoleShell("mvn",
						" Enter - ?list to get a list of commands \n\n The GRID size is = {(10,10),(10,-10),(-10,10),(-10,-10)}", gridCarSimConsole)
						.commandLoop();
		} catch (IOException e) {
			System.out.println("Failed to initialise the grid; " + e.getMessage());
		}
	}
}
