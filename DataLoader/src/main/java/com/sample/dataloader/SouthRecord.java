package com.sample.dataloader;

public class SouthRecord extends HotelRecord {

	public SouthRecord(String sensor, Integer frontInterval, Integer backInterval) {
		super(sensor,frontInterval, backInterval);
	}
	
	public SouthRecord(String sensor, String dataLine1, String dataLine2) {
		super(sensor,new Integer(dataLine1.substring(1)), new Integer(dataLine2.substring(1)));
	}
	
	public SouthRecord(NorthRecord northCarRecord) {
		super(northCarRecord.getSensor(), northCarRecord.getFrontTimeStamp(), northCarRecord.getBackTimeStamp());
	}
	
	@Override
	public Direction getDirection() {
		return Direction.SOUTH;
	}

}
