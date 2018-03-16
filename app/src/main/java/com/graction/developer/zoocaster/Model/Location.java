package com.graction.developer.zoocaster.Model;

/**
 * Created by Graction06 on 2018-03-16.
 */

public class Location {
    private String lat, lng;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Location [lat=" + lat + ", lng=" + lng + "]";
    }

}