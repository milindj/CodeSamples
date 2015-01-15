package com.sample.dataloader;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

public class DataResult {

	private LinkedList<UnitTimePeriodRecord> hotelSet = new LinkedList<UnitTimePeriodRecord>();
	private Integer dayCount=0;
	private final Integer slotsInADay;
	private Integer numberOfSensors;
	private static final Character OUT_FORMAT_DELIMITER = '|';
	ArrayList<Integer> sumOfCountOverDays = new ArrayList<Integer>();
	ArrayList<Integer> counts = new ArrayList<Integer>();
	private static final Integer SPEED_KMPH_FACTOR = 3600/1000;
	
	public DataResult(Integer periodInterval, Integer numberOfSensors) {
		this.slotsInADay = (int) (TimeUnit.DAYS.toMillis(1) / periodInterval);
		this.numberOfSensors = numberOfSensors;
	}

	public void addHotelRecordInSlots(UnitTimePeriodRecord periodUnit, boolean nextDay) {
		if (nextDay)
			dayCount++;
		Double periodSlot = Math.floor(periodUnit.getIntervalStartStamp() / periodUnit.getInterval()) + dayCount;
		periodSlot = periodSlot + slotsInADay * dayCount;
		for (int i = 0; i < periodSlot - this.hotelSet.size(); i++) {
			if (this.hotelSet.size()>0){
				this.hotelSet.add(new UnitTimePeriodRecord(this.hotelSet.getLast().getIntervalEndStamp() + 1, periodUnit.getInterval(), periodUnit.getSpeedDistMatrix()));
			}else{
				this.hotelSet.add(new UnitTimePeriodRecord(1, periodUnit.getInterval(), periodUnit.getSpeedDistMatrix()));
			}
		}
		this.hotelSet.add(periodUnit);
	}

	public void printResults(Integer periodInterval) {
		StringBuilder strBuilder = new StringBuilder();
		String periodHeadLine = this.buildCSVHeaderLine(12, periodInterval);
		int numOfDays = this.hotelSet.size()/this.slotsInADay;
		for (int day = 0; day < numOfDays; day++) {
			strBuilder.append("#### Day # ").append(day+1);
			strBuilder.append("\n");
		///	strBuilder.append(periodHeadLine);
			strBuilder.append("\n");
			strBuilder.append("Counts > ");
			strBuilder.append(this.buildCSVString(this.summarizeCounts(12, periodInterval, day)));
			strBuilder.append("\n");
			strBuilder.append("AvgSpd > ");
			strBuilder.append(this.buildCSVString(this.summarizeAvgSpeedLine(12, periodInterval, day)));
			strBuilder.append("\n");
			
			UnitTimePeriodRecord peakPeriod = this.getPeakPeriod(12, periodInterval, day);
			
			strBuilder.append(" Peak " + (peakPeriod.getIntervalStartStamp()/(12*periodInterval)));
			strBuilder.append("\n");
			strBuilder.append("SpdDst > ");
			strBuilder.append("\n");
			Map<Integer, List<Double>> speedDist = this.summarizeRoughSpeedDistribution(12, periodInterval, day);
			for (Entry<Integer, List<Double>> distEntry : speedDist.entrySet() ) {
				strBuilder.append(" " + distEntry.getKey()*SPEED_KMPH_FACTOR + " > ");
				strBuilder.append(this.buildCSVString(distEntry.getValue()));
				strBuilder.append("\n");
			}
			strBuilder.append("\n");
		}
	
		System.out.println(strBuilder);
		System.out.println(sumOfCountOverDays);
		
		
	}

	public UnitTimePeriodRecord getPeakPeriod(Integer unitMultiplier, Integer periodInterval, Integer day) {
		List<Integer> countList = new ArrayList<Integer>(); 
		int count = 0;
		int peakCount = 0;
		UnitTimePeriodRecord peakPeriod =null;
		for (int i = 0, j=0; i < slotsInADay; i++) {
			UnitTimePeriodRecord unitTimePeriodRecord  = hotelSet.get(i+(day*(this.slotsInADay-1)));			
			count = count + unitTimePeriodRecord.getCountHotels();
			if ((i % unitMultiplier) == 0) {
				if (count > peakCount){
					peakCount = count;
					peakPeriod = unitTimePeriodRecord;
				}
				
				//Reset counts.
				count = 0;
			}
		}
		return peakPeriod;
	}
	
	
	public List<Integer> summarizeCounts(Integer unitMultiplier, Integer periodInterval, Integer day) {
		List<Integer> countList = new ArrayList<Integer>(); 
		int count = 0;
		int peakCount = 0;
		for (int i = 0, j=0; i < slotsInADay; i++) {
			UnitTimePeriodRecord unitTimePeriodRecord  = hotelSet.get(i+(day*(this.slotsInADay-1)));			
			count = count + unitTimePeriodRecord.getCountHotels();
			if ((i % unitMultiplier) == 0) {
				countList.add(count/this.numberOfSensors);
				//Calculate counts for getting averages per period for all the days.
				if (sumOfCountOverDays.size() == (this.slotsInADay/unitMultiplier)){
					sumOfCountOverDays.set(j, count/this.numberOfSensors + sumOfCountOverDays.get(j));
					j++;
				}else{
					sumOfCountOverDays.add(count/this.numberOfSensors);
				}
				peakCount = (count > peakCount)? count:peakCount;
				//Reset counts.
				count = 0;
			}
		}
		return countList;
	}
	
