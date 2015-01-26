package com.sample.dataloader;

import com.sample.dataprocessor.DataAggregator;
import com.sample.dataprocessor.UnitTimePeriodProcessor;

/**
 * Classifies the data and fits it into the right unit coarse form.
 * @author Milind
 *
 */
public class DataClassifier {
	private DataAggregator aggregator;
	private UnitTimePeriodProcessor currentUnitRecord;
	private final Long periodInterval;
	private final int [] speedDistMatrix;

	/**
	 * Primary constructor.
	 * @param aggregator
	 * @param periodInterval
	 * @param speedDistMatrix
	 */
	public DataClassifier(DataAggregator aggregator, final Long periodInterval, int[] speedDistMatrix) {
		this.aggregator = aggregator;
		this.periodInterval = periodInterval;
		this.speedDistMatrix = speedDistMatrix;
	}

	/**
	 * Takes in a sensorReord, and distributes it into an appropriate unit record.
	 * @param sensorRecord
	 */
	public void groupIntoUnitProcessors(SensorRecord sensorRecord) {		
		if (currentUnitRecord == null) {
			this.currentUnitRecord = new UnitTimePeriodProcessor(sensorRecord.getFrontWheelStamp(), this.periodInterval,this.speedDistMatrix);
			this.aggregator.fitPeriodProcIntoTimeSlot(currentUnitRecord, false);
		}
		if (sensorRecord.getFrontWheelStamp() > this.currentUnitRecord.getIntervalEndStamp()) {
			this.aggregator.fitPeriodProcIntoTimeSlot(currentUnitRecord, false);
			this.currentUnitRecord = new UnitTimePeriodProcessor(sensorRecord.getFrontWheelStamp(), this.periodInterval,this.speedDistMatrix);		
			this.currentUnitRecord.addData(sensorRecord);
		} else if ( sensorRecord.getFrontWheelStamp() > this.currentUnitRecord.getIntervalStartStamp()){
			this.currentUnitRecord.addData(sensorRecord);
		} else if (sensorRecord.getFrontWheelStamp() < this.currentUnitRecord.getIntervalStartStamp()) {
			//Next day record.
			this.aggregator.fitPeriodProcIntoTimeSlot(currentUnitRecord, true);
			this.currentUnitRecord = new UnitTimePeriodProcessor(sensorRecord.getFrontWheelStamp(), this.periodInterval,this.speedDistMatrix);		
			this.currentUnitRecord.addData(sensorRecord);
		}
	}

}
