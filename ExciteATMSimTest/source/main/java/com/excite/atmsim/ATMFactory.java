package com.excite.atmsim;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.excite.atmsim.components.CashDispenserImpl;
import com.excite.atmsim.components.CashRegisterImpl;
import com.excite.atmsim.components.NoteType;

/**
 *  Factory utility for building ATMs.
 * @author Milind J.
 *
 */
public class ATMFactory {

	/**
	 * Setup a default ATM with 100 notes of $50 and $20 each.
	 * @return
	 */
	public static ATM buildDefaultATM(){
		
		Map<NoteType,BigInteger> bundles = new HashMap<NoteType,BigInteger>();
		bundles.put(NoteType.getFifty(), new BigInteger("100"));
		bundles.put(NoteType.getTwenty(), new BigInteger("100"));	
		
		return buildATM(bundles);
		
	}
	
	/**
	 * Setup an ATM with given type and count of notes.
	 * @param bundles
	 * @return
	 */
	public static ATM buildATM(Map<NoteType,BigInteger> bundles){
		return new ATM(new CashRegisterImpl(bundles), new CashDispenserImpl());
	}

}
