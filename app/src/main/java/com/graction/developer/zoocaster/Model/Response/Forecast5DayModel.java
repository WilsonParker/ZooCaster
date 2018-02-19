package com.graction.developer.zoocaster.Model.Response;


import com.graction.developer.zoocaster.Model.Weather.City;
import com.graction.developer.zoocaster.Model.Weather.Clouds;
import com.graction.developer.zoocaster.Model.Weather.Main;
import com.graction.developer.zoocaster.Model.Weather.Rain;
import com.graction.developer.zoocaster.Model.Weather.Sys;
import com.graction.developer.zoocaster.Model.Weather.Weather;
import com.graction.developer.zoocaster.Model.Weather.Wind;
import com.graction.developer.zoocaster.Util.StringUtil;

import java.util.ArrayList;

public class Forecast5DayModel {
	private int cod // Internal parameter
			, cnt; // Number of lines returned by this API call
	private String message; // Internal parameter
	private ArrayList<ForecasttDayItem> list;
	private City city;


	class ForecasttDayItem {
		private long dt; // Time of data forecasted
		private String dt_txt;
		private Main main;
		private Clouds clouds;
		private Wind wind;
		private Rain rain;
		private Sys sys;
		private ArrayList<Weather> weather;

		public long getDt() {
			return dt;
		}

		public void setDt(long dt) {
			this.dt = dt;
		}

		public String getDt_txt() {
			return dt_txt;
		}

		public void setDt_txt(String dt_txt) {
			this.dt_txt = dt_txt;
		}

		public ArrayList<Weather> getWeather() {
			return weather;
		}

		public void setWeather(ArrayList<Weather> weather) {
			this.weather = weather;
		}

		public Weather getFirstWeather() {
			return weather.get(0);
		}

		public Main getMain() {
			return main;
		}

		public void setMain(Main main) {
			this.main = main;
		}

		public Clouds getClouds() {
			return clouds;
		}

		public void setClouds(Clouds clouds) {
			this.clouds = clouds;
		}

		public Wind getWind() {
			return wind;
		}

		public void setWind(Wind wind) {
			this.wind = wind;
		}

		public Rain getRain() {
			return rain;
		}

		public void setRain(Rain rain) {
			this.rain = rain;
		}

		public Sys getSys() {
			return sys;
		}

		public void setSys(Sys sys) {
			this.sys = sys;
		}

		@Override
		public String toString() {
			return "ForecasttDayItem [dt=" + dt + ", dt_txt=" + dt_txt + ", main=" + main + ", clouds=" + clouds
					+ ", wind=" + wind + ", rain=" + rain + ", sys=" + sys + ", "+ StringUtil.createString("weather", weather) + "]";
		}

	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<ForecasttDayItem> getList() {
		return list;
	}

	public void setList(ArrayList<ForecasttDayItem> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "Forecast5DayModel [cod=" + cod + ", cnt=" + cnt + ", message=" + message + ", " + StringUtil.createString("list", list) + ", city="
				+ city + "]";
	}
}
