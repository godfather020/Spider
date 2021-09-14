package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TransactionDataDetail implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String username;

    @SerializedName("userid")
    private String userid;

    @SerializedName("coins")
    private String coins;

    @SerializedName("withdrawdetail")
    private List<Accountdetail> withdrawdetail = null;

    @SerializedName("paymentmethodid")
    private String paymentmethodid;

    @SerializedName("paymentscreenshot")
    private String paymentscreenshot;

    @SerializedName("paymentmethod")
    private String paymentmethod;

    @SerializedName("status")
    private String status;

    @SerializedName("created_date")
    private String createdDate;

    @SerializedName("websitename")
    private String websitename;

    @SerializedName("websiteurl")
    private String websiteurl;

    @SerializedName("websitephoto")
    private String websitephoto;

    @SerializedName("acceptrejectdate")
    private String acceptrejectdate;

    @SerializedName("closeiddate")
    private String closeiddate;

    @SerializedName("requestdate")
    private String requestdate;

    @SerializedName("adminremark")
    private List<Adminremark> adminremark = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public List<Accountdetail> getWithdrawdetail() {
        return withdrawdetail;
    }

    public void setWithdrawdetail(List<Accountdetail> withdrawdetail) {
        this.withdrawdetail = withdrawdetail;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getWebsitename() {
        return websitename;
    }

    public void setWebsitename(String websitename) {
        this.websitename = websitename;
    }

    public String getWebsiteurl() {
        return websiteurl;
    }

    public void setWebsiteurl(String websiteurl) {
        this.websiteurl = websiteurl;
    }

    public String getWebsitephoto() {
        return websitephoto;
    }

    public void setWebsitephoto(String websitephoto) {
        this.websitephoto = websitephoto;
    }

    public String getPaymentscreenshot() {
        return paymentscreenshot;
    }

    public void setPaymentscreenshot(String paymentscreenshot) {
        this.paymentscreenshot = paymentscreenshot;
    }

    public String getAcceptrejectdate() {
        return acceptrejectdate;
    }

    public void setAcceptrejectdate(String acceptrejectdate) {
        this.acceptrejectdate = acceptrejectdate;
    }

    public List<Adminremark> getAdminremark() {
        return adminremark;
    }

    public void setAdminremark(List<Adminremark> adminremark) {
        this.adminremark = adminremark;
    }

    public String getCloseiddate() {
        return closeiddate;
    }

    public void setCloseiddate(String closeiddate) {
        this.closeiddate = closeiddate;
    }

    public String getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(String requestdate) {
        this.requestdate = requestdate;
    }
}
