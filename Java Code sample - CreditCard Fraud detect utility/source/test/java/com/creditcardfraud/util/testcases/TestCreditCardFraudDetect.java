package com.creditcardfraud.util.testcases;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.creditcardfraud.util.CreditCardFraudDetect;
import com.creditcardfraud.util.TransactionListParsingException;


/**
 * Unit test cases for CreditCardFraudDetect.
 * 
 * @author Milind J.
 *
 */
public class TestCreditCardFraudDetect {

	// Dummy credit card hashes.
	public static final String SAMPLE_CREDIT_CARD_HASH_1 = "28d7ce2f43e35fa57d1bbf6d8jr";
	public static final String SAMPLE_CREDIT_CARD_HASH_2 = "54f3c342frde567y65fa5bf5gt4";
	public static final String SAMPLE_CREDIT_CARD_HASH_3 = "10d7ce2f43e35fa57d1bbf8b1e2";
	public static final String SAMPLE_CREDIT_CARD_HASH_4 = "3vt65b12v87n9110n859bm356rt";

	public static final String SAMPLE_DATE_1 = "2014-05-29T13:15:54";
	public static final String SAMPLE_DATE_1_DIFF_TIME = "2014-05-29T15:12:35";
	public static final String SAMPLE_DATE_2 = "2014-05-18T13:15:54";
	public static final String SAMPLE_DATE_3 = "2014-05-04T13:15:54";

	public static final String DELIMITER = ", ";
	public static final String BAD_DELIMITER = " ";

