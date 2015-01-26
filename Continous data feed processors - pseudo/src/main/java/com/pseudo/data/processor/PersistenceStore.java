package com.pseudo.data.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pseudo.data.processor.UnitPeriodRecord;

/**
 * Dummy persistent store. 
 * @author Milind
 *
 */
public class PersistenceStore {

	List<UnitPeriodRecord> observationRecords = new ArrayList<UnitPeriodRecord>();
	
	public List<UnitPeriodRecord> getObservationRecords() {
		return observationRecords;
	}

	/**
	 * Dummy.
	 * Searches for appropriate unit record which matches the unitType, TimePeriod and GeoHash.
	 * @param normTimeStamp
	 * @param geoHash
	 * @param unitType 
	 * @return
	 */
	public UnitPeriodRecord searchRecord(Date normTimeStamp, String geoHash, String unitType) {
		for (UnitPeriodRecord unitPeriodRecord : observationRecords) {
			if (unitPeriodRecord.getGeoArea().getGeoHash().equals(geoHash)
					&& unitPeriodRecord.getPeriodStart().equals(normTimeStamp)){
				return unitPeriodRecord;
			}
		}
		return null;
	}	
}
