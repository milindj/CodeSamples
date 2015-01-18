package com.sample.dataloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.sample.dataloader.SensorRecord.Direction;
import com.sample.dataprocessor.DataAggregator;

import static com.sample.dataloader.SensorRecord.Sensor.*;
/**
 * Implementation of a solution to measure data from 1 north and two south way sensors.
 * Loads and sorts the data into a more coarse form. 
 * @author Milind
 *
 */
public class CarDataLoader1N2S implements SensorDataLoader{
	//This loader is designed for 1 north and two south way sensors.
	private static final Integer NUM_NORTH_SENSORS = 1;
	private static final Integer NUM_SOUTH_SENSORS = 2;
	private LinkedList<String> inputBuffer = new LinkedList<String>();
	private ArrayList<NorthRecord> northBoundRecords = new ArrayList<NorthRecord>();
	private ArrayList<SouthRecord> southBoundRecords = new ArrayList<SouthRecord>();
	private TrafficDataManager northTrafficManager, southTrafficManager;
	private DataAggregator northDataAggr, southDataAggr;

	/**
	 * Primary constructor.
	 * @param unitPeriod the period of unit for for collecting and formulating coarse data.
	 * @param speedDistMatrixMPS distribution reference matrix in meters per second.
	 */
	public CarDataLoader1N2S(Long unitPeriod, int[] speedDistMatrixMPS) {
		this.northDataAggr = new DataAggregator(unitPeriod, NUM_NORTH_SENSORS, speedDistMatrixMPS, Direction.NORTH);
		this.northTrafficManager = new TrafficDataManager(this.northDataAggr, unitPeriod, speedDistMatrixMPS);
		this.southDataAggr = new DataAggregator(unitPeriod, NUM_SOUTH_SENSORS, speedDistMatrixMPS, Direction.SOUTH);
		this.southTrafficManager = new TrafficDataManager(this.southDataAggr, unitPeriod, speedDistMatrixMPS);
	}
	
