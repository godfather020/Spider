package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

public class Referaldatum {

    @SerializedName("referalid")
    private String referalid;

    @SerializedName("code")
    private String code;

    @SerializedName("reward")
    private String reward;

    @SerializedName("username")
    private String username;

    @SerializedName("referdate")
    private String referdate;

    @SerializedName("uid")
    private String uid;

    @SerializedName("totaluser")
    private String totaluser;

    public String getReferalid() {
        return referalid;
    }

    public void setReferalid(String referalid) {
        this.referalid = referalid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReferdate() {
        return referdate;
    }

    public void setReferdate(String referdate) {
        this.referdate = referdate;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTotaluser() {
        return totaluser;
    }

    public void setTotaluser(String totaluser) {
        this.totaluser = totaluser;
    }
}
