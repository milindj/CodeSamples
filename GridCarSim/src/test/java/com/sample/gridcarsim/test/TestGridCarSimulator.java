package com.sample.gridcarsim.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sample.gridcarsim.GridCarSimulator;
import com.sample.gridcarsim.components.Car;
import com.sample.gridcarsim.components.Position;
import com.sample.gridcarsim.components.DirectionMap.Direction;
import com.sample.gridcarsim.exceptions.CarSimException;
import com.sample.gridcarsim.exceptions.OutOfGridRangeException;
/**
 * Test cases for GridCarSimulator class.
 * @author milind
 *
 */
public class TestGridCarSimulator {
	private Car car;
	GridCarSimulator gridCarSimulator;

	@Before
	public void setUp() throws Exception {
		this.gridCarSimulator = GridCarSimulator.getInstance();
		this.car = this.gridCarSimulator.getCar();
	}
	
	/**
	 * Test where car is made to go beyond the grid.
	 * @throws CarSimException
	 */
	@Test(expected = OutOfGridRangeException.class)
	public void testCarPositionOutOfGrid() throws CarSimException {
		this.gridCarSimulator.initPosition(1, 10,"NORTH");
		this.gridCarSimulator.moveForward();
	}
	
	/**
	 * Test the functionality of initializing the car with appropriate values. 
	 * @throws CarSimException
	 */
	@Test
	public void testInitPosition() throws CarSimException {
		this.gridCarSimulator.initPosition(1, 2,"NORTH");
		assertEquals(Direction.NORTH, this.car.getDirection());
		assertEquals(new Integer(1), this.car.getPosition().getX());
		assertEquals(new Integer(2), this.car.getPosition().getY());
	}
	
	/**
	 * Test to check the functionality of left turn.
	 * @throws CarSimException
	 */
	@Test
	public void testDirectionLeft() throws CarSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("NORTH");
		this.gridCarSimulator.turnLeft();
		assertEquals(Direction.WEST, this.car.getDirection());
	}
	
	/**
	 * Test to check the functionality of right turn.
	 * @throws CarSimException
	 */
	@Test
	public void testDirectionRight() throws CarSimException {
		this.car.setPosition(new Position(1, 1)); 
		this.car.setDirection("NORTH");
		this.gridCarSimulator.turnRight();
		assertEquals(Direction.EAST, this.car.getDirection());
	}
	
	/**
	 * Test to check the functionality of a series of left turns.
	 * @throws CarSimException
	 */
	@Test
	public void testDirectionRight360() throws CarSimException {
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
	 * @throws CarSimException
	 */
	@Test
	public void testDirectionLeft360() throws CarSimException {
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
	 * @throws CarSimException
	 */
	@Test
	public void testGpsReport() throws CarSimException {
		this.car.setPosition(new Position(1, 2)); 
		this.car.setDirection("NORTH"); 
		this.gridCarSimulator.gpsReport();
		assertEquals("Output: 1, 2, NORTH", this.car.getGpsReport());
	}
	
	/**
	 * Test car movement in EAST direction.
	 * @throws CarSimException
	 */
	@Test
	public void testMoveForwardE() throws CarSimException {
		this.car.setPosition(new Position(0, 1)); 
		this.car.setDirection("EAST");
		this.gridCarSimulator.moveForward();
		assertEquals(new Integer(1), this.car.getPosition().getX());
	}
	
	/**
	 * Test car movement in NORTH direction.
	 * @throws CarSimException
	 */
	@Test
	public void testMoveForwardN() throws CarSimException {
		this.car.setPosition(new Position(0, 1)); 
		this.car.setDirection("NORTH");
		this.gridCarSimulator.moveForward();
		assertEquals(new Integer(2), this.car.getPosition().getY());
	}
	
	/**
	 * Test car movement in WEST direction.
	 * @throws CarSimException
	 */
	@Test
	public void testMoveForwardW() throws CarSimException {
		this.car.setPosition(new Position(0, 1)); 
		this.car.setDirection("WEST");
		this.gridCarSimulator.moveForward();
		assertEquals(new Integer(-1), this.car.getPosition().getX());
	}
	
	/**
	 * Test car movement in SOUTH direction.
	 * @throws CarSimException
	 */
	@Test
	public void testMoveForwardS() throws CarSimException {
		this.car.setPosition(new Position(0, 1)); 
		this.car.setDirection("SOUTH");
		this.gridCarSimulator.moveForward();
		assertEquals(new Integer(0), this.car.getPosition().getY());
	}
}
