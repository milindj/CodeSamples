package com.excite.atmsim.exception;

/**
 * Exception for an illegal AmountValue. 
 * @author Milind J.
 *
 */
@SuppressWarnings("serial")
public class IllegalAmountValueException extends ATMCashException {

	public IllegalAmountValueException(String message) {
		super(message);
	}

}
