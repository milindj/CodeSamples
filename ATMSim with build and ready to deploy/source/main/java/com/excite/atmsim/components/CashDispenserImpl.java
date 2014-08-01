package com.excite.atmsim.components;

import java.math.BigInteger;
import java.util.Map;

import com.excite.atmsim.exception.CashDispenserExcpetion;
import com.excite.atmsim.interfaces.CashDispenser;

/**
 * Pseudo implementation of a CashDispenser
 * @author Milind J.
 *
 */
public class CashDispenserImpl implements CashDispenser{

	@Override
	public Map<NoteType, BigInteger> dispense(Map<NoteType, BigInteger> notesToBeDispensed)
			throws CashDispenserExcpetion {
		//Simply return all notes as finally dispensed.
		return notesToBeDispensed;
		
	}

}
