package com.sample.dataloader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import static com.sample.dataloader.BaseSensorRecord.Sensor.*;

public class DataLoader {

	public static final int[] DEFAULT_SPEED_MATRIX_SCALE_MPS = { 0, 5, 10, 15, 20, 25, 30, 35, 40 };
	private static final Charset FILE_CHARSET = StandardCharsets.UTF_8;
	private static final Integer DEFAULT_INTERVAL = 5 * 60000;
	private static final Integer NUM_NORTH_SENSORS = 1;
	private static final Integer NUM_SOUTH_SENSORS = 2;
	private LinkedList<String> inputBuffer = new LinkedList<String>();
	private ArrayList<NorthRecord> northBoundRecords = new ArrayList<NorthRecord>();
	private ArrayList<SouthRecord> southBoundRecords = new ArrayList<SouthRecord>();
	private TrafficDataManager northDataProcessor, southDataProcessor;
	private DataAggregator northDataAggr, southDataAggr;

	public DataLoader() {
		this(DEFAULT_INTERVAL, DEFAULT_SPEED_MATRIX_SCALE_MPS);
	}

	public DataLoader(Integer interval, int[] speedDistMatrixMPS) {
		this.northDataAggr = new DataAggregator(interval, NUM_NORTH_SENSORS);
		this.northDataProcessor = new TrafficDataManager(this.northDataAggr, interval, speedDistMatrixMPS);
		this.southDataAggr = new DataAggregator(interval, NUM_SOUTH_SENSORS);
		this.southDataProcessor = new TrafficDataManager(this.southDataAggr, interval, speedDistMatrixMPS);
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		if (args.length <= 0 || args[0] == null) {
			throw new RuntimeException("Please supply the data file as arguments say ./data.txt ");
		}
		DataLoader dl = new DataLoader();
		dl.loadAndSortRecords(new FileInputStream(args[0]), FILE_CHARSET);
		new CSVBuilder(dl.northDataAggr).printResults(DEFAULT_INTERVAL);
		new CSVBuilder(dl.northDataAggr).printResults(DEFAULT_INTERVAL);
	}

	public void loadAndSortRecords(InputStream inputStream, Charset charSet) throws IOException {
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
							BaseSensorRecord orphanRecord = new SouthRecord(A, dataLine1, orphanA);
							BaseSensorRecord trialRecord = new SouthRecord(A, dataLine1, trialLine);
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
							BaseSensorRecord prevRecord = new SouthRecord(A, dataLine1, prevOrphan);
							BaseSensorRecord orphanRecord = new SouthRecord(A, dataLine1, orphanA);
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
							BaseSensorRecord prevRecord = new SouthRecord(A, dataLine2, prevOrphan);
							BaseSensorRecord orphanRecord = new SouthRecord(A, dataLine2, orphanA);
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
				// Time to flush our sorted buffers to process each record further.
				this.flushRecordBuffersToProcess(this.northBoundRecords, this.northDataProcessor);
				this.flushRecordBuffersToProcess(this.southBoundRecords, this.southDataProcessor);
			}
		}
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
	private void flushRecordBuffersToProcess(ArrayList<? extends BaseSensorRecord> records, TrafficDataManager dataProcessor) {
		for (BaseSensorRecord hotelRecord : records) {
			dataProcessor.groupIntoUnitProcessors(hotelRecord);
		}
		records.clear();
	}
}
