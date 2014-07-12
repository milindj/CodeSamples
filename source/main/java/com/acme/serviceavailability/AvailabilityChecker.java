package com.acme.serviceavailability;

public interface AvailabilityChecker {
	String isPostCodeIn3DTVServiceArea(String postCode) throws TechnicalFailureException;
}
