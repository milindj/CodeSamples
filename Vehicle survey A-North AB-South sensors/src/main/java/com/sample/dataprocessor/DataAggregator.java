package com.sample.dataprocessor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.sample.dataloader.SensorRecord.Direction;

/**
 * DataAggregator,which contains coarse data, in a ready to be further calculated form.
 * And contains methods to calculate and return various parameters like average speed, total counts etc.
 * @author Milind.
 *
 */
public class DataAggregator {

	public static final Integer SPEED_KMPH_FACTOR = 3600/1000;
	
	private final Integer slotsInADay;
	private Integer numberOfSensors;
	private Integer dayCount=0;
	private Direction direction;
	private LinkedList<UnitTimePeriodProcessor> unitPeriodProcs = new LinkedList<UnitTimePeriodProcessor>();

	public DataAggregator(Long periodInterval, Integer numberOfSensors, int[] speedDistMatrix, Direction direction) {
		this.slotsInADay = (int)(TimeUnit.DAYS.toMillis(1) / periodInterval);
		this.numberOfSensors = numberOfSensors;
		this.initPeriodSlots(this.slotsInADay, periodInterval, speedDistMatrix);
		this.direction = direction;
	}
	
	/**
	 * Empty unit period slots are filled by unitTimePeriodRecord of 0 values for the new day
	 * @param slotsInADay
	 * @param periodInterval
	 * @param speedDistMatrix
	 */
	private void initPeriodSlots(Integer slotsInADay,Long periodInterval, int[] speedDistMatrix){
		for (int i = 0; i <= slotsInADay; i++) {
			this.unitPeriodProcs.add(new UnitTimePeriodProcessor(new Long(i*slotsInADay), periodInterval, speedDistMatrix));
		}
	}

	/**
	 * Fits the unitTimePeriodRecord into the right slot. 
	 * For the sake of continuity empty slots are filled by unitTimePeriodRecord of 0 values for the new day.
	 * @param periodUnit
	 * @param nextDay
	 */
	public void fitPeriodProcIntoTimeSlot(UnitTimePeriodProcessor periodUnit, boolean nextDay) {
		if (nextDay){
			this.dayCount++;
			initPeriodSlots(slotsInADay, periodUnit.getInterval(), periodUnit.getSpeedDistMatrix());
		}
		Integer periodSlot = (int)Math.floor(periodUnit.getIntervalStartStamp() / periodUnit.getInterval()) + this.dayCount;
		periodSlot = periodSlot + this.slotsInADay * this.dayCount;	
		this.unitPeriodProcs.set(periodSlot, periodUnit);
	}

	/**
	 * Identifies the peak period.
	 * @param numOfSlots
	 * @param day
	 * @return
	 */
	public UnitTimePeriodProcessor identifyPeakPeriod(Integer numOfSlots, Integer day) {
		long count = 0l;
		long peakCount = 0l;
		UnitTimePeriodProcessor peakPeriod =null;
		for (int i = 0; i < this.slotsInADay; i++) {
			UnitTimePeriodProcessor unitTimePeriodRecord  = this.unitPeriodProcs.get(i+(day*(this.slotsInADay-1)));			
			count = count + unitTimePeriodRecord.getCountCars();
			if ((i % numOfSlots) == 0) {
				if (count > peakCount){
					peakCount = count;
					peakPeriod = unitTimePeriodRecord;
				}
				//Reset the count.
				count = 0;
			}
		}
		return peakPeriod;
	}
	
	/**
	 * Calculate and return the counts for averages per period for all the days.
	 * @param numOfSlots
	 * @param periodInterval
	 * @return
	 */
	public List<Double> aggregateAvgCountOverAllDays(Integer numOfSlots) {
		List<Double> avgCountList = new ArrayList<Double>();
		//Initialize average countList to the first days data.
		//avgCountList.addAll((Collection<? extends Double>) this.aggregateCounts(numOfSlots, 0));
		for (Long count : this.aggregateCounts(numOfSlots, 0)) {
			avgCountList.add(count.doubleValue());
		} 
		//Add-up rest of the days.
		for (int d = 1; this.dayCount > 0 && d <= this.dayCount; d++) {
			List<Long> dayCounts = this.aggregateCounts(numOfSlots, d);
			for (int i = 0; i < dayCounts.size(); i++) {
				avgCountList.set(i, dayCounts.get(i) + avgCountList.get(i));
			}
		}
		//Calculate average.
		for (int i = 0; i < avgCountList.size(); i++) {
			avgCountList.set(i, avgCountList.get(i) / (this.dayCount + 1));
		}
		return avgCountList;
	}
	
