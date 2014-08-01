package com.excite.atmsim.testcases;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;


import com.excite.atmsim.components.NoteType;
import com.excite.atmsim.exception.IllegalNoteValueException;

/**
 * Test cases for NoteType
 * @author Milind J.
 *
 */
public class TestNoteType {

	/**
	 * Test to ensure object of only legal value should be formed.
	 * @throws IllegalNoteValueException
	 */
	@Test(expected = IllegalNoteValueException.class)
	public void noteTypeLegalTest() throws IllegalNoteValueException{
		new NoteType(new BigInteger("3"));
	}
	
	/**
	 * Test self equality.
	 * @throws IllegalNoteValueException
	 */
	@Test
	public void noteTypeEqaulityTest() throws IllegalNoteValueException{	 
		assertTrue(new NoteType(new BigInteger("50")).equals(NoteType.getFifty()));
	}
	
	/**
	 * Test hashcodes are same for same types of notes.
	 * @throws IllegalNoteValueException
	 */
	@Test
	public void noteTypeHashCodeTest() throws IllegalNoteValueException{	 
		assertEquals(NoteType.getFifty().hashCode(), (new NoteType(new BigInteger("50")).hashCode()));	
	}
	
	/**
	 * Test that compareTo method performs as per expectations.
	 * @throws IllegalNoteValueException
	 */
	@Test
	public void noteTypeCompareTest() throws IllegalNoteValueException{	 
		assertTrue((new NoteType(new BigInteger("50")).compareTo(NoteType.getTwenty()) > 0));
		assertTrue((new NoteType(new BigInteger("10")).compareTo(NoteType.getTwenty()) < 0));
		assertEquals(0,(new NoteType(new BigInteger("10")).compareTo(NoteType.getTen())));
	}
	
	
}
