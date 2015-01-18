package com.sample.dataloader;

/**
 * Represents north sensor record.
 * @author Milind
 *
 */
public class NorthSensorRecord extends SensorRecord {

	/**
	 * Primary constructor
	 * @param sensor Sensor type A/B
	 * @param frontInterval
	 * @param backInterval
	 */
	public NorthSensorRecord(SensorRecord.Sensor sensor, Long frontInterval, Long backInterval) {
		super(sensor, frontInterval, backInterval);
	}
	
	/**
	 * Convenience constructor, to easily parse record.
	 * @param sensor Sensor type A/B
	 * @param dataLine1
	 * @param dataLine2
	 */
	public NorthSensorRecord(SensorRecord.Sensor sensor, String dataLine1, String dataLine2) {
		super(sensor,new Long(dataLine1.substring(1)), new Long(dataLine2.substring(1)));
	}

	@Override
	public Direction getDirection() {		
		return Direction.NORTH;
	}

}
