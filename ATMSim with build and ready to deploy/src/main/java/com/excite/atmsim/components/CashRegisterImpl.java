package com.excite.atmsim.components;

import java.math.BigInteger;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

import com.excite.atmsim.exception.CashRegisterExcpetion;
import com.excite.atmsim.exception.IllegalNoteValueException;
import com.excite.atmsim.exception.NoCashInRegisterException;
import com.excite.atmsim.interfaces.CashRegister;

/**
 * Class which keeps track of notes and manages withdrawal requests
 * @author Milind J.
 *
 */
public class CashRegisterImpl implements CashRegister {

	public static final Comparator<NoteType> COMPARATOR_DESC_NOTES = new Comparator<NoteType>() {
		@Override
		public int compare(NoteType o1, NoteType o2) {
			// Reverse order for efficient cash dispensing.
			return o2.compareTo(o1);
		}
	};
	private SortedMap<NoteType, BigInteger> noteRegister;

	/**
	 * Initialises for a given map of note-types and their quantity. 
	 * @param bundles
	 */
	public CashRegisterImpl(Map<NoteType, BigInteger> bundles) {
		this.noteRegister = new TreeMap<NoteType, BigInteger>(COMPARATOR_DESC_NOTES);
		this.noteRegister.putAll(bundles);
	}

	/**
	 * Manages cash withdrawal.
	 */
	@Override
	public Map<NoteType, BigInteger> withdrawCash(BigInteger amount)
			throws CashRegisterExcpetion {
		//Validate negative amount.
		if(amount.compareTo(BigInteger.ZERO) < 0){
			throw new IllegalArgumentException("Amount negative");
		}

		//Check if ATM has cash.
		if (noteRegister.isEmpty()) {
			throw new NoCashInRegisterException("The atm is out of cash");
		}

		//Prepare the structure for note-type and quantity to be dispensed.
		TreeMap<NoteType, BigInteger> bundleToDispense = new TreeMap<NoteType, BigInteger>(
				COMPARATOR_DESC_NOTES);

		//Prepare to find an available combination of notes for the requested amount. 
		BigInteger[] noteType = new BigInteger[noteRegister.entrySet().size()];
		BigInteger[] noteCount = new BigInteger[noteRegister.entrySet().size()];
		BigInteger[] combination = new BigInteger[noteRegister.entrySet().size()];

		//initialise the arrays.
		int index = 0;
		for (Entry<NoteType, BigInteger> entry : noteRegister.entrySet()) {
			noteType[index] = entry.getKey().getValue();
			noteCount[index] = entry.getValue();
			combination[index] = BigInteger.ZERO;
			index++;
		}

		// Search for the combination.
		BigInteger[] results = findCombination(noteType, noteCount,
				combination, amount, 0);

		//Check if combination exists.
		if (results == null) {
			throw new CashRegisterExcpetion(
					"Combination of notes not available for the requested amount :"
							+ amount);
		}

		//Fill the object from combination result.
		for (int arrIndex = 0; arrIndex < results.length; arrIndex++) {
			try {
				bundleToDispense.put(new NoteType(noteType[arrIndex]),
						results[arrIndex]);
			} catch (IllegalNoteValueException e) {
				e.printStackTrace();
			}
		}
		return bundleToDispense;

	}

	
	/**
	 * Method to find an available combination of notes for the requested amount. 
	 * @param noteType
	 * @param noteCount
	 * @param combination
	 * @param amount
	 * @param combRef
	 * @return
	 */
	public BigInteger[] findCombination(BigInteger[] noteType,
			BigInteger[] noteCount, BigInteger[] combination,
			BigInteger amount, int combRef) {

		BigInteger[] result = null;

		BigInteger combAmount = totalCombinationAmount(noteType, combination);

		if (combAmount.compareTo(amount) < 0) {
			//Iterate and shift for all the combinations
			for (int index = combRef; index < noteType.length; index++) {

				if (noteCount[index].compareTo(combination[index]) > 0) {

					BigInteger[] nextCombination = new BigInteger[combination.length];
					for (int innerIndex = 0; innerIndex < combination.length; innerIndex++) {
						if (innerIndex == index) {
							nextCombination[innerIndex] = combination[innerIndex].add(BigInteger.ONE);
						} else {
							nextCombination[innerIndex] = combination[innerIndex];
						}

					}
					result = findCombination(noteType, noteCount,
							nextCombination, amount, index);
					//Exit if result found;
					if (result != null) {
						return result;
					}

				}
			}
		} else if (combAmount.equals(amount)) {
			//Record result.
			result = combination;
		}
		return result;
	}

	/**
	 * Method to get the total amount of a combination.
	 * @param noteType
	 * @param combination
	 * @return
	 */
	public BigInteger totalCombinationAmount(BigInteger[] noteType,
			BigInteger[] combination) {
		BigInteger result = BigInteger.ZERO;
		for (int index = 0; index < combination.length; index++) {
			result = result.add(noteType[index].multiply(combination[index]));
		}
		return result;
	}

	/**
	 * Update the cash register about the dispensed notes.
	 */
	@Override
	public void updateNotesDispensed(Map<NoteType, BigInteger> notesDispensed) {

		for (Entry<NoteType, BigInteger> bundleDispensed : notesDispensed.entrySet()) {

			NoteType noteType = bundleDispensed.getKey();
			BigInteger noteCount = bundleDispensed.getValue();

			noteCount = noteRegister.get(noteType).subtract(noteCount);

			if (noteCount.equals(BigInteger.ZERO)) {
				noteRegister.remove(noteType);
			} else {
				noteRegister.put(noteType, noteCount);
			}

		}

	}

	/**
	 * Get bundles, the structure where notetype and quantities are stored.
	 */
	@Override
	public Map<NoteType, BigInteger> getBundles() {
		return this.noteRegister;
	}

}
