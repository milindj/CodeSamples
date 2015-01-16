package com.sample.dataloader;

public class TrafficDataManager {
	private DataAggregator result;
	private UnitTimePeriodProcessor currentUnitRecord;
	private final Integer periodInterval;
	private final int [] speedDistMatrix;

	public TrafficDataManager(DataAggregator result, final Integer periodInterval, int[] speedDistMatrix) {
		this.result = result;
		this.periodInterval = periodInterval;
		this.speedDistMatrix = speedDistMatrix;
	}

	public void groupIntoUnitProcessors(BaseSensorRecord sensorRecord) {		
		if (currentUnitRecord == null) {
			this.currentUnitRecord = new UnitTimePeriodProcessor(sensorRecord.getFrontWheelStamp(), this.periodInterval,this.speedDistMatrix);
		}
		if (sensorRecord.getFrontWheelStamp() > this.currentUnitRecord.getIntervalEndStamp()) {
			this.result.fitPeriodProcIntoTimeSlot(currentUnitRecord, false);
			this.currentUnitRecord = new UnitTimePeriodProcessor(sensorRecord.getFrontWheelStamp(), this.periodInterval,this.speedDistMatrix);		
			this.currentUnitRecord.addData(sensorRecord);
		} else if ( sensorRecord.getFrontWheelStamp() > this.currentUnitRecord.getIntervalStartStamp()){
			this.currentUnitRecord.addData(sensorRecord);
		} else if (sensorRecord.getFrontWheelStamp() < this.currentUnitRecord.getIntervalStartStamp()) {
			//Next day record.
			this.result.fitPeriodProcIntoTimeSlot(currentUnitRecord, true);
			this.currentUnitRecord = new UnitTimePeriodProcessor(sensorRecord.getFrontWheelStamp(), this.periodInterval,this.speedDistMatrix);		
			this.currentUnitRecord.addData(sensorRecord);
		}
	}

}
