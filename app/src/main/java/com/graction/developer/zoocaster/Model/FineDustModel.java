package com.graction.developer.zoocaster.Model;

public class FineDustModel {
	private Double value;
	private String grade;
	
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "FineDustModel{" +
				"value=" + value +
				", grade='" + grade + '\'' +
				'}';
	}
}
