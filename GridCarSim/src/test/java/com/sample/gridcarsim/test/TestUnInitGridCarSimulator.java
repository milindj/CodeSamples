package com.sample.gridcarsim.test;

import org.junit.Before;
import org.junit.Test;

import com.sample.gridsim.car.components.GridCarSimImpl;
import com.sample.gridsim.exceptions.PositionNotInitialised;
import com.sample.gridsim.exceptions.GridSimException;

/**
 * Test to check GridCarSimulator in an uninitialized state.
 * @author milind
 *
 */
public class TestUnInitGridCarSimulator {
	GridCarSimImpl gridCarSimulator;

	@Before
	public void setUp() throws Exception {
		this.gridCarSimulator = GridCarSimImpl.getInstance();
	}

	/**
	 * Validate to get an uninitialized error when moving forward.
	 * @throws GridSimException
	 */
	@Test(expected = PositionNotInitialised.class)
	public void testExecuteUninitialisedFwd() throws GridSimException {
		this.gridCarSimulator.moveForward();
	}
	
	/**
	 * Validate to get an uninitialized error when turning left.
	 * @throws GridSimException
	 */
	@Test(expected = PositionNotInitialised.class)
	public void testExecuteUninitialisedLeft() throws GridSimException {
		this.gridCarSimulator.turnLeft();
	}
	
	/**
	 * Validate to get an uninitialized error when turning right.
	 * @throws GridSimException
	 */
	@Test(expected = PositionNotInitialised.class)
	public void testExecuteUninitialisedRight() throws GridSimException {
		this.gridCarSimulator.turnRight();
	}
	
	/**
	 * Validate to get an uninitialized error when getting gps report.
	 * @throws GridSimException
	 */
	@Test(expected = PositionNotInitialised.class)
	public void testExecuteUninitialisedGpsReport() throws GridSimException {
		this.gridCarSimulator.gpsReport();
	}

}
