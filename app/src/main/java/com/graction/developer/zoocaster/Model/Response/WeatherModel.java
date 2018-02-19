package com.graction.developer.zoocaster.Model.Response;


import com.graction.developer.zoocaster.Model.AddressItem;
import com.graction.developer.zoocaster.Model.FineDustModel;
import com.graction.developer.zoocaster.Model.ImageModel;
import com.graction.developer.zoocaster.Model.Weather.Clouds;
import com.graction.developer.zoocaster.Model.Weather.Coord;
import com.graction.developer.zoocaster.Model.Weather.Main;
import com.graction.developer.zoocaster.Model.Weather.Sys;
import com.graction.developer.zoocaster.Model.Weather.Weather;
import com.graction.developer.zoocaster.Model.Weather.Wind;

import java.util.ArrayList;

/*
 * Current weather model
 */
public class WeatherModel {
	private static final String RESOURCE_URL = "http://192.168.0.8:8101/zoocasterAssets";
	private Coord coord;
	private ArrayList<Weather> weather;
	private Main main;
	private Wind wind;
	private Clouds clouds;
	private Sys sys;
	private String base		// Internal parameter
					, name	//	City Mane
					;

	private long dt			// Time of data calculation, unix, UTC
				, id;		// City ID
	private int cod;		// Internal parameter

	private FineDustModel fineDustModel;
	private AddressItem addressModel;
	private ImageModel imageModel;

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public ArrayList<Weather> getWeather() {
		return weather;
	}

	public Weather getFirstWeather() {
		return weather.get(0);
	}

	public void setWeather(ArrayList<Weather> weather) {
		this.weather = weather;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public Clouds getClouds() {
		return clouds;
	}

	public void setClouds(Clouds clouds) {
		this.clouds = clouds;
	}

	public Sys getSys() {
		return sys;
	}

	public void setSys(Sys sys) {
		this.sys = sys;
	}

	public long getDt() {
		return dt;
	}

	public void setDt(long dt) {
		this.dt = dt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getBackground_img_url() {
		return RESOURCE_URL+imageModel.getBackground_img_path()+imageModel.getBackground_img_name();
	}

	public String getCharacter_img_url() {
		return RESOURCE_URL+imageModel.getCharacter_img_path()+imageModel.getCharacter_img_name();
	}

	public String getEffect_img_url() {
		return RESOURCE_URL+imageModel.getEffect_img_path()+imageModel.getEffect_img_name();
	}

	public FineDustModel getFineDustModel() {
		return fineDustModel;
	}

	public void setFineDustModel(FineDustModel fineDustModel) {
		this.fineDustModel = fineDustModel;
	}

	public AddressItem getAddressModel() {
		return addressModel;
	}

	public void setAddressModel(AddressItem addressModel) {
		this.addressModel = addressModel;
	}

	public ImageModel getImageModel() {
		return imageModel;
	}

	public void setImageModel(ImageModel imageModel) {
		this.imageModel = imageModel;
	}

	@Override
	public String toString() {
		return "WeatherModel{" +
				"coord=" + coord +
				", weather=" + weather +
				", main=" + main +
				", wind=" + wind +
				", clouds=" + clouds +
				", sys=" + sys +
				", base='" + base + '\'' +
				", name='" + name + '\'' +
				", dt=" + dt +
				", id=" + id +
				", cod=" + cod +
				", fineDustModel=" + fineDustModel +
				", addressModel=" + addressModel +
				", imageModel=" + imageModel +
				'}';
	}
}
