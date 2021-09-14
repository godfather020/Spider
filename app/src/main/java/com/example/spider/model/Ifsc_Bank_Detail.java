package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ifsc_Bank_Detail implements Serializable {

    @SerializedName("ADDRESS")
    
    private String address;
    @SerializedName("SWIFT")
    
    private String swift;
    @SerializedName("CONTACT")
    
    private String contact;
    @SerializedName("CENTRE")
    
    private String centre;
    @SerializedName("BRANCH")
    
    private String branch;
    @SerializedName("RTGS")
    
    private Boolean rtgs;
    @SerializedName("IMPS")
    
    private Boolean imps;
    @SerializedName("MICR")
    
    private String micr;
    @SerializedName("STATE")
    
    private String state;
    @SerializedName("CITY")
    
    private String city;
    @SerializedName("NEFT")
    
    private Boolean neft;
    @SerializedName("DISTRICT")
    
    private String district;
    @SerializedName("UPI")
    
    private Boolean upi;
    @SerializedName("BANK")
    
    private String bank;
    @SerializedName("BANKCODE")
    
    private String bankcode;
    @SerializedName("IFSC")
    
    private String ifsc;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCentre() {
        return centre;
    }

    public void setCentre(String centre) {
        this.centre = centre;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Boolean getRtgs() {
        return rtgs;
    }

    public void setRtgs(Boolean rtgs) {
        this.rtgs = rtgs;
    }

    public Boolean getImps() {
        return imps;
    }

    public void setImps(Boolean imps) {
        this.imps = imps;
    }

    public String getMicr() {
        return micr;
    }

    public void setMicr(String micr) {
        this.micr = micr;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getNeft() {
        return neft;
    }

    public void setNeft(Boolean neft) {
        this.neft = neft;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Boolean getUpi() {
        return upi;
    }

    public void setUpi(Boolean upi) {
        this.upi = upi;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }
}
