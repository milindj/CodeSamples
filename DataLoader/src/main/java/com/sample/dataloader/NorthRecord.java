package com.sample.dataloader;

public class NorthRecord extends HotelRecord {

	public NorthRecord(String sensor, Integer frontInterval, Integer backInterval) {
		super(sensor, frontInterval, backInterval);
	}
	
	public NorthRecord(String sensor, String dataLine1, String dataLine2) {
		super(sensor,new Integer(dataLine1.substring(1)), new Integer(dataLine2.substring(1)));
	}

	@Override
	public Direction getDirection() {		
		return Direction.NORTH;
	}

}
