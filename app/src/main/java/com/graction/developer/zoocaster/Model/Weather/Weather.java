package com.graction.developer.zoocaster.Model.Weather;

public class Weather {
	private int id;				// Weather condition id
	private String main			// Group of weather parameters (Rain, Snow, Extreme etc)
				, description	// Weather condition within the group
				, icon;			// Weather icon id

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "Weather [id=" + id + ", main=" + main + ", description=" + description + ", icon=" + icon + "]";
	}

}
