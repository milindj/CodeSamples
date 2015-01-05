package com.excite.atmsim.interfaces;

import java.math.BigInteger;
import java.util.Map;

import com.excite.atmsim.components.NoteType;
import com.excite.atmsim.exception.CashDispenserExcpetion;

/**
 * Interface representing a CashDispenser of an ATM.
 * @author Milind J.
 *
 */
public interface CashDispenser {
	
	/**
	 * Method to dispense cash.
	 * @param cashToBeDispensed
	 * @return
	 * @throws CashDispenserExcpetion
	 */
	public Map<NoteType, BigInteger> dispense(Map<NoteType, BigInteger> cashToBeDispensed) throws CashDispenserExcpetion;
}
