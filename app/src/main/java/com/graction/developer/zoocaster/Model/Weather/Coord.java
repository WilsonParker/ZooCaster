package com.graction.developer.zoocaster.Model.Weather;

public class Coord {
	private double lon		// City geo location, longitude
				, lat;		// City geo location, latitude

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	@Override
	public String toString() {
		return "Coord [lon=" + lon + ", lat=" + lat + "]";
	}

}
