package com.sample.gridcarsim.commands.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sample.gridsim.Grid;
import com.sample.gridsim.Position;
import com.sample.gridsim.DirectionMap.Direction;
import com.sample.gridsim.car.commands.LeftCmd;
import com.sample.gridsim.car.components.Car;
import com.sample.gridsim.exceptions.PositionNotInitialised;
import com.sample.gridsim.exceptions.GridSimException;

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
	 * @throws GridSimException
	 */
	@Test(expected = PositionNotInitialised.class)
	public void testExecuteUninitialisedCar() throws GridSimException {
		this.leftCmd.execute();
	}

	/**
	 * Test to change direction from NtoW
	 * @throws GridSimException
	 */
	@Test
	public void testDirectionIncrementN() throws GridSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("NORTH");
		this.leftCmd.execute();
		assertEquals(Direction.WEST, this.car.getDirection());
	}
	
	/**
	 * Test to change direction from StoE
	 * @throws GridSimException
	 */
	@Test
	public void testDirectionIncrementS() throws GridSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("SOUTH");
		this.leftCmd.execute();
		assertEquals(Direction.EAST, this.car.getDirection());
	}
	
	/**
	 * Test to change direction from WtoS
	 * @throws GridSimException
	 */
	@Test
	public void testDirectionIncrementW() throws GridSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("WEST");
		this.leftCmd.execute();
		assertEquals(Direction.SOUTH, this.car.getDirection());
	}
	
	/**
	 * Test to change direction from EtoN
	 * @throws GridSimException
	 */
	@Test
	public void testDirectionIncrementE() throws GridSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("EAST");
		this.leftCmd.execute();
		assertEquals(Direction.NORTH, this.car.getDirection());
	}
}
