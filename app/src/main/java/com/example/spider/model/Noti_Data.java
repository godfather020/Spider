package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Noti_Data implements Serializable {
    @SerializedName("sub_title")
    
    private String subTitle;
    @SerializedName("type")
    
    private String type;
    @SerializedName("image")
    
    private String image;
    @SerializedName("title")
    
    private String title;
    @SerializedName("click_action")
    
    private String clickAction;
    @SerializedName("message")
    
    private String message;
    @SerializedName("activitydata")
    
    private String activitydata;

    public Noti_Data(JSONObject jsonObject) {

        try {
            this.subTitle=jsonObject.getString("sub_title");
            this.type=jsonObject.getString("type");
            this.image=jsonObject.getString("image");
            this.title=jsonObject.getString("title");
            this.clickAction=jsonObject.getString("click_action");
            this.message=jsonObject.getString("message");
            this.activitydata=jsonObject.getString("activitydata");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClickAction() {
        return clickAction;
    }

    public void setClickAction(String clickAction) {
        this.clickAction = clickAction;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getActivitydata() {
        return activitydata;
    }

    public void setActivitydata(String activitydata) {
        this.activitydata = activitydata;
    }
}
