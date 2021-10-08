package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private DATA data;

    @SerializedName("msg_code")
    private Integer msgCode;

    @SerializedName("otp")
    private Integer otp;

    @SerializedName("websitelist")
    private List<Website_item> websitelist = null;

    @SerializedName("pymentmethodlist")
    private List<Pymentmethod> pymentmethodlist = null;

    @SerializedName("adminpaymentmethodlist")
    private List<Pymentmethod> adminpaymentmethodlist = null;

    @SerializedName("adminaccountlist")
    private List<Adminaccount> adminaccountlist = null;

    @SerializedName("bannerlist")
    private List<Banner> bannerlist = null;

    @SerializedName("approvedrequestidlist")
    private List<Approvedrequestidlist> approvedrequestidlist = null;

    @SerializedName("walletlist")
    private Walletlist walletlist;

    @SerializedName("accountlist")
    private List<Account> accountlist = null;

    @SerializedName("transactionhistorylist")
    private List<Transactionhistory> transactionhistorylist = null;

    @SerializedName("websitedetail")
    private List<Websitedetail> websitedetail = null;

    @SerializedName("referalcode")
    private List<Referalcode> referalcode = null;

    @SerializedName("codeid")
    private String codeid ;

    @SerializedName("referaldata")
    private List<Referaldatum> referaldata = null;

    @SerializedName("totaluser")
    private Integer totaluser;

    @SerializedName("offerlist")
    private List<Offer> offerlist = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DATA getData() {
        return data;
    }

    public void setData(DATA data) {
        this.data = data;
    }

    public Integer getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(Integer msgCode) {
        this.msgCode = msgCode;
    }

    public List<Website_item> getWebsitelist() {
        return websitelist;
    }

    public void setWebsitelist(List<Website_item> websitelist) {
        this.websitelist = websitelist;
    }

    public List<Pymentmethod> getPymentmethodlist() {
        return pymentmethodlist;
    }

    public void setPymentmethodlist(List<Pymentmethod> pymentmethodlist) {
        this.pymentmethodlist = pymentmethodlist;
    }

    public List<Adminaccount> getAdminaccountlist() {
        return adminaccountlist;
    }

    public void setAdminaccountlist(List<Adminaccount> adminaccountlist) {
        this.adminaccountlist = adminaccountlist;
    }

    public List<Banner> getBannerlist() {
        return bannerlist;
    }

    public void setBannerlist(List<Banner> bannerlist) {
        this.bannerlist = bannerlist;
    }

    public List<Approvedrequestidlist> getApprovedrequestidlist() {
        return approvedrequestidlist;
    }

    public void setApprovedrequestidlist(List<Approvedrequestidlist> approvedrequestidlist) {
        this.approvedrequestidlist = approvedrequestidlist;
    }

    public Walletlist getWalletlist() {
        return walletlist;
    }

    public void setWalletlist(Walletlist walletlist) {
        this.walletlist = walletlist;
    }

    public List<Account> getAccountlist() {
        return accountlist;
    }

    public void setAccountlist(List<Account> accountlist) {
        this.accountlist = accountlist;
    }

    public List<Transactionhistory> getTransactionhistorylist() {
        return transactionhistorylist;
    }

    public void setTransactionhistorylist(List<Transactionhistory> transactionhistorylist) {
        this.transactionhistorylist = transactionhistorylist;
    }

    public List<Websitedetail> getWebsitedetail() {
        return websitedetail;
    }

    public void setWebsitedetail(List<Websitedetail> websitedetail) {
        this.websitedetail = websitedetail;
    }

    public List<Referalcode> getReferalcode() {
        return referalcode;
    }

    public void setReferalcode(List<Referalcode> referalcode) {
        this.referalcode = referalcode;
    }

    public String getCodeid() {
        return codeid;
    }

    public void setCodeid(String codeid) {
        this.codeid = codeid;
    }

    public List<Referaldatum> getReferaldata() {
        return referaldata;
    }

    public void setReferaldata(List<Referaldatum> referaldata) {
        this.referaldata = referaldata;
    }

    public Integer getTotaluser() {
        return totaluser;
    }

    public void setTotaluser(Integer totaluser) {
        this.totaluser = totaluser;
    }

    public List<Offer> getOfferlist() {
        return offerlist;
    }

    public void setOfferlist(List<Offer> offerlist) {
        this.offerlist = offerlist;
    }

    public List<Pymentmethod> getAdminpaymentmethodlist() {
        return adminpaymentmethodlist;
    }

    public void setAdminpaymentmethodlist(List<Pymentmethod> adminpaymentmethodlist) {
        this.adminpaymentmethodlist = adminpaymentmethodlist;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }
}