	public void checkForPeakPeriods(){
		
	}
	
	public List<Integer> summarizeAvgSpeedLine(Integer unitMultiplier, Integer periodInterval, Integer day) {
		List<Integer> speedList = new ArrayList<Integer>(); 
		double speedSum = 0;
		int count = 0;
		for (int i = 0; i < slotsInADay; i++) {
			//Iterate through all the unitTimeSlots for the given #day
			UnitTimePeriodRecord unitTimePeriodRecord  = hotelSet.get(i+(day*(this.slotsInADay-1)));
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

	public Map<Integer, List<Double>> summarizeRoughSpeedDistribution(Integer unitMultiplier, Integer periodInterval, Integer day){
		Map<Integer, List<Double>> mapSpeedDist = new LinkedHashMap<Integer, List<Double>>();
		int[] speedDistMatrix = {};
		if(hotelSet.size()>0)
		speedDistMatrix = hotelSet.getFirst().getSpeedDistMatrix();
		for (Integer refSpeedDist : speedDistMatrix) {
		Double speedDist = 0.0;
		// summarize all the speeds for the given refSpeed. 
		List<Double> speedList = new ArrayList<Double>();
		for (int i = 0; i < slotsInADay; i++) {
			UnitTimePeriodRecord unitTimePeriodRecord  = hotelSet.get(i+(day*(this.slotsInADay-1)));
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
	
	public String buildCSVHeaderLine(Integer unitMultiplier, Integer periodInterval) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("TimePeriod > ");
		for (int i = 1, j = 0; i <= slotsInADay; i++) {
			j = j + periodInterval;
			if ((i % unitMultiplier) == 0) {
				strBuilder.append(OUT_FORMAT_DELIMITER).append(j / 60000);
			}			
		}
		return strBuilder.toString();
	}	

	public String buildCSVString(List<?> valueList) {
		StringBuilder strBuilder = new StringBuilder();
		for (Object value : valueList) {
			strBuilder.append(OUT_FORMAT_DELIMITER).append(value);
		}
		return strBuilder.toString();
	}
	
	@Deprecated
	public String buildRoughSpeedDistribution(Integer unitMultiplier, Integer periodInterval, Integer day){
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("SpDist> \n");
		int[] speedDistMatrix = {};
		if(hotelSet.size()>0)
		speedDistMatrix = hotelSet.getFirst().getSpeedDistMatrix();		
		for (Integer refSpeedDist : speedDistMatrix) {
		Integer speedDist = 0;
		strBuilder.append("SpD " + SPEED_KMPH_FACTOR*refSpeedDist + "> ");
		for (int i = 0; i < slotsInADay; i++) {
			UnitTimePeriodRecord unitTimePeriodRecord  = hotelSet.get(i+(day*(this.slotsInADay-1)));
			speedDist = speedDist + unitTimePeriodRecord.getSpeedDistribution().get(refSpeedDist);
			if ((i % unitMultiplier) == 0) {
				strBuilder.append(OUT_FORMAT_DELIMITER).append(SPEED_KMPH_FACTOR*speedDist/this.numberOfSensors);
				speedDist = 0;
			}
		}
		strBuilder.append("\n");
		}
		return strBuilder.toString();
	}
	
	@Deprecated
	public String buildAvgSpeedLine(Integer unitMultiplier, Integer periodInterval, Integer day) {
		StringBuilder strBuilder = new StringBuilder();
		double speedSum = 0;
		int count = 0;
		strBuilder.append("AvgSp > ");
		for (int i = 0; i < slotsInADay; i++) {
			UnitTimePeriodRecord unitTimePeriodRecord  = hotelSet.get(i+(day*(this.slotsInADay-1)));
			count = count + unitTimePeriodRecord.getCountHotels();
			speedSum = speedSum + unitTimePeriodRecord.getSumPrice();
			if ((i % unitMultiplier) == 0) {
				// int normaliseCount = ;
			 int avgSpeed = (int) (SPEED_KMPH_FACTOR*(speedSum/this.numberOfSensors)/(count/this.numberOfSensors));
				strBuilder.append(OUT_FORMAT_DELIMITER).append(avgSpeed);
				count = 0;
				speedSum =0;
			}
		}
		return strBuilder.toString();
	}
	
	@Deprecated
	public String buildCountsLine(Integer unitMultiplier, Integer periodInterval, Integer day) {
		StringBuilder strBuilder = new StringBuilder();
		int count = 0;
		strBuilder.append("Count > ");
		for (int i = 0, j=0; i < slotsInADay; i++) {
			UnitTimePeriodRecord unitTimePeriodRecord  = hotelSet.get(i+(day*(this.slotsInADay-1)));			
			count = count + unitTimePeriodRecord.getCountHotels();
			if ((i % unitMultiplier) == 0) {
				strBuilder.append(OUT_FORMAT_DELIMITER).append(count/this.numberOfSensors);
				//counts.add(count/this.numberOfSensors);
				//Calculate count for getting averages per period for all the days.
				if (sumOfCountOverDays.size() == (this.slotsInADay/unitMultiplier)){
					sumOfCountOverDays.set(j, count/this.numberOfSensors + sumOfCountOverDays.get(j));
					j++;
				}else{
					sumOfCountOverDays.add(count/this.numberOfSensors);
				}
				count = 0;
			}
		}
		return strBuilder.toString();
	}
	
}
