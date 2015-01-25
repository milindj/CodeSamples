package com.sample.gridsim;

import com.sample.gridsim.car.commands.Command;
import com.sample.gridsim.exceptions.GridSimException;

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
	 * Executes various commands, typically for the car or an object's movement in the grid.
	 * 
	 * @param command
	 * @throws GridSimException
	 */
	public void execute(Command command) throws GridSimException {
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
