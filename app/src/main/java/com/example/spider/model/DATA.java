package com.example.spider.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DATA implements Serializable {

    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("deviceid")
    String deviceid;
    @SerializedName("devicetype")
    String devicetype;
    @SerializedName("devicetoken")
    String devicetoken;

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("contact")
    private String contact;
    @SerializedName("created_date")
    private String createdDate;
    @SerializedName("role")
    private String role;
    @SerializedName("uid")
    private String uid;


 @SerializedName("username")
    private String username;
 @SerializedName("userid")
    private String userid;
 @SerializedName("websiteid")
    private String websiteid;
 @SerializedName("coins")
    private String coins;
 @SerializedName("paymentmethod")
    private String paymentmethod;
 @SerializedName("paymentscreenshot")
    private String paymentscreenshot;

@SerializedName("accountno")
    private String accountno;
@SerializedName("bankname")
    private String bankname;
@SerializedName("ifsc")
    private String ifsc;
@SerializedName("branch")
    private String branch;
    @SerializedName("accountid")
    private String accountid;
    @SerializedName("accountholder")
    private String accountholder;
 @SerializedName("accounttype")
    private String accounttype;


@SerializedName("displayname")
    private String displayname;
@SerializedName("number")
    private String number;
@SerializedName("paymentmethodid")
    private String paymentmethodid;

@SerializedName("status")
    private String status;
@SerializedName("requestid")
    private String requestid;

@SerializedName("pay_wallet")
    private String pay_wallet;

@SerializedName("totalbalanceless")
    private String totalbalanceless;
@SerializedName("noactivebets")
    private String noactivebets;
@SerializedName("withdraw")
    private String withdraw;
@SerializedName("reason")
    private String reason;
@SerializedName("otherissue")
    private String otherissue;


@SerializedName("description")
    private String description;
@SerializedName("remarkphoto")
    private String remarkphoto;


@SerializedName("notificationid")
    private String notificationid;
@SerializedName("mainid")
    private String mainid;
@SerializedName("remark")
    private String remark;
@SerializedName("type")
    private String type;
@SerializedName("code")
    private String code;

@SerializedName("codeid")
    private String codeid;

@SerializedName("reward")
    private String reward;

@SerializedName("otp")
    private String otp;




    public DATA() {
    }
    public DATA(String contact) {

        this.contact = contact;

    }
    public DATA(@Nullable String uid,@Nullable String deviceid,@Nullable String id) {
        this.uid = uid;
        this.deviceid = deviceid;
        this.id = id;

    }
    public DATA(String username, String userid, String websiteid, String coins, String paymentmethod, String paymentscreenshot) {
        this.username = username;
        this.userid = userid;
        this.websiteid = websiteid;
        this.coins = coins;
        this.paymentmethod = paymentmethod;
        this.paymentscreenshot = paymentscreenshot;
    }

    public DATA(String name, String contact, String password, String role) {
        this.password = password;
        this.name = name;
        this.contact = contact;
        this.role = role;
    }

    public DATA(String email, String password, String deviceid, String devicetype, String devicetoken) {
        this.email = email;
        this.password = password;
        this.deviceid = deviceid;
        this.devicetype = devicetype;
        this.devicetoken = devicetoken;
    }





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;

    }
    public DATA setEmailData(String email) {
        this.email = email;
        return null;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public String getDevicetoken() {
        return devicetoken;
    }

    public void setDevicetoken(String devicetoken) {
        this.devicetoken = devicetoken;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getWebsiteid() {
        return websiteid;
    }

    public void setWebsiteid(String websiteid) {
        this.websiteid = websiteid;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getPaymentscreenshot() {
        return paymentscreenshot;
    }

    public void setPaymentscreenshot(String paymentscreenshot) {
        this.paymentscreenshot = paymentscreenshot;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }
    public DATA setAccountnoData(String accountno) {
        this.accountno = accountno;
        return null;
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

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountholder() {
        return accountholder;
    }

    public void setAccountholder(String accountholder) {
        this.accountholder = accountholder;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getPay_wallet() {
        return pay_wallet;
    }

    public void setPay_wallet(String pay_wallet) {
        this.pay_wallet = pay_wallet;
    }

    public String getTotalbalanceless() {
        return totalbalanceless;
    }

    public void setTotalbalanceless(String totalbalanceless) {
        this.totalbalanceless = totalbalanceless;
    }

    public String getNoactivebets() {
        return noactivebets;
    }

    public void setNoactivebets(String noactivebets) {
        this.noactivebets = noactivebets;
    }

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOtherissue() {
        return otherissue;
    }

    public void setOtherissue(String otherissue) {
        this.otherissue = otherissue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemarkphoto() {
        return remarkphoto;
    }

    public void setRemarkphoto(String remarkphoto) {
        this.remarkphoto = remarkphoto;
    }

    public String getNotificationid() {
        return notificationid;
    }

    public void setNotificationid(String notificationid) {
        this.notificationid = notificationid;
    }

    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeid() {
        return codeid;
    }

    public void setCodeid(String codeid) {
        this.codeid = codeid;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
