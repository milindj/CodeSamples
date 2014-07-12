/**
 * 
 */
package com.company.onlinestore;

/**
 * Pseudo representation of an Entertainment product, which stores the
 * productCode and the respective compatible AddOn.
 * 
 * @author Milind J.
 */
public class EntertainmentProduct {

	private String productCode;
	private AddOnProduct compatibleAddOnProduct;

	/**
	 * Constructor to initialise a standard Entertainment product.
	 * 
	 * @param productCode
	 */
	public EntertainmentProduct(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Constructor to initialise an entertainment product and specify it's
	 * compatibleAddOn.
	 * 
	 * @param productCode
	 * @param compatibleAddOnProduct
	 */
	public EntertainmentProduct(String productCode,
			AddOnProduct compatibleAddOnProduct) {
		this.productCode = productCode;
		this.compatibleAddOnProduct = compatibleAddOnProduct;
	}

	/**
	 * 
	 * @return Returns the compatible addOn product.
	 */
	public AddOnProduct getCompatibleAddOnProduct() {
		return compatibleAddOnProduct;
	}

	/**
	 * Sets the compatible addOn product.
	 * 
	 * @param compatibleAddOnProduct
	 */
	public void setCompatibleAddOnProduct(AddOnProduct compatibleAddOnProduct) {
		this.compatibleAddOnProduct = compatibleAddOnProduct;
	}

	/**
	 * Gets the entertainment product code.
	 * 
	 * @return
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the entertainment product code.
	 * 
	 * @param productCode
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}
