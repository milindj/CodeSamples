package com.sample.dataloader;

public class NorthRecord extends SensorRecord {

	public NorthRecord(SensorRecord.Sensor sensor, Long frontInterval, Long backInterval) {
		super(sensor, frontInterval, backInterval);
	}
	
	public NorthRecord(SensorRecord.Sensor sensor, String dataLine1, String dataLine2) {
		super(sensor,new Long(dataLine1.substring(1)), new Long(dataLine2.substring(1)));
	}

	@Override
	public Direction getDirection() {		
		return Direction.NORTH;
	}

}
