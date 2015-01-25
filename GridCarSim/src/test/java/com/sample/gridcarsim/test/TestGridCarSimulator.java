package com.sample.gridcarsim.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sample.gridsim.Position;
import com.sample.gridsim.DirectionMap.Direction;
import com.sample.gridsim.car.components.Car;
import com.sample.gridsim.car.components.GridCarSimImpl;
import com.sample.gridsim.exceptions.GridSimException;
import com.sample.gridsim.exceptions.OutOfGridRangeException;
/**
 * Test cases for GridCarSimulator class.
 * @author milind
 *
 */
public class TestGridCarSimulator {
	private Car car;
	GridCarSimImpl gridCarSimulator;

	@Before
	public void setUp() throws Exception {
		this.gridCarSimulator = GridCarSimImpl.getInstance();
		this.car = this.gridCarSimulator.getCar();
	}
	
	/**
	 * Test where car is made to go beyond the grid.
	 * @throws GridSimException
	 */
	@Test(expected = OutOfGridRangeException.class)
	public void testCarPositionOutOfGrid() throws GridSimException {
		this.gridCarSimulator.initPosition(1, 10,"NORTH");
		this.gridCarSimulator.moveForward();
	}
	
	/**
	 * Test the functionality of initializing the car with appropriate values. 
	 * @throws GridSimException
	 */
	@Test
	public void testInitPosition() throws GridSimException {
		this.gridCarSimulator.initPosition(1, 2,"NORTH");
		assertEquals(Direction.NORTH, this.car.getDirection());
		assertEquals(new Integer(1), this.car.getPosition().getX());
		assertEquals(new Integer(2), this.car.getPosition().getY());
	}
	
	/**
	 * Test to check the functionality of left turn.
	 * @throws GridSimException
	 */
	@Test
	public void testDirectionLeft() throws GridSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("NORTH");
		this.gridCarSimulator.turnLeft();
		assertEquals(Direction.WEST, this.car.getDirection());
	}
	
	/**
	 * Test to check the functionality of right turn.
	 * @throws GridSimException
	 */
	@Test
	public void testDirectionRight() throws GridSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("NORTH");
		this.gridCarSimulator.turnRight();
		assertEquals(Direction.EAST, this.car.getDirection());
	}
	
	/**
	 * Test to check the functionality of a series of left turns.
	 * @throws GridSimException
	 */
	@Test
	public void testDirectionRight360() throws GridSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("NORTH");
		this.gridCarSimulator.turnRight();
		this.gridCarSimulator.turnRight();
		this.gridCarSimulator.turnRight();
		this.gridCarSimulator.turnRight();
		assertEquals(Direction.NORTH, this.car.getDirection());
	}
	
	/**
	 * Test to check the functionality of a series of right turns.
	 * @throws GridSimException
	 */
	@Test
	public void testDirectionLeft360() throws GridSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("NORTH");
		this.gridCarSimulator.turnLeft();
		this.gridCarSimulator.turnLeft();
		this.gridCarSimulator.turnLeft();
		this.gridCarSimulator.turnLeft();
		assertEquals(Direction.NORTH, this.car.getDirection());
	}
	
	/**
	 * Test gps reports output.
	 * @throws GridSimException
	 */
	@Test
	public void testGpsReport() throws GridSimException {
		this.car.setPosition(new Position(1, 2)); 
		this.car.setDirection("NORTH"); 
		this.gridCarSimulator.gpsReport();
		assertEquals("Output: 1, 2, NORTH", this.car.getGpsReport());
	}
	
	/**
	 * Test car movement in EAST direction.
	 * @throws GridSimException
	 */
	@Test
	public void testMoveForwardE() throws GridSimException {
		this.car.setPosition(new Position(0, 1)); 
		this.car.setDirection("EAST");
		this.gridCarSimulator.moveForward();
		assertEquals(new Integer(1), this.car.getPosition().getX());
	}
	
	/**
	 * Test car movement in NORTH direction.
	 * @throws GridSimException
	 */
	@Test
	public void testMoveForwardN() throws GridSimException {
		this.car.setPosition(new Position(0, 1)); 
		this.car.setDirection("NORTH");
		this.gridCarSimulator.moveForward();
		assertEquals(new Integer(2), this.car.getPosition().getY());
	}
	
	/**
	 * Test car movement in WEST direction.
	 * @throws GridSimException
	 */
	@Test
	public void testMoveForwardW() throws GridSimException {
		this.car.setPosition(new Position(0, 1)); 
		this.car.setDirection("WEST");
		this.gridCarSimulator.moveForward();
		assertEquals(new Integer(-1), this.car.getPosition().getX());
	}
	
	/**
	 * Test car movement in SOUTH direction.
	 * @throws GridSimException
	 */
	@Test
	public void testMoveForwardS() throws GridSimException {
		this.car.setPosition(new Position(0, 1)); 
		this.car.setDirection("SOUTH");
		this.gridCarSimulator.moveForward();
		assertEquals(new Integer(0), this.car.getPosition().getY());
	}
}
