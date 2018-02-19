package com.graction.developer.zoocaster.Model.Weather;

public class Clouds {
	private long all;	// Cloudiness, %

	public long getAll() {
		return all;
	}

	public void setAll(long all) {
		this.all = all;
	}

	@Override
	public String toString() {
		return "Clouds [all=" + all + "]";
	}

}
