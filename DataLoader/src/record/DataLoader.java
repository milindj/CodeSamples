package record;

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

import record.HotelRecord.Direction;

public class DataLoader {

	private LinkedList<NorthRecord> northBoundRecords;
	private LinkedList<SouthRecord> southBoundRecords;
	private static final Charset FILE_CHARSET = StandardCharsets.UTF_8;
	private static final Integer DEFAULT_INTERVAL = 5 * 60000;
	private LinkedList<String> sensorA = new LinkedList<String>();
	private LinkedList<String> sensorB = new LinkedList<String>();
	private LinkedList<String> inputBuffer = new LinkedList<String>();

	private TrafficDataProcessor northDataProcessor, southDataProcessor;
	private DataResult northDataResult, southDataResult;

	public DataLoader() {
		this.northBoundRecords = new LinkedList<NorthRecord>();
		this.southBoundRecords = new LinkedList<SouthRecord>();
		this.northDataResult = new DataResult(DEFAULT_INTERVAL, 1);
		this.northDataProcessor = new TrafficDataProcessor(this.northDataResult, DEFAULT_INTERVAL);
		this.southDataResult = new DataResult(DEFAULT_INTERVAL, 2);
		this.southDataProcessor = new TrafficDataProcessor(this.southDataResult, DEFAULT_INTERVAL);
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		DataLoader dl = new DataLoader();
		// TODO args[0]
		dl.load(new FileInputStream("./data.txt"), FILE_CHARSET);
		System.out.println(dl.northDataResult + " " + dl.southDataResult);
		dl.northDataResult.printResults(DEFAULT_INTERVAL);
		dl.southDataResult.printResults(DEFAULT_INTERVAL);
	}

	/*
	 * public void load(InputStream inputStream, Charset charSet) throws
	 * IOException {
	 * 
	 * try (BufferedReader br = new BufferedReader(new InputStreamReader(
	 * inputStream, charSet))) { String firstLine, secondLine; while ((firstLine
	 * = br.readLine()) != null && (secondLine = br.readLine()) != null) {
	 * String[] dataLines = { firstLine, secondLine }; // check dataline 0 /1
	 * A!=B bad data switch (dataLines[1].charAt(0)) { case 'A':
	 * this.parseRecordA(dataLines); break; case 'B': // sensor b record
	 * this.parseRecordB(dataLines); break; default: //TODO exception break; }
	 * this.checkAndFlushBuffers(this.northBoundRecords,
	 * this.northDataProcessor);
	 * this.checkAndFlushBuffers(this.southBoundRecords,
	 * this.southDataProcessor); } } }
	 * 
	 * 
	 * private void checkAndFlushBuffers(LinkedList<? extends HotelRecord>
	 * records, TrafficDataProcessor dataProcessor) { if (records.size() > 40) {
	 * for (int i = 0; i < 20; i++) { dataProcessor.process(records.remove()); }
	 * } }
	 * 
	 * private void parseRecordB(String[] dataLine) { SouthRecord
	 * sensorBHotelRecord = new SouthRecord("B", new
	 * Integer(dataLine[0].substring(1)), new
	 * Integer(dataLine[1].substring(1))); // This means that previous sensor A
	 * entry was actually on South. // TODO check approx speed to elliminate
	 * parallel hit by north bound // traffic. // TODO Exception if previous is
	 * B. NorthRecord sensorAHotelRecord = this.northBoundRecords.removeLast();
	 * this.southBoundRecords.add(new SouthRecord(sensorAHotelRecord));
	 * this.southBoundRecords.add(sensorBHotelRecord); }
	 * 
	 * private void parseRecordA(String[] dataLine) {
	 * if(dataLine[0].charAt(0)=='A'){ NorthRecord sensorAHotelRecord = new
	 * NorthRecord("A", new Integer(dataLine[0].substring(1)), new
	 * Integer(dataLine[1].substring(1)));
	 * this.northBoundRecords.add(sensorAHotelRecord); }else{ throw new
	 * RuntimeException("Bad record, could not parse."); } }
	 */

