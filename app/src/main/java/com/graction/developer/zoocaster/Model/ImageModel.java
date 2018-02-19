package com.graction.developer.zoocaster.Model;

public class ImageModel {
	private String background_img_path
		, background_img_name
		, character_img_path, character_img_name
		, effect_img_path, effect_img_name
		;

	public String getBackground_img_path() {
		return background_img_path;
	}

	public void setBackground_img_path(String background_img_path) {
		this.background_img_path = background_img_path;
	}

	public String getBackground_img_name() {
		return background_img_name;
	}

	public void setBackground_img_name(String background_img_name) {
		this.background_img_name = background_img_name;
	}

	public String getCharacter_img_path() {
		return character_img_path;
	}

	public void setCharacter_img_path(String character_img_path) {
		this.character_img_path = character_img_path;
	}

	public String getCharacter_img_name() {
		return character_img_name;
	}

	public void setCharacter_img_name(String character_img_name) {
		this.character_img_name = character_img_name;
	}

	public String getEffect_img_path() {
		return effect_img_path;
	}

	public void setEffect_img_path(String effect_img_path) {
		this.effect_img_path = effect_img_path;
	}

	public String getEffect_img_name() {
		return effect_img_name;
	}

	public void setEffect_img_name(String effect_img_name) {
		this.effect_img_name = effect_img_name;
	}

	public String getBackground_img_url() {
		return background_img_path + background_img_name;
	}

	public String getCharacter_img_url() {
		return character_img_path + character_img_name;
	}

	public String getEffect_img_url() {
		return effect_img_path + effect_img_name;
	}

	@Override
	public String toString() {
		return "ImageModel{" +
				"background_img_path='" + background_img_path + '\'' +
				", background_img_name='" + background_img_name + '\'' +
				", character_img_path='" + character_img_path + '\'' +
				", character_img_name='" + character_img_name + '\'' +
				", effect_img_path='" + effect_img_path + '\'' +
				", effect_img_name='" + effect_img_name + '\'' +
				'}';
	}
}
