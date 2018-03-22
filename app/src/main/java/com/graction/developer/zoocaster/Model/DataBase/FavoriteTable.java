package com.graction.developer.zoocaster.Model.DataBase;

import com.graction.developer.zoocaster.DataBase.SqlIgnore;

/**
 * Created by JeongTaehyun on 2018-01-25.
 */

/*
 * 즐겨찾기 Table
 */

public class FavoriteTable {
    public static final String TIME_FORMAT = "yyyy-MM-dd";
    private String favorite_origin_address, favorite_new_address, favorite_set_time;

    public FavoriteTable() {
    }

    public FavoriteTable(String favorite_origin_address, String favorite_new_address, String favorite_set_time) {
        this.favorite_origin_address = favorite_origin_address;
        this.favorite_new_address = favorite_new_address;
        this.favorite_set_time = favorite_set_time;
    }

    public String getFavorite_origin_address() {
        return favorite_origin_address;
    }

    public void setFavorite_origin_address(String favorite_origin_address) {
        this.favorite_origin_address = favorite_origin_address;
    }

    public String getFavorite_new_address() {
        return favorite_new_address;
    }

    public void setFavorite_new_address(String favorite_new_address) {
        this.favorite_new_address = favorite_new_address;
    }

    public String getFavorite_set_time() {
        return favorite_set_time;
    }

    public void setFavorite_set_time(String favorite_set_time) {
        this.favorite_set_time = favorite_set_time;
    }

    @Override
    public String toString() {
        return "FavoriteTable{" +
                "favorite_origin_address='" + favorite_origin_address + '\'' +
                ", favorite_new_address='" + favorite_new_address + '\'' +
                ", favorite_set_time='" + favorite_set_time + '\'' +
                '}';
    }
}
