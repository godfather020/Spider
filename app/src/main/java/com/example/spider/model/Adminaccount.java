package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Adminaccount {

    @SerializedName("name")
    
    private String name;
    @SerializedName("accountdetail")
    
    private List<Accountdetail> accountdetail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Accountdetail> getAccountdetail() {
        return accountdetail;
    }

    public void setAccountdetail(List<Accountdetail> accountdetail) {
        this.accountdetail = accountdetail;
    }
}


