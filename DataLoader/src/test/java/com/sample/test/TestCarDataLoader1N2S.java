/**
 * 
 */
package com.sample.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sample.dataloader.CarDataLoader1N2S;
import com.sample.dataprocessor.DataAggregator;

/**
 * Test data loading and sorting in com.sample.dataloader.CarDataLoader1N2S
 * @author Milind.
 *
 */
public class TestCarDataLoader1N2S {

	private CarDataLoader1N2S dataLoader;
	public static final int[] REF_SPEED_DIST_MATRIX_MPS = { 0, 5, 10, 15, 20, 25, 30, 35, 40 };
	private static final Long ONE_HR_SLOT = 60*60*1000l;
	private static final Integer NUM_OF_NORTH_SENSORS = 1;
	private static final Integer NUM_OF_SOUTH_SENSORS = 2;
	
	@Before
	public void setUp() {
		//Unit slots of 1 hr.
		this.dataLoader = new CarDataLoader1N2S(ONE_HR_SLOT, REF_SPEED_DIST_MATRIX_MPS);
	}

	/**
	 * Ideal test case for sorting of records in given order,
	 * Case record sequence: AAAAABABAAAA
	 * @throws IOException
	 */
	@Test
	public void testNorthBoundCarsSorting() throws IOException{
		String testData = "A100000\nA100124\nA120000\nA120126\nA130000\nB130004\nA130145\nB130150\nA160000\nA160129\nA230000\nA230129"; 
		InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
		
		List<DataAggregator> dataAggregators = this.dataLoader.loadRecords(inputStream, StandardCharsets.UTF_8);
		DataAggregator dataAggregator  = dataAggregators.get(0);
		Long actualCarCounts = dataAggregator.getUnitPeriodProcs().get(0).getCountCars()/NUM_OF_NORTH_SENSORS;
		//Check counts of cars northBound.
		assertEquals(new Long(4), actualCarCounts);
	}
	
	/**
	 * Ideal test case  for sorting of records in given order; 
	 * Case record sequence: ABABABABAAAA
	 * @throws IOException
	 */
	@Test
	public void testSouthBoundCarsSorting() throws IOException{
		String testData = "A100000\nB100004\nA100122\nB100126\nA130000\nB130004\nA130145\nB130150\nA160000\nA160129\nA230000\nA230129"; 
		InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
		
		List<DataAggregator> dataAggregators = this.dataLoader.loadRecords(inputStream, StandardCharsets.UTF_8);
		DataAggregator dataAggregator  = dataAggregators.get(1);
		Long actualCarCounts = dataAggregator.getUnitPeriodProcs().get(0).getCountCars() /NUM_OF_SOUTH_SENSORS;
		//Check counts of cars southBound.
		assertEquals(new Long(2), actualCarCounts);
	}
	
	/**
	 * Test case where data is tangled because of co-incident sensor hits by cars from both side. 
	 * Case record sequence: ABAABAABAB
	 * @throws IOException
	 */
	@Test
	public void testTangledCoIncidentRecordsABAAB() throws IOException{
		String testData = "A100000\nB100004\nA100005\nA100122\nB100126\nA100139\nA130000\nB130004\nA130145\nB130150"; 
		InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
		
		List<DataAggregator> dataAggregators = this.dataLoader.loadRecords(inputStream, StandardCharsets.UTF_8);
		DataAggregator dataNorthAggregator  = dataAggregators.get(0);
		DataAggregator dataSouthAggregator  = dataAggregators.get(1);
		Long actualNorthCarCounts = dataNorthAggregator.getUnitPeriodProcs().get(0).getCountCars() /NUM_OF_NORTH_SENSORS;
		Long actualSouthCarCounts = dataSouthAggregator.getUnitPeriodProcs().get(0).getCountCars() /NUM_OF_SOUTH_SENSORS;
		
		//Check counts of cars north and southBound.
		assertEquals(new Long(1), actualNorthCarCounts);
		assertEquals(new Long(2), actualSouthCarCounts);
	}
	
	/**
	 * Test case where data is tangled because of co-incident sensor hits by cars from both side. 
	 * Case record sequence: AABABAABAB.
	 * @throws IOException
	 */
	@Test
	public void testTangledCoIncidentRecordsAAABABA() throws IOException{
		String testData = "A100001\nA100003\nB100004\nA100122\nB100126\nA100139\nA130000\nB130004\nA130145\nB130150"; 
		InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
		
		List<DataAggregator> dataAggregators = this.dataLoader.loadRecords(inputStream, StandardCharsets.UTF_8);
		DataAggregator dataNorthAggregator  = dataAggregators.get(0);
		DataAggregator dataSouthAggregator  = dataAggregators.get(1);
		Long actualNorthCarCounts = dataNorthAggregator.getUnitPeriodProcs().get(0).getCountCars() /NUM_OF_NORTH_SENSORS;
		Long actualSouthCarCounts = dataSouthAggregator.getUnitPeriodProcs().get(0).getCountCars() /NUM_OF_SOUTH_SENSORS;
		
		//Check counts of cars north and southBound.
		assertEquals(new Long(1), actualNorthCarCounts);
		assertEquals(new Long(2), actualSouthCarCounts);
	}
	
	/**
	 * Corrupt data sequence.
	 * Case record sequence: AABABAAAB.
	 * @throws IOException
	 */
	@Test(expected = RuntimeException.class)
	public void testBadData() throws IOException{
		String testData = "A100001\nA100003\nB100004\nA100122\nB100126\nA100139\nA130000\nA130145\nB130150"; 
		InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
		this.dataLoader.loadRecords(inputStream, StandardCharsets.UTF_8);
	}
}
