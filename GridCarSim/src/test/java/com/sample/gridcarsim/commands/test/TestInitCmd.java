package com.sample.gridcarsim.commands.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sample.gridcarsim.commands.InitCmd;
import com.sample.gridcarsim.components.Car;
import com.sample.gridcarsim.components.Grid;
import com.sample.gridcarsim.components.Position;
import com.sample.gridcarsim.components.DirectionMap.Direction;
import com.sample.gridcarsim.exceptions.CarSimException;
import com.sample.gridcarsim.exceptions.InvalidDirectionException;
import com.sample.gridcarsim.exceptions.OutOfGridRangeException;

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
	 * @throws CarSimException
	 */
	@Test
	public void testInitCMD() throws CarSimException {
		InitCmd initCmd = new InitCmd(this.car, new Position(1, 2),"NORTH");
		initCmd.execute();
		assertEquals(Direction.NORTH, this.car.getDirection());
		assertEquals(new Integer(1), this.car.getPosition().getX());
		assertEquals(new Integer(2), this.car.getPosition().getY());
	}
	
	/**
	 * Test to check if bad string direction input is handled
	 * @throws CarSimException
	 */
	@Test(expected = InvalidDirectionException.class)
	public void testInitBadDirection() throws CarSimException {
		InitCmd initCmd = new InitCmd(this.car, new Position(1, 2),"BADDIRECTION");
		initCmd.execute();
	}
	
	/**
	 * Test to check that car is not initialized outside the grids range.
	 * @throws CarSimException
	 */
	@Test(expected = OutOfGridRangeException.class)
	public void testInitBadPosition() throws CarSimException {
		InitCmd initCmd = new InitCmd(this.car, new Position(11, 2),"SOUTH");
		initCmd.execute();
	}
	
}
