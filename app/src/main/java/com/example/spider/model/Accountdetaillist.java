package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Accountdetaillist {


    @SerializedName("Bank Transfer")
    
    private List<Accountdetail> bankTransfer = null;
    @SerializedName("Phone Pe")
    
    private List<Accountdetail> phonePay = null;
    @SerializedName("Google Pay")
    
    private List<Accountdetail> googlePay = null;
    @SerializedName("Paytm Wallet")
    
    private List<Accountdetail> paytmWallet = null;
    @SerializedName("Paytm UPI")
    
    private List<Accountdetail> paytmUPI = null;

    public List<Accountdetail> getBankTransfer() {
        return bankTransfer;
    }

    public void setBankTransfer(List<Accountdetail> bankTransfer) {
        this.bankTransfer = bankTransfer;
    }

    public List<Accountdetail> getPhonePay() {
        return phonePay;
    }

    public void setPhonePay(List<Accountdetail> phonePay) {
        this.phonePay = phonePay;
    }

    public List<Accountdetail> getGooglePay() {
        return googlePay;
    }

    public void setGooglePay(List<Accountdetail> googlePay) {
        this.googlePay = googlePay;
    }

    public List<Accountdetail> getPaytmWallet() {
        return paytmWallet;
    }

    public void setPaytmWallet(List<Accountdetail> paytmWallet) {
        this.paytmWallet = paytmWallet;
    }

    public List<Accountdetail> getPaytmUPI() {
        return paytmUPI;
    }

    public void setPaytmUPI(List<Accountdetail> paytmUPI) {
        this.paytmUPI = paytmUPI;
    }

}
