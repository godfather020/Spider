package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Account {

    @SerializedName("id")
     private String id;

    @SerializedName("uid")
    private String uid;

    @SerializedName("username")
    private String username;

    @SerializedName("paymentmethodid")
    private String paymentmethodid;

    @SerializedName("paymentmethod")
    private String paymentmethod;

    @SerializedName("status")
    private String status;

    @SerializedName("accountdetail")
    private Accountdetaillist accountdetail = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPaymentmethodid() {
        return paymentmethodid;
    }

    public void setPaymentmethodid(String paymentmethodid) {
        this.paymentmethodid = paymentmethodid;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Accountdetaillist getAccountdetail() {
        return accountdetail;
    }

    public void setAccountdetail(Accountdetaillist accountdetail) {
        this.accountdetail = accountdetail;
    }
}