	/**
	 * Loads and sort the data into appropriate sensors. 
	 * @param inputStream input data stream.
	 * @param charSet charset of the input data.
	 * @return DataAggregator,which contains coarse data, in a ready to be calculated form.
	 * @throws IOException
	 */
	@Override
	public List<DataAggregator> loadRecords(InputStream inputStream, Charset charSet) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charSet))) {
			LinkedList<String> orphanAs = new LinkedList<String>();
			LinkedList<String> unprocessedAs = new LinkedList<String>();
			while (true) {
				while (unprocessedAs.size() > 0) {
					inputBuffer.addFirst(unprocessedAs.removeLast());
				}
				while (inputBuffer.size() < 40) {
					String bufferedLine = br.readLine();
					if (bufferedLine != null) {
						inputBuffer.addLast(bufferedLine);
					} else {
						break;
					}
				}
				// Load first three lines.
				String dataLine1 = inputBuffer.pollFirst();
				String dataLine2 = inputBuffer.pollFirst();
				String dataLine3 = inputBuffer.pollFirst();
				// If its null, then its the end of file.
				if (dataLine1 == null || dataLine2 == null) {
					break;
				}
				// Based on the three line, check which kind of record it is south/north.
				String newLine;
				if (isA(dataLine1) && isA(dataLine2) && !isB(dataLine3)) {
					// ideal A north record case 'AA(A|null)'.
					this.northBoundRecords.add(new NorthRecord(A, dataLine1, dataLine2));
					if (dataLine3 == null) {
						break;
					}
					unprocessedAs.add(dataLine3);
				} else if (isA(dataLine1) && isB(dataLine2) && isA(dataLine3)) {
					// south record case with 'AB(AA..)AB'
					while ((newLine = inputBuffer.pollFirst()) != null && isA(newLine)) {
						orphanAs.add(newLine);
					}
					// newLine is B because, it cannot be null at this stage is obviously not 'A'
					String lineB = newLine;
					// Handle the intermediate co-incident (AA..) records.
					if (orphanAs.size() > 0) {
						// Choose the combo from southward A amongst orphans and dataLine3,
						// compare the above to have the best speed match with sensor B.
						String orphanA;
						SouthRecord southRefRecord = new SouthRecord(B, dataLine2, lineB);
						Double refSouthSpeed = southRefRecord.getSpeed();
						String trialLine = dataLine3;
						// Iterate to find closest speed match with corresponding B sensors.
						while (orphanAs.size() > 0) {
							orphanA = orphanAs.removeLast();
							SensorRecord orphanRecord = new SouthRecord(A, dataLine1, orphanA);
							SensorRecord trialRecord = new SouthRecord(A, dataLine1, trialLine);
							if (Math.abs(refSouthSpeed - trialRecord.getSpeed()) > Math.abs(refSouthSpeed - orphanRecord.getSpeed())) {
								unprocessedAs.add(trialLine);
								trialLine = orphanA;
							} else {
								unprocessedAs.add(orphanA);
							}
						}
						this.southBoundRecords.add(southRefRecord);
						this.southBoundRecords.add(new SouthRecord(A, dataLine1, trialLine));
					} else {
						// ideal 'ABAB' scenario, hence simply create two south records.
						this.southBoundRecords.add(new SouthRecord(A, dataLine1, dataLine3));
						this.southBoundRecords.add(new SouthRecord(B, dataLine2, lineB));
					}
				} else if (isA(dataLine1) && isA(dataLine2) && isB(dataLine3)) {
					// scenario where we have a trailing A before south record like (A)AB(A)AB
					while ((newLine = inputBuffer.pollFirst()) != null && isA(newLine)) {
						orphanAs.add(newLine);
					}
					String lineB = newLine;
					if (orphanAs.size() > 0) {
						SouthRecord southRefRecord = new SouthRecord(B, dataLine3, lineB);
						Double refSouthSpeed = southRefRecord.getSpeed();
						String prevOrphan = orphanAs.getFirst();
						// Iterate to find closest speed match with corresponding B sensors.
						for (int i = 1; i < orphanAs.size(); i++) {
							String orphanA = orphanAs.get(i);
							SensorRecord prevRecord = new SouthRecord(A, dataLine1, prevOrphan);
							SensorRecord orphanRecord = new SouthRecord(A, dataLine1, orphanA);
							if (Math.abs(refSouthSpeed - prevRecord.getSpeed()) > Math.abs(refSouthSpeed - orphanRecord.getSpeed())) {
								prevOrphan = orphanA;
							}
						}
						// Now prevOrphan is the best match of line1
						String line1OrphanMatch = prevOrphan;

						// Checking how good speed combination match with line2
						prevOrphan = orphanAs.getFirst();
						for (int i = 1; i < orphanAs.size(); i++) {
							String orphanA = orphanAs.get(i);
							SensorRecord prevRecord = new SouthRecord(A, dataLine2, prevOrphan);
							SensorRecord orphanRecord = new SouthRecord(A, dataLine2, orphanA);
							if (Math.abs(refSouthSpeed - prevRecord.getSpeed()) > Math.abs(refSouthSpeed - orphanRecord.getSpeed())) {
								prevOrphan = orphanA;
							}
						}
						// Now prevOrphan is the best match of line2
						String line2OrphanMatch = prevOrphan;
						// Compare the best match between dataline1 and dataline2.
						SouthRecord record1 = new SouthRecord(A, dataLine2, line1OrphanMatch);
						SouthRecord record2 = new SouthRecord(A, dataLine2, line2OrphanMatch);
						// Record the best match as the corresponding A south record wrt sensor B.
						if (Math.abs(refSouthSpeed - record1.getSpeed()) > Math.abs(refSouthSpeed - record2.getSpeed())) {
							this.southBoundRecords.add(record2);
						} else {
							this.southBoundRecords.add(record1);
						}
						this.southBoundRecords.add(southRefRecord);
						//Collect all unprocessed orphans, they will be queued again in the buffer.
						while (orphanAs.size() > 0) {
							unprocessedAs.addFirst(orphanAs.removeLast());
						}
					} else {
						throw new RuntimeException("Unexpected sequence of sensor records d1:d2:d3 = " + dataLine1 + ":" + dataLine2 + ":" + dataLine3);
					}
				}
				// Regularly flush out sorted buffers to process each record further.
				this.flushRecordBuffersToProcess(this.northBoundRecords, this.northTrafficManager);
				this.flushRecordBuffersToProcess(this.southBoundRecords, this.southTrafficManager);
			}
		}
		//Flush it out finally.
		this.flushRecordBuffersToProcess(this.northBoundRecords, this.northTrafficManager);
		this.flushRecordBuffersToProcess(this.southBoundRecords, this.southTrafficManager);
		
		List<DataAggregator> dataAggregators = new ArrayList<DataAggregator>();
		dataAggregators.add(this.northDataAggr);
		dataAggregators.add(this.southDataAggr);
		return dataAggregators;
	}

	/**
	 * Convenience method to check if the line record belongs to sensor B
	 * 
	 * @param dataB
	 * @return
	 */
	private boolean isB(String dataB) {
		return dataB != null && dataB.charAt(0) == B.toString().charAt(0); 
	}

	/**
	 * Convenience method to check if the line record belongs to sensor A
	 * 
	 * @param dataA
	 * @return
	 */
	private boolean isA(String dataA) {
		return dataA != null && dataA.charAt(0) == A.toString().charAt(0);
	}

	/**
	 * Flushes our sorted buffers, and pushes these records to be further
	 * processed.
	 * 
	 * @param records
	 * @param dataProcessor
	 */
	private void flushRecordBuffersToProcess(ArrayList<? extends SensorRecord> records, TrafficDataManager dataProcessor) {
		for (SensorRecord carRecord : records) {
			dataProcessor.groupIntoUnitProcessors(carRecord);
		}
		records.clear();
	}
}
