package com.graction.developer.zoocaster.Model.VO;


import com.graction.developer.zoocaster.Model.ImageModel;

/*
    Open Data API
	등급	좋음	보통	나쁨	매우나쁨
	Grade 값	1	2	3	4
*/
public class FineDustVO extends ImageModel {
    private int fineDust_index, fineDust_background_ref_file_index, fineDust_character_ref_file_index;
    private double fineDust_min, fineDust_max;
    private String fineDust_grade, fineDust_activation, fineDustType_activation, fineDust_color, fineDust_message, fineDust_type;

    public int getFineDust_index() {
        return fineDust_index;
    }

    public void setFineDust_index(int fineDust_index) {
        this.fineDust_index = fineDust_index;
    }

    public double getFineDust_min() {
        return fineDust_min;
    }

    public void setFineDust_min(int fineDust_min) {
        this.fineDust_min = fineDust_min;
    }

    public double getFineDust_max() {
        return fineDust_max;
    }

    public void setFineDust_max(int fineDust_max) {
        this.fineDust_max = fineDust_max;
    }

    public int getFineDust_background_ref_file_index() {
        return fineDust_background_ref_file_index;
    }

    public void setFineDust_background_ref_file_index(int fineDust_background_ref_file_index) {
        this.fineDust_background_ref_file_index = fineDust_background_ref_file_index;
    }

    public int getFineDust_character_ref_file_index() {
        return fineDust_character_ref_file_index;
    }

    public void setFineDust_character_ref_file_index(int fineDust_character_ref_file_index) {
        this.fineDust_character_ref_file_index = fineDust_character_ref_file_index;
    }

    public String getFineDust_grade() {
        return fineDust_grade;
    }

    public void setFineDust_grade(String fineDust_grade) {
        this.fineDust_grade = fineDust_grade;
    }

    public String getFineDust_activation() {
        return fineDust_activation;
    }

    public void setFineDust_activation(String fineDust_activation) {
        this.fineDust_activation = fineDust_activation;
    }

    public String getFineDust_color() {
        return fineDust_color;
    }

    public void setFineDust_color(String fineDust_color) {
        this.fineDust_color = fineDust_color;
    }

    public String getFineDust_message() {
        return fineDust_message;
    }

    public void setFineDust_message(String fineDust_message) {
        this.fineDust_message = fineDust_message;
    }

    public String getFineDustType_activation() {
        return fineDustType_activation;
    }

    public void setFineDustType_activation(String fineDustType_activation) {
        this.fineDustType_activation = fineDustType_activation;
    }

    public String getFineDust_type() {
        return fineDust_type;
    }

    public void setFineDust_type(String fineDust_type) {
        this.fineDust_type = fineDust_type;
    }

    public String getStringGrade(String grade) {
        String result = "보통";
        switch (grade) {
            case "1":
                result = "좋음";
                break;
            case "2":
                result = "보통";
                break;
            case "3":
                result = "나쁨";
                break;
            case "4":
                result = "매우나쁨";
                break;
        }
        return result;
    }
}
