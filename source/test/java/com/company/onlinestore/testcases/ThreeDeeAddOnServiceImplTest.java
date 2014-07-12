package com.company.onlinestore.testcases;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.acme.serviceavailability.AvailabilityChecker;
import com.company.onlinestore.AddOnProduct;
import com.company.onlinestore.Basket;
import com.company.onlinestore.EntertainmentProduct;
import com.company.onlinestore.PostCodeInvalidException;
import com.company.onlinestore.ThreeDeeAddOnServiceImpl;

public class ThreeDeeAddOnServiceImplTest {

	public static ThreeDeeAddOnServiceImpl threeDeeAddOnServiceImpl;

	@BeforeClass
	public static void setUpAvailabilityChecker() {

		// Create ThreeDeeAddOnServiceImpl to be tested and set up a mock
		// AvailabilityChecker.
		AvailabilityChecker availabilityChecker = new AvailabilityCheckerMockImpl();
		threeDeeAddOnServiceImpl = new ThreeDeeAddOnServiceImpl(
				availabilityChecker);

	}

	/**
	 * Acceptance Criteria#1
	 * 
	 * PostCode - Currently in a 3D area 
	 * Basket - 3D compatible products in the basket 
	 * Result - Return relevant 3D addons
	 * 
	 */
	@Test
	public void testThreeDeeAddOnServiceImplCriteria1() {

		String postcode = AvailabilityCheckerMockImpl.TEST_SERVICE_AVAILABLE_POSTCODE;
		
		// Create 3D AddOns.
		AddOnProduct sports3DAddOn = new AddOnProduct("SPORTS_3D_ADD_ON");
		AddOnProduct news3DAddOn = new AddOnProduct("NEWS_3D_ADD_ON");
		AddOnProduct movies3DAddOn = new AddOnProduct("MOVIES_3D_ADD_ON");

		// Create standard products and add them to the Basket.
		Basket basket = new Basket();
		basket.add(new EntertainmentProduct("KIDS"));
		basket.add(new EntertainmentProduct("VARIETY"));

		// Create and add 3D compatible products to the Basket.
		basket.add(new EntertainmentProduct("SPORTS", sports3DAddOn));
		basket.add(new EntertainmentProduct("NEWS", news3DAddOn));
		basket.add(new EntertainmentProduct("MOVIES_1", movies3DAddOn));
		basket.add(new EntertainmentProduct("MOVIES_2", movies3DAddOn));

		try {
			//Execute the method to be tested.
			Set<AddOnProduct> result = threeDeeAddOnServiceImpl
					.checkFor3DAddOnProducts(basket, postcode);
			
			//Assert that the result contains the 3D AddOns.
			assertTrue("Expected sports3DAddOn in the result",
					result.contains(sports3DAddOn));
			assertTrue("Expected news3DAddOn in the result",
					result.contains(news3DAddOn));
			assertTrue("Expected movies3DAddOn in the result",
					result.contains(movies3DAddOn));

		} catch (PostCodeInvalidException e) {
			fail("Caught PostCodeInvalidException");
		}

	}

	/**
	 * Acceptance Criteria#2
	 * 
	 * PostCode - NA 
	 * Basket - No products with 3D addons 
	 * Result - Return no 3D addons
	 * 
	 */
	@Test
	public void testThreeDeeAddOnServiceImplCriteria2() {

		String postcode = AvailabilityCheckerMockImpl.TEST_SERVICE_AVAILABLE_POSTCODE;
		
		// Create 3D AddOns.
		AddOnProduct sports3DAddOn = new AddOnProduct("SPORTS_3D_ADD_ON");
		AddOnProduct news3DAddOn = new AddOnProduct("NEWS_3D_ADD_ON");
		AddOnProduct movies3DAddOn = new AddOnProduct("MOVIES_3D_ADD_ON");

		// Create standard products and add them to the Basket.
		Basket basket = new Basket();
		basket.add(new EntertainmentProduct("KIDS"));
		basket.add(new EntertainmentProduct("VARIETY"));

		try {
			//Execute the method to be tested.
			Set<AddOnProduct> result = threeDeeAddOnServiceImpl
					.checkFor3DAddOnProducts(basket, postcode);
							
			//Assert that the result does NOT contain the 3D AddOns.
			assertFalse("Does not expect sports3DAddOn in the result",
					result.contains(sports3DAddOn));
			assertFalse("Does not expect news3DAddOn in the result",
					result.contains(news3DAddOn));
			assertFalse("Does not expect movies3DAddOn in the result",
					result.contains(movies3DAddOn));

		} catch (PostCodeInvalidException e) {
			fail("Caught PostCodeInvalidException");
		}

	}

