package com.sample.gridcarsim.commands.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sample.gridsim.Grid;
import com.sample.gridsim.Position;
import com.sample.gridsim.DirectionMap.Direction;
import com.sample.gridsim.car.commands.InitCmd;
import com.sample.gridsim.car.components.Car;
import com.sample.gridsim.exceptions.GridSimException;
import com.sample.gridsim.exceptions.InvalidDirectionException;
import com.sample.gridsim.exceptions.OutOfGridRangeException;

/**
 * Test cases for the InitCmd class.
 * @author milind
 *
 */
public class TestInitCmd {

	private Car car;

	@Before
	public void setUp() throws Exception {
		this.car = new Car(new Grid(10, 10, -10, -10));
	}

	/**
	 * Test the core functionality of initializing the car with appropriate values. 
	 * @throws GridSimException
	 */
	@Test
	public void testInitCMD() throws GridSimException {
		InitCmd initCmd = new InitCmd(this.car, new Position(1, 2),"NORTH");
		initCmd.execute();
		assertEquals(Direction.NORTH, this.car.getDirection());
		assertEquals(new Integer(1), this.car.getPosition().getX());
		assertEquals(new Integer(2), this.car.getPosition().getY());
	}
	
	/**
	 * Test to check if bad string direction input is handled
	 * @throws GridSimException
	 */
	@Test(expected = InvalidDirectionException.class)
	public void testInitBadDirection() throws GridSimException {
		InitCmd initCmd = new InitCmd(this.car, new Position(1, 2),"BADDIRECTION");
		initCmd.execute();
	}
	
	/**
	 * Test to check that car is not initialized outside the grids range.
	 * @throws GridSimException
	 */
	@Test(expected = OutOfGridRangeException.class)
	public void testInitBadPosition() throws GridSimException {
		InitCmd initCmd = new InitCmd(this.car, new Position(11, 2),"SOUTH");
		initCmd.execute();
	}
	
}
