package com.example.spider.view_model;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spider.MainActivity;
import com.example.spider.api.RetrofitInstance;
import com.example.spider.api.RetrofitInterface;
import com.example.spider.databinding.PaymentMethodFragmentBinding;
import com.example.spider.databinding.WithdrawFragmentBinding;
import com.example.spider.databinding.WithdrawIdFragmentBinding;
import com.example.spider.model.DATA;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.Pymentmethod;
import com.example.spider.model.Walletlist;
import com.example.spider.model.Website_item;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.Constant;
import com.example.spider.utils.ExtentionUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Withdraw_id_Fragment_viewModel extends ViewModel {

    private String TAG ="Home_Fragment_viewModel";

    private MutableLiveData<String> withdraw=new MutableLiveData<>();

   MainActivity activity;
   WithdrawIdFragmentBinding binding;
   RetrofitInterface retrofitInterface;
    ExtentionUtils extentionUtils=new ExtentionUtils();



    public LiveData<String> withdraw(MainActivity activity, WithdrawIdFragmentBinding binding, String coins, String accId, String websiteId){

        this.activity=activity;
        this.binding=binding;

        initiate();

        DATA data=new DATA();


            data.setUserid(new AppSharedPref(activity).getUserData().getId());
            data.setRequestid(websiteId);
            data.setCoins(coins);
            data.setPay_wallet("1");
            data.setPaymentmethodid("");


        Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_WITHDRAW,data);
        String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
        Log.d(TAG,jsonBody);
        RequestBody param=extentionUtils.toRequestBody(jsonBody);



        sendDataForWithdraw(param);





        return withdraw;
    }

    public void  sendDataForWithdraw(RequestBody param){


        Call<com.example.spider.model.Response> call =retrofitInterface.getLogin(param);

        call.enqueue(new Callback<com.example.spider.model.Response>() {
            @Override
            public void onResponse(Call<com.example.spider.model.Response> call, Response<com.example.spider.model.Response> response) {

                if(response.isSuccessful()){

                    withdraw.setValue(response.body().getMsgCode().toString());
                    extentionUtils.showToast(activity,response.body().getMessage());

                }else {
                    withdraw.setValue(null);
                    binding.progressBar.setVisibility(View.GONE);
                    extentionUtils.showToast(activity,response.body().getMessage());
                }


            }

            @Override
            public void onFailure(Call<com.example.spider.model.Response> call, Throwable t) {
//                Log.e("loginResponse","error=== "+t.getMessage().toString());

                extentionUtils.showToast(activity,t.getMessage());
                binding.progressBar.setVisibility(View.GONE);

            }
        });
    }

    void initiate(){
retrofitInterface=RetrofitInstance.getmRetrofitInstance().create(RetrofitInterface.class);


    }



}
