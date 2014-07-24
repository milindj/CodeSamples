package com.excite.atmsim.console;

import java.math.BigInteger;
import java.util.Map;

import com.excite.atmsim.ATM;
import com.excite.atmsim.ATMFactory;
import com.excite.atmsim.components.NoteType;
import com.excite.atmsim.exception.ATMCashException;
import com.excite.atmsim.exception.CashDispenserExcpetion;
import com.excite.atmsim.exception.IllegalFormatException;
import com.excite.atmsim.exception.IllegalNoteValueException;

/**
 * An adapter and a client for a shell/console interface.
 * @author Milind J.
 *
 */
public class ATMShellClient {

	private ATM atm;

	/**
	 * Init ATM with default 100 notes of $50 and $20 each.
	 * @return
	 */
	public String initialiseDefaultATM() {
		this.atm = ATMFactory.buildDefaultATM();
		return StringBundleUtil.bundleToString(this.atm
				.getCountOfAllNotes());
	}

	/**
	 * Init ATM with supplied note types and quantity.
	 * @param strBundles - format: '50,12;20,12;10,12;5,12'
	 * @return
	 */
	public String initialiseATMFromBundle(String strBundles) {
		
		Map<NoteType, BigInteger> mapBundles;
		try {
			mapBundles = StringBundleUtil.stringToBundle(strBundles);
		} catch (IllegalNoteValueException e) {
			return e.getMessage();
		} catch (IllegalFormatException e) {
			return e.getMessage();
		}
				
		this.atm = ATMFactory.buildATM(mapBundles);
		
		return StringBundleUtil.bundleToString(this.atm
				.getCountOfAllNotes());
	}

	/**
	 * Gets the count of a given note type.
	 * @param strNoteType
	 * @return
	 */
	public String getCountOfGivenNote(String strNoteType) {
		NoteType noteType;
		try {
			noteType = new NoteType(new BigInteger(strNoteType.trim()));
		} catch (IllegalNoteValueException e) {
			return e.getMessage();
		}catch (NumberFormatException e) {
			return "It should be a valid denomination and a number." + e.getMessage();
		}
		return this.atm.getCountOfGivenNote(noteType).toString();
	}

	/**
	 * Get count of all given note types.
	 * @return
	 */
	public String getCountOfAllNotes() {

		Map<NoteType, BigInteger> bundles = this.atm.getCountOfAllNotes();
		if (bundles.isEmpty()){
			return "No Cash in the ATM";
		}else{
			return "The ATM has the following Type and Quantity of Notes:\n"  + StringBundleUtil.bundleToString(bundles);
		}

	}
	
	/**
	 * Method to withdraw cash from ATM.
	 * @param strAmount
	 * @return
	 */
	public String withdrawCash(String strAmount) {
		Map<NoteType, BigInteger> bundles;
		BigInteger amount; 
				
		//Validate for a number.
		try {
			amount = new BigInteger(strAmount);
		}catch (NumberFormatException e){
			return  e.getMessage() + " Amount should be a valid integer";
		}
		
		//Withdraw cash, and relay messages to the console.
		try {
			bundles = this.atm.withdrawCash(amount);
			
		} catch (CashDispenserExcpetion e) {
		
			String errorMessgae = "Error in dispensing cash : " + e.getMessage(); 
			errorMessgae = errorMessgae + "\nFollowing undispensed cash is restored in the cash register:\n" 
			+ StringBundleUtil.bundleToString(e.getUnDispensedCAsh());
			
			return errorMessgae;
		//Report error message back to console.
		}catch (ATMCashException e) {
			return "Error in dispensing cash : " + e.getMessage();
		}
		
		//Report a confirmation note on what notetypes and quantity was dispensed.
		return "Cash dispensed in the following denominations, Thank you.\n" + StringBundleUtil.bundleToString(bundles);
		
	}
	

}
