package com.graction.developer.zoocaster.Model.Weather;

public class City {

	private long id;
	private int population;
	private String name, country;
	private Coord coord;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", population=" + population + ", name=" + name + ", country=" + country + ", coord="
				+ coord + "]";
	}

}
