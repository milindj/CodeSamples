package com.sample.gridcarsim.commands.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sample.gridsim.Grid;
import com.sample.gridsim.Position;
import com.sample.gridsim.car.commands.GpsReportCmd;
import com.sample.gridsim.car.components.Car;
import com.sample.gridsim.exceptions.PositionNotInitialised;
import com.sample.gridsim.exceptions.GridSimException;

/**
 * Test GpsReport Command.
 * @author milind
 *
 */
public class TestGpsReport {
	private Car car;

	@Before
	public void setUp() throws Exception {
		this.car = new Car(new Grid(10, 10, -10, -10));
	}

	/**
	 * Test unitialised car scenario.
	 * @throws GridSimException
	 */
	@Test(expected = PositionNotInitialised.class)
	public void testExecuteUninitialisedCar() throws GridSimException {
		 GpsReportCmd gpsReportCmd = new GpsReportCmd(this.car);
		 gpsReportCmd.execute();
	}
	
	/**
	 *  Test GpsReport command output.
	 * @throws GridSimException
	 */
	@Test
	public void testGpsReport() throws GridSimException {
		this.car.setPosition(new Position(1, 2)); 
		this.car.setDirection("NORTH"); 
		GpsReportCmd gpsReportCmd = new GpsReportCmd(this.car);
		gpsReportCmd.execute();
		assertEquals("Output: 1, 2, NORTH", this.car.getGpsReport());
	}

}
