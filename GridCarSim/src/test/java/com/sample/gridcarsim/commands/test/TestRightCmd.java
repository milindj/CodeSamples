package com.sample.gridcarsim.commands.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sample.gridcarsim.commands.RightCmd;
import com.sample.gridcarsim.components.Car;
import com.sample.gridcarsim.components.Grid;
import com.sample.gridcarsim.components.Position;
import com.sample.gridcarsim.components.DirectionMap.Direction;
import com.sample.gridcarsim.exceptions.CarPositionNotInitialised;
import com.sample.gridcarsim.exceptions.CarSimException;
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
	 * @throws CarSimException
	 */
	@Test(expected = CarPositionNotInitialised.class)
	public void testExecuteUninitialisedCar() throws CarSimException {
		this.rightCmd.execute();
	}
	
	/**
	 * Test to change direction from NtoE
	 * @throws CarSimException
	 */
	@Test
	public void testDirectionIncrementN() throws CarSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("NORTH");
		this.rightCmd.execute();
		assertEquals(Direction.EAST, this.car.getDirection());
	}
	
	/**
	 * Test to change direction from StoW
	 * @throws CarSimException
	 */
	@Test
	public void testDirectionIncrementS() throws CarSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("SOUTH");
		this.rightCmd.execute();
		assertEquals(Direction.WEST, this.car.getDirection());
	}
	
	/**
	 * Test to change direction from WtoN
	 * @throws CarSimException
	 */
	@Test
	public void testDirectionIncrementW() throws CarSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("WEST");
		this.rightCmd.execute();
		assertEquals(Direction.NORTH, this.car.getDirection());
	}
	
	/**
	 * Test to change direction from EtoS
	 * @throws CarSimException
	 */
	@Test
	public void testDirectionIncrementE() throws CarSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("EAST");
		this.rightCmd.execute();
		assertEquals(Direction.SOUTH, this.car.getDirection());
	}
}
