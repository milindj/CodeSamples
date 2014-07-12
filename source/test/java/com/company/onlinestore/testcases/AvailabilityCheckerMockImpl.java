package com.company.onlinestore.testcases;

import com.acme.serviceavailability.AvailabilityChecker;
import com.acme.serviceavailability.TechnicalFailureException;

/**
 * Mock implementation for a 3rd Party AvailabilityChecker service.
 * 
 * @author Milind J.
 * 
 */
public class AvailabilityCheckerMockImpl implements AvailabilityChecker {
	public enum ServiceStatusCode {
		SERVICE_AVAILABLE, SERVICE_UNAVAILABLE, SERVICE_PLANNED, POSTCODE_INVALID,
	}

	/**
	 * Dummy PostCode where 3DTV service is available.
	 */
	public static final String TEST_SERVICE_AVAILABLE_POSTCODE = "SERAVL";
	/**
	 * Dummy PostCode where 3DTV service is unavailable.
	 */
	public static final String TEST_SERVICE_UNAVAILABLE_POSTCODE = "SERUNL";
	/**
	 * Dummy PostCode where 3DTV service is not available right now, but it
	 * should be available within the next 3 months.
	 */
	public static final String TEST_SERVICE_PLANNED_POSTCODE = "SERPLN";
	/**
	 * Dummy invalid supplied PostCode.
	 */
	public static final String TEST_POSTCODE_INVALID = "XXXX";
	/**
	 * A dummy PostCode which forces {@link AvailabilityCheckerMockImpl} to
	 * throw a {@link TechnicalFailureException} exception.
	 */
	public static final String AVAILABILITY_CHECKER_FAILS = "ERROR";

	@Override
	public String isPostCodeIn3DTVServiceArea(String postCode)
			throws TechnicalFailureException {
		switch (postCode) {
		case TEST_SERVICE_AVAILABLE_POSTCODE:
			return ServiceStatusCode.SERVICE_AVAILABLE.toString();
		case TEST_SERVICE_UNAVAILABLE_POSTCODE:
			return ServiceStatusCode.SERVICE_UNAVAILABLE.toString();
		case TEST_SERVICE_PLANNED_POSTCODE:
			return ServiceStatusCode.SERVICE_PLANNED.toString();
		case TEST_POSTCODE_INVALID:
			return ServiceStatusCode.POSTCODE_INVALID.toString();
		case AVAILABILITY_CHECKER_FAILS:
			throw new TechnicalFailureException();
		}

		return null;
	}

}
