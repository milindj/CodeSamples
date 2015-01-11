package record;

public class NorthRecord extends HotelRecord {

	public NorthRecord(String sensor, Integer frontInterval, Integer backInterval) {
		super(sensor, frontInterval, backInterval);
	}

	@Override
	public Direction getDirection() {		
		return Direction.NORTH;
	}

}
