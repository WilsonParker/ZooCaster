package com.graction.developer.zoocaster.Model.Request;

import java.util.HashMap;
import java.util.Map;

public class WeatherRequestModel {
	private static final String KEY_APPID = "appid"
								, KEY_LAT = "lat"
								, KEY_LON = "lon"
								;
	private Map<String ,String> map;

	public WeatherRequestModel(String lat, String lon) {
		map = new HashMap<String, String>();
		map.put(KEY_LAT, lat);
		map.put(KEY_LON, lon);
	}

	public void setLat(String lat) {
		map.put(KEY_LAT, lat);
	}
	
	public void setLon(String lon) {
		map.put(KEY_LON, lon);
	}
	
	public Map<String, String> getMap() {
		return map;
	}

}
