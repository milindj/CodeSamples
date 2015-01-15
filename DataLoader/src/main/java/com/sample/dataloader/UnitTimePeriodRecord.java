package com.sample.dataloader;

import java.util.HashMap;
import java.util.Map;

public class UnitTimePeriodRecord {

	public enum PeakPeriodType{
		PEAK, NOT_PEAK, UNDETERMINED
	}
	
	
	//avg price
// count of hotels	
// peak boolean 
// distance btwn hotels  
	// period time start
	// period time end
	private double sumPrice =0;
	private Integer countHotels = 0;
	private PeakPeriodType peakPeriod; 
	private double sumDistBtwnHotels=0;
	private Integer intervalStartStamp;
	private Integer intervalEndStamp;
	private Integer previousCarEndStamp;
	private Integer interval;
	private Boolean capacityFull=false;
	private final int [] speedDistMatrix;
	
	public Map<Integer, Integer> getSpeedDistribution() {
		return speedDistribution;
	}
	public int[] getSpeedDistMatrix() {
		return speedDistMatrix;
	}

	private Map<Integer, Integer> speedDistribution;
	
	public Integer getIntervalEndStamp() {
		return intervalEndStamp;
	}
	public double getSumPrice() {
		return sumPrice;
	}

	public Integer getCountHotels() {
		return countHotels;
	}

	public PeakPeriodType getPeakPeriod() {
		return peakPeriod;
	}

	public double getSumDistBtwnHotels() {
		return sumDistBtwnHotels;
	}

	public Integer getIntervalStartStamp() {
		return intervalStartStamp;
	}

	public Integer getPreviousCarEndStamp() {
		return previousCarEndStamp;
	}

	public Integer getInterval() {
		return interval;
	}

	public Boolean getCapacityFull() {
		return capacityFull;
	}

	public void setCapacityFull(Boolean capacityFull) {
		this.capacityFull = capacityFull;
	}
	
	public UnitTimePeriodRecord(Integer recordStartStamp, Integer interval,final int [] speedDistMatrix) {
		this.intervalStartStamp  = (int) (interval * Math.floor(recordStartStamp/interval));
		this.intervalEndStamp = intervalStartStamp + interval;
		this.interval = interval;
		this.speedDistMatrix = speedDistMatrix;
		this.speedDistribution = this.initSpeedDistMap();
	}

	public boolean addData(HotelRecord hotelRecord) {
		if(hotelRecord.getFrontTimeStamp() - intervalStartStamp > interval) {
			// This record is marked full as period interval has exceeded. 
			this.capacityFull = true;
			//Return false to indicate data was not added.
			return false;
		}
		int front = hotelRecord.getFrontTimeStamp();
		int back = hotelRecord.getBackTimeStamp();
		double speed = hotelRecord.getSpeed();
		this.addSpeedDistribution(hotelRecord.getSpeed());
		this.countHotels++;
		if (this.previousCarEndStamp != null && this.previousCarEndStamp != 0 ){
			this.sumDistBtwnHotels = this.sumDistBtwnHotels + (hotelRecord.getFrontTimeStamp() - this.previousCarEndStamp) * hotelRecord.getSpeed();
		}
		this.previousCarEndStamp = hotelRecord.getBackTimeStamp();
		this.sumPrice = this.sumPrice + hotelRecord.getSpeed();
		return true;
	}

	public void addSpeedDistribution(Double speed){
		for (Integer refSpeed : speedDistMatrix) {
			if(speed<=refSpeed){
				this.speedDistribution.put(refSpeed,  1 + this.speedDistribution.get(refSpeed));
				break;
			} 
		}
	}
	
	private Map<Integer, Integer> initSpeedDistMap() {
		Map<Integer, Integer> speedMap = new HashMap<Integer, Integer>();
		for (int integer : speedDistMatrix) {
			speedMap.put(integer, 0);
		}
		return speedMap;
	}
	
	
}
