package com.pseudo.data.processor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.pseudo.data.processor.Observation;
import com.pseudo.data.processor.PersistenceStore;
import com.pseudo.data.processor.UnitPeriodRecord;
import com.pseudo.data.processor.Observation.GeoLocation;

import ch.hsr.geohash.GeoHash;

/**
 * A runnable task for processing a bunch of observations into a more coarse UnitPeriodRecord.
 * @author Milind
 *
 */
public class ProcessFeed implements Runnable {

	private ArrayList<Observation> localObservationsBuffer;
	private PersistenceStore persistenceStore;
	public final static Integer GEO_PRECISION = 4;

	public ProcessFeed(ArrayList<Observation> localObservationsBuffer, PersistenceStore persistenceStore) {
		this.localObservationsBuffer = localObservationsBuffer;
		this.persistenceStore = persistenceStore;
	}

	@Override
	public void run() {
		//Process all the observations one by one.
		for (Observation observation : this.localObservationsBuffer) {
			
			//Identify the space&time a.k.a period & geohash slot.
			Date normTimeStamp = this.normaliseTimeStamp(observation.getObservationTime(), Calendar.HOUR);
			String geoHash = this.convertToGeoHash(observation.getLocation(),GEO_PRECISION);			
			
			//Get the unitRecord/ bucket where this observation fits in terms of time and location.
			UnitPeriodRecord unitPeriodRecord = this.persistenceStore.searchRecord(normTimeStamp, geoHash, observation.getObservedUnits());	
			
			//Calculate information, sum-up to calculate later averages and thus have relevant and more coarse data.
			unitPeriodRecord.setSumValues(observation.getObservedValue() + unitPeriodRecord.getSumValues());
			unitPeriodRecord.incrementRecordCount();			
		}
	}

	/**
	 * Normalises an input date to the closest time unit. Ex. for Minutes its
	 * equivalent to: 
	 * cal.set(Calendar.MILLISECOND, 0); 
	 * cal.set(Calendar.SECOND, 0); 
	 * ..and so on
	 * 
	 * @param date
	 * @param timeUnit Ex. Calendar.HOUR
	 * @return
	 */
	private Date normaliseTimeStamp(Date date, int timeUnit) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// Keeping normalize ie set it to 0 upto given timeunit.
		for (int i = Calendar.MILLISECOND; i < timeUnit; i++) {
			cal.set(i, 0);
		}
		return cal.getTime();
	}	
	
	private String convertToGeoHash(GeoLocation geoData, Integer precision){
		// Any 3rd party API to convert to geohash.
		GeoHash hash = GeoHash.withCharacterPrecision(geoData.getLatitude(), geoData.getLongitude(), precision);
		return hash.toBase32();
	}
}
