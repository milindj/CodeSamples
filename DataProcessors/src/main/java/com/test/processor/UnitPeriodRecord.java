package com.test.processor;

import java.util.Date;
import java.util.Set;

import com.test.processor.GeoArea;

/**
 * Unit persistent record object. May represent a row in a db table.
 * Data is summarize into the dimensions of space and a time (geohash & time-period resp.)
 * This is a more coarse form of record, which is suitable to maintain and generate quantitative/analytical data from it.
 * @author Milind
 *
 */
public class UnitPeriodRecord {
	//Date here is the timestamp of period start slot. 
	Date periodStart;
	//Period denotes the span of observations within this time-frame.
	Long periodInMinutes;
	// A less precise geo-area covering multiple locations and defining the space;
	// within which all observations were received from.
	GeoArea geoArea;
	// Set of siteUUids. 
	Set<String> siteUUIDs; 
	
	//summarize data.
	Double sumValues;
	Double maxPosDeviation;
	Double maxNegDeviation;
	//A count helps to calculate average and so on.
	Integer countOfRecords; 
	
	//Getter / setters.
	public void incrementRecordCount() {
		this.countOfRecords++;
	}
	public Set<String> getSiteUUIDs() {
		return siteUUIDs;
	}
	public void addToSiteUUIDs(String siteUUID) {
		this.siteUUIDs.add(siteUUID);
	}
	public Double getSumValues() {
		return sumValues;
	}
	public void setSumValues(Double sumValues) {
		this.sumValues = sumValues;
	}
	public Integer getCountOfRecords() {
		return countOfRecords;
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
	public Double getMaxPosDeviation() {
		return maxPosDeviation;
	}
	public void setMaxPosDeviation(Double maxPosDeviation) {
		this.maxPosDeviation = maxPosDeviation;
	}
	public Double getMaxNegDeviation() {
		return maxNegDeviation;
	}
	public void setMaxNegDeviation(Double maxNegDeviation) {
		this.maxNegDeviation = maxNegDeviation;
	}
	public GeoArea getGeoArea() {
		return geoArea;
	}
	public void setGeoArea(GeoArea geoArea) {
		this.geoArea = geoArea;
	}
		
}
