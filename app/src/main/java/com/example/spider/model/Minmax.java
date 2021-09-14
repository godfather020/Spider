package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

public class Minmax {

    @SerializedName("id")
    private String id;

    @SerializedName("minrefil")
    private Object minrefil;

    @SerializedName("minmaintainbal")
    private Object minmaintainbal;

    @SerializedName("minwithdraw")
    private Object minwithdraw;

    @SerializedName("maxwithdraw")
    private Object maxwithdraw;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getMinrefil() {
        return minrefil;
    }

    public void setMinrefil(Object minrefil) {
        this.minrefil = minrefil;
    }

    public Object getMinmaintainbal() {
        return minmaintainbal;
    }

    public void setMinmaintainbal(Object minmaintainbal) {
        this.minmaintainbal = minmaintainbal;
    }

    public Object getMinwithdraw() {
        return minwithdraw;
    }

    public void setMinwithdraw(Object minwithdraw) {
        this.minwithdraw = minwithdraw;
    }

    public Object getMaxwithdraw() {
        return maxwithdraw;
    }

    public void setMaxwithdraw(Object maxwithdraw) {
        this.maxwithdraw = maxwithdraw;
    }
}
