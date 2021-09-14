package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

public class Banner {

    @SerializedName("id")
   
    private String id;
    @SerializedName("name")
   
    private String name;
    @SerializedName("url")
   
    private String url;
    @SerializedName("photo")
   
    private String photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