	public static final SimpleDateFormat DATE_FORMAT_PARSER = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * TEST-CASE #1
	 * 
	 * TransactionList : Two fraudulent cards, one with an extra transaction over the threshold. 			
	 * Date : Date associated with the two cards.
	 * Threshold: 50.
	 * 
	 * Result : Two fraudulent cards detected 
	 * 
	 * @throws TransactionListParsingException 
	 * @throws ParseException 
	 */
	@Test
	public void testDetectFraudulentTransactionsTwoCards()
			throws TransactionListParsingException, ParseException {

		Float fraudThreshold = 50.00f;
		// Date to be audited.
		Date dateOfAudit = DATE_FORMAT_PARSER.parse(SAMPLE_DATE_1);
		
		StringBuilder strTransactionList = new StringBuilder();
		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_1).append(DELIMITER)
				.append(SAMPLE_DATE_1).append(DELIMITER).append("24.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_2).append(DELIMITER)
				.append(SAMPLE_DATE_1).append(DELIMITER).append("19.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_1).append(DELIMITER)
				.append(SAMPLE_DATE_1).append(DELIMITER).append("34.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_2).append(DELIMITER)
				.append(SAMPLE_DATE_1).append(DELIMITER).append("34.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_3).append(DELIMITER)
				.append(SAMPLE_DATE_2).append(DELIMITER).append("13.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_4).append(DELIMITER)
				.append(SAMPLE_DATE_3).append(DELIMITER).append("12.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_2).append(DELIMITER)
				.append(SAMPLE_DATE_1).append(DELIMITER).append("17.00")
				.append(DELIMITER);

		// Execute method to be tested.
		List<String> listOfFraudCards = CreditCardFraudDetect.detectFraudulentTransactions(
				strTransactionList.toString(), dateOfAudit, fraudThreshold);

		//Assert that the fraud credit cards are detected.
		assertTrue(listOfFraudCards.contains(SAMPLE_CREDIT_CARD_HASH_1));
		assertTrue(listOfFraudCards.contains(SAMPLE_CREDIT_CARD_HASH_2));
		
		//Assert that the good credit cards are NOT accidently detected.
		assertFalse(listOfFraudCards.contains(SAMPLE_CREDIT_CARD_HASH_3));
		assertFalse(listOfFraudCards.contains(SAMPLE_CREDIT_CARD_HASH_4));

	}
	
	
	/**
	 * TEST-CASE #2
	 * 
	 * TransactionList :One Card among others with sum of price for all dates > threshold, 
	 * 					but NOT fraudulent as sum of price for a given date < threshold .
	 * 					
	 * Date : Date associated with the said card.
	 * Threshold: 50.
	 * 
	 * Result : None detected
	 * 
	 * @throws TransactionListParsingException 
	 * @throws ParseException 
	 */
	@Test
	public void testDetectFraudulentTransactionsTotalPriceGtThreshold() throws TransactionListParsingException, ParseException{
		
		Float fraudThreshold = 50.00f;
		// Date to be audited.
		Date dateOfAudit = DATE_FORMAT_PARSER.parse(SAMPLE_DATE_1);

		StringBuilder strTransactionList = new StringBuilder();
	
		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_4).append(DELIMITER)
				.append(SAMPLE_DATE_1).append(DELIMITER).append("19.00")
				.append(DELIMITER);
		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_3).append(DELIMITER)
				.append(SAMPLE_DATE_2).append(DELIMITER).append("13.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_4).append(DELIMITER)
				.append(SAMPLE_DATE_1).append(DELIMITER).append("23.00")
				.append(DELIMITER);
		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_4).append(DELIMITER)
				.append(SAMPLE_DATE_2).append(DELIMITER).append("12.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_2).append(DELIMITER)
				.append(SAMPLE_DATE_3).append(DELIMITER).append("17.00")
				.append(DELIMITER);

		// Execute method to be tested.
		List<String> listOfFraudCards = CreditCardFraudDetect.detectFraudulentTransactions(
				strTransactionList.toString(), dateOfAudit, fraudThreshold);

		
		//Assert that the good credit cards are NOT accidently detected.
		assertFalse(listOfFraudCards.contains(SAMPLE_CREDIT_CARD_HASH_3));
		assertFalse(listOfFraudCards.contains(SAMPLE_CREDIT_CARD_HASH_4));
		
	}
	

	
	/**
	 * TEST-CASE #3
	 * 
	 * TransactionList : Two Fraudulent card of same date with different times. 
	 * Date : Date associated with the two cards.
	 * Threshold: 50.
	 * 
	 * Result : Two fraudulent cards detected 
	 * 
	 * @throws ParseException 
	 * @throws TransactionListParsingException 
	 */
	@Test
	public void testDetectFraudulentTransactionsCardsOfSameDate() throws ParseException, TransactionListParsingException{
		
		Float fraudThreshold = 50.00f;
		// Date to be audited.
		Date dateOfAudit = DATE_FORMAT_PARSER.parse(SAMPLE_DATE_1);
		
		StringBuilder strTransactionList = new StringBuilder();
		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_1).append(DELIMITER)
				.append(SAMPLE_DATE_1).append(DELIMITER).append("24.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_2).append(DELIMITER)
				.append(SAMPLE_DATE_1_DIFF_TIME).append(DELIMITER).append("19.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_1).append(DELIMITER)
				.append(SAMPLE_DATE_1_DIFF_TIME).append(DELIMITER).append("34.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_2).append(DELIMITER)
				.append(SAMPLE_DATE_1).append(DELIMITER).append("34.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_3).append(DELIMITER)
				.append(SAMPLE_DATE_2).append(DELIMITER).append("13.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_4).append(DELIMITER)
				.append(SAMPLE_DATE_3).append(DELIMITER).append("12.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_2).append(DELIMITER)
				.append(SAMPLE_DATE_1).append(DELIMITER).append("17.00")
				.append(DELIMITER);


		// Execute method to be tested.
		List<String> listOfFraudCards = CreditCardFraudDetect.detectFraudulentTransactions(
				strTransactionList.toString(), dateOfAudit, fraudThreshold);

		//Assert that the fraud credit cards are detected.
		assertTrue(listOfFraudCards.contains(SAMPLE_CREDIT_CARD_HASH_1));
		assertTrue(listOfFraudCards.contains(SAMPLE_CREDIT_CARD_HASH_2));
		
		//Assert that the good credit cards are not accidently detected.
		assertFalse(listOfFraudCards.contains(SAMPLE_CREDIT_CARD_HASH_3));
		assertFalse(listOfFraudCards.contains(SAMPLE_CREDIT_CARD_HASH_4));		
	}
	
	
	/**
	 * TEST-CASE #4
	 * 
	 * TransactionList : Fradulent cards but NOT associated with the date to be audited.
	 * Date : Other than that of the fraudulent cards.
	 * Threshold: 50
	 * 
	 * Result : None detected
	 * 
	 * @throws TransactionListParsingException 
	 * @throws ParseException 
	 */
	@Test
	public void testDetectFraudulentTransactionsCardDiffDate() throws TransactionListParsingException, ParseException{
		
		Float fraudThreshold = 50.00f;
		// Different date to be audited.
		Date dateOfAudit = DATE_FORMAT_PARSER.parse(SAMPLE_DATE_2);

		StringBuilder strTransactionList = new StringBuilder();
		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_1).append(DELIMITER)
				.append(SAMPLE_DATE_1).append(DELIMITER).append("24.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_2).append(DELIMITER)
				.append(SAMPLE_DATE_1).append(DELIMITER).append("19.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_1).append(DELIMITER)
				.append(SAMPLE_DATE_1).append(DELIMITER).append("34.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_2).append(DELIMITER)
				.append(SAMPLE_DATE_1).append(DELIMITER).append("34.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_3).append(DELIMITER)
				.append(SAMPLE_DATE_2).append(DELIMITER).append("13.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_4).append(DELIMITER)
				.append(SAMPLE_DATE_3).append(DELIMITER).append("12.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_2).append(DELIMITER)
				.append(SAMPLE_DATE_1).append(DELIMITER).append("17.00")
				.append(DELIMITER);

		// Execute method to be tested.
		List<String> listOfFraudCards = CreditCardFraudDetect.detectFraudulentTransactions(
				strTransactionList.toString(), dateOfAudit, fraudThreshold);

		//Assert that the fraud credit cards are NOT detected.
		assertFalse(listOfFraudCards.contains(SAMPLE_CREDIT_CARD_HASH_1));
		assertFalse(listOfFraudCards.contains(SAMPLE_CREDIT_CARD_HASH_2));
		
		//Assert that the good credit cards are NO accidently detected.
		assertFalse(listOfFraudCards.contains(SAMPLE_CREDIT_CARD_HASH_3));
		assertFalse(listOfFraudCards.contains(SAMPLE_CREDIT_CARD_HASH_4));
	}
	
	
	/**
	 * TEST-CASE #5
	 * TransactionList : Bad file format.
	 * 
	 * Result : TransactionListParsingException
	 * 
	 * @throws ParseException 
	 * @throws TransactionListParsingException 
	 * 
	 */
	@Test(expected = TransactionListParsingException.class)
	public void testDetectFraudulentTransactionsParseException() throws ParseException, TransactionListParsingException{
		
		Float fraudThreshold = 50.00f;
		// Date to be audited.
		Date dateOfAudit = DATE_FORMAT_PARSER.parse(SAMPLE_DATE_1);
		
		StringBuilder strTransactionList = new StringBuilder();
		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_1).append(DELIMITER)
				.append(SAMPLE_DATE_2).append(DELIMITER).append("24.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_2).append(BAD_DELIMITER)
				.append(SAMPLE_DATE_1).append(DELIMITER).append("19.00")
				.append(DELIMITER);

		strTransactionList.append(SAMPLE_CREDIT_CARD_HASH_1).append(DELIMITER)
				.append(SAMPLE_DATE_1).append(DELIMITER).append("34.00")
				.append(DELIMITER);


		// Execute method to be tested.
		@SuppressWarnings("unused")
		List<String> listOfFraudCards = CreditCardFraudDetect.detectFraudulentTransactions(
				strTransactionList.toString(), dateOfAudit, fraudThreshold);		
	}


}
