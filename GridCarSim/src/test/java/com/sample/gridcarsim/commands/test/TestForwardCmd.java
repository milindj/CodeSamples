package com.sample.gridcarsim.commands.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sample.gridcarsim.commands.ForwardCmd;
import com.sample.gridcarsim.components.Car;
import com.sample.gridcarsim.components.Grid;
import com.sample.gridcarsim.components.Position;
import com.sample.gridcarsim.exceptions.CarPositionNotInitialised;
import com.sample.gridcarsim.exceptions.CarSimException;
import com.sample.gridcarsim.exceptions.OutOfGridRangeException;

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
	 * @throws CarSimException
	 */
	@Test(expected = CarPositionNotInitialised.class)
	public void testExecuteUninitialisedCar() throws CarSimException {
		this.fwdCmd.execute();
	}
	
	/**
	 * Test car movement in NORTH direction.
	 * @throws CarSimException
	 */
	@Test
	public void testPositionIncrementN() throws CarSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("NORTH");
		this.fwdCmd.execute();
		assertEquals(new Integer(2), this.car.getPosition().getY());
	}
	
	/**
	 * Test car movement in WEST direction.
	 * @throws CarSimException
	 */
	@Test
	public void testPositionIncrementW() throws CarSimException {
		this.car.setPosition(new Position(0, 1)); 
		this.car.setDirection("WEST");
		this.fwdCmd.execute();
		assertEquals(new Integer(-1), this.car.getPosition().getX());
	}
	
	/**
	 * Test car movement in SOUTH direction.
	 * @throws CarSimException
	 */
	@Test
	public void testPositionIncrementS() throws CarSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("SOUTH");
		this.fwdCmd.execute();
		assertEquals(new Integer(0), this.car.getPosition().getY());
	}
	
	/**
	 * Test car movement in EAST direction.
	 * @throws CarSimException
	 */
	@Test
	public void testPositionIncrementE() throws CarSimException {
		this.car.setPosition(new Position(0, 1)); 
		this.car.setDirection("EAST");
		this.fwdCmd.execute();
		assertEquals(new Integer(1), this.car.getPosition().getX());
	}
	
	/**
	 * Test when car goes beyond the limits of NORTH end.
	 * @throws CarSimException
	 */
	@Test(expected = OutOfGridRangeException.class)
	public void testBadPositionN() throws CarSimException {
		// On the edge of the grid
		this.car.setPosition(new Position(7, 10)); 
		this.car.setDirection("NORTH");
		this.fwdCmd.execute();
	}

	/**
	 * Test when car goes beyond the limits of WEST end.
	 * @throws CarSimException
	 */
	@Test(expected = OutOfGridRangeException.class)
	public void testBadPositionW() throws CarSimException {
		// On the edge of the grid
		this.car.setPosition(new Position(-10, 8)); 
		this.car.setDirection("WEST");
		this.fwdCmd.execute();
	}
	
	/**
	 * Test when car goes beyond the limits of SOUTH end.
	 * @throws CarSimException
	 */
	@Test(expected = OutOfGridRangeException.class)
	public void testBadPositionS() throws CarSimException {
		// On the edge of the grid
		this.car.setPosition(new Position(7, -10)); 
		this.car.setDirection("SOUTH");
		this.fwdCmd.execute();
	}

	/**
	 * Test when car goes beyond the limits of EAST end.
	 * @throws CarSimException
	 */
	@Test(expected = OutOfGridRangeException.class)
	public void testBadPositionE() throws CarSimException {
		// On the edge of the grid
		this.car.setPosition(new Position(10, 8)); 
		this.car.setDirection("EAST");
		this.fwdCmd.execute();
	}

}
