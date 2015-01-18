package com.sample.dataloader;

public class SouthRecord extends SensorRecord {

	public SouthRecord(Sensor sensor, Long frontInterval, Long backInterval) {
		super(sensor,frontInterval, backInterval);
	}
	
	public SouthRecord(Sensor sensor, String dataLine1, String dataLine2) {
		super(sensor,new Long(dataLine1.substring(1)), new Long(dataLine2.substring(1)));
	}
	
	public SouthRecord(NorthRecord northCarRecord) {
		super(northCarRecord.getSensor(), northCarRecord.getFrontWheelStamp(), northCarRecord.getBackWheelStamp());
	}
	
	@Override
	public Direction getDirection() {
		return Direction.SOUTH;
	}

}