	/**
	 * Calculate and return the counts of cars per period.
	 * @param numOfSlots
	 * @param day
	 * @return
	 */
	public List<Long> aggregateCounts(Integer numOfSlots, Integer day) {
		List<Long> countList = new ArrayList<Long>(); 
		long count = 0;
		for (int i = 0; i < this.slotsInADay; i++) {
			UnitTimePeriodProcessor unitTimePeriodRecord  = this.unitPeriodProcs.get(i+(day*(this.slotsInADay-1)));			
			count = count + unitTimePeriodRecord.getCountCars();
			if ((i % numOfSlots) == 0) {
				countList.add(count/this.numberOfSensors);
				//Reset counts.
				count = 0;
			}
		}
		return countList;
	}
	
	/**
	 * Calculate and return the average speed(meters/sec) of cars per period 
	 * @param numOfSlots
	 * @param day
	 * @return
	 */
	public List<Double> aggregateAvgSpeed(Integer numOfSlots, Integer day) {
		List<Double> speedList = new ArrayList<Double>();
		double speedSum = 0;
		long count = 0;
		for (int i = 0; i < this.slotsInADay; i++) {
			//Iterate through all the unitTimeSlots for the given #day
			UnitTimePeriodProcessor unitTimePeriodRecord  = this.unitPeriodProcs.get(i+(day*(this.slotsInADay-1)));
			count = count + unitTimePeriodRecord.getCountCars();
			speedSum = speedSum + unitTimePeriodRecord.getSumSpeed();
			if ((i % numOfSlots) == 0) {
				//Calculate and record average speed.
				double avgSpeed = (SPEED_KMPH_FACTOR*(speedSum/count));
			 	speedList.add(avgSpeed);
				//Reset values
			 	count = 0;
				speedSum =0;
			}
		}
		return speedList;
	}

	/**
	 * Calculate and returns the speed(meters/sec) distribution per period for a given day. 
	 * @param numOfSlots
	 * @param day
	 * @return
	 */
	public Map<Integer, List<Double>> aggregateRoughSpeedDistribution(Integer numOfSlots, Integer day){
		Map<Integer, List<Double>> mapSpeedDist = new LinkedHashMap<Integer, List<Double>>();
		int[] speedDistMatrix = {};
		if(this.unitPeriodProcs.size()>0)
		speedDistMatrix = this.unitPeriodProcs.getFirst().getSpeedDistMatrix();
		for (Integer refSpeedDist : speedDistMatrix) {
		Double speedDist = 0.0;
		// summarize all the speeds for the given refSpeed. 
		List<Double> speedList = new ArrayList<Double>();
		for (int i = 0; i < this.slotsInADay; i++) {
			UnitTimePeriodProcessor unitTimePeriodRecord  = this.unitPeriodProcs.get(i+(day*(this.slotsInADay-1)));
			speedDist = speedDist + unitTimePeriodRecord.getSpeedDistribution().get(refSpeedDist);
			if ((i % numOfSlots) == 0) {
				double resultSpdDist = speedDist/this.numberOfSensors;
				speedList.add(resultSpdDist);
				speedDist = 0.0;
			}
		}
		//Store the speed distribution against the referenceSpeed.
		mapSpeedDist.put(refSpeedDist, speedList);
		}
		return mapSpeedDist;
	}
	
	/**
	 * Calculate and returns the average distance(meters) between cars per period for a given day. 
	 * @param numOfSlots
	 * @param day
	 * @return
	 */
	public List<Double> aggregateDistanceBetweenCars(Integer numOfSlots, Integer day) {
		List<Double> resultList = new ArrayList<Double>(); 
		double distBtwnCarsSum = 0;
		long count = 0;
		for (int i = 0; i < this.slotsInADay; i++) {
			//Iterate through all the unitTimeSlots for the given #day
			UnitTimePeriodProcessor unitTimePeriodRecord  = this.unitPeriodProcs.get(i+(day*(this.slotsInADay-1)));
			count = count + unitTimePeriodRecord.getCountCars();
			distBtwnCarsSum = distBtwnCarsSum + unitTimePeriodRecord.getSumDistBtwnCars();
			if ((i % numOfSlots) == 0) {
				//Calculate and record average speed.
				double avgDistance = (distBtwnCarsSum/count);
			 	resultList.add(avgDistance);
				//Reset values
			 	count = 0;
				distBtwnCarsSum =0;
			}
		}
		return resultList;
	}

	/**
	 * @return the slotsInADay
	 */
	public Integer getSlotsInADay() {
		return slotsInADay;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @return the unitPeriodProcs
	 */
	public LinkedList<UnitTimePeriodProcessor> getUnitPeriodProcs() {
		return unitPeriodProcs;
	}
}
