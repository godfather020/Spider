package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Adminremark implements Serializable {

    @SerializedName("description")
    private String description;

    @SerializedName("photo")
    private String photo;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
