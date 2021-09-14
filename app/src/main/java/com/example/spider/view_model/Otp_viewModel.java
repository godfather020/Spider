package com.example.spider.view_model;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spider.api.RetrofitInstance;
import com.example.spider.api.RetrofitInterface;
import com.example.spider.model.DATA;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.Response;
import com.example.spider.model.UserDetail;
import com.example.spider.ui.activity.Otp_Activity;
import com.example.spider.utils.Constant;
import com.example.spider.utils.ExtentionUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Otp_viewModel extends ViewModel {

    String TAG="Otp_viewModel";

   private  MutableLiveData<Boolean>userExist=new MutableLiveData<>();

   Otp_Activity activity;
   ExtentionUtils extentionUtils =new ExtentionUtils();
   RetrofitInterface retrofitInterface = RetrofitInstance.getmRetrofitInstance().create(RetrofitInterface.class);

    public LiveData<Boolean> checkPhoneNo(Otp_Activity activity,String mob_no){

        this.activity=activity;

        activity.binding.progressBar.setVisibility(View.VISIBLE);
        if(mobNo_Validation(mob_no)){

            Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_CHECK_MOBILE_NO,
                    new DATA(mob_no));
            String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
            Log.d(TAG,jsonBody);
            RequestBody param=extentionUtils.toRequestBody(jsonBody);
            loadUsers(param);
        }else {

            activity.binding.progressBar.setVisibility(View.GONE);
            ExtentionUtils extentionUtils=new ExtentionUtils();

            extentionUtils.showToast(activity,"Invalid Phone No.");


        }


       return userExist;
    }

    public boolean mobNo_Validation(String mob_no){

        return !mob_no.isEmpty()
                && Patterns.PHONE.matcher(mob_no).matches()
                && mob_no.length()==10;
    }
// && Patterns.EMAIL_ADDRESS.matcher(mob_no).matches()
    public void  loadUsers(RequestBody param){

        Call<Response> call =retrofitInterface.check_mobile_no(param);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<com.example.spider.model.Response> call, retrofit2.Response<Response> response) {

                if(response.body().getMsgCode()==1){

                    userExist.setValue(true);
                }else {
                    userExist.setValue(false);
                    activity.binding.progressBar.setVisibility(View.GONE);
//                    extentionUtils.showToast(context,response.body().getMessage());
                }


            }

            @Override
            public void onFailure(Call<com.example.spider.model.Response> call, Throwable t) {
//                Log.e("loginResponse","error=== "+t.getMessage().toString());
                extentionUtils.showToast(activity,t.getMessage());
                activity.binding.progressBar.setVisibility(View.GONE);

            }
        });
    }

}
