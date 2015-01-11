package record;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

import record.HotelRecord.Direction;

public class DataLoader {

	private LinkedList<NorthRecord> northBoundRecords;
	private LinkedList<SouthRecord> southBoundRecords;
	private static final Charset FILE_CHARSET = StandardCharsets.UTF_8;
	private static final Integer DEFAULT_INTERVAL = 5*60000;
	
	private TrafficDataProcessor northDataProcessor , southDataProcessor;
	private DataResult northDataResult, southDataResult;

	public DataLoader() {
		this.northBoundRecords = new LinkedList<NorthRecord>();
		this.southBoundRecords = new LinkedList<SouthRecord>();
		this.northDataResult = new DataResult(DEFAULT_INTERVAL, 1);
		this.northDataProcessor = new TrafficDataProcessor(this.northDataResult, DEFAULT_INTERVAL);
		this.southDataResult = new DataResult(DEFAULT_INTERVAL, 2);
		this.southDataProcessor = new TrafficDataProcessor(this.southDataResult, DEFAULT_INTERVAL);
	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		DataLoader dl = new DataLoader();
		//TODO args[0]
		dl.load(new FileInputStream("./data.txt"), FILE_CHARSET);
		System.out.println(dl.northDataResult + " " + dl.southDataResult);
	}

	public void load(InputStream inputStream, Charset charSet)
			throws IOException {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				inputStream, charSet))) {
			String firstLine, secondLine;
			while ((firstLine = br.readLine()) != null
					&& (secondLine = br.readLine()) != null) {
				String[] dataLines = { firstLine, secondLine };
				// check dataline 0 /1 A!=B bad data
				switch (dataLines[0].charAt(0)) {
				case 'A':
					this.parseRecordA(dataLines);
					break;
				case 'B':
					// sensor b record
					this.parseRecordB(dataLines);
					break;
				default:
					//TODO exception
					break;
				}
				this.checkAndFlushBuffers(this.northBoundRecords, this.northDataProcessor);
				this.checkAndFlushBuffers(this.southBoundRecords, this.southDataProcessor);			
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

	private void parseRecordB(String[] dataLine) {
		SouthRecord sensorBHotelRecord = new SouthRecord("B", new Integer(dataLine[0].substring(1)), new Integer(dataLine[1].substring(1)));
		// This means that previous sensor A entry was actually on South.
		// TODO check approx speed to elliminate parallel hit by north bound
		// traffic.
		// TODO Exception if previous is B.
		NorthRecord sensorAHotelRecord = this.northBoundRecords.removeLast();
		this.southBoundRecords.add(new SouthRecord(sensorAHotelRecord));
		this.southBoundRecords.add(sensorBHotelRecord);
	}

	private void parseRecordA(String[] dataLine) {
		NorthRecord sensorAHotelRecord = new NorthRecord("A", new Integer(dataLine[0].substring(1)), new Integer(dataLine[1].substring(1)));
		this.northBoundRecords.add(sensorAHotelRecord);

	}

}
