package com.excite.atmsim.exception;

import java.math.BigInteger;
import java.util.Map;

import com.excite.atmsim.components.NoteType;

/**
 * CashDispenserExcpetion
 * @author Milind J.
 *
 */
@SuppressWarnings("serial")
public class CashDispenserExcpetion extends ATMCashException {

	private  Map<NoteType,BigInteger> undispensedNotes;
	public CashDispenserExcpetion(String arg0, Map<NoteType,BigInteger> undispensedNotes) {
		super(arg0);
		this.undispensedNotes = undispensedNotes;
	}

	/**
	 * Keep track of undispensed cash.
	 * @return
	 */
	public Map<NoteType, BigInteger> getUnDispensedCAsh() {
		return this.undispensedNotes;
	}

}
