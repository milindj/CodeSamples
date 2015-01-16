package com.sample.dataloader;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CSVBuilder {
	
	DataAggregator dataAggregator;
	private static final Character CSV_DELIMITER = ',';
	
	public CSVBuilder(DataAggregator dataAggregator) {
		this.dataAggregator = dataAggregator;
	}

	public void printResults(Integer periodInterval) {
		StringBuilder strBuilder = new StringBuilder();
		String periodHeadLine = this.buildCSVHeaderLine(12, periodInterval);
		int numOfDays = this.dataAggregator.getUnitPeriodProcs().size()/this.dataAggregator.getSlotsInADay();
		for (int day = 0; day < numOfDays; day++) {
			strBuilder.append("#### Day # ").append(day+1);
			strBuilder.append("\n");
		///	strBuilder.append(periodHeadLine);
			strBuilder.append("\n");
			strBuilder.append("Counts > ");
			strBuilder.append(this.buildCSVString(this.dataAggregator.aggregateCounts(12, periodInterval, day)));
			strBuilder.append("\n");
			strBuilder.append("AvgSpd > ");
			strBuilder.append(this.buildCSVString(this.dataAggregator.aggregateAvgSpeed(12, periodInterval, day)));
			strBuilder.append("\n");
			
			UnitTimePeriodProcessor peakPeriod = this.dataAggregator.identifyPeakPeriod(12, periodInterval, day);
			
			strBuilder.append(" Peak " + (peakPeriod.getIntervalStartStamp()/(12*periodInterval)));
			strBuilder.append("\n");
			strBuilder.append("SpdDst > ");
			strBuilder.append("\n");
			Map<Integer, List<Double>> speedDist = this.dataAggregator.aggregateRoughSpeedDistribution(12, periodInterval, day);
			for (Entry<Integer, List<Double>> distEntry : speedDist.entrySet() ) {
				strBuilder.append(" " + distEntry.getKey()*DataAggregator.SPEED_KMPH_FACTOR + " > ");
				strBuilder.append(this.buildCSVString(distEntry.getValue()));
				strBuilder.append("\n");
			}
			strBuilder.append("\n");

		}
	
		System.out.println(strBuilder);
		System.out.println(this.dataAggregator.getSumOfCountOverDays());
		
		
	}
	
	public String buildCSVHeaderLine(Integer unitMultiplier, Integer periodInterval) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("TimePeriod > ");
		for (int i = 1, j = 0; i <= this.dataAggregator.getSlotsInADay(); i++) {
			j = j + periodInterval;
			if ((i % unitMultiplier) == 0) {
				strBuilder.append(CSV_DELIMITER).append(j / 60000);
			}			
		}
		return strBuilder.toString();
	}	

	public String buildCSVString(List<?> valueList) {
		StringBuilder strBuilder = new StringBuilder();
		for (Object value : valueList) {
			strBuilder.append(CSV_DELIMITER).append(value);
		}
		return strBuilder.toString();
	}
	
}
