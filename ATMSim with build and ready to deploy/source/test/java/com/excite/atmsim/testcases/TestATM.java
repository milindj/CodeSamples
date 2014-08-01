package com.excite.atmsim.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.excite.atmsim.ATM;
import com.excite.atmsim.components.CashDispenserImpl;
import com.excite.atmsim.components.CashRegisterImpl;
import com.excite.atmsim.components.NoteType;
import com.excite.atmsim.exception.ATMCashException;
import com.excite.atmsim.exception.CashDispenserExcpetion;
import com.excite.atmsim.exception.CashRegisterExcpetion;
import com.excite.atmsim.exception.IllegalAmountValueException;
import com.excite.atmsim.exception.NoCashInRegisterException;
import com.excite.atmsim.interfaces.CashRegister;

/**
 * ATM test cases.
 * @author Milind J.
 *
 */
public class TestATM {

	public static ATM atm;
	
	/**
	 * Presetup atm for convenience;
	 */
	@Before
	public void setupATM(){
		Map<NoteType,BigInteger> bundles = new HashMap<NoteType,BigInteger>();
		bundles.put(NoteType.getFifty(), new BigInteger("10000"));
		bundles.put(NoteType.getTwenty(), new BigInteger("10000"));	
		bundles.put(NoteType.getTen(), new BigInteger("10000"));	
		
		atm = new ATM( new CashRegisterImpl(bundles), new CashDispenserImpl());
	}
	
