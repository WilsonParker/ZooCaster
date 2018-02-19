package com.graction.developer.zoocaster.Model.Xml;

public class Weather {
    private int id;
    private String message, image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
