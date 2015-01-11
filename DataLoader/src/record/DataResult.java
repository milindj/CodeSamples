package record;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class DataResult {

	private TreeMap<Integer ,UnitTimePeriodRecord> hotelSet;
	private Integer dayCount;
	private final Integer slotsInADay;
	private Integer numberOfSensors;


	public DataResult(Integer periodInterval, Integer numberOfSensors) {
		this.hotelSet = new TreeMap<Integer, UnitTimePeriodRecord>();
		this.dayCount = 0;
		this.slotsInADay = (int) (TimeUnit.DAYS.toMillis(1) / periodInterval);
		this.numberOfSensors =numberOfSensors;
		
	}
	
	public void addHotels(UnitTimePeriodRecord record, boolean nextDay){		
		if(nextDay)dayCount++;
		Double periodSlot = Math.floor( record.getIntervalStartStamp() / record.getInterval()) + dayCount;
		periodSlot = periodSlot + slotsInADay*dayCount;
		this.hotelSet.put(periodSlot.intValue(), record);
	}
	
	
}
