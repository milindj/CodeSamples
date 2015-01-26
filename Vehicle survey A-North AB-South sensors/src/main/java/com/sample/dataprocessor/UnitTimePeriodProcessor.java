package com.sample.dataprocessor;

import java.util.HashMap;
import java.util.Map;

import com.sample.dataloader.SensorRecord;

/**
 * Captures and processes the data into a more coarse form.
 * @author Milind
 *
 */
public class UnitTimePeriodProcessor {
	
	private double sumSpeed =0;
	private Long countCars = 0l;
	private double sumDistBtwnCars=0;
	private Long intervalStartStamp;
	private Long intervalEndStamp;
	private Long previousCarEndStamp;
	private Long interval;
	private Boolean capacityFull=false;
	private final int [] speedDistMatrix;
	private Map<Integer, Long> speedDistribution;
	
	public UnitTimePeriodProcessor(Long recordStartStamp, Long interval,final int [] speedDistMatrix) {
		this.intervalStartStamp  = (long) (interval * Math.floor(recordStartStamp/interval));
		this.intervalEndStamp = intervalStartStamp + interval;
		this.interval = interval;
		this.speedDistMatrix = speedDistMatrix;
		this.speedDistribution = this.initSpeedDistMap();
	}

	/**
	 * Adds an individual raw record, and processes it into a coarse form.
	 * @param carRecord
	 * @return
	 */
	public boolean addData(SensorRecord carRecord) {
		if(carRecord.getFrontWheelStamp() - intervalStartStamp > interval) {
			// This record is marked full as period interval has exceeded. 
			this.capacityFull = true;
			//Return false to indicate data was not added.
			return false;
		}
		this.addSpeedDistribution(carRecord.getSpeed());
		this.countCars++;
		if (this.previousCarEndStamp != null && this.previousCarEndStamp != 0 ){
			this.sumDistBtwnCars = this.sumDistBtwnCars 
					+ (((carRecord.getFrontWheelStamp() - this.previousCarEndStamp)/1000) * carRecord.getSpeed());
		}
		this.previousCarEndStamp = carRecord.getBackWheelStamp();
		this.sumSpeed = this.sumSpeed + carRecord.getSpeed();
		return true;
	}

	/**
	 * Store car counts according to their respective speeds.
	 * @param speed
	 */
	public void addSpeedDistribution(Double speed){
		for (Integer refSpeed : speedDistMatrix) {
			if(speed<=refSpeed){
				this.speedDistribution.put(refSpeed,  1 + this.speedDistribution.get(refSpeed));
				break;
			} 
		}
	}
	
	/**
	 * Initialize the speed distribution map.
	 * @return
	 */
	private Map<Integer, Long> initSpeedDistMap() {
		Map<Integer, Long> speedMap = new HashMap<Integer, Long>();
		for (int integer : this.speedDistMatrix) {
			speedMap.put(integer, 0l);
		}
		return speedMap;
	}

	/**
	 * @return the sumSpeed
	 */
	public double getSumSpeed() {
		return sumSpeed;
	}

	/**
	 * @param sumSpeed the sumSpeed to set
	 */
	public void setSumSpeed(double sumSpeed) {
		this.sumSpeed = sumSpeed;
	}

	/**
	 * @return the countCars
	 */
	public Long getCountCars() {
		return countCars;
	}

	/**
	 * @return the sumDistBtwnCars
	 */
	public double getSumDistBtwnCars() {
		return sumDistBtwnCars;
	}

	/**
	 * @param sumDistBtwnCars the sumDistBtwnCars to set
	 */
	public void setSumDistBtwnCars(double sumDistBtwnCars) {
		this.sumDistBtwnCars = sumDistBtwnCars;
	}

	/**
	 * @return the intervalStartStamp
	 */
	public Long getIntervalStartStamp() {
		return intervalStartStamp;
	}

	/**
	 * @param intervalStartStamp the intervalStartStamp to set
	 */
	public void setIntervalStartStamp(Long intervalStartStamp) {
		this.intervalStartStamp = intervalStartStamp;
	}

	/**
	 * @return the previousCarEndStamp
	 */
	public Long getPreviousCarEndStamp() {
		return previousCarEndStamp;
	}

	/**
	 * @param previousCarEndStamp the previousCarEndStamp to set
	 */
	public void setPreviousCarEndStamp(Long previousCarEndStamp) {
		this.previousCarEndStamp = previousCarEndStamp;
	}

	/**
	 * @return the interval
	 */
	public Long getInterval() {
		return interval;
	}

	/**
	 * @param interval the interval to set
	 */
	public void setInterval(Long interval) {
		this.interval = interval;
	}

	/**
	 * @return the capacityFull
	 */
	public Boolean getCapacityFull() {
		return capacityFull;
	}

	/**
	 * @param capacityFull the capacityFull to set
	 */
	public void setCapacityFull(Boolean capacityFull) {
		this.capacityFull = capacityFull;
	}

	/**
	 * @return the intervalEndStamp
	 */
	public Long getIntervalEndStamp() {
		return intervalEndStamp;
	}

	/**
	 * @return the speedDistribution
	 */
	public Map<Integer, Long> getSpeedDistribution() {
		return speedDistribution;
	}

	/**
	 * @return the speedDistMatrix
	 */
	public int[] getSpeedDistMatrix() {
		return speedDistMatrix;
	}
}
