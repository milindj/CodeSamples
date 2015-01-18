package com.sample.util;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sample.dataprocessor.DataAggregator;
import com.sample.dataprocessor.UnitTimePeriodProcessor;

/**
 * Utility class to fetch the results in form of a CSV.
 * @author Milind
 *
 */
public class CSVBuilder {
	
	DataAggregator dataAggregator;
	private static final Character CSV_DELIMITER = ',';
	DecimalFormat decimalFormat = new DecimalFormat();
	
	public CSVBuilder(DataAggregator dataAggregator) {
		this.dataAggregator = dataAggregator;
		this.decimalFormat.setMaximumFractionDigits(2);
	}

	/**
	 *  Generate results in form of a CSV.
	 * @param numOfSlots
	 * @param slotPeriod
	 * @return
	 */
	public String getResultAsCSVString(Integer numOfSlots, Long slotPeriod) {
		StringBuilder strBuilder = new StringBuilder();
		String periodHeadLine = this.buildCSVMinsHeaderLine(numOfSlots, slotPeriod);
		int numOfDays = this.dataAggregator.getUnitPeriodProcs().size()/this.dataAggregator.getSlotsInADay();
		for (int day = 0; day < numOfDays; day++) {
			strBuilder.append("#### Day # ").append(day+1);
			strBuilder.append("\n");
			strBuilder.append(periodHeadLine);
			strBuilder.append("\n");
			strBuilder.append("Count of Cars     > ");
			strBuilder.append(this.buildCSVString(this.dataAggregator.aggregateCounts(numOfSlots, day)));
			strBuilder.append("\n");
			strBuilder.append("Average Speed     > ");
			strBuilder.append(this.buildCSVString(this.dataAggregator.aggregateAvgSpeed(numOfSlots, day)));
			strBuilder.append("\n");			
			UnitTimePeriodProcessor peakPeriod = this.dataAggregator.identifyPeakPeriod(numOfSlots, day);			
			strBuilder.append("Peak Time Period: " + (peakPeriod.getIntervalStartStamp()/(numOfSlots*slotPeriod)));
			strBuilder.append("\n");
			strBuilder.append("Speed Distribution > ");
			strBuilder.append("\n");
			Map<Integer, List<Double>> speedDist = this.dataAggregator.aggregateRoughSpeedDistribution(numOfSlots, day);
			for (Entry<Integer, List<Double>> distEntry : speedDist.entrySet() ) {
				strBuilder.append(" {" + distEntry.getKey()*DataAggregator.SPEED_KMPH_FACTOR + " } > ");
				strBuilder.append(this.buildCSVString(distEntry.getValue()));
				strBuilder.append("\n");
			}			
			strBuilder.append("Avg Dist Btwn Cars > ");
			strBuilder.append(this.buildCSVString(this.dataAggregator.aggregateDistanceBetweenCars(numOfSlots, day)));
			strBuilder.append("\n");			
		}
		strBuilder.append("Avg count over all > ");
		strBuilder.append(this.buildCSVString(this.dataAggregator.aggregateAvgCountOverAllDays(numOfSlots)));
		strBuilder.append("\n");	
		return strBuilder.toString();
	}
	
	/**
	 * Builds the header minute slots according to the period.
	 * @param unitMultiplier
	 * @param periodInterval
	 * @return
	 */
	public String buildCSVMinsHeaderLine(Integer unitMultiplier, Long periodInterval) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("TimePeriod > ");
		long j = 0l;
		for (int i = 1; i <= this.dataAggregator.getSlotsInADay(); i++) {
			j = j + periodInterval;
			if ((i % unitMultiplier) == 0) {
				strBuilder.append(CSV_DELIMITER).append(j / 60000);
			}			
		}
		return strBuilder.toString();
	}	

	/**
	 * Converts the list into a delimited csv.
	 * @param valueList
	 * @return
	 */
	public String buildCSVString(List<?> valueList) {
		StringBuilder strBuilder = new StringBuilder();
		for (Object value : valueList) {
			strBuilder.append(CSV_DELIMITER).append(decimalFormat.format(value));
		}
		return strBuilder.toString();
	}
	
}
