package com.sample.gridcarsim.components.test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sample.gridcarsim.components.Grid;
import com.sample.gridcarsim.components.Position;

/**
 * Test the grid we would run are simulated car on.
 * @author milind
 *
 */
public class TestGridPosition {
	
	private Grid grid;

	@Before
	public void setUp() throws Exception {
		this.grid = new Grid(10, 10, -10, -10);
	}

	/**
	 * Test1 to validate a position being in range inside the grid.
	 */
	@Test
	public void testCasePosition1() {
		assertTrue(this.grid.validateGridPosition(new Position(9, 9)));
	}
	
	/**
	 * Test2 to validate a position being in range inside the grid.
	 */
	@Test
	public void testCasePosition2() {
		assertTrue(this.grid.validateGridPosition(new Position(0, 0)));
	}

	/**
	 * Test3 to validate a position being in range inside the grid.
	 */
	@Test
	public void testCasePosition3() {
		assertTrue(this.grid.validateGridPosition(new Position(-10,-10)));
	}

	/**
	 * Test1 to invalidate a position being outside the range of the grid.
	 */
	@Test
	public void testCaseBadPosition1() {
		assertFalse(this.grid.validateGridPosition(new Position(-11,-11)));
	}

	/**
	 * Test2 to invalidate a position being outside the range of the grid.
	 */
	@Test
	public void testCaseBadPosition2() {
		assertFalse(this.grid.validateGridPosition(new Position(0,-11)));
	}
	
	/**
	 * Test3 to invalidate a position being outside the range of the grid.
	 */
	@Test
	public void testCaseBadPosition3() {
		assertFalse(this.grid.validateGridPosition(new Position(11,0)));
	}
	
}
