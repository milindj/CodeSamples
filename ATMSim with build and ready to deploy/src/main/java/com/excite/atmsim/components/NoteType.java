package com.excite.atmsim.components;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.excite.atmsim.exception.IllegalNoteValueException;
/**
 * Class representing a noteType.
 * @author Milind J.
 *
 */
public class NoteType implements Comparable<NoteType>{
	
	/**
	 * Legal note types/ denominations.
	 */
	public static final Set<BigInteger> LEGAL_DENOMINATIONS = new HashSet<BigInteger>(
			Arrays.asList(new BigInteger("100"), 
					new BigInteger("50"), 
					new BigInteger("20"), 
					new BigInteger("10"), 
					new BigInteger("5")));

	private BigInteger value;
	
	/**
	 * Init noteType with a legal denomination.
	 * @param value
	 * @throws IllegalNoteValueException
	 */
	public NoteType(BigInteger value) throws IllegalNoteValueException{
		if (LEGAL_DENOMINATIONS.contains(value)){
		this.value = value;
		}else{
			throw new IllegalNoteValueException("Note denomination[" + value + "] not legal , please make sure it is one of : " + LEGAL_DENOMINATIONS );
		}
		
	}
	
	/**
	 * Returns the value of the denomination.
	 * @return
	 */
	public BigInteger getValue() {
		return value;
	}

	@Override
	public int compareTo(NoteType other) {		
		return this.getValue().compareTo(other.getValue());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NoteType other = (NoteType) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NoteType [" + value + "]";
	}
	
	
	/**
	 * @return the hundred
	 */
	public static NoteType getHundred() {
		NoteType noteType = null;
		try {
			noteType = new NoteType(new BigInteger("100"));
		} catch (IllegalNoteValueException e) {
			e.printStackTrace();
		}
		return noteType;	
	}

	/**
	 * @return the fifty
	 */
	public static NoteType getFifty() {
		NoteType noteType = null;
		try {
			noteType = new NoteType(new BigInteger("50"));
		} catch (IllegalNoteValueException e) {
			e.printStackTrace();
		}
		return noteType;	
	}

	/**
	 * @return the twenty
	 */
	public static NoteType getTwenty() {
		NoteType noteType = null;
		try {
			noteType = new NoteType(new BigInteger("20"));
		} catch (IllegalNoteValueException e) {
			e.printStackTrace();
		}
		return noteType;	
	}

	/**
	 * @return the ten
	 */
	public static NoteType getTen() {
		NoteType noteType = null;
		try {
			noteType = new NoteType(new BigInteger("10"));
		} catch (IllegalNoteValueException e) {
			e.printStackTrace();
		}
		return noteType;	
	}

	/**
	 * @return the five
	 */
	public static NoteType getFive() {
		NoteType noteType = null;
		try {
			noteType = new NoteType(new BigInteger("5"));
		} catch (IllegalNoteValueException e) {
			e.printStackTrace();
		}
		return noteType;	
	}

}
