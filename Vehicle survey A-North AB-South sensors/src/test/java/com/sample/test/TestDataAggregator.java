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
import com.sample.dataprocessor.UnitTimePeriodProcessor;

/**
 * Test class to test all calculation results of DataAggregator.
 * @author Milind
 *
 */
public class TestDataAggregator {

	private CarDataLoader1N2S dataLoader;
	public static final int[] REF_SPEED_DIST_MATRIX_MPS = { 0, 5, 10, 15, 20, 25, 30, 35, 40 };
	private static final Long ONE_HR_SLOT = 60*60*1000l;
	
	@Before
	public void setUp() {
		//Unit slots of 1 hr.
		this.dataLoader = new CarDataLoader1N2S(ONE_HR_SLOT, REF_SPEED_DIST_MATRIX_MPS);
	}
	
	/**
	 * Test aggregated north bound car counts. 
	 * @throws IOException
	 */
	@Test
	public void testNorthCarCounts() throws IOException{
		String testData = "A100000\nB100004\nA100122\nB100126\nA130000\nB130004\nA130145\nB130150\nA160000\nA160129"; 
		InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
		
		List<DataAggregator> dataAggregators = this.dataLoader.loadRecords(inputStream, StandardCharsets.UTF_8);
		DataAggregator dataNorthAggregator  = dataAggregators.get(0);
				
		//First day(0) , period slots 1 = ONE_HR_SLOT
		Long actualNorthCarCounts = dataNorthAggregator.aggregateCounts(1, 0).get(0);
		//Check counts of cars north bound.
		assertEquals(new Long(1), actualNorthCarCounts);
	}
	
	/**
	 * Test aggregated south bound car counts. 
	 * @throws IOException
	 */
	@Test
	public void testSouthCarCounts() throws IOException{
		String testData = "A100000\nB100004\nA100122\nB100126\nA130000\nB130004\nA130145\nB130150\nA160000\nA160129"; 
		InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
		
		List<DataAggregator> dataAggregators = this.dataLoader.loadRecords(inputStream, StandardCharsets.UTF_8);
		DataAggregator dataSouthAggregator  = dataAggregators.get(1);
				
		//First day(0) , period slots 1 = ONE_HR_SLOT
		Long actualSouthCarCounts = dataSouthAggregator.aggregateCounts(1, 0).get(0);
		//Check counts of cars south bound.
		assertEquals(new Long(2), actualSouthCarCounts);
	}
	
	/**
	 * Test aggregated north bound car count's average over days. 
	 * @throws IOException
	 */
	@Test
	public void testNorthCarCountAvgOverDays() throws IOException{
		String testData = "A100000\nB100004\nA100122\nB100126\nA130000\nB130004\nA130145\nB130150\nA160000\nA160129\n" + 
				"A100000\nB100004\nA100122\nB100126\nA130000\nB130004\nA130145\nB130150\nA160000\nA160129"; 
		InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
		
		List<DataAggregator> dataAggregators = this.dataLoader.loadRecords(inputStream, StandardCharsets.UTF_8);
		DataAggregator dataNorthAggregator  = dataAggregators.get(0);
				
		//First day(0) , period slots 1 = ONE_HR_SLOT
		Double actualNorthCarAvgCounts = dataNorthAggregator.aggregateAvgCountOverAllDays(1).get(0);
		
		//Check avg counts of cars.
		assertEquals(new Double(2.0), actualNorthCarAvgCounts);
	}
	
