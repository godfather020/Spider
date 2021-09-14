package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

public class Referalcode {

    @SerializedName("id")
    private String id;

    @SerializedName("code")
    private String code;

    @SerializedName("username")
    private String username;

    @SerializedName("reward")
    private String reward;

    @SerializedName("uid")
    private String uid;

    @SerializedName("status")
    private String status;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
