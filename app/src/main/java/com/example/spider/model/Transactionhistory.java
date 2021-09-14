package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Transactionhistory implements Serializable {


    @SerializedName("id")
    private String id;

    @SerializedName("referenceno")
    private String referenceno;

    @SerializedName("loginusername")
    private String loginusername;

    @SerializedName("userid")
    private String userid;

    @SerializedName("mainid")
    private String mainid;

    @SerializedName("title")
    private String title;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

    @SerializedName("type")
    private String type;

    @SerializedName("coins")
    private String coins;

    @SerializedName("username")
    private String username;

    @SerializedName("notification_date")
    private String notificationDate;

    @SerializedName("detail_data")
    private List<TransactionDataDetail> detailData = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public String getLoginusername() {
        return loginusername;
    }

    public void setLoginusername(String loginusername) {
        this.loginusername = loginusername;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public List<TransactionDataDetail> getDetailData() {
        return detailData;
    }

    public void setDetailData(List<TransactionDataDetail> detailData) {
        this.detailData = detailData;
    }
}
