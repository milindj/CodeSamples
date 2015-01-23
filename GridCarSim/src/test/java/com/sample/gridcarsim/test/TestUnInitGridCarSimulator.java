package com.sample.gridcarsim.test;

import org.junit.Before;
import org.junit.Test;

import com.sample.gridcarsim.GridCarSimulator;
import com.sample.gridcarsim.exceptions.CarPositionNotInitialised;
import com.sample.gridcarsim.exceptions.CarSimException;

/**
 * Test to check GridCarSimulator in an uninitialized state.
 * @author milind
 *
 */
public class TestUnInitGridCarSimulator {
	GridCarSimulator gridCarSimulator;

	@Before
	public void setUp() throws Exception {
		this.gridCarSimulator = GridCarSimulator.getInstance();
	}

	/**
	 * Validate to get an uninitialized error when moving forward.
	 * @throws CarSimException
	 */
	@Test(expected = CarPositionNotInitialised.class)
	public void testExecuteUninitialisedFwd() throws CarSimException {
		this.gridCarSimulator.moveForward();
	}
	
	/**
	 * Validate to get an uninitialized error when turning left.
	 * @throws CarSimException
	 */
	@Test(expected = CarPositionNotInitialised.class)
	public void testExecuteUninitialisedLeft() throws CarSimException {
		this.gridCarSimulator.turnLeft();
	}
	
	/**
	 * Validate to get an uninitialized error when turning right.
	 * @throws CarSimException
	 */
	@Test(expected = CarPositionNotInitialised.class)
	public void testExecuteUninitialisedRight() throws CarSimException {
		this.gridCarSimulator.turnRight();
	}
	
	/**
	 * Validate to get an uninitialized error when getting gps report.
	 * @throws CarSimException
	 */
	@Test(expected = CarPositionNotInitialised.class)
	public void testExecuteUninitialisedGpsReport() throws CarSimException {
		this.gridCarSimulator.gpsReport();
	}

}
