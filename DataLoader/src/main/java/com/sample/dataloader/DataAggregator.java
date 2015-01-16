package com.sample.dataloader;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DataAggregator {

	public static final Integer SPEED_KMPH_FACTOR = 3600/1000;
	
	private final Integer slotsInADay;
	private Integer numberOfSensors;
	private Integer dayCount=0;
	private ArrayList<Integer> sumOfCountOverDays = new ArrayList<Integer>();
	private LinkedList<UnitTimePeriodProcessor> unitPeriodProcs = new LinkedList<UnitTimePeriodProcessor>();

	public DataAggregator(Integer periodInterval, Integer numberOfSensors) {
		this.slotsInADay = (int) (TimeUnit.DAYS.toMillis(1) / periodInterval);
		this.numberOfSensors = numberOfSensors;
	}

	public void fitPeriodProcIntoTimeSlot(UnitTimePeriodProcessor periodUnit, boolean nextDay) {
		if (nextDay)
			this.dayCount++;
		Double periodSlot = Math.floor(periodUnit.getIntervalStartStamp() / periodUnit.getInterval()) + this.dayCount;
		periodSlot = periodSlot + this.slotsInADay * this.dayCount;
		for (int i = 0; i < periodSlot - this.unitPeriodProcs.size(); i++) {
			if (this.unitPeriodProcs.size()>0){
				this.unitPeriodProcs.add(new UnitTimePeriodProcessor(this.unitPeriodProcs.getLast().getIntervalEndStamp() + 1, periodUnit.getInterval(), periodUnit.getSpeedDistMatrix()));
			}else{
				this.unitPeriodProcs.add(new UnitTimePeriodProcessor(1, periodUnit.getInterval(), periodUnit.getSpeedDistMatrix()));
			}
		}
		this.unitPeriodProcs.add(periodUnit);
	}

	public UnitTimePeriodProcessor identifyPeakPeriod(Integer unitMultiplier, Integer periodInterval, Integer day) {
		int count = 0;
		int peakCount = 0;
		UnitTimePeriodProcessor peakPeriod =null;
		for (int i = 0; i < this.slotsInADay; i++) {
			UnitTimePeriodProcessor unitTimePeriodRecord  = this.unitPeriodProcs.get(i+(day*(this.slotsInADay-1)));			
			count = count + unitTimePeriodRecord.getCountHotels();
			if ((i % unitMultiplier) == 0) {
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
	
	public List<Integer> aggregateAvgSpeedOverAllDays(Integer unitMultiplier, Integer periodInterval) {
		List<Integer> avgList = new ArrayList<Integer>(); 
		List counts;
		List speeds;
		
		for (int d = 0; d < this.dayCount; d++) {
			this.aggregateCounts(unitMultiplier, periodInterval, d);
			this.aggregateAvgSpeed(unitMultiplier, periodInterval, d);
			}
		
		return avgList;
	}
	
	
	public List<Integer> aggregateCounts(Integer unitMultiplier, Integer periodInterval, Integer day) {
		List<Integer> countList = new ArrayList<Integer>(); 
		int count = 0;
		int peakCount = 0;
		for (int i = 0, j=0; i < this.slotsInADay; i++) {
			UnitTimePeriodProcessor unitTimePeriodRecord  = this.unitPeriodProcs.get(i+(day*(this.slotsInADay-1)));			
			count = count + unitTimePeriodRecord.getCountHotels();
			if ((i % unitMultiplier) == 0) {
				countList.add(count/this.numberOfSensors);
				//Calculate counts for getting averages per period for all the days.
				if (this.sumOfCountOverDays.size() == (this.slotsInADay/unitMultiplier)){
					this.sumOfCountOverDays.set(j, count/this.numberOfSensors + this.sumOfCountOverDays.get(j));
					j++;
				}else{
					this.sumOfCountOverDays.add(count/this.numberOfSensors);
				}
				peakCount = (count > peakCount)? count:peakCount;
				//Reset counts.
				count = 0;
			}
		}
		return countList;
	}
	
	public List<Integer> aggregateAvgSpeed(Integer unitMultiplier, Integer periodInterval, Integer day) {
		List<Integer> speedList = new ArrayList<Integer>(); 
		double speedSum = 0;
		int count = 0;
		for (int i = 0; i < this.slotsInADay; i++) {
			//Iterate through all the unitTimeSlots for the given #day
			UnitTimePeriodProcessor unitTimePeriodRecord  = this.unitPeriodProcs.get(i+(day*(this.slotsInADay-1)));
			count = count + unitTimePeriodRecord.getCountHotels();
			speedSum = speedSum + unitTimePeriodRecord.getSumPrice();
			if ((i % unitMultiplier) == 0) {
				//Calculate and record average speed.
				int avgSpeed = (int) (SPEED_KMPH_FACTOR*(speedSum/this.numberOfSensors)/(count/this.numberOfSensors));
			 	speedList.add(avgSpeed);
				//Reset values
			 	count = 0;
				speedSum =0;
			}
		}
		return speedList;
	}

	public Map<Integer, List<Double>> aggregateRoughSpeedDistribution(Integer unitMultiplier, Integer periodInterval, Integer day){
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
			if ((i % unitMultiplier) == 0) {
				double resultSpdDist = speedDist/this.numberOfSensors;
				speedList.add(resultSpdDist);
				speedDist = 0.0;
			}
		}
		//Store the speed dist against the referenceSpeed.
		mapSpeedDist.put(refSpeedDist, speedList);
		}
		return mapSpeedDist;
	}

	public ArrayList<Integer> getSumOfCountOverDays() {
		return sumOfCountOverDays;
	}

	public void setSumOfCountOverDays(ArrayList<Integer> sumOfCountOverDays) {
		this.sumOfCountOverDays = sumOfCountOverDays;
	}

	public LinkedList<UnitTimePeriodProcessor> getUnitPeriodProcs() {
		return unitPeriodProcs;
	}

	public void setUnitPeriodProcs(LinkedList<UnitTimePeriodProcessor> unitPeriodProcs) {
		this.unitPeriodProcs = unitPeriodProcs;
	}

	public Integer getSlotsInADay() {
		return slotsInADay;
	}	
}
