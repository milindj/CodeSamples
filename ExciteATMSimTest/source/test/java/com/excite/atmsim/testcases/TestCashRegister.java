package com.excite.atmsim.testcases;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.excite.atmsim.components.CashRegisterImpl;
import com.excite.atmsim.components.NoteType;
import com.excite.atmsim.exception.CashRegisterExcpetion;
import com.excite.atmsim.exception.NoCashInRegisterException;
import com.excite.atmsim.interfaces.CashRegister;

/**
 * Test cases for CashRegister
 * @author Milind J.
 *
 */
public class TestCashRegister {

	public static CashRegister cashRegister;
	
	/**
	 * Pre-setup for ease of access.
	 */
	@Before
	public void setupCashRegister(){
		Map<NoteType,BigInteger> bundles = new HashMap<NoteType,BigInteger>();
		bundles.put(NoteType.getFifty(), new BigInteger("10000"));
		bundles.put(NoteType.getTwenty(), new BigInteger("10000"));	
		bundles.put(NoteType.getTen(), new BigInteger("10000"));	
		
		cashRegister = new CashRegisterImpl(bundles);
	}
	
	/**
	 * Test sum of cash withdrawn is equal to the amount requested. 
	 * @throws CashRegisterExcpetion
	 */
	@Test
	public void withdrawCashAmountTotalTest() throws CashRegisterExcpetion{
		
		//Execute test for a multiple series like 10,20,30...
		for (int intAmt = 0; intAmt < 200; intAmt++) {
			BigInteger amount = new BigInteger(Integer.toString(intAmt * 10));
			
			//Execute
			Map<NoteType, BigInteger> result = cashRegister.withdrawCash(amount);
			
			//Calculate cash
			BigInteger totalNoteSum = BigInteger.ZERO;
			for (Entry<NoteType, BigInteger> entry : result.entrySet()) {
				totalNoteSum = totalNoteSum.add(entry.getKey().getValue()
						.multiply(entry.getValue()));
			}
			
			// Test sum of cash withdrawn is equal to the amount requested.
			assertEquals(amount, totalNoteSum);
		}
		
	}
		
	/**
	 * 
	 * @throws CashRegisterExcpetion
	 */
	@Test(expected = CashRegisterExcpetion.class)
	public void withdrawCashExceptionAmountNotDispensableTest() throws CashRegisterExcpetion {
		BigInteger amount = new BigInteger("25");
		cashRegister.withdrawCash(amount);
	}
	
	/**
	 * Test for error when no combination of notes exist.
	 * @throws CashRegisterExcpetion
	 */
	@Test(expected = CashRegisterExcpetion.class)
	public void withdrawCashCombinationNotFoundTest() throws CashRegisterExcpetion {
		BigInteger amount = new BigInteger("25");
		cashRegister.withdrawCash(amount);
	}
	
	/**
	 * Test for error for a negative amount.
	 * @throws CashRegisterExcpetion
	 */
	@Test(expected = IllegalArgumentException.class)
	public void withdrawCashNegativeAmountTest() throws CashRegisterExcpetion {
		BigInteger amount = new BigInteger("-50");
		cashRegister.withdrawCash(amount);
	}
	
	/**
	 * Test for error for an empty cash register.
	 * @throws CashRegisterExcpetion
	 */
	@Test(expected = NoCashInRegisterException.class)
	public void withdrawCashNoCashInRegisterTest() throws CashRegisterExcpetion {
		cashRegister = new CashRegisterImpl(new HashMap<NoteType, BigInteger>());
		BigInteger amount = new BigInteger("50");
		cashRegister.withdrawCash(amount);
	}
	
}
