package com.sample.dataloader;

/**
 * Represents a raw sensor record of either A/B type, north/south.
 * @author Milind
 *
 */
public abstract class SensorRecord {

	public static final Integer AVERAGE_CAR_AXLE_LENGTH = 2500; //millimeters
	public enum Direction{NORTH, SOUTH};
	public enum Sensor{A, B};
	private final Sensor sensor;
	private final Long frontWheelStamp;
	private final Long backWheelStamp;
	private Double speed;
	
	public SensorRecord(Sensor sensor, final Long frontTimeStamp,final Long backTimeStamp) {
		this.sensor = sensor;
		this.frontWheelStamp = frontTimeStamp;
		this.backWheelStamp = backTimeStamp;
	}

	public abstract Direction getDirection();
	
	/**
	 * Returns speed in meters/sec. The calculation only happens once, the speed value is directly returned on sequent calls.
	 * @return
	 */
	public Double getSpeed() {
		// Speed in meters / second ie millimeters / millisecond
		this.speed = (this.speed == null) ? AVERAGE_CAR_AXLE_LENGTH / (backWheelStamp - frontWheelStamp) : this.speed;
		return this.speed;
	}
	
	public Sensor getSensor() {
		return sensor;
	}
	
	public Long getFrontWheelStamp() {
		return frontWheelStamp;
	}
	
	public Long getBackWheelStamp() {
		return backWheelStamp;
	}
}
