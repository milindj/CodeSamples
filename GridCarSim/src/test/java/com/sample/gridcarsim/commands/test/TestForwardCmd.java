package com.sample.gridcarsim.commands.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sample.gridsim.Grid;
import com.sample.gridsim.Position;
import com.sample.gridsim.car.commands.ForwardCmd;
import com.sample.gridsim.car.components.Car;
import com.sample.gridsim.exceptions.PositionNotInitialised;
import com.sample.gridsim.exceptions.GridSimException;
import com.sample.gridsim.exceptions.OutOfGridRangeException;

/**
 * Test case to test ForwardCmd.
 * @author milind
 *
 */
public class TestForwardCmd {

	private Car car;
	private ForwardCmd fwdCmd;

	@Before
	public void setUp() throws Exception {
		this.car = new Car(new Grid(10, 10, -10, -10));
		this.fwdCmd = new ForwardCmd(this.car);
	}

	/**
	 * Test unitialised car scenario.
	 * @throws GridSimException
	 */
	@Test(expected = PositionNotInitialised.class)
	public void testExecuteUninitialisedCar() throws GridSimException {
		this.fwdCmd.execute();
	}
	
	/**
	 * Test car movement in NORTH direction.
	 * @throws GridSimException
	 */
	@Test
	public void testPositionIncrementN() throws GridSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("NORTH");
		this.fwdCmd.execute();
		assertEquals(new Integer(2), this.car.getPosition().getY());
	}
	
	/**
	 * Test car movement in WEST direction.
	 * @throws GridSimException
	 */
	@Test
	public void testPositionIncrementW() throws GridSimException {
		this.car.setPosition(new Position(0, 1)); 
		this.car.setDirection("WEST");
		this.fwdCmd.execute();
		assertEquals(new Integer(-1), this.car.getPosition().getX());
	}
	
	/**
	 * Test car movement in SOUTH direction.
	 * @throws GridSimException
	 */
	@Test
	public void testPositionIncrementS() throws GridSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("SOUTH");
		this.fwdCmd.execute();
		assertEquals(new Integer(0), this.car.getPosition().getY());
	}
	
	/**
	 * Test car movement in EAST direction.
	 * @throws GridSimException
	 */
	@Test
	public void testPositionIncrementE() throws GridSimException {
		this.car.setPosition(new Position(0, 1)); 
		this.car.setDirection("EAST");
		this.fwdCmd.execute();
		assertEquals(new Integer(1), this.car.getPosition().getX());
	}
	
	/**
	 * Test when car goes beyond the limits of NORTH end.
	 * @throws GridSimException
	 */
	@Test(expected = OutOfGridRangeException.class)
	public void testBadPositionN() throws GridSimException {
		// On the edge of the grid
		this.car.setPosition(new Position(7, 10)); 
		this.car.setDirection("NORTH");
		this.fwdCmd.execute();
	}

	/**
	 * Test when car goes beyond the limits of WEST end.
	 * @throws GridSimException
	 */
	@Test(expected = OutOfGridRangeException.class)
	public void testBadPositionW() throws GridSimException {
		// On the edge of the grid
		this.car.setPosition(new Position(-10, 8)); 
		this.car.setDirection("WEST");
		this.fwdCmd.execute();
	}
	
	/**
	 * Test when car goes beyond the limits of SOUTH end.
	 * @throws GridSimException
	 */
	@Test(expected = OutOfGridRangeException.class)
	public void testBadPositionS() throws GridSimException {
		// On the edge of the grid
		this.car.setPosition(new Position(7, -10)); 
		this.car.setDirection("SOUTH");
		this.fwdCmd.execute();
	}

	/**
	 * Test when car goes beyond the limits of EAST end.
	 * @throws GridSimException
	 */
	@Test(expected = OutOfGridRangeException.class)
	public void testBadPositionE() throws GridSimException {
		// On the edge of the grid
		this.car.setPosition(new Position(10, 8)); 
		this.car.setDirection("EAST");
		this.fwdCmd.execute();
	}

}
