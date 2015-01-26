/**
 * 
 */
package com.sample.dataloader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import com.sample.dataprocessor.DataAggregator;

/**
 * Interface for a data loader.
 * @author Milind
 *
 */
public interface SensorDataLoader {
	
	/**
	 * Loads and sort the data into appropriate sensors. 
	 * @param inputStream input data stream.
	 * @param charSet charset of the input data.
	 * @return DataAggregator,which contains coarse data, in a ready to be calculated form.
	 * @throws IOException
	 */
	public List<DataAggregator> loadRecords(InputStream inputStream, Charset charSet) throws IOException;

}
