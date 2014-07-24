package com.excite.atmsim.interfaces;

import java.math.BigInteger;
import java.util.Map;

import com.excite.atmsim.components.NoteType;
import com.excite.atmsim.exception.CashRegisterExcpetion;

/**
 * Interface representing an ATM's cash register.
 * @author Milind J.
 *
 */
public interface CashRegister {

	/**
	 * 	Get bundles, the structure where notetype and quantities are stored.
	 * @return
	 */
	public Map<NoteType, BigInteger> getBundles();
	
	/**
	 * Manage cash withdrawal.
	 * @param amount
	 * @return
	 * @throws CashRegisterExcpetion
	 */
	public Map<NoteType, BigInteger> withdrawCash(BigInteger amount)throws CashRegisterExcpetion;
	
	/**
	 * Method to update cashregister about the notes dispensed.
	 * @param notesDispensed
	 */
	public void updateNotesDispensed(Map<NoteType, BigInteger> notesDispensed);

}
