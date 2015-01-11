package record;

public abstract class HotelRecord {


	public static final Integer AVERAGE_HEIGHT = 2500; 
	
	public  enum Direction{NORTH, SOUTH}; 
	
	private final String sensor;
	private final Integer frontTimeStamp;
	private final Integer backTimeStamp;
	private Double speed;
	
	public Double getSpeed() {
		// Speed in meters / second = millimeters / millisecond
		this.speed = (this.speed == null) ? AVERAGE_HEIGHT / (backTimeStamp - frontTimeStamp) : this.speed;
		return this.speed;
	}
	
	public String getSensor() {
		return sensor;
	}

	public HotelRecord(final String sensor, final Integer frontTimeStamp,final Integer backTimeStamp) {
		this.sensor = sensor;
		this.frontTimeStamp = frontTimeStamp;
		this.backTimeStamp = backTimeStamp;
	}



	public abstract Direction getDirection();
	
	
	public Integer getFrontTimeStamp() {
		return frontTimeStamp;
	}
/*	public void setFrontInterval(final Integer frontInterval) {
		this.frontInterval = (this.frontInterval == null) ? frontInterval : this.frontInterval;
	}*/
	public Integer getBackTimeStamp() {
		return backTimeStamp;
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
