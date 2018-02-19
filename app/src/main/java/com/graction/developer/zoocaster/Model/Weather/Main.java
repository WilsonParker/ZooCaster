package com.graction.developer.zoocaster.Model.Weather;

public class Main {
	private double temp			// Temperature, Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit
				, pressure		// Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
				, temp_min		// Minimum temperature at the moment, This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: kelvin, Metric: Celsius, Imperial: Fahrenheit  
				, temp_max		// Maximum temperature at the moment, This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: kelvin, Metric: Celsius, Imperial: Fahrenheit
				, sea_level		// Atmospheric pressure on the sea level, hPa
				, grnd_level;	// Atmospheric pressure on the ground level, hPa
	private int humidity;		// Humidity, %

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public double getTemp_min() {
		return temp_min;
	}

	public void setTemp_min(double temp_min) {
		this.temp_min = temp_min;
	}

	public double getTemp_max() {
		return temp_max;
	}

	public void setTemp_max(double temp_max) {
		this.temp_max = temp_max;
	}

	public double getSea_level() {
		return sea_level;
	}

	public void setSea_level(double sea_level) {
		this.sea_level = sea_level;
	}

	public double getGrnd_level() {
		return grnd_level;
	}

	public void setGrnd_level(double grnd_level) {
		this.grnd_level = grnd_level;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	@Override
	public String toString() {
		return "Main [temp=" + temp + ", pressure=" + pressure + ", temp_min=" + temp_min + ", temp_max=" + temp_max
				+ ", sea_level=" + sea_level + ", grnd_level=" + grnd_level + ", humidity=" + humidity + "]";
	}

}
