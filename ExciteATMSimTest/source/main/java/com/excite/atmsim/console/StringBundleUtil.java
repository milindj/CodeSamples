package com.excite.atmsim.console;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.excite.atmsim.components.NoteType;
import com.excite.atmsim.exception.IllegalFormatException;
import com.excite.atmsim.exception.IllegalNoteValueException;

/**
 * Utility to easliy convert note bundles to string and vice-versa for a console based client.
 * @author Milind J.
 *
 */
public class StringBundleUtil {

	public static final String BUNDLE_DELIMITER = ";";
	public static final String NOTE_DELIMITER = ",";
	public static final String SAMPLE_BUNDLE_STRING = "50,12;20,12;10,12;5,12";

	/**
	 * Convert a bundle representation of String into Map.
	 * @param strBundles
	 * @return
	 * @throws IllegalNoteValueException
	 * @throws IllegalFormatException
	 */
	public static Map<NoteType, BigInteger> stringToBundle(String strBundles)
			throws IllegalNoteValueException, IllegalFormatException {

		Map<NoteType, BigInteger> mapBundles = new HashMap<NoteType, BigInteger>();

		String[] bundleArray = strBundles.split(BUNDLE_DELIMITER);

		for (int i = 0; i < bundleArray.length; i++) {
			String[] noteArray = bundleArray[i].split(NOTE_DELIMITER);
			//Validate format.
			if (noteArray.length != 2) {
				throw new IllegalFormatException(
						"Bundle input string not well formatted, it should be : "
								+ SAMPLE_BUNDLE_STRING);
			}
			
			//Create and validate noteType
			try {
			NoteType noteType = new NoteType(
					new BigInteger(noteArray[0].trim()));
			BigInteger noteCount = new BigInteger(noteArray[1].trim());
			if (noteCount.compareTo(BigInteger.ZERO)<0){
				throw new IllegalFormatException("Note count cannot be negative");
			}
			mapBundles.put(noteType, noteCount);
			}catch (NumberFormatException e){
				throw new IllegalFormatException(e.getMessage() + " Amount should be a valid integer");
			}
		}
		return mapBundles;
	}

	/**
	 * Converts a map to a string for a structural display.
	 * @param bundles
	 * @return
	 */
	public static String bundleToString(Map<NoteType, BigInteger> bundles) {

		StringBuilder strBuild = new StringBuilder();

		for (Entry<NoteType, BigInteger> bundle : bundles.entrySet()) {

			strBuild.append(bundle.getKey()).append(": count =")
					.append(bundle.getValue()).append("\n");
		}

		return strBuild.toString();
	}

}
