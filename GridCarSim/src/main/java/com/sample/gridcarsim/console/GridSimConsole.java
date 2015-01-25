package com.sample.gridcarsim.console;

import java.io.IOException;

import com.sample.gridsim.GridSimulator;
import com.sample.gridsim.car.components.GridCarSimImpl;
import com.sample.gridsim.exceptions.GridSimException;

import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.ShellFactory;

/**
 * An end console client, which provides a console based UI for the the grid
 * simulator. This class takes input commands from user to operate the car or
 * object on the grid.
 * 
 * @author Milind
 *
 */
public class GridSimConsole {

	public static final String CMD_HELP_STRING = ("---------------- Help ---------------- \n") + ("\n") + ("Commands \t\t| Description\n")
			+ ("---------------------\t--------------\n") + ("?list \t\t\t| Get the list of all commands.\n")
			+ ("?help cmd \t\t| Help on the entered cmd/command\n") + ("help \t\t\t| Help menu \n") + ("exit \t\t\t| Exit the simulator\n")
			+ ("INIT x y direction \t| Initialize the car position. \n") + ("FORWARD \t\t| Move the car forward by one unit \n")
			+ ("TURN_LEFT \t\t| Turn left \n") + ("TURN_RIGHT \t\t| Turn right \n") + ("GPS_REPORT \t\t| Print position and direction \n");

	private GridSimulator gridSimulator;

	public GridSimConsole(GridSimulator gridSimulator) {
		this.gridSimulator = gridSimulator;
	}

	/**
	 * Method called when help command is issued.
	 * 
	 * @return
	 */
	@Command(name = "help")
	public String help() {
		return CMD_HELP_STRING;
	}

	/**
	 * Method called when INIT command is issued.
	 * 
	 * @param x
	 * @param y
	 * @param direction
	 * @return
	 */
	@Command(name = "INIT")
	public String init(@Param(name = "x", description = "X position of the car") Integer x,
			@Param(name = "y", description = "Y position of the car") Integer y,
			@Param(name = "direction", description = "Direction of the car(NORTH|SOUTH|EAST|WEST)") String direction) {
		try {
			this.gridSimulator.initPosition(x, y, direction);
		} catch (GridSimException e) {
			return "Error initialising the car; " + e.getMessage();
		}
		return "";
	}

	/**
	 * Method called when FORWARD command is issued.
	 * 
	 * @return
	 */
	@Command(name = "FORWARD")
	public String forward() {
		try {
			this.gridSimulator.moveForward();
		} catch (GridSimException e) {
			return "Error in moving the car; " + e.getMessage();
		}
		return "";
	}

	/**
	 * Method called when TURN_LEFT command is issued.
	 * 
	 * @return
	 */
	@Command(name = "TURN_LEFT")
	public String left() {
		try {
			this.gridSimulator.turnLeft();
		} catch (GridSimException e) {
			return "Error in turning the car; " + e.getMessage();
		}
		return "";
	}

	/**
	 * Method called when TURN_RIGHT command is issued.
	 * 
	 * @return
	 */
	@Command(name = "TURN_RIGHT")
	public String right() {
		try {
			this.gridSimulator.turnRight();
		} catch (GridSimException e) {
			return "Error in turning the car; " + e.getMessage();
		}
		return "";
	}

	/**
	 * Method called when GPS_REPORT command is issued.
	 * 
	 * @return
	 */
	@Command(name = "GPS_REPORT")
	public String gpsReport() {
		try {
			return this.gridSimulator.gpsReport();
		} catch (GridSimException e) {
			return "Error in getting GPS report; " + e.getMessage();
		}
	}

	/**
	 * Default exit method.
	 */
	@Command
	public void exit() {
	}

	public static void main(String[] args) {
		// Initialise the simulator.
		GridSimConsole gridCarSimConsole;
		try {
			gridCarSimConsole = new GridSimConsole(GridCarSimImpl.getInstance());
			System.out.println(CMD_HELP_STRING);
			// Let cliche manage the console.
			ShellFactory.createConsoleShell(
					"GRID",
					" Enter - ?list to get a list of commands \n\n"
					+ "The GRID size is defined by the following point co-ordinates (x,y):\n"
							+ "          ^ y-axis\n"
							+ "          |\n"
							+ "(-10,10)  | (10,10)\n"
							+ " ---------|--------->x-axis \n"
							+ "(-10,-10) | (10,-10)\n", gridCarSimConsole).commandLoop();
		} catch (IOException e) {
			System.out.println("Failed to initialise the grid; " + e.getMessage());
		}
	}
}
