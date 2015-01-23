package com.sample.gridcarsim.components.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sample.gridcarsim.components.Car;
import com.sample.gridcarsim.components.DirectionMap.Direction;
import com.sample.gridcarsim.components.Grid;
import com.sample.gridcarsim.components.Position;
import com.sample.gridcarsim.exceptions.InvalidDirectionException;
import com.sample.gridcarsim.exceptions.OutOfGridRangeException;

/**
 * Test cases to test the car functionalities.
 * @author milind
 *
 */
public class TestCar {
	private Car car;
	
	/**
	 * Initialised to the standard grid.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.car = new Car(new Grid(10, 10, -10, -10));
	}

	/**
	 * Test bad string direction argument.
	 * @throws InvalidDirectionException
	 */
	@Test(expected = InvalidDirectionException.class)
	public void testSetBadDirection() throws InvalidDirectionException {
		this.car.setDirection("BADDIRECTION");
	}
	
	/**
	 * Test change of direction NtoW by 90 degrees.
	 * @throws InvalidDirectionException
	 */
	@Test
	public void testChangeOfDirectionN2W() throws InvalidDirectionException {
		this.car.setDirection("NORTH");
		this.car.changeDirectionDegrees(90);
		assertEquals(Direction.WEST, this.car.getDirection());
	}

	/**
	 * Test change of direction EtoS by 90 degrees.
	 * @throws InvalidDirectionException
	 */
	@Test
	public void testChangeOfDirectionE2S() throws InvalidDirectionException {
		this.car.setDirection("EAST");
		this.car.changeDirectionDegrees(-90);
		assertEquals(Direction.SOUTH, this.car.getDirection());
	}
	
	/**
	 * Test change of direction StoE by 90 degrees.
	 * @throws InvalidDirectionException
	 */
	@Test
	public void testChangeOfDirectionS2E() throws InvalidDirectionException {
		this.car.setDirection("SOUTH");
		this.car.changeDirectionDegrees(90);
		assertEquals(Direction.EAST, this.car.getDirection());
	}
	
	/**
	 * Test out of range position scenario.
	 * @throws OutOfGridRangeException
	 */
	@Test(expected = OutOfGridRangeException.class)
	public void testSetBadPosition() throws OutOfGridRangeException {
		Position badPosition = new Position(0,-11);
		this.car.setPosition(badPosition);
	}
	
	/**
	 * Test setting of positions x,y of the car.
	 * @throws OutOfGridRangeException
	 */
	@Test
	public void testSetPosition() throws OutOfGridRangeException {
		Position position = new Position(0,-1);
		this.car.setPosition(position);
		assertEquals(new Integer(0), position.getX());
		assertEquals(new Integer(-1), position.getY());
	}
	
	/**
	 * Test NORTH direction
	 * @throws InvalidDirectionException
	 */
	@Test
	public void testSetDirectionN() throws InvalidDirectionException {
		this.car.setDirection("NORTH");
		assertEquals(Direction.NORTH, this.car.getDirection());
	}

	/**
	 * Test SOUTH direction
	 * @throws InvalidDirectionException
	 */
	@Test
	public void testSetDirectionS() throws InvalidDirectionException {
		this.car.setDirection("SOUTH");
		assertEquals(Direction.SOUTH, this.car.getDirection());
	}

	/**
	 * Test EAST direction
	 * @throws InvalidDirectionException
	 */
	@Test
	public void testSetDirectionE() throws InvalidDirectionException {
		this.car.setDirection("EAST");
		assertEquals(Direction.EAST, this.car.getDirection());
	}

	/**
	 * Test WEST direction
	 * @throws InvalidDirectionException
	 */
	@Test
	public void testSetDirectionW() throws InvalidDirectionException {
		this.car.setDirection("WEST");
		assertEquals(Direction.WEST, this.car.getDirection());
	}
}
