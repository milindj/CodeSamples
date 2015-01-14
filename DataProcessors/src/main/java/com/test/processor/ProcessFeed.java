package com.test.processor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.test.processor.Observation;
import com.test.processor.UnitPeriodRecord;
import com.test.processor.Observation.GeoData;
import com.test.processor.Observation.GeoLocation;
import com.test.processor.PersistenceStore;

import ch.hsr.geohash.GeoHash;

/**
 * A runnable task for processing bunch of observations into a more coarse UnitPeriodRecord.
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
		for (Observation observation : this.localObservationsBuffer) {
			//Period& geohash slot search params
			Date normTimeStamp = this.normaliseTimeStamp(observation.getTimeStamp(), Calendar.HOUR);
			String geoHash = this.convertToGeoHash(observation.getLocation(),GEO_PRECISION);			
			
			//Get the unitRecord/ bucket where this observation fits in terms of time and location.
			UnitPeriodRecord unitPeriodRecord = this.persistenceStore.searchRecord(normTimeStamp, geoHash);	
			//Calculate information, add-up for averages and so to have relevant and more coarse data.
			unitPeriodRecord.setSumPrice(observation.getPrice() + unitPeriodRecord.getSumPrice());
			unitPeriodRecord.incrementRecordCount();			
		}
	}

	/**
	 * Normalises an input date to the closest time unit. Ex. for Minutes its
	 * equivalent to: cal.set(Calendar.MILLISECOND, 0); cal.set(Calendar.SECOND,
	 * 0); ..and so on
	 * 
	 * @param date
	 *            Ex. Calendar.HOUR
	 * @param timeUnit
	 * @return
	 */
	private Date normaliseTimeStamp(Date date, int timeUnit) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// Keeping normalising/set=0 upto given timeunit.
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
