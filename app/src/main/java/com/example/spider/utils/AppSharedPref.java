package com.example.spider.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.spider.model.UserDetail;

public class AppSharedPref {

    private SharedPreferences sp;
    private Context context;
    private String KEY_USER_DATA="key_user_data";

    public AppSharedPref(Context context) {
        this.context = context;
        sp=context.getSharedPreferences("My_Prefrence",Context.MODE_PRIVATE);
    }


    public void saveUserData(UserDetail userDetail){

        new SharedPrefrenceUtil().addObjectInJsonString(sp,KEY_USER_DATA,userDetail,UserDetail.class);
    }

    public UserDetail getUserData(){

        UserDetail userDetail= (UserDetail) new SharedPrefrenceUtil().getObjectFromJsonString(sp,KEY_USER_DATA,UserDetail.class);
        return userDetail;
    }

    public void saveString(String key,String value){

        new SharedPrefrenceUtil().addString(sp,key,value);
    }

    public String getString(String key){

        return  new SharedPrefrenceUtil().getString(sp,key);
    }
    public void saveBoolean(String key,boolean value){

        new SharedPrefrenceUtil().addBoolean(sp,key,value);
    }

    public Boolean getBoolean(String key){

        return  new SharedPrefrenceUtil().getBoolean(sp,key);
    }
    public void clearData(){

        new SharedPrefrenceUtil().clearSharedPrefrence(sp);
    }

    public void saveInteger(String key,Integer value){

        new SharedPrefrenceUtil().addInteger(sp,key,value);
    }

    public Integer getInteger(String key){

        return  new SharedPrefrenceUtil().getInteger(sp,key);
    }
}
