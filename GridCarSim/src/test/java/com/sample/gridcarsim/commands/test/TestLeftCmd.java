package com.sample.gridcarsim.commands.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sample.gridcarsim.commands.LeftCmd;
import com.sample.gridcarsim.components.Car;
import com.sample.gridcarsim.components.Grid;
import com.sample.gridcarsim.components.Position;
import com.sample.gridcarsim.components.DirectionMap.Direction;
import com.sample.gridcarsim.exceptions.CarPositionNotInitialised;
import com.sample.gridcarsim.exceptions.CarSimException;

/**
 * Test cases to test LeftCmd class.
 * @author milind
 *
 */
public class TestLeftCmd {


	private Car car;
	private LeftCmd leftCmd;

	@Before
	public void setUp() throws Exception {
		this.car = new Car(new Grid(10, 10, -10, -10));
		this.leftCmd = new LeftCmd(this.car);
	}
	
	/**
	 * Test unitialized car scenario.
	 * @throws CarSimException
	 */
	@Test(expected = CarPositionNotInitialised.class)
	public void testExecuteUninitialisedCar() throws CarSimException {
		this.leftCmd.execute();
	}

	/**
	 * Test to change direction from NtoW
	 * @throws CarSimException
	 */
	@Test
	public void testDirectionIncrementN() throws CarSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("NORTH");
		this.leftCmd.execute();
		assertEquals(Direction.WEST, this.car.getDirection());
	}
	
	/**
	 * Test to change direction from StoE
	 * @throws CarSimException
	 */
	@Test
	public void testDirectionIncrementS() throws CarSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("SOUTH");
		this.leftCmd.execute();
		assertEquals(Direction.EAST, this.car.getDirection());
	}
	
	/**
	 * Test to change direction from WtoS
	 * @throws CarSimException
	 */
	@Test
	public void testDirectionIncrementW() throws CarSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("WEST");
		this.leftCmd.execute();
		assertEquals(Direction.SOUTH, this.car.getDirection());
	}
	
	/**
	 * Test to change direction from EtoN
	 * @throws CarSimException
	 */
	@Test
	public void testDirectionIncrementE() throws CarSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("EAST");
		this.leftCmd.execute();
		assertEquals(Direction.NORTH, this.car.getDirection());
	}
}
