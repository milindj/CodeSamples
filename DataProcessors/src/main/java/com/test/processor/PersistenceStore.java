package com.test.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.test.processor.UnitPeriodRecord;

/**
 * Dummy persistant store. 
 * @author Milind
 *
 */
public class PersistenceStore {

	List<UnitPeriodRecord> observationRecords = new ArrayList<UnitPeriodRecord>();
	
	public List<UnitPeriodRecord> getObservationRecords() {
		return observationRecords;
	}

	/**
	 * Searches for appropriate unit record of TimePeriod and GeoHash.
	 * @param normTimeStamp
	 * @param geoHash
	 * @return
	 */
	public UnitPeriodRecord searchRecord(Date normTimeStamp, String geoHash) {
		for (UnitPeriodRecord unitPeriodRecord : observationRecords) {
			if (unitPeriodRecord.getGeoArea().getGeoHash().equals(geoHash)
					&& unitPeriodRecord.getPeriodStart().equals(normTimeStamp)){
				return unitPeriodRecord;
			}
		}
	}
	
}
