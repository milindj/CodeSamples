package com.sample.gridcarsim.commands.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sample.gridsim.Grid;
import com.sample.gridsim.Position;
import com.sample.gridsim.DirectionMap.Direction;
import com.sample.gridsim.car.commands.RightCmd;
import com.sample.gridsim.car.components.Car;
import com.sample.gridsim.exceptions.PositionNotInitialised;
import com.sample.gridsim.exceptions.GridSimException;
/**
 * Test cases for RightCmd class.
 * @author milind
 *
 */
public class TestRightCmd {

	private Car car;
	private RightCmd rightCmd;

	@Before
	public void setUp() throws Exception {
		this.car = new Car(new Grid(10, 10, -10, -10));
		this.rightCmd = new RightCmd(this.car);
	}

	/**
	 * Test unitialised car scenario.
	 * @throws GridSimException
	 */
	@Test(expected = PositionNotInitialised.class)
	public void testExecuteUninitialisedCar() throws GridSimException {
		this.rightCmd.execute();
	}
	
	/**
	 * Test to change direction from NtoE
	 * @throws GridSimException
	 */
	@Test
	public void testDirectionIncrementN() throws GridSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("NORTH");
		this.rightCmd.execute();
		assertEquals(Direction.EAST, this.car.getDirection());
	}
	
	/**
	 * Test to change direction from StoW
	 * @throws GridSimException
	 */
	@Test
	public void testDirectionIncrementS() throws GridSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("SOUTH");
		this.rightCmd.execute();
		assertEquals(Direction.WEST, this.car.getDirection());
	}
	
	/**
	 * Test to change direction from WtoN
	 * @throws GridSimException
	 */
	@Test
	public void testDirectionIncrementW() throws GridSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("WEST");
		this.rightCmd.execute();
		assertEquals(Direction.NORTH, this.car.getDirection());
	}
	
	/**
	 * Test to change direction from EtoS
	 * @throws GridSimException
	 */
	@Test
	public void testDirectionIncrementE() throws GridSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("EAST");
		this.rightCmd.execute();
		assertEquals(Direction.SOUTH, this.car.getDirection());
	}
}
