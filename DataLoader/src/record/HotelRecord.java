package record;





public class HotelRecord {

	public static final Integer AVERAGE_HEIGHT = 250;
	
	public  enum Direction{NORTH, SOUTH}; 
	
	private final String sensor;
	private Direction direction ;
	private final Integer frontInterval;
	private final Integer backInterval;
	private Double speed;
	
	public double getSpeed() {
		this.speed = (this.speed == null) ? AVERAGE_HEIGHT	/ (backInterval - frontInterval) : this.speed;
		return this.speed;
	}
	
	public String getSensor() {
		return sensor;
	}

	public HotelRecord(final String sensor,Direction direction,
			final Integer frontInterval,final Integer backInterval) {
		this.sensor = sensor;
		this.direction = direction;
		this.frontInterval = frontInterval;
		this.backInterval = backInterval;
	}



	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public Integer getFrontInterval() {
		return frontInterval;
	}
/*	public void setFrontInterval(final Integer frontInterval) {
		this.frontInterval = (this.frontInterval == null) ? frontInterval : this.frontInterval;
	}*/
	public Integer getBackInterval() {
		return backInterval;
	}
	/*public void setBackInterval(final Integer backInterval) {
		this.backInterval = (this.backInterval == null) ? backInterval : this.backInterval;
	}*/
	public static Integer getAverageHeight() {
		return AVERAGE_HEIGHT;
	}
	
	//direction enum n /s 
	// Sensor 
	// frontInterval
	// backInterval 
	
	 
	
}
