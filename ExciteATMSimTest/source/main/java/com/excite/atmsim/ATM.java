package com.excite.atmsim;


import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

import com.excite.atmsim.components.NoteType;
import com.excite.atmsim.exception.ATMCashException;
import com.excite.atmsim.exception.IllegalAmountValueException;
import com.excite.atmsim.interfaces.CashDispenser;
import com.excite.atmsim.interfaces.CashRegister;

/**
 * Class representing the core ATM composite entity.
 * @author Milind J.
 *
 */
public class ATM {

	//ATM components
	private CashRegister cashRegister;
	private CashDispenser cashDispenser;
	
	/**
	 * Constructor to initialise atm.
	 * @param cashRegister
	 * @param cashDispenser
	 */
	public ATM(CashRegister cashRegister, CashDispenser cashDispenser) {
		this.cashRegister = cashRegister;
		this.cashDispenser = cashDispenser;
	}

	/**
	 * Gets count of all notes of each type in the ATM.
	 * @return
	 */
	public Map<NoteType,BigInteger> getCountOfAllNotes(){
		//Return a copy.
		return new TreeMap<NoteType, BigInteger>(cashRegister.getBundles());
	}
	
	/**
	 * Method to withdraw cash from ATM.
	 * @param amount
	 * @return
	 * @throws ATMCashException
	 */
	public Map<NoteType,BigInteger> withdrawCash(BigInteger amount) throws ATMCashException {
		
		//Validate negative amount
		if (amount.compareTo(BigInteger.ZERO) <0){
			throw new IllegalAmountValueException("Cannot dispense negative amount :" + amount);
		}
		
		//Fetch combination of notes from cash register.
		Map<NoteType,BigInteger> notesToBeDispensed = this.cashRegister.withdrawCash(amount);
		
		//To keep track of notes which are finally dispensed.
		Map<NoteType,BigInteger> notesDispensed;
		notesDispensed = this.cashDispenser.dispense(notesToBeDispensed);
		
		// Update cashRegister with final notes dispensed.
		// This approach helps us to be tolerant with cash dispenser errors.
		this.cashRegister.updateNotesDispensed(notesDispensed);
		
		return notesDispensed;
	}
	
	/**
	 * Gets count of a given Note.
	 * @param noteType
	 * @return
	 */
	public BigInteger getCountOfGivenNote(NoteType noteType){
		BigInteger count = this.cashRegister.getBundles().get(noteType);
		return count != null?count:BigInteger.ZERO ;
	}
	
	/**
	 * @return the cashDispenser
	 */
	public CashDispenser getCashDispenser() {
		return cashDispenser;
	}

}