	/**
	 * Acceptance Criteria#3
	 * 
	 * PostCode - Currently, not in a 3D area, or technical failure occurs
	 * Basket - 3D compatible products in the basket 
	 * Result - Return no 3D addons
	 * 
	 */
	@Test
	public void testThreeDeeAddOnServiceImplCriteria3() {
		
		String postcode = AvailabilityCheckerMockImpl.TEST_SERVICE_UNAVAILABLE_POSTCODE;

		// Create 3D AddOns.
		AddOnProduct sports3DAddOn = new AddOnProduct("SPORTS_3D_ADD_ON");
		AddOnProduct news3DAddOn = new AddOnProduct("NEWS_3D_ADD_ON");
		AddOnProduct movies3DAddOn = new AddOnProduct("MOVIES_3D_ADD_ON");

		// Create standard products and add them to the Basket.
		Basket basket = new Basket();
		basket.add(new EntertainmentProduct("KIDS"));
		basket.add(new EntertainmentProduct("VARIETY"));

		// Create and add 3D compatible products to the Basket.
		basket.add(new EntertainmentProduct("SPORTS", sports3DAddOn));
		basket.add(new EntertainmentProduct("NEWS", news3DAddOn));
		basket.add(new EntertainmentProduct("MOVIES_1", movies3DAddOn));
		basket.add(new EntertainmentProduct("MOVIES_2", movies3DAddOn));

		// Test with an unavailable PostCode
		try {
			//Execute the method to be tested.
			Set<AddOnProduct> result = threeDeeAddOnServiceImpl
					.checkFor3DAddOnProducts(basket, postcode);
			
			//Assert that the result does NOT contain the 3D AddOns.
			assertFalse("Does not expect sports3DAddOn in the result",
					result.contains(sports3DAddOn));
			assertFalse("Does not expect news3DAddOn in the result",
					result.contains(news3DAddOn));
			assertFalse("Does not expect movies3DAddOn in the result",
					result.contains(movies3DAddOn));

		} catch (PostCodeInvalidException e) {
			fail("Caught PostCodeInvalidException");
		}

		
		// Test with a planned PostCode
		postcode = AvailabilityCheckerMockImpl.TEST_SERVICE_PLANNED_POSTCODE;
		try {
			//Execute the method to be tested.
			Set<AddOnProduct> result = threeDeeAddOnServiceImpl
					.checkFor3DAddOnProducts(basket, postcode);
			
			//Assert that the result does NOT contain the 3D AddOns.
			assertFalse("Does not expect sports3DAddOn in the result",
					result.contains(sports3DAddOn));
			assertFalse("Does not expect news3DAddOn in the result",
					result.contains(news3DAddOn));
			assertFalse("Does not expect movies3DAddOn in the result",
					result.contains(movies3DAddOn));
			
		} catch (PostCodeInvalidException e) {
			fail("Caught PostCodeInvalidException");
		}

	}

	/**
	 * Acceptance Criteria#4
	 * 
	 * PostCode - Invalid 
	 * Basket - 3D compatible products in the basket 
	 * Result - Return no 3D addons and notify the client that the 
	 * 			postcode was invalid
	 * 
	 * The Client is notified by an Exception that the postcode was invalid.
	 * 
	 */
	@Test(expected = PostCodeInvalidException.class)
	public void testThreeDeeAddOnServiceImplCriteria4() throws Exception {

		String postcode = AvailabilityCheckerMockImpl.TEST_POSTCODE_INVALID;

		// Create standard products and add them to the Basket.
		Basket basket = new Basket();
		basket.add(new EntertainmentProduct("KIDS"));
		basket.add(new EntertainmentProduct("VARIETY"));

		// Execute the method to be tested.
		threeDeeAddOnServiceImpl.checkFor3DAddOnProducts(basket, postcode);

	}

}
