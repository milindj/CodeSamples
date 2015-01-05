package com.excite.atmsim.exception;

/**
 * Parent of all type of exception while withdrawing cash from ATM.
 * @author Milind J.
 *
 */
@SuppressWarnings("serial")
public class ATMCashException extends Exception {

	public ATMCashException(String arg0) {
		super(arg0);
	}

}
