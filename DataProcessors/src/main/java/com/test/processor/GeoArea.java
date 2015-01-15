package com.test.processor;

/**
 * A class defining the space dimension to contain the unit-records.
 * @author Milind
 *
 */
public class GeoArea {
	String geoHash;
	Integer geoHashPrecision;

	public GeoArea(Integer geoHashPrecision) {
		this.geoHashPrecision = geoHashPrecision;
	}
	
	public GeoArea(String geoHash, Integer geoHashPrecision) {		
		this.geoHashPrecision = geoHashPrecision;
		this.setGeoHash(geoHash);
	}
	
	public String getGeoHash() {
		return geoHash;
	}
	public void setGeoHash(String geoHash) {
		if(geoHash.length()==geoHashPrecision){
			this.geoHash = geoHash;
		}else{
			throw new RuntimeException("Geohash not within precision.");
		}
	}
	public Integer getGeoHashPrecision() {
		return geoHashPrecision;
	}
	public void setGeoHashPrecision(Integer geoHashPrecision) {
		this.geoHashPrecision = geoHashPrecision;
	}
}
