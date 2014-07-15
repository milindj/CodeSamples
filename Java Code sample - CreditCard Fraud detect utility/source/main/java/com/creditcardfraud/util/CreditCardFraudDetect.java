package com.creditcardfraud.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Consist of utility methods to detect fraudulent credit cards.
 * 
 * @author Milind J.
 * 
 */
public class CreditCardFraudDetect {

	public static final String DELIMITER = ", ";

	public static final SimpleDateFormat DATE_FORMAT_PARSER = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * 
	 * When given a list of transactions, a date and a price threshold, this
	 * function returns a list of hashed credit card numbers that have been
	 * identified as fraudulent for that day.
	 * 
	 * @param strTransactionList
	 *            Transactions are received as a comma separated string of
	 *            elements eg. '10d7ce2f43e35fa57d1bbf8b1e2,
	 *            2014-04-29T13:15:54, 10.00'
	 * @param dateOfAudit
	 *            The day for which transactions are to be audited.
	 * @param fraudThreshold
	 *            The threshold sum of price which determines a card as
	 *            fraudulent if it exceeds this value.
	 * @return
	 * @throws TransactionListParsingException
	 */
	public static List<String> detectFraudulentTransactions(
			String strTransactionList, Date dateOfAudit, BigDecimal fraudThreshold)
			throws TransactionListParsingException {

		// Map to record the sum of price against each unique hashed Credit
		// card.
		Map<String, BigDecimal> sumOfPriceMap = new HashMap<>();
		// HashSet to store the fraud credit cards.
		Set<String> fraudCreditCards = new HashSet<>();

		// Parse the list of transactions into an array and iterate over it.
		String[] transactionAry = strTransactionList.split(DELIMITER);
		int elementIndex = 0;

		do {

			try {
				// First element in the series represents the hashed credit card
				// number.
				String creditCard = transactionAry[elementIndex];
				// Second element in the series represents the date of
				// transaction
				Date dateOfTransaction = DATE_FORMAT_PARSER
						.parse(transactionAry[elementIndex + 1]);

				// 1# Check for the date which is to be audited, if not then skip.
				// 2# Skip the processing if the credit card is already known as fraudulent.
				if (dateOfTransaction.equals(dateOfAudit)
						&& !fraudCreditCards.contains(creditCard)) {

					// Third element in the series represents the price.
					BigDecimal transactionPrice = new BigDecimal(transactionAry[elementIndex + 2]);

					// Fetch an earlier record of price/sum of price for the given card.
					BigDecimal sumOfPrice = sumOfPriceMap.get(creditCard);

					if (sumOfPrice != null) {
						sumOfPrice = sumOfPrice.add(transactionPrice);
					} else {
						sumOfPrice = transactionPrice;
					}

					// Record the sum against a given card for sequent iterations.
					sumOfPriceMap.put(creditCard, sumOfPrice);

					// Add the card to the fraudulent Set.
					if (sumOfPrice.compareTo(fraudThreshold) > 0) {
						fraudCreditCards.add(creditCard);
					}

				}

			} catch (ParseException parseException) {
				throw new TransactionListParsingException(
						"Date not well formatted or transaction list is corrupt, current record-reference: "
								+ transactionAry[elementIndex], parseException);
			} catch (NumberFormatException parseException) {
				throw new TransactionListParsingException(
						"Please check the format of transaction list, current record-reference: "
								+ transactionAry[elementIndex], parseException);
			}
			
			// Move to the next series of transaction.
			elementIndex = elementIndex + 3;
		} while (elementIndex < transactionAry.length);

		// Converting to and returning a list as requested in the Test
		// requirements.
		return new ArrayList<>(fraudCreditCards);

	}

}