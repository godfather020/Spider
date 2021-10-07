package com.example.spider.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Noticedatum implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("notice")
    private String notice;

    @SerializedName("supportno")
    private String supportno;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getSupportno() {
        return supportno;
    }

    public void setSupportno(String supportno) {
        this.supportno = supportno;
    }
}
