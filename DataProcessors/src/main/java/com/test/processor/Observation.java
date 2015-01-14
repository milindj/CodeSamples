package com.test.processor;

import java.util.Date;

public class Observation {
	private Integer price;

	String siteUUID; // UUID of the site of the observation
	String observationUUID;
	Double observedValue;
	String observedUnits;
	private Date observationTime;
	private GeoLocation location;

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getSiteUUID() {
		return siteUUID;
	}

	public void setSiteUUID(String siteUUID) {
		this.siteUUID = siteUUID;
	}

	public String getObservationUUID() {
		return observationUUID;
	}

	public void setObservationUUID(String observationUUID) {
		this.observationUUID = observationUUID;
	}

	public Double getObservedValue() {
		return observedValue;
	}

	public void setObservedValue(Double observedValue) {
		this.observedValue = observedValue;
	}

	public String getObservedUnits() {
		return observedUnits;
	}

	public void setObservedUnits(String observedUnits) {
		this.observedUnits = observedUnits;
	}

	public Date getObservationTime() {
		return observationTime;
	}

	public void setObservationTime(Date observationTime) {
		this.observationTime = observationTime;
	}

	public GeoLocation getLocation() {
		return location;
	}

	public void setLocation(GeoLocation location) {
		this.location = location;
	}

	public class GeoLocation {

		private Double latitude, longitude;

		public Double getLongitude() {
			return longitude;
		}

		public void setLongitude(Double longitude) {
			this.longitude = longitude;
		}

		public Double getLatitude() {
			return latitude;
		}

		public void setLatitude(Double latitude) {
			this.latitude = latitude;
		}

	}
}
