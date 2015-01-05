package com.excite.atmsim.testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.excite.atmsim.console.ATMShellClient;
import com.excite.atmsim.exception.ATMCashException;

/**
 * Test cases for ATMShellClient.
 * @author Milind J.
 *
 */
public class TestATMShellClient {

	public static ATMShellClient atmShellClient;

	/**
	 * Test initialising of ATM is smooth.
	 */
	@Before
	public void setupATM(){
		atmShellClient = new ATMShellClient();
		atmShellClient.initialiseDefaultATM();
	}
	
	/**
	 * Test that the client can retrieve count of all notes.
	 */
	@Test
	public void getCountOfallNotesTest(){	
		assertNotNull(atmShellClient.getCountOfAllNotes());
	}
	
	/**
	 * Test that the client can retrieve count of the given note.
	 */
	@Test
	public void getCountOfOneNoteTest() {
		atmShellClient.initialiseATMFromBundle("50,2;20,3");
		String result = atmShellClient.getCountOfGivenNote("50");
		assertEquals("2", result);
	}
	
	/**
	 * Test that the client can perform withdraw operations smoothly.
	 */
	@Test
	public void withdrawCashAmountTotalTest() throws ATMCashException{
		for (int intAmt = 0; intAmt < 200; intAmt++) {
			String result = atmShellClient.withdrawCash(Integer.toString(intAmt * 10));
			assertNotNull(result);
		}
	}
	
}
