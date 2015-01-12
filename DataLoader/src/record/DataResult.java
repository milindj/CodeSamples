package record;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class DataResult {

	private LinkedList<UnitTimePeriodRecord> hotelSet = new LinkedList<UnitTimePeriodRecord>();
	private Integer dayCount=0;
	private final Integer slotsInADay;
	private Integer numberOfSensors;
	private static final Character OUT_FORMAT_DELIMITER = '|';
	ArrayList<Integer> sumOfCountOverDays = new ArrayList<Integer>();
	ArrayList<Integer> counts = new ArrayList<Integer>();
	
	public DataResult(Integer periodInterval, Integer numberOfSensors) {
		this.slotsInADay = (int) (TimeUnit.DAYS.toMillis(1) / periodInterval);
		this.numberOfSensors = numberOfSensors;
	}

	public void addHotels(UnitTimePeriodRecord record, boolean nextDay) {
		if (nextDay)
			dayCount++;
		Double periodSlot = Math.floor(record.getIntervalStartStamp() / record.getInterval()) + dayCount;
		periodSlot = periodSlot + slotsInADay * dayCount;
		for (int i = 0; i < periodSlot - this.hotelSet.size(); i++) {
			this.hotelSet.add(new UnitTimePeriodRecord(this.hotelSet.getLast().getIntervalEndStamp() + 1, record.getInterval()));
		}
		this.hotelSet.add(record);
	}

	public void printResults(Integer periodInterval) {
		StringBuilder strBuilder = new StringBuilder();
		String periodHeadLine = this.buildHeaderLine(12, periodInterval);
		int numOfDays = this.hotelSet.size()/this.slotsInADay;
		for (int day = 0; day < numOfDays; day++) {
			
			strBuilder.append("#### Day # ").append(day+1);
			strBuilder.append("\n");
			strBuilder.append(periodHeadLine);
			strBuilder.append("\n");
			strBuilder.append(this.buildCountsLine(12, periodInterval, day));
			strBuilder.append("\n");
			
		}
		
System.out.println(strBuilder);
System.out.println(sumOfCountOverDays);
	}

	public String buildCountsLine(Integer unitMultiplier, Integer periodInterval, Integer day) {
		StringBuilder strBuilder = new StringBuilder();
		int count = 0;
		strBuilder.append("Count > ");
		for (int i = 0, j=0; i < slotsInADay; i++) {
			UnitTimePeriodRecord unitTimePeriodRecord  = hotelSet.get(i+(day*(this.slotsInADay-1)));			
			count = count + unitTimePeriodRecord.getCountHotels();
			if ((i % unitMultiplier) == 0) {
				strBuilder.append(OUT_FORMAT_DELIMITER).append(count/this.numberOfSensors);
				counts.add(count/this.numberOfSensors);
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
	
	public String buildSpeedLine(Integer unitMultiplier, Integer periodInterval, Integer day) {
		StringBuilder strBuilder = new StringBuilder();
		int count = 0;
		strBuilder.append("Count > ");
		for (int i = 0; i < slotsInADay; i++) {
			UnitTimePeriodRecord unitTimePeriodRecord  = hotelSet.get(i+(day*(this.slotsInADay-1)));
			count = count + unitTimePeriodRecord.getCountHotels();
			if ((i % unitMultiplier) == 0) {
				strBuilder.append(OUT_FORMAT_DELIMITER).append(count/this.numberOfSensors);
				count = 0;
			}
		}
		return strBuilder.toString();
	}

	public String buildHeaderLine(Integer unitMultiplier, Integer periodInterval) {
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

}
