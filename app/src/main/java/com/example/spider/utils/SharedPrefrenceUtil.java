package com.example.spider.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.AnyRes;

import com.example.spider.model.UserDetail;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SharedPrefrenceUtil {


    private  SharedPreferences.Editor editor;




    public void addObjectInJsonString(SharedPreferences sp, String key, Object object, Class objectClass){

        editor=sp.edit();
        String gsonString= new GsonBuilder().create().toJson(object,objectClass);
        editor.putString(key,gsonString);
        editor.apply();
    }
    public Object getObjectFromJsonString(SharedPreferences sp,String key,Class objectClass){

        Object object=new Object();
        String data = sp.getString(key,"");

        if(data.isEmpty()){
            object=null;
        }else
            object=new GsonBuilder().create().fromJson(data,objectClass);
        
        
        return object;
    }

    public void addString(SharedPreferences sp, String key ,String value){
        editor=sp.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public String getString(SharedPreferences sp,String key){

        return sp.getString(key,null);
    }

    public void addBoolean(SharedPreferences sp, String key ,boolean value){
        editor=sp.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public boolean getBoolean(SharedPreferences sp,String key){

        return sp.getBoolean(key,false);
    }

    public void addInteger(SharedPreferences sp, String key ,Integer value){
        editor=sp.edit();
        editor.putInt(key,value);
        editor.apply();
    }
    public Integer getInteger(SharedPreferences sp,String key){

        return sp.getInt(key,-1);
    }

    public void clearSharedPrefrence(SharedPreferences sp){

        editor=sp.edit();
        editor.clear();
        editor.apply();
    }
}
