package com.excite.atmsim.testcases;

import java.math.BigInteger;
import java.util.Map;

import com.excite.atmsim.components.NoteType;
import com.excite.atmsim.exception.CashDispenserExcpetion;
import com.excite.atmsim.interfaces.CashDispenser;

/**
 * Dummy class to simulate failure in cash dispenser.
 * @author Milind J.
 *
 */
public class DummyFaultyCashDispenserImpl implements CashDispenser {

	@Override
	public Map<NoteType, BigInteger> dispense(
			Map<NoteType, BigInteger> notesToBeDispensed)
			throws CashDispenserExcpetion {
		throw new CashDispenserExcpetion("Error encountered in cash dispenser", notesToBeDispensed);
	}

}
