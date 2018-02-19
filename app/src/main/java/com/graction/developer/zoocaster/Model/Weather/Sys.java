package com.graction.developer.zoocaster.Model.Weather;

public class Sys {
	private double message;	// Internal parameter
	private long sunrise	// Sunrise time, unix, UTC
				, sunset	// Sunset time, unix, UTC
				, type		// Internal parameter
				, id		// Internal parameter
				;
	private String country;	// Country code (GB, JP etc.)

	public double getMessage() {
		return message;
	}

	public void setMessage(double message) {
		this.message = message;
	}

	public long getSunrise() {
		return sunrise;
	}

	public void setSunrise(long sunrise) {
		this.sunrise = sunrise;
	}

	public long getSunset() {
		return sunset;
	}

	public void setSunset(long sunset) {
		this.sunset = sunset;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Sys [message=" + message + ", sunrise=" + sunrise + ", sunset=" + sunset + ", type=" + type + ", id="
				+ id + ", country=" + country + "]";
	}

}
