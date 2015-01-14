package com.test.processor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ProcessFeed implements Runnable{

	private ArrayList<Observation> localObservationsBuffer;
		
	public ProcessFeed(ArrayList<Observation> localObservationsBuffer) {
		this.localObservationsBuffer = localObservationsBuffer;
	}

	@Override
	public void run() {
		for (Observation observation : this.localObservationsBuffer) {
			this.normaliseTimeStamp(observation.getTimeStamp());
			observation.getPrice();			
		}
	}
 
	/**
	 * Normalises an input date to the closest time unit.
	 * Ex. for Minutes its equivalent to:
	 * cal.set(Calendar.MILLISECOND, 0);
	 * cal.set(Calendar.SECOND, 0); 
	 * ..and so on
	 * @param date
	 * @return
	 */
	private Date normaliseTimeStamp(Date date){
	 Calendar cal = Calendar.getInstance(); 
	 cal.setTime(date);
	 //Keeping normalising flexible upto minutes or upto seconds.
	 
	
	 
	 for (int i = Calendar.MILLISECOND; i < Calendar.MINUTE; i++) {
		 cal.set(i,0);
	 }
	 
	 
		
		return cal.getTime();
	}
}
