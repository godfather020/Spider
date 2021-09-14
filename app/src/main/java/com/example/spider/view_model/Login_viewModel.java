package com.example.spider.view_model;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spider.api.RetrofitInstance;
import com.example.spider.api.RetrofitInterface;
import com.example.spider.model.DATA;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.UserDetail;
import com.example.spider.ui.activity.Login_Activity;
import com.example.spider.utils.Constant;
import com.example.spider.utils.ExtentionUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_viewModel extends ViewModel {

   private MutableLiveData<List<UserDetail>> userDetail;
   private  List <UserDetail> detail=new ArrayList<>();
   Login_Activity activity;
   RetrofitInterface retrofitInterface;
    ExtentionUtils extentionUtils=new ExtentionUtils();

    public LiveData<List<UserDetail>> userLogin(Login_Activity activity, String mob_no, String password,String token,String deviceId){

        this.activity=activity;
        activity.binding.progressBar.setVisibility(View.VISIBLE);
        userDetail = new MutableLiveData<List<UserDetail>>();
        initiate();
        if(LoginValidation(mob_no,password)){

            Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_LOGIN,new DATA(mob_no,
                    password,
                    deviceId,
                    "android",
                    token));
            String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
            Log.d("loginResponse",jsonBody);
            RequestBody param=extentionUtils.toRequestBody(jsonBody);

//            if (userDetail == null) {

                loadUsers(param);
//            }
//            detail.clear();
//            detail.add(new UserDetail("Savan","8839891738"));
//            userDetail.setValue(detail);

        }else {


            activity.binding.progressBar.setVisibility(View.GONE);
            extentionUtils.showToast(activity,"Invalid User & Password");


        }


       return userDetail;
    }

    public LiveData<List<UserDetail>> userOtpLogin(Login_Activity activity, String mob_no, String password,String token,String deviceId){

        this.activity=activity;
        activity.binding.progressBar.setVisibility(View.VISIBLE);
        userDetail = new MutableLiveData<List<UserDetail>>();
        initiate();

        DATA data=new DATA();

        data.setContact(mob_no);
        data.setDevicetype("android");
        data.setDeviceid(deviceId);
        data.setDevicetoken(token);

            Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_OTP_LOGIN,data);
            String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
            Log.d("loginResponse",jsonBody);
            RequestBody param=extentionUtils.toRequestBody(jsonBody);

//            if (userDetail == null) {

            loadUsers(param);
//            }
//            detail.clear();
//            detail.add(new UserDetail("Savan","8839891738"));
//            userDetail.setValue(detail);




        return userDetail;
    }



    public boolean LoginValidation(String mob_no,String password){

        return !mob_no.isEmpty()
                && mob_no.length()==10
                && !password.isEmpty();
    }
// && Patterns.EMAIL_ADDRESS.matcher(mob_no).matches()
    public void  loadUsers(RequestBody param){

        detail.clear();
//        detail.add(new UserDetail("Savan","8839891738"));
//        userDetail.setValue(detail);

        Call<com.example.spider.model.Response> call =retrofitInterface.getLogin(param);

        call.enqueue(new Callback<com.example.spider.model.Response>() {
            @Override
            public void onResponse(Call<com.example.spider.model.Response> call, Response<com.example.spider.model.Response> response) {

                if(response.body().getMsgCode()==1){
                detail.add(new UserDetail(response.body().getData().getName(),response.body().getData().getId(),response.body().getData().getEmail()
                        ,response.body().getData().getContact(),response.body().getData().getCreatedDate(),response.body().getData().getRole()));
                userDetail.setValue(detail);
                }else {
                    userDetail.setValue(null);
                    activity.binding.progressBar.setVisibility(View.GONE);
                    extentionUtils.showToast(activity,response.body().getMessage());
                }


            }

            @Override
            public void onFailure(Call<com.example.spider.model.Response> call, Throwable t) {
//                Log.e("loginResponse","error=== "+t.getMessage().toString());
                activity.binding.progressBar.setVisibility(View.GONE);
                extentionUtils.showToast(activity,t.getMessage());

            }
        });
    }

    void initiate(){
retrofitInterface=RetrofitInstance.getmRetrofitInstance().create(RetrofitInterface.class);


    }

}
