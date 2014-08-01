package com.company.onlinestore;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.acme.serviceavailability.AvailabilityChecker;
import com.acme.serviceavailability.TechnicalFailureException;

/**
 * Implementation of the ThreeDeeAddOnService interface. 
 * @author Milind J.
 *
 */
public class ThreeDeeAddOnServiceImpl implements ThreeDeeAddOnService {

	//Ideally the service status codes could be moved into the AvailabilityCheker, 
	// however they are declared here, because for the test it is specified that
	// the AvailabilityChecker and TechnicalFailureException cannot be modified.
	/**
	 * Availability Checker Service status code, 3DTV service is available for
	 * the given post code
	 */
	public static final String SERVICE_AVAILABLE = "SERVICE_AVAILABLE";
	/**
	 * Availability Checker Service status code, 3DTV service is unavailable for
	 * the given post code
	 */
	public static final String SERVICE_UNAVAILABLE = "SERVICE_UNAVAILABLE";
	/**
	 * Availability Checker Service status code, 3DTV service is not available
	 * right now, but it should be available within the next 3 months
	 */
	public static final String SERVICE_PLANNED = "SERVICE_PLANNED";
	/**
	 * Availability Checker Service status code, The supplied postcode is
	 * invalid
	 */
	public static final String POSTCODE_INVALID = "POSTCODE_INVALID";

	private AvailabilityChecker availabilityChecker;

	/**
	 * Initialise the service with a 3rd party availabilityChecker implementation.
	 * @param availabilityChecker
	 */
	public ThreeDeeAddOnServiceImpl(AvailabilityChecker availabilityChecker) {
		this.availabilityChecker = availabilityChecker;
	}

	@Override
	public Set<AddOnProduct> checkFor3DAddOnProducts(Basket basket,
			String postCode) throws PostCodeInvalidException {

		try {
			switch (availabilityChecker.isPostCodeIn3DTVServiceArea(postCode)) {
			case SERVICE_AVAILABLE:
				// Return 3DAddOn products.
				return get3DAddOnProductsFromBasket(basket);
			case SERVICE_UNAVAILABLE:
				// Return an empty set.
				return Collections.emptySet();
			case SERVICE_PLANNED:
				// Return an empty set.
				return Collections.emptySet();
			case POSTCODE_INVALID:
				// Notify the client that the postcode supplied was invalid.
				throw new PostCodeInvalidException("The postcode received as "
						+ postCode + " is found to be invalid");
			}
		} catch (TechnicalFailureException e) {
			// Return an empty set if there is a technical issue with AvailabilityChecker service.
			return Collections.emptySet();
		}

		// default return statement.
		return Collections.emptySet();

	}

	/**
	 * A convenience implementation to get a list of AddonProducts for their
	 * respective compatible entertainment products available in the Basket.
	 * 
	 * @param basket
	 *            Basket of entertainment product.
	 * @return Set of corresponding {@link AddOnProduct}s
	 */
	private Set<AddOnProduct> get3DAddOnProductsFromBasket(Basket basket) {

		Set<AddOnProduct> addonProductSet = new HashSet<>();
		for (EntertainmentProduct product : basket.getBasketContents()) {
			if (product.getCompatibleAddOnProduct() != null)
				addonProductSet.add(product.getCompatibleAddOnProduct());
		}
		return addonProductSet;

	}
}
