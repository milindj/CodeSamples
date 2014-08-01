package com.creditcardfraud.util;

/**
 * Exception to indicate that the supplied list of credit card transactions is
 * not well formatted.
 * 
 * @author Milind J.
 * 
 */
@SuppressWarnings("serial")
public class TransactionListParsingException extends Exception {

	public TransactionListParsingException(String message, Throwable originalException) {
		super(message, originalException);
	}

}
