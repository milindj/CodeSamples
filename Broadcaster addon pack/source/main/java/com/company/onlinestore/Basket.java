package com.company.onlinestore;

import java.util.HashSet;
import java.util.Set;

/**
 * Basket to store the Entertainment pack/products.
 * 
 * @author Milind J.
 */
public class Basket {

	private Set<EntertainmentProduct> basketContents;

	/**
	 * Initialise the basket contents.
	 */
	public Basket() {
		basketContents = new HashSet<EntertainmentProduct>();
	}

	/**
	 * Retrieves the basket contents, which are entertainment products.
	 * 
	 * @return
	 */
	public Set<EntertainmentProduct> getBasketContents() {
		return basketContents;
	}

	/**
	 * Add an entertainment product to the Basket.
	 * 
	 * @param product
	 */
	public void add(EntertainmentProduct product) {
		basketContents.add(product);
	}

	/**
	 * Remove an entertainment product from the Basket.
	 * 
	 * @param productCode
	 */
	public void remove(EntertainmentProduct productCode) {
		basketContents.remove(productCode);
	}

}
