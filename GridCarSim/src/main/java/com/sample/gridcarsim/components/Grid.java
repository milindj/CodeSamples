package com.sample.gridcarsim.components;

import com.sample.gridcarsim.commands.Command;
import com.sample.gridcarsim.exceptions.CarSimException;

/**
 * The class representing the 2D Grid, on which the car operates.
 * 
 * @author Milind
 *
 */
public class Grid {

	// Grid Max size or dimensions
	Integer gridSizeX, gridSizeY, gridSizeNegX, gridSizeNegY;

	/**
	 * Constructor which takes input to define the grid-size.
	 * 
	 * @param gridSizeX
	 * @param gridSizeY
	 * @param gridSizeNegX
	 * @param gridSizeNegY
	 */
	public Grid(Integer gridSizeX, Integer gridSizeY, Integer gridSizeNegX, Integer gridSizeNegY) {
		this.gridSizeX = gridSizeX;
		this.gridSizeY = gridSizeY;
		this.gridSizeNegX = gridSizeNegX;
		this.gridSizeNegY = gridSizeNegY;
	}

	/**
	 * Executes various commands, typically for the car's movement in the grid.
	 * 
	 * @param command
	 * @throws CarSimException
	 */
	public void execute(Command command) throws CarSimException {
		command.execute();
	}

	/**
	 * Validates the input position to be inside the grid range.
	 * 
	 * @param position
	 * @return
	 */
	public boolean validateGridPosition(Position position) {
		Integer x = position.getX();
		Integer y = position.getY();
		return ((x < 0 && x >= gridSizeNegX) || (x >= 0 && x <= gridSizeX)) 
				&& ((y < 0 && y >= gridSizeNegY) || (y >= 0 && y <= gridSizeY));
	}

}
