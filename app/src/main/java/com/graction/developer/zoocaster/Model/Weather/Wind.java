package com.graction.developer.zoocaster.Model.Weather;

public class Wind {
	private double speed	// Wind speed, Unit Default: meter/sec, Metricr: meer/sec, Imperial: miles/hour
				, deg;		 // Wind direction, degrees (meteorological)

	public double getDeg() {
		return deg;
	}

	public void setDeg(double deg) {
		this.deg = deg;
	}

	@Override
	public String toString() {
		return "Wind [speed=" + speed + ", deg=" + deg + "]";
	}

}
