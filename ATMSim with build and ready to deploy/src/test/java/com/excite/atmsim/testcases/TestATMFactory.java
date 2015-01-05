package com.excite.atmsim.testcases;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.excite.atmsim.ATM;
import com.excite.atmsim.ATMFactory;
import com.excite.atmsim.components.NoteType;

/**
 * Test cases for ATMFactory.
 * @author Milind J.
 *
 */
public class TestATMFactory {

	/**
	 * Test default ATM setup.
	 */
	@Test
	public void buildDefaultATMTest(){
		
		ATM atm = ATMFactory.buildDefaultATM();
		
		assertNotNull(atm.getCountOfAllNotes());
		assertNotNull(atm.getCashDispenser());
	}
	
	/**
	 * Test ATM setup build from a bundle.
	 */
	@Test
	public void buildATMFromBundleTest(){
		Map<NoteType,BigInteger> bundles = new HashMap<NoteType,BigInteger>();
		bundles.put(NoteType.getFifty(), new BigInteger("10000"));
		bundles.put(NoteType.getTwenty(), new BigInteger("10000"));	
		bundles.put(NoteType.getTen(), new BigInteger("10000"));	
		
		ATM atm = ATMFactory.buildATM(bundles);
		
		assertNotNull(atm.getCountOfAllNotes());
		assertNotNull(atm.getCashDispenser());
	}
	
}
