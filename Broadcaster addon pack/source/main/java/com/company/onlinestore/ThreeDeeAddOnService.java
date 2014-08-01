package com.company.onlinestore;

import java.util.Set;

/**
 * 3DAddOnServicce Interface.
 * 
 */
public interface ThreeDeeAddOnService {
	/**
	 * @param basket
	 *            The basket containing Entertainment products
	 * @param postCode
	 *            The postcode of the client where the service is requested.
	 * @return Set of {@link AddOnProduct}s
	 * @throws PostCodeInvalidException
	 *             Notify the client if the post code is invalid.
	 */
	public Set<AddOnProduct> checkFor3DAddOnProducts(Basket basket,
			String postCode) throws PostCodeInvalidException;
}