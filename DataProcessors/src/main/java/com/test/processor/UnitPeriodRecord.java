package com.test.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import com.test.processor.GeoArea;

/**
 * Unit persistent record object. May represent a row in a db table.
 * This is a more coarse form of record, which is suitable to maintain and generate quantitative/analytical data from it.
 * Data is summarize into the dimension of geohash(location-area) and a time period.
 * @author Milind
 *
 */
public class UnitPeriodRecord {
	//Date here is the timestamp of period start slot. 
	Date periodStart;
	//Period governs the group of observations within this time-frame.
	Long periodInMinutes;
	// A less precise geo-area covering multiple locations
	GeoArea geoArea;
	
	Set<String> siteUUIDs; // Set of siteUUids. 
	
	//summarize data.
	Integer sumPrice;
	Integer maxPosDeviation;
	Integer maxNegDeviation;
	//A count helps to calculate average and so on.
	Integer countOfRecords; 
	
	public void incrementRecordCount() {
		this.countOfRecords++;
	}
	
	public Date getPeriodStart() {
		return periodStart;
	}
	public void setPeriodStart(Date periodStart) {
		this.periodStart = periodStart;
	}
	public Long getPeriodInMinutes() {
		return periodInMinutes;
	}
	public void setPeriodInMinutes(Long periodInMinutes) {
		this.periodInMinutes = periodInMinutes;
	}
	public Integer getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(Integer sumPrice) {
		this.sumPrice = sumPrice;
	}
	public Integer getMaxPosDeviation() {
		return maxPosDeviation;
	}
	public void setMaxPosDeviation(Integer maxPosDeviation) {
		this.maxPosDeviation = maxPosDeviation;
	}
	public Integer getMaxNegDeviation() {
		return maxNegDeviation;
	}
	public void setMaxNegDeviation(Integer maxNegDeviation) {
		this.maxNegDeviation = maxNegDeviation;
	}
	public GeoArea getGeoArea() {
		return geoArea;
	}
	public void setGeoArea(GeoArea geoArea) {
		this.geoArea = geoArea;
	}
		
}
