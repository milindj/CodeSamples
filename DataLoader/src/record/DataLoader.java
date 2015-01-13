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

public class DataLoader {

	private ArrayList<NorthRecord> northBoundRecords = new ArrayList<NorthRecord>();
	private ArrayList<SouthRecord> southBoundRecords = new ArrayList<SouthRecord>();
	private static final Charset FILE_CHARSET = StandardCharsets.UTF_8;
	private static final Integer DEFAULT_INTERVAL = 5 * 60000;
	private LinkedList<String> sensorA = new LinkedList<String>();
	private LinkedList<String> sensorB = new LinkedList<String>();
	private LinkedList<String> inputBuffer = new LinkedList<String>();
	public static final Integer[] DEFAULT_SPEED_MATRIX_SCALE = {0,5,10,15,20,25,30,35,40};
	private static final Integer NUM_NORTH_SENSORS =1;
	private static final Integer NUM_SOUTH_SENSORS =2;

	private TrafficDataProcessor northDataProcessor, southDataProcessor;
	private DataResult northDataResult, southDataResult;

	public DataLoader() {
		this(DEFAULT_INTERVAL,DEFAULT_SPEED_MATRIX_SCALE);
	}

	public DataLoader(Integer interval,Integer[] speedDistMatrix) {
		this.northDataResult = new DataResult(interval, NUM_NORTH_SENSORS);
		this.northDataProcessor = new TrafficDataProcessor(this.northDataResult, interval, speedDistMatrix);
		this.southDataResult = new DataResult(interval, NUM_SOUTH_SENSORS);
		this.southDataProcessor = new TrafficDataProcessor(this.southDataResult, interval, speedDistMatrix);
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		DataLoader dl = new DataLoader();
		// TODO args[0]
		dl.load(new FileInputStream("./data.txt"), FILE_CHARSET);
		System.out.println(dl.northDataResult + " " + dl.southDataResult);
		dl.northDataResult.printResults(DEFAULT_INTERVAL);
		dl.southDataResult.printResults(DEFAULT_INTERVAL);
	}

	public void load(InputStream inputStream, Charset charSet) throws IOException {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charSet))){
			LinkedList<String> orphanAs = new LinkedList<String>();
			LinkedList<String> unprocessedAs = new LinkedList<String>();
			while (true) { 
				while(unprocessedAs.size() > 0){
					inputBuffer.addFirst(unprocessedAs.removeLast());
				}
				while(inputBuffer.size() < 40){
					String bufferedLine = br.readLine();
					if (bufferedLine !=null){
						inputBuffer.addLast(bufferedLine);
					}else{
						break;
					}
				}
				String dataLine1 = inputBuffer.pollFirst();
				String dataLine2 = inputBuffer.pollFirst();
				String dataLine3 = inputBuffer.pollFirst();
				
				if (dataLine1==null||dataLine2==null){
					break;
				}
				
				String newLine;
				if (isA(dataLine1) && isA(dataLine2) && !isB(dataLine3)) {
					// ideal
					this.northBoundRecords.add(new NorthRecord("A", dataLine1, dataLine2));
					if(dataLine3==null){
						break;
					}
					unprocessedAs.add(dataLine3);
				} else if (isA(dataLine1) && isB(dataLine2) && isA(dataLine3)) {
					while((newLine = inputBuffer.pollFirst()) != null && isA(newLine)){
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
					while((newLine = inputBuffer.pollFirst()) != null && isA(newLine)){
						orphanAs.add(newLine);
					}
					String lineB = newLine;
					if (orphanAs.size() > 0 ) {
						SouthRecord southRefRecord = new SouthRecord("B", dataLine3, lineB);
						Double refSouthSpeed = southRefRecord.getSpeed();	
						//dataLine1 test, assuming dataline2 to be close enough for B
						String prevOrphan = orphanAs.getFirst();
						
						for (int i = 1; i < orphanAs.size(); i++) {
							String orphanA = orphanAs.get(i);
							HotelRecord prevRecord = new SouthRecord("A", dataLine1, prevOrphan);
							HotelRecord orphanRecord = new SouthRecord("A", dataLine1, orphanA);
							if (Math.abs(refSouthSpeed - prevRecord.getSpeed()) >  Math.abs(refSouthSpeed - orphanRecord.getSpeed())) {
								prevOrphan = orphanA;
							}
						}
						//Now prevOrphan is the best match of line1
						String line1OrphanMatch = prevOrphan; 
						
						prevOrphan = orphanAs.getFirst();
						for (int i = 1; i < orphanAs.size(); i++) {
							String orphanA = orphanAs.get(i);
							HotelRecord prevRecord = new SouthRecord("A", dataLine2, prevOrphan);
							HotelRecord orphanRecord = new SouthRecord("A", dataLine2, orphanA);
							if (Math.abs(refSouthSpeed - prevRecord.getSpeed()) >  Math.abs(refSouthSpeed - orphanRecord.getSpeed())) {
								prevOrphan = orphanA;
							}
						}
						//Now prevOrphan is the best match of line2
						String line2OrphanMatch = prevOrphan;
						//Compare the best match between dataline1 and dataline2.
						SouthRecord record1 = new SouthRecord("A", dataLine2, line1OrphanMatch);
						SouthRecord record2 = new SouthRecord("A", dataLine2, line2OrphanMatch);
						
						if (Math.abs(refSouthSpeed - record1.getSpeed()) >  Math.abs(refSouthSpeed - record2.getSpeed())) {
							this.southBoundRecords.add(record2);	
						}else{
							this.southBoundRecords.add(record1);	
						}
						this.southBoundRecords.add(southRefRecord);
						while(orphanAs.size()>0){
							unprocessedAs.addFirst(orphanAs.removeLast());
						}
					}else {
						throw new RuntimeException("Unexpected sequence of sensor records d1:d2:d3 = " + dataLine1 + ":" + dataLine2 + ":" + dataLine3);
					}
				}
				this.flushRecordBuffersToProcess(this.northBoundRecords, this.northDataProcessor);
				this.flushRecordBuffersToProcess(this.southBoundRecords, this.southDataProcessor);				
			}	
		}
		
	}
	
	
	private boolean isB(String dataB){
		return dataB!=null && dataB.charAt(0)=='B';
	}
	
	private boolean isA(String dataA){
		return dataA!=null && dataA.charAt(0)=='A';
	}

	private void flushRecordBuffersToProcess(ArrayList<? extends HotelRecord> records, TrafficDataProcessor dataProcessor) {
		for (HotelRecord hotelRecord : records) {
			dataProcessor.process(hotelRecord);
		}
		records.clear();
	}
}
