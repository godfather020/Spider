package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Approvedrequestidlist implements Serializable {

    @SerializedName("id")
    
    private String id;
    @SerializedName("websitename")
    
    private String websitename;
    @SerializedName("paymentmethod")
    
    private String paymentmethod;
    @SerializedName("loginusername")
    
    private String loginusername;
    @SerializedName("username")
    
    private String username;
    @SerializedName("coins")
    
    private String coins;
    @SerializedName("status")
    
    private String status;
    @SerializedName("paymentscreenshot")
    
    private String paymentscreenshot;

    @SerializedName("websitephoto")

    private String websitephoto;

    @SerializedName("websiteurl")

    private String websiteurl;

    @SerializedName("minrefil")
    private String minrefil;

    @SerializedName("minmaintainbal")
    private String minmaintainbal;

    @SerializedName("minwithdraw")
    private String minwithdraw;

    @SerializedName("maxwithdraw")
    private String maxwithdraw;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWebsitename() {
        return websitename;
    }

    public void setWebsitename(String websitename) {
        this.websitename = websitename;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getLoginusername() {
        return loginusername;
    }

    public void setLoginusername(String loginusername) {
        this.loginusername = loginusername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentscreenshot() {
        return paymentscreenshot;
    }

    public void setPaymentscreenshot(String paymentscreenshot) {
        this.paymentscreenshot = paymentscreenshot;
    }

    public String getWebsitephoto() {
        return websitephoto;
    }

    public void setWebsitephoto(String websitephoto) {
        this.websitephoto = websitephoto;
    }

    public String getWebsiteurl() {
        return websiteurl;
    }

    public void setWebsiteurl(String websiteurl) {
        this.websiteurl = websiteurl;
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
