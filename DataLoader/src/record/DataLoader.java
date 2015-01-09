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

private LinkedList<HotelRecord> sensorRecords;
private Charset charSet;
private DataProcessor dataProcessor;

public DataLoader() {
this.sensorRecords = new LinkedList<HotelRecord>();
//this.sensorRecordsA = new LinkedList<HotelRecord>();
this.dataProcessor = new DataProcessor();
}

public static void main(String[] args) throws FileNotFoundException, IOException {
	DataLoader dl = new DataLoader();
	dl.load(new FileInputStream("./data.txt"), StandardCharsets.UTF_8);
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
					break;
				}
				if (sensorRecords.size() > 40) {
					for (int i = 0; i < 20; i++) {
						this.dataProcessor.addToQueue((sensorRecords.remove()));
					}
				}
			}
		}
	}


private void parseRecordB(String[] dataLine) {

	HotelRecord hotelRecord = new HotelRecord("B",Direction.SOUTH, new Integer(dataLine[0]) , new Integer(dataLine[1]));
	//This means that previous sensor A entry was actually on South. 
	//TODO check approx speed to elliminate parallel hit by north bound traffic.
	//TODO Exception if previous is B.
	this.sensorRecords.getLast().setDirection(Direction.SOUTH);
	this.sensorRecords.add(hotelRecord);
	//hotelRecord.s
	//sensorRecords.add()
	// TODO Auto-generated method stub	
}

private void parseRecordA(String[] dataLine) {
	HotelRecord hotelRecord = new HotelRecord("A",Direction.NORTH, new Integer(dataLine[0]) , new Integer(dataLine[1]));
	this.sensorRecords.add(hotelRecord);
	
}


	


}