	/**
	 * Test aggregated south bound car count's average over days. 
	 * @throws IOException
	 */
	@Test
	public void testSouthCarCountAvgOverDays() throws IOException{
		String testData = "A100000\nB100004\nA100122\nB100126\nA130000\nB130004\nA130145\nB130150\nA160000\nA160129\n" + 
				"A100000\nB100004\nA100122\nB100126\nA130000\nB130004\nA130145\nB130150\nA160000\nA160129"; 
		InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
		
		List<DataAggregator> dataAggregators = this.dataLoader.loadRecords(inputStream, StandardCharsets.UTF_8);
		DataAggregator dataSouthAggregator  = dataAggregators.get(1);
				
		//First day(0) , period slots 1 = ONE_HR_SLOT
		Double actualSouthCarAvgCounts = dataSouthAggregator.aggregateAvgCountOverAllDays(1).get(0);
		
		//Check avg counts of cars.
		assertEquals(new Double(4.0), actualSouthCarAvgCounts);
	}
	
	/**
	 * Test aggregated north bound car speeds. 
	 * @throws IOException
	 */
	@Test
	public void testNorthBoundCarSpeeds() throws IOException{
		String testData = "A100000\nB100004\nA100122\nB100126\nA130000\nB130004\nA130145\nB130150\nA160000\nA160129"; 
		InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
		
		List<DataAggregator> dataAggregators = this.dataLoader.loadRecords(inputStream, StandardCharsets.UTF_8);
		DataAggregator dataNorthAggregator  = dataAggregators.get(0);
				
		//First day(0) , period slots 1 = ONE_HR_SLOT
		Double actualNorthCarAvgSpeed = dataNorthAggregator.aggregateAvgSpeed(1, 0).get(0);
		//Check speed of cars.
		assertEquals(new Double(57.0), actualNorthCarAvgSpeed);
	}
	
	/**
	 * Test aggregated south bound car speeds. 
	 * @throws IOException
	 */
	@Test
	public void testSouthBoundCarSpeeds() throws IOException{
		String testData = "A100000\nB100004\nA100122\nB100126\nA130000\nB130004\nA130145\nB130150\nA160000\nA160129"; 
		InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
		
		List<DataAggregator> dataAggregators = this.dataLoader.loadRecords(inputStream, StandardCharsets.UTF_8);
		DataAggregator dataSouthAggregator  = dataAggregators.get(1);
				
		//First day(0) , period slots 1 = ONE_HR_SLOT
		Double actualSouthCarAvgSpeed = dataSouthAggregator.aggregateAvgSpeed(1, 0).get(0);
		//Check speed of cars.
		assertEquals(new Double(55.5), actualSouthCarAvgSpeed);
	}
	
	/**
	 * Test aggregated north bound car distribution of speeds. 
	 * @throws IOException
	 */
	@Test
	public void testNorthSpeedDistribution() throws IOException{
		String testData = "A100000\nB100004\nA100122\nB100126\nA130000\nB130004\nA130145\nB130150\nA160000\nA160129\n";
		InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
		
		List<DataAggregator> dataAggregators = this.dataLoader.loadRecords(inputStream, StandardCharsets.UTF_8);
		DataAggregator dataNorthAggregator  = dataAggregators.get(0);
				
		//First day(0) , period slots 1 = ONE_HR_SLOT
		Integer speedDistKey = 20; //meters/sec.
		List<Double> actualNorthSpeedDistr = dataNorthAggregator.aggregateRoughSpeedDistribution(1, 0).get(speedDistKey);
		
		//Check distribution of cars.
		assertEquals(new Double(1.0), actualNorthSpeedDistr.get(0));
	}
	
	/**
	 * Test aggregated south bound car distribution of speeds. 
	 * @throws IOException
	 */
	@Test
	public void testSouthSpeedDistribution() throws IOException{
		String testData = "A100000\nB100004\nA100122\nB100126\nA130000\nB130004\nA130145\nB130150\nA160000\nA160129\n";
		InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
		
		List<DataAggregator> dataAggregators = this.dataLoader.loadRecords(inputStream, StandardCharsets.UTF_8);
		DataAggregator dataSouthAggregator  = dataAggregators.get(1);
				
		//First day(0) , period slots 1 = ONE_HR_SLOT
		Integer speedDistKey = 20; //meters/sec.
		List<Double> actualSouthSpeedDistr = dataSouthAggregator.aggregateRoughSpeedDistribution(1, 0).get(speedDistKey);
		
		//Check distribution of cars.
		assertEquals(new Double(2.0), actualSouthSpeedDistr.get(0));
	}
	
