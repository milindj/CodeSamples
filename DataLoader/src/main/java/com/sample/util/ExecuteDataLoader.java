package com.sample.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.sample.dataloader.CarDataLoader1N2S;
import com.sample.dataprocessor.DataAggregator;

/**
 * Entry point to run the data loader and print the results into file ./Result.csv.
 * @author Milind
 *
 */
public class ExecuteDataLoader {
	
	private static final Charset FILE_CHARSET = StandardCharsets.UTF_8;
	public static final int[] REF_SPEED_DIST_MATRIX_MPS = { 0, 5, 10, 15, 20, 25, 30, 35, 40 }; //meters/sec.
	private static final Long UNIT_PERIOD = 5 * 60000l; // 5mins.

	public static void main(String[] args) throws FileNotFoundException, IOException {
		if (args.length <= 1 || args[0] == null || args[1] == null) {
			throw new RuntimeException("Please supply the data and result files as arguments say ./data.txt and result file as ./Result.csv ");
		}
		CarDataLoader1N2S dl = new CarDataLoader1N2S(UNIT_PERIOD, REF_SPEED_DIST_MATRIX_MPS);
		List<DataAggregator> dataAggregators = dl.loadRecords(new FileInputStream(args[0]), FILE_CHARSET);
		try(PrintWriter out = new PrintWriter(args[1]);){
			for (DataAggregator  dataAggregator: dataAggregators) {
				out.println("############# Direction: " + dataAggregator.getDirection());
				//Per hour; UnitPeriot is 5mins; hence 1 hr = 12slots*5mins = 60 mins and so on.
				out.println("######## Period : 60 mins");
				out.println(new CSVBuilder(dataAggregator).getResultAsCSVString(12, UNIT_PERIOD));
				// Per half an hour = 6slots*5mins = 30 mins.
				out.println("######## Period : 30 mins");
				out.println(new CSVBuilder(dataAggregator).getResultAsCSVString(6, UNIT_PERIOD));
				// Per 20mins = 4slots*5mins.
				out.println("######## Period : 20 mins");
				out.println(new CSVBuilder(dataAggregator).getResultAsCSVString(4, UNIT_PERIOD));
				// Per 15mins = 3slots*5mins.
				out.println("######## Period : 15 mins");
				out.println(new CSVBuilder(dataAggregator).getResultAsCSVString(3, UNIT_PERIOD));
				out.println("#################################################################");
			}
		}
	}
}
