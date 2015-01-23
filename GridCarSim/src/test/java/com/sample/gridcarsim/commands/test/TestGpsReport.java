package com.sample.gridcarsim.commands.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sample.gridcarsim.commands.GpsReportCmd;
import com.sample.gridcarsim.components.Car;
import com.sample.gridcarsim.components.Grid;
import com.sample.gridcarsim.components.Position;
import com.sample.gridcarsim.exceptions.CarPositionNotInitialised;
import com.sample.gridcarsim.exceptions.CarSimException;

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
	 * @throws CarSimException
	 */
	@Test(expected = CarPositionNotInitialised.class)
	public void testExecuteUninitialisedCar() throws CarSimException {
		 GpsReportCmd gpsReportCmd = new GpsReportCmd(this.car);
		 gpsReportCmd.execute();
	}
	
	/**
	 *  Test GpsReport command output.
	 * @throws CarSimException
	 */
	@Test
	public void testGpsReport() throws CarSimException {
		this.car.setPosition(new Position(1, 2)); 
		this.car.setDirection("NORTH"); 
		GpsReportCmd gpsReportCmd = new GpsReportCmd(this.car);
		gpsReportCmd.execute();
		assertEquals("Output: 1, 2, NORTH", this.car.getGpsReport());
	}

}
