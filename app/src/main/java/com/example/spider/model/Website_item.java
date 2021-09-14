package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Website_item implements Serializable {

    @SerializedName("id")

    private String id;
    @SerializedName("name")

    private String name;
    @SerializedName("url")

    private String url;
    @SerializedName("description")

    private String description;
    @SerializedName("photo")

    private String photo;
    @SerializedName("games")

    private List<Game> games = null;

    @SerializedName("minrefil")
    private String minrefil;

    @SerializedName("minmaintainbal")
    private String minmaintainbal;

    @SerializedName("minwithdraw")
    private String minwithdraw;

    @SerializedName("maxwithdraw")
    private String maxwithdraw;





    public Website_item(String name) {
        this.name = name;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMinrefil() {
        return minrefil;
    }

    public void setMinrefil(String minrefil) {
        this.minrefil = minrefil;
    }

    public String getMinmaintainbal() {
        return minmaintainbal;
    }

    public void setMinmaintainbal(String minmaintainbal) {
        this.minmaintainbal = minmaintainbal;
    }

    public String getMinwithdraw() {
        return minwithdraw;
    }

    public void setMinwithdraw(String minwithdraw) {
        this.minwithdraw = minwithdraw;
    }

    public String getMaxwithdraw() {
        return maxwithdraw;
    }

    public void setMaxwithdraw(String maxwithdraw) {
        this.maxwithdraw = maxwithdraw;
    }
}