	public void load(InputStream inputStream, Charset charSet) throws IOException {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charSet))) {
			LinkedList<String> orphanAs = new LinkedList<String>();
			LinkedList<String> unprocessedAs = new LinkedList<String>();;
			
			while (true) {
				
				while(unprocessedAs.size() > 0){
				unprocessedAs.removeLast();
				}
				
				String dataLine1 = br.readLine();
				String dataLine2 = br.readLine();
				String dataLine3 = br.readLine();
				
				
				String newLine;
				if (isA(dataLine1) && isA(dataLine2) && isA(dataLine3)) {
					// ideal
					this.northBoundRecords.add(new NorthRecord("A", dataLine1, dataLine2));
					unprocessedAs.add(dataLine3);
				} else if (isA(dataLine1) && isB(dataLine2) && isA(dataLine3)) {
					while((newLine = br.readLine()) != null && isA(newLine)){
						orphanAs.add(newLine);
					}
					String lineB = newLine;
					if (orphanAs.size() > 0 ) {
						//map for future A choose amongst orphans and dataLine3 for best match.						
						String orphanA;
						SouthRecord southRefRecord = new SouthRecord("B", dataLine2, lineB);
						Double refSouthSpeed = southRefRecord.getSpeed();						
						String trialLine = dataLine3;
						while(orphanAs.size()>0){
							orphanA = orphanAs.removeLast();
							HotelRecord orphanRecord = new SouthRecord("A", dataLine1, orphanA);
							HotelRecord trialRecord = new SouthRecord("A", dataLine1, trialLine);
							if (Math.abs(refSouthSpeed - trialRecord.getSpeed()) >  Math.abs(refSouthSpeed - orphanRecord.getSpeed())) {
								unprocessedAs.add(trialLine);
								trialLine = orphanA;
							}else {
								unprocessedAs.add(orphanA);
							}
						}
						this.southBoundRecords.add(southRefRecord);
						this.southBoundRecords.add(new SouthRecord("A", dataLine1, trialLine));						
					}else {
						// newLine is B create two records
						this.southBoundRecords.add(new SouthRecord("A", dataLine1, dataLine3));
						this.southBoundRecords.add(new SouthRecord("B", dataLine2, lineB));
					}
				} else if (isA(dataLine1) && isA(dataLine2) && isB(dataLine3)) {
					throw new RuntimeException("Data is corrupt.");
				}
			}
		}
	}
	
	
	private boolean isB(String dataB){
		return dataB.charAt(0)=='B';
	}
	
	private boolean isA(String dataA){
		return dataA.charAt(0)=='A';
	}

	private void checkAndFlushBuffers(LinkedList<String> inputBuffer) {
		if (inputBuffer.size() > 40) {
			String orphanA = "";
			for (int i = 0; i < 20; i++) {
				String dataLine1 = inputBuffer.remove();
				String dataLine2 = inputBuffer.remove();
				String dataLine3 = inputBuffer.remove();
				String dataLine4 = inputBuffer.remove();
				if (dataLine1.charAt(0) == 'A' && dataLine2.charAt(0) == 'A' && dataLine3.charAt(0) != 'B') {
					// ideal
				} else if (dataLine1.charAt(0) == 'A' && dataLine2.charAt(0) == 'B') {
					// ideal start.

				} else if (dataLine1.charAt(0) == 'A' && dataLine2.charAt(0) == 'A' && dataLine3.charAt(0) == 'B') {

				}
			}
		}
	}

	private void checkAndFlushBuffers(LinkedList<? extends HotelRecord> records, TrafficDataProcessor dataProcessor) {
		if (records.size() > 40) {
			for (int i = 0; i < 20; i++) {
				dataProcessor.process(records.remove());
			}
		}
	}

	private void parseRecordB(String dataLine) {

		// Steal last record from A.

		SouthRecord sensorBHotelRecord = new SouthRecord("B", new Integer(dataLine.substring(1)), new Integer(dataLine.substring(1)));
		// This means that previous sensor A entry was actually on South.
		// TODO check approx speed to elliminate parallel hit by north bound
		// traffic.
		// TODO Exception if previous is B.
		NorthRecord sensorAHotelRecord = this.northBoundRecords.removeLast();
		this.southBoundRecords.add(new SouthRecord(sensorAHotelRecord));
		this.southBoundRecords.add(sensorBHotelRecord);
	}

	/*
	 * private void parseRecordA(String dataLine) { if(dataLine.charAt(0)=='A'){
	 * NorthRecord sensorAHotelRecord = new NorthRecord("A", new
	 * Integer(dataLine[0].substring(1)), new
	 * Integer(dataLine[1].substring(1)));
	 * this.northBoundRecords.add(sensorAHotelRecord); }else{ throw new
	 * RuntimeException("Bad record, could not parse."); } }
	 */
}