	/**
	 * Test aggregated north bound average distance between cars. 
	 * @throws IOException
	 */
	@Test
	public void testNorthAvgDistanceBtwnCars() throws IOException{
		String testData = "A100000\nB100004\nA100122\nB100126\nA130000\nB130004\nA130145\nB130150\nA160000\nA160129\nA170000\nA170139\n";
		InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
		
		List<DataAggregator> dataAggregators = this.dataLoader.loadRecords(inputStream, StandardCharsets.UTF_8);
		DataAggregator dataNorthAggregator  = dataAggregators.get(0);
				
		//First day(0) , period slots 1 = ONE_HR_SLOT
		Double actualNorthAvgDistBtwnCars = dataNorthAggregator.aggregateDistanceBetweenCars(1, 0).get(0);
		//Check counts of cars southBound.
		assertEquals(new Double(76.5), actualNorthAvgDistBtwnCars);
	}
	
	/**
	 * Test aggregated south bound average distance between cars. 
	 * @throws IOException
	 */
	@Test
	public void testSouthAvgDistanceBtwnCars() throws IOException{
		String testData = "A100000\nB100004\nA100122\nB100126\nA130000\nB130004\nA130145\nB130150\nA160000\nA160129\n";
		InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
		
		List<DataAggregator> dataAggregators = this.dataLoader.loadRecords(inputStream, StandardCharsets.UTF_8);
		DataAggregator dataSouthAggregator  = dataAggregators.get(1);
				
		//First day(0) , period slots 1 = ONE_HR_SLOT
		Double actualSouthAvgDistBtwnCars = dataSouthAggregator.aggregateDistanceBetweenCars(1, 0).get(0);
		//Check counts of cars southBound.
		assertEquals(new Double(123.25), actualSouthAvgDistBtwnCars);
	}
	
	/**
	 * Test peak period of north bound cars. 
	 * @throws IOException
	 */
	@Test
	public void testNorthPeakPeriod() throws IOException{
		String testData = "A100000\nB100004\nA100122\nB100126\nA130000\nB130004\nA130145\nB130150\nA160000\nA160129\n";
		InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
		
		List<DataAggregator> dataAggregators = this.dataLoader.loadRecords(inputStream, StandardCharsets.UTF_8);
		DataAggregator dataNorthAggregator  = dataAggregators.get(0);
				
		//First day(0) , period slots 1 = ONE_HR_SLOT
		UnitTimePeriodProcessor actualNorthUnitProc = dataNorthAggregator.identifyPeakPeriod(1, 0);
		
		//Check Peak periods, the first period(0) in this case.
		assertEquals(new Long(0), actualNorthUnitProc.getIntervalStartStamp());
	}
	
	/**
	 * Test peak period of south bound cars. 
	 * @throws IOException
	 */
	@Test
	public void testSouthPeakPeriod() throws IOException{
		String testData = "A100000\nB100004\nA100122\nB100126\nA130000\nB130004\nA130145\nB130150\nA160000\nA160129\n";
		InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
		
		List<DataAggregator> dataAggregators = this.dataLoader.loadRecords(inputStream, StandardCharsets.UTF_8);
		DataAggregator dataSouthAggregator  = dataAggregators.get(1);
				
		//First day(0) , period slots 1 = ONE_HR_SLOT
		UnitTimePeriodProcessor actualSouthUnitProc = dataSouthAggregator.identifyPeakPeriod(1, 0);
		
		//Check Peak periods, the first period(0) in this case.
		assertEquals(new Long(0), actualSouthUnitProc.getIntervalStartStamp());
	}
}
