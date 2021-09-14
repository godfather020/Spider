package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Accountdetail implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    @SerializedName("display")
    private String display;

    @SerializedName("accountno")
    
    private String accountNo;
    @SerializedName("accountholder")
    
    private String accountholder;
    @SerializedName("bankname")
    
    private String bankname;
    @SerializedName("ifsc")
    
    private String ifsc;
    @SerializedName("branch")


    private String branch;
    @SerializedName("accounttype")
    
    private String bankType;

    @SerializedName("displayname")
    
    private String displayname;
    @SerializedName("number")
    
    private String number;

 @SerializedName("paymentmethodid")

    private String paymentmethodid;
 @SerializedName("paymentmethod")

    private String paymentmethod;

 @SerializedName("preferstatus")

    private String preferstatus;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountholder() {
        return accountholder;
    }

    public void setAccountholder(String accountholder) {
        this.accountholder = accountholder;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

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

    public String getPreferstatus() {
        return preferstatus;
    }

    public void setPreferstatus(String preferstatus) {
        this.preferstatus = preferstatus;
    }
}
