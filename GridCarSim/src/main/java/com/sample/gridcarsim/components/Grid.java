package com.sample.gridcarsim.components;

import com.sample.gridcarsim.commands.Command;
import com.sample.gridcarsim.exceptions.CarSimException;

public class Grid {

Integer gridSizeX, gridSizeY ,gridSizeNegX, gridSizeNegY; 

public Grid(Integer gridSizeX, Integer gridSizeY, Integer gridSizeNegX, Integer gridSizeNegY){
	this.gridSizeX = gridSizeX;
	this.gridSizeY = gridSizeY;
	this.gridSizeNegX = gridSizeNegX;
	this.gridSizeNegY = gridSizeNegY;
}

public void execute(Command command) throws CarSimException {
	command.execute();
}

public boolean validateGridPosition(Position position){
	Integer x = position.getX();
	Integer y = position.getY();
	return ((x<0 && x>=gridSizeNegX)||(x>=0 && x <= gridSizeX)) 
			&& 	((y<0 && y>=gridSizeNegY)||(y>=0 && y <= gridSizeY));
}


}