	/**
	 * Test sum of cash withdrawn is equal to the amount requested. 
	 * @throws ATMCashException
	 */
	@Test
	public void withdrawCashAmountTotalTest() throws ATMCashException{

		//Execute test for a multiple series like 10,20,30...
		for (int intAmt = 0; intAmt < 200; intAmt++) {
			
			BigInteger amount = new BigInteger(Integer.toString(intAmt * 10));
			
			//Execute
			Map<NoteType, BigInteger> result = atm.withdrawCash(amount);
			
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
	 * Test cash withdrawn is deducted equally from the ATM register. 
	 * @throws ATMCashException
	 */
	@Test
	public void withdrawCashAmountRegisterDeductTest() throws ATMCashException{

		//Execute test for a multiple series like 10,20,30...
		for (int intAmt = 0; intAmt < 10; intAmt++) {
			
			BigInteger amount = new BigInteger(Integer.toString(intAmt * 10));
			
			//Execute
			Map<NoteType, BigInteger> resultBefore = atm.getCountOfAllNotes();
			Map<NoteType, BigInteger> cashWithdrawn = atm.withdrawCash(amount);
			Map<NoteType, BigInteger> resultAfter = atm.getCountOfAllNotes();
			
			//Check cash register deductions are equal to the cash withdrawn.
			for (Entry<NoteType, BigInteger> entry : resultBefore.entrySet()) {
				assertEquals(cashWithdrawn.get(entry.getKey()), entry
						.getValue().subtract(resultAfter.get(entry.getKey())));
			}
		}
	}
	
	/**
	 * Inject a faulty dispenser to test Error reporting of ATM.
	 * @throws ATMCashException
	 */
	@Test(expected = CashDispenserExcpetion.class)
	public void withdrawCashFaultyDispenserTest() throws ATMCashException{
		
		Map<NoteType,BigInteger> bundles = new HashMap<NoteType,BigInteger>();
		bundles.put(NoteType.getFifty(), new BigInteger("10000"));
		bundles.put(NoteType.getTwenty(), new BigInteger("10000"));	
		bundles.put(NoteType.getTen(), new BigInteger("10000"));	
		
		//Create atm with DummyFaultyCashDispenser.
		atm = new ATM( new CashRegisterImpl(bundles), new DummyFaultyCashDispenserImpl());
		
		BigInteger amount = new BigInteger("30");
		atm.withdrawCash(amount);
	}
	
	
	/**
	 * Inject a faulty dispenser to test that cash requested 
	 * is not deducted from cash register.
	 * @throws ATMCashException
	 */
	@Test(expected = CashDispenserExcpetion.class)
	public void withdrawCashFaultyDispenserCashNotDeductedTest() throws ATMCashException{
		
		Map<NoteType,BigInteger> bundles = new HashMap<NoteType,BigInteger>();
		bundles.put(NoteType.getFifty(), new BigInteger("10000"));
		bundles.put(NoteType.getTwenty(), new BigInteger("10000"));	
		bundles.put(NoteType.getTen(), new BigInteger("10000"));	
		
		//Create atm with DummyFaultyCashDispenser.
		atm = new ATM( new CashRegisterImpl(bundles), new DummyFaultyCashDispenserImpl());
		
		//Execute test for a multiple series like 10,20,30...
		for (int intAmt = 0; intAmt < 10; intAmt++) {

			BigInteger amount = new BigInteger(Integer.toString(intAmt * 10));
			Map<NoteType, BigInteger> resultBefore = atm.getCountOfAllNotes();
			atm.withdrawCash(amount);
			Map<NoteType, BigInteger> resultAfter = atm.getCountOfAllNotes();

			// Check cash register deductions are equal to the cash withdrawn.
			for (Entry<NoteType, BigInteger> entry : resultBefore.entrySet()) {
				assertEquals(entry.getValue(), resultAfter.get(entry.getKey()));
			}
		}
	}
	
	/**
	 * Test amounts which has no combination of notes in ATM assert that cash requested 
	 * is not deducted from cash register.
	 * @throws ATMCashException
	 */
	@Test(expected = CashDispenserExcpetion.class)
	public void withdrawCashNoCombinationCashNotDeductedTest() throws ATMCashException{
		
		Map<NoteType,BigInteger> bundles = new HashMap<NoteType,BigInteger>();
		bundles.put(NoteType.getFifty(), new BigInteger("10000"));
		bundles.put(NoteType.getTwenty(), new BigInteger("10000"));	
		bundles.put(NoteType.getTen(), new BigInteger("10000"));	
		
		//Create atm with DummyFaultyCashDispenser.
		atm = new ATM( new CashRegisterImpl(bundles), new DummyFaultyCashDispenserImpl());
		
		//Execute test for a multiple series like 5,15,25,35...
		for (int intAmt = 0; intAmt < 10; intAmt++) {
			
			BigInteger amount = new BigInteger(Integer.toString(intAmt * 5));
			Map<NoteType, BigInteger> resultBefore = atm.getCountOfAllNotes();
			atm.withdrawCash(amount);
			Map<NoteType, BigInteger> resultAfter = atm.getCountOfAllNotes();

			// Check cash register deductions are equal to the cash withdrawn.
			for (Entry<NoteType, BigInteger> entry : resultBefore.entrySet()) {
				assertEquals(entry.getValue(), resultAfter.get(entry.getKey()));
			}
		}
	}
	
	/**
	 * Test if we get accurate count of each note in ATM.
	 */
	@Test
	public void getCountOfallNotesTest(){
		
		//Setup atm.
		Map<NoteType,BigInteger> bundles = new HashMap<NoteType,BigInteger>();
		bundles.put(NoteType.getFifty(), new BigInteger("10000"));
		bundles.put(NoteType.getTwenty(), new BigInteger("50000"));
		bundles.put(NoteType.getTen(), new BigInteger("20000"));	
		atm = new ATM( new CashRegisterImpl(bundles), new CashDispenserImpl());
		
		//Execute
		Map<NoteType, BigInteger> result = atm.getCountOfAllNotes();
		
		//Test counts for all notes.
		for (Entry<NoteType, BigInteger> entry : result.entrySet()) {
			assertTrue(bundles.containsKey(entry.getKey()));
			assertEquals(bundles.get(entry.getKey()), entry.getValue());
		}
	}
	
	/**
	 * Test to get count of a given note
	 */
	@Test
	public void getCountOfOneNoteTest() {

		//Setup atm.
		Map<NoteType, BigInteger> bundles = new HashMap<NoteType,BigInteger>();
		bundles.put(NoteType.getFifty(), new BigInteger("10000"));
		bundles.put(NoteType.getTwenty(), new BigInteger("50000"));
		bundles.put(NoteType.getTen(), new BigInteger("20000"));
		atm = new ATM(new CashRegisterImpl(bundles), new CashDispenserImpl());

		//Execute
		BigInteger result = atm.getCountOfGivenNote(NoteType.getFifty());

		//Test count of a given note is consistent.
		assertEquals(new BigInteger("10000"), result);
	}
	
	/**
	 * Test for error when no combination of notes exist.
	 * @throws ATMCashException
	 */
	
	@Test(expected = CashRegisterExcpetion.class)
	public void withdrawCashCombinationNotFoundTest() throws ATMCashException {
		//Execute test for a multiple series like 5,15,25,35...
		for (int intAmt = 0; intAmt < 10; intAmt++) {
			BigInteger amount = new BigInteger(Integer.toString(intAmt * 3));
			atm.withdrawCash(amount);
		}
	}
	
	/**
	 * Test for error for a negative amount.
	 * @throws ATMCashException
	 */
	@Test(expected = IllegalAmountValueException.class)
	public void withdrawCashNegativeAmountTest() throws ATMCashException {
		BigInteger amount = new BigInteger("-50");
		atm.withdrawCash(amount);
	}
	
	/**
	 * Test for error for an empty cash register.
	 * @throws ATMCashException
	 */
	@Test(expected = NoCashInRegisterException.class)
	public void withdrawCashNoCashInRegisterTest() throws ATMCashException {
		CashRegister cashRegister = new CashRegisterImpl(new HashMap<NoteType, BigInteger>());
		atm = new ATM( cashRegister, new CashDispenserImpl()); 
		
		BigInteger amount = new BigInteger("50");
		atm.withdrawCash(amount);
	}
}
