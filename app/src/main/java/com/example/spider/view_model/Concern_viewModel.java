package com.example.spider.view_model;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spider.MainActivity;
import com.example.spider.api.RetrofitInstance;
import com.example.spider.api.RetrofitInterface;
import com.example.spider.databinding.ConcernFragmentBinding;
import com.example.spider.databinding.PaymentFragmentBinding;
import com.example.spider.model.Adminaccount;
import com.example.spider.model.DATA;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.Response;
import com.example.spider.model.Transactionhistory;
import com.example.spider.model.UserDetail;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.Constant;
import com.example.spider.utils.ExtentionUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Concern_viewModel extends ViewModel {

   private MutableLiveData<String> userDetail=new MutableLiveData<String>();
    private MutableLiveData<String> raiseConcern=new MutableLiveData<String>();

   private ConcernFragmentBinding binding;
   MainActivity activity;
    RetrofitInterface retrofitInterface= RetrofitInstance.getmRetrofitInstance().create(RetrofitInterface.class);
    ExtentionUtils extentionUtils=new ExtentionUtils();


    public MutableLiveData<String> cancelWithdraw(MainActivity activity, ConcernFragmentBinding binding,String id,String reason,String image){

        this.activity=activity;
        this.binding=binding;


        DATA data=new DATA();


        data.setId(id);
        data.setDescription(reason);
        data.setRemarkphoto(image);
//            if (userDetail == null) {

            Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_WITHDRAW_CANCEL,data);
            String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
            Log.d("concern",jsonBody);
            RequestBody param=extentionUtils.toRequestBody(jsonBody);

            cancelWithdraw(param);
//            }
//            detail.clear();
//            detail.add(new UserDetail("Savan","8839891738"));
//            userDetail.setValue(detail);




       return userDetail;
    }
    public MutableLiveData<String> raiseConcern(MainActivity activity, ConcernFragmentBinding binding, String id, String reason, String image, Transactionhistory transactionhistory,
                                                boolean comeFromMenu){

        this.activity=activity;
        this.binding=binding;


        DATA data=new DATA();

        if(comeFromMenu){

            data.setRemark(reason);
            data.setRemarkphoto(image);
            data.setType("general");
            data.setNotificationid("0");
            data.setUid(new AppSharedPref(activity).getUserData().getId());
            data.setMainid("0");

        }else {

            data.setRemark(reason);
            data.setRemarkphoto(image);
            data.setType(transactionhistory.getType());
            data.setNotificationid(transactionhistory.getId());
            data.setUid(transactionhistory.getUserid());
            data.setMainid(transactionhistory.getMainid());
        }
//            if (userDetail == null) {

        Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_RAISE_CONCERN,data);
        String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
        Log.d("concern",jsonBody);
        RequestBody param=extentionUtils.toRequestBody(jsonBody);

        raiseConcern(param);
//            }
//            detail.clear();
//            detail.add(new UserDetail("Savan","8839891738"));
//            userDetail.setValue(detail);




        return raiseConcern;
    }




    public void  cancelWithdraw(RequestBody param){

        Call<Response> call =retrofitInterface.createId(param);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                Log.d("concern",response.body().toString());
                if(response.body().getMsgCode()==1){

                    userDetail.setValue(response.body().getMsgCode().toString());
                    extentionUtils.showToast(activity,response.body().getMessage());
                }else {
                    binding.progressBar.setVisibility(View.GONE);
                    userDetail.setValue(response.body().getMsgCode().toString());
                    extentionUtils.showToast(activity,response.body().getMessage());
                }


            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
//                Log.e("loginResponse","error=== "+t.getMessage().toString());
                binding.progressBar.setVisibility(View.GONE);
                extentionUtils.showToast(activity,t.getMessage());

            }
        });
    }
    public void  raiseConcern(RequestBody param){

        Call<Response> call =retrofitInterface.createId(param);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                Log.d("concern",response.body().toString());
                if(response.body().getMsgCode()==1){

                    raiseConcern.setValue(response.body().getMsgCode().toString());
                    extentionUtils.showToast(activity,response.body().getMessage());
                }else {
                    binding.progressBar.setVisibility(View.GONE);
                    raiseConcern.setValue(response.body().getMsgCode().toString());
                    extentionUtils.showToast(activity,response.body().getMessage());
                }


            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
//                Log.e("loginResponse","error=== "+t.getMessage().toString());
                binding.progressBar.setVisibility(View.GONE);
                extentionUtils.showToast(activity,t.getMessage());

            }
        });
    }



}
