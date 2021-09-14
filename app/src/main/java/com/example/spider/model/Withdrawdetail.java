package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Withdrawdetail implements Serializable {

    @SerializedName("displayname")
    private String displayname;

    @SerializedName("number")
    private String number;

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
