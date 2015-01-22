package com.sample.gridcarsim.components;

import com.sample.gridcarsim.commands.Command;
import com.sample.gridcarsim.exceptions.CarSimException;
import com.sample.gridcarsim.exceptions.InvalidGridSizeException;
import com.sample.gridcarsim.exceptions.OutOfGridRangeException;

public class Grid {

Integer gridSizeX, gridSizeY ,gridSizeNegX, gridSizeNegY; 

public Grid(Integer gridSizeX, Integer gridSizeY, Integer gridSizeNegX, Integer gridSizeNegY) throws InvalidGridSizeException {
	this.validateGridInit(gridSizeX, gridSizeY ,gridSizeNegX, gridSizeNegY);
	this.gridSizeX = gridSizeX;
	this.gridSizeY = gridSizeY;
	this.gridSizeNegX = gridSizeNegX;
	this.gridSizeNegY = gridSizeNegY;
}

private void validateGridInit(Integer x, Integer y, Integer negX, Integer negY) throws InvalidGridSizeException {
 if (!(x>0 && y>0 && negX <0 && negY <0)){
	 throw new InvalidGridSizeException();
 }
}

public void execute(Command command) throws CarSimException {
	command.execute();
}

public boolean validateGridPosition(Integer x, Integer y){
	return ((x<0 && x>=gridSizeNegX)||(x>=0 && x <= gridSizeX)) 
			&& 	((y<0 && y>=gridSizeNegY)||(y>=0 && y <= gridSizeY));
}


}
