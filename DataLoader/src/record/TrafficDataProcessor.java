package record;

public class TrafficDataProcessor {
	private DataResult result;
	private UnitTimePeriodRecord currentUnitRecord;
	private final Integer periodInterval;

	public TrafficDataProcessor(DataResult result, final Integer periodInterval) {
		this.result = result;
		this.periodInterval = periodInterval;
	}

	public void process(HotelRecord hotelRecord) {		
		if (currentUnitRecord == null) {
			this.currentUnitRecord = new UnitTimePeriodRecord(hotelRecord.getFrontTimeStamp(), this.periodInterval);
		}
		if (hotelRecord.getFrontTimeStamp() > this.currentUnitRecord.getIntervalEndStamp()) {
			this.result.addHotels(currentUnitRecord, false);
			this.currentUnitRecord = new UnitTimePeriodRecord(hotelRecord.getFrontTimeStamp(), this.periodInterval);		
			this.currentUnitRecord.addData(hotelRecord);
		} else if ( hotelRecord.getFrontTimeStamp() > this.currentUnitRecord.getIntervalStartStamp()){
			this.currentUnitRecord.addData(hotelRecord);
		} else if (hotelRecord.getFrontTimeStamp() < this.currentUnitRecord.getIntervalStartStamp()) {
			//Next day.
			this.result.addHotels(currentUnitRecord, true);
			this.currentUnitRecord = new UnitTimePeriodRecord(hotelRecord.getFrontTimeStamp(), this.periodInterval);		
			this.currentUnitRecord.addData(hotelRecord);
		}
	}

}
