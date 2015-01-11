package record;

public class SouthRecord extends HotelRecord {

	public SouthRecord(String sensor, Integer frontInterval, Integer backInterval) {
		super(sensor,frontInterval, backInterval);
	}
	
	public SouthRecord(NorthRecord northCarRecord) {
		super(northCarRecord.getSensor(), northCarRecord.getFrontTimeStamp(), northCarRecord.getBackTimeStamp());
	}

	@Override
	public Direction getDirection() {
		return Direction.SOUTH;
	}

}
