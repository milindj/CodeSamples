package com.sample.dataloader;

public class TrafficDataProcessor {
	private DataResult result;
	private UnitTimePeriodRecord currentUnitRecord;
	private final Integer periodInterval;
	private final int [] speedDistMatrix;

	public TrafficDataProcessor(DataResult result, final Integer periodInterval, int[] speedDistMatrix) {
		this.result = result;
		this.periodInterval = periodInterval;
		this.speedDistMatrix = speedDistMatrix;
	}

	public void process(HotelRecord hotelRecord) {		
		if (currentUnitRecord == null) {
			this.currentUnitRecord = new UnitTimePeriodRecord(hotelRecord.getFrontTimeStamp(), this.periodInterval,this.speedDistMatrix);
		}
		if (hotelRecord.getFrontTimeStamp() > this.currentUnitRecord.getIntervalEndStamp()) {
			this.result.addHotelRecordInSlots(currentUnitRecord, false);
			this.currentUnitRecord = new UnitTimePeriodRecord(hotelRecord.getFrontTimeStamp(), this.periodInterval,this.speedDistMatrix);		
			this.currentUnitRecord.addData(hotelRecord);
		} else if ( hotelRecord.getFrontTimeStamp() > this.currentUnitRecord.getIntervalStartStamp()){
			this.currentUnitRecord.addData(hotelRecord);
		} else if (hotelRecord.getFrontTimeStamp() < this.currentUnitRecord.getIntervalStartStamp()) {
			//Next day.
			this.result.addHotelRecordInSlots(currentUnitRecord, true);
			this.currentUnitRecord = new UnitTimePeriodRecord(hotelRecord.getFrontTimeStamp(), this.periodInterval,this.speedDistMatrix);		
			this.currentUnitRecord.addData(hotelRecord);
		}
	}

}
