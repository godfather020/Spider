package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

public class UserDetail {

    @SerializedName("name")
    String name;
    @SerializedName("id")
    private String id;
    @SerializedName("email")
    String email;
    @SerializedName("contact")
    private String contact;
    @SerializedName("created_date")
    private String createdDate;
    @SerializedName("role")
    private String role;


    public UserDetail() {
    }

    public UserDetail(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public UserDetail(String name, String id, String email, String contact, String createdDate, String role) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.contact = contact;
        this.createdDate = createdDate;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
