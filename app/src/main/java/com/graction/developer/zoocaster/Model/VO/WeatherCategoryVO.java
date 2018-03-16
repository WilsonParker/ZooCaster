package com.graction.developer.zoocaster.Model.VO;

public class WeatherCategoryVO {
	private int weatherCategory_index				// WeatherCategory primary code
				, weatherCategory_icon_ref_index	// Reference FileInformation_index
				;
	private String weatherCategory_name			// Weather name
					, weatherCategory_message		// Weather message
					, weatherCategory_icon_path
					, weatherCategory_icon_name
					;
	public int getWeatherCategory_index() {
		return weatherCategory_index;
	}
	public void setWeatherCategory_index(int weatherCategory_index) {
		this.weatherCategory_index = weatherCategory_index;
	}
	public int getWeatherCategory_icon_ref_index() {
		return weatherCategory_icon_ref_index;
	}
	public void setWeatherCategory_icon_ref_index(int weatherCateogry_icon_ref_index) {
		this.weatherCategory_icon_ref_index = weatherCateogry_icon_ref_index;
	}
	public String getWeatherCategory_name() {
		return weatherCategory_name;
	}
	public void setWeatherCategory_name(String weatherCategory_name) {
		this.weatherCategory_name = weatherCategory_name;
	}
	public String getWeatherCategory_message() {
		return weatherCategory_message;
	}
	public void setWeatherCategory_message(String weatherCategory_message) {
		this.weatherCategory_message = weatherCategory_message;
	}
	public String getWeatherCategory_icon_path() {
		return weatherCategory_icon_path;
	}
	public void setWeatherCategory_icon_path(String weatherCategory_icon_path) {
		this.weatherCategory_icon_path = weatherCategory_icon_path;
	}
	public String getWeatherCategory_icon_name() {
		return weatherCategory_icon_name;
	}
	public void setWeatherCategory_icon_name(String weatherCategory_icon_name) {
		this.weatherCategory_icon_name = weatherCategory_icon_name;
	}
	@Override
	public String toString() {
		return "WeatherCategoryVO [weatherCategory_index=" + weatherCategory_index + ", weatherCateogry_icon_ref_index="
				+ weatherCategory_icon_ref_index + ", weatherCategory_name=" + weatherCategory_name
				+ ", weatherCategory_message=" + weatherCategory_message + ", weatherCategory_icon_path="
				+ weatherCategory_icon_path + ", weatherCategory_icon_name=" + weatherCategory_icon_name + "]";
	}
	
}
