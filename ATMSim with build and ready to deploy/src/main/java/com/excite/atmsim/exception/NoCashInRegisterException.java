package com.excite.atmsim.exception;

/**
 * Exception to represent no cash in ATM.
 * @author Milind J.
 *
 */
@SuppressWarnings("serial")
public class NoCashInRegisterException extends CashRegisterExcpetion {

	public NoCashInRegisterException(String arg0) {
		super(arg0);
	}

}
