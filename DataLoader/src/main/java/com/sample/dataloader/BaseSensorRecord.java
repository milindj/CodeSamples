package com.sample.dataloader;

public abstract class BaseSensorRecord {

	public static final Integer AVERAGE_CAR_AXLE_LENGTH = 2500; //millimeters
	public enum Direction{NORTH, SOUTH};
	public enum Sensor{A, B};
	private final Sensor sensor;
	private final Integer frontWheelStamp;
	private final Integer backWheelStamp;
	private Double speed;
	
	public BaseSensorRecord(Sensor sensor, final Integer frontTimeStamp,final Integer backTimeStamp) {
		this.sensor = sensor;
		this.frontWheelStamp = frontTimeStamp;
		this.backWheelStamp = backTimeStamp;
	}

	public abstract Direction getDirection();
	
	public Double getSpeed() {
		// Speed in meters / second = millimeters / millisecond
		this.speed = (this.speed == null) ? AVERAGE_CAR_AXLE_LENGTH / (backWheelStamp - frontWheelStamp) : this.speed;
		return this.speed;
	}
	
	public Sensor getSensor() {
		return sensor;
	}
	
	public Integer getFrontWheelStamp() {
		return frontWheelStamp;
	}
	
	public Integer getBackWheelStamp() {
		return backWheelStamp;
	}
}
