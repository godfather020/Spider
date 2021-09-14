package com.example.spider.view_model;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spider.MainActivity;
import com.example.spider.api.RetrofitInstance;
import com.example.spider.api.RetrofitInterface;
import com.example.spider.databinding.PaymentDetailFragmentBinding;
import com.example.spider.databinding.PaymentMethodFragmentBinding;
import com.example.spider.model.Account;
import com.example.spider.model.DATA;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.Pymentmethod;
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
import retrofit2.Response;

public class Paymetn_Detail_viewModel extends ViewModel {

   private MutableLiveData<List<Pymentmethod>> userDetail=new MutableLiveData<List<Pymentmethod>>();;
   private MutableLiveData<List<Account>> accDetail=new MutableLiveData<List<Account>>();
   private  List <UserDetail> detail=new ArrayList<>();
   private MutableLiveData<String> delete_msg=new MutableLiveData<String>();
   private MutableLiveData<String> preferred_msg=new MutableLiveData<String>();
   MainActivity activity;
   String TAG="Paymetn_Method_viewModel";
   RetrofitInterface retrofitInterface;
   PaymentDetailFragmentBinding binding;
    ExtentionUtils extentionUtils=new ExtentionUtils();

    public LiveData<List<Pymentmethod>> getPaymetMethodList(MainActivity activity, PaymentDetailFragmentBinding binding){

        this.activity=activity;
        this.binding=binding;
        userDetail = new MutableLiveData<List<Pymentmethod>>();
        initiate();


            Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_PAYMENT_METHOD_LIST,new DATA());
            String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
            Log.d(TAG,jsonBody);
            RequestBody param=extentionUtils.toRequestBody(jsonBody);



                getPaymentList(param);





       return userDetail;
    }

    public LiveData<List<Account>> getAccList(MainActivity activity, PaymentDetailFragmentBinding binding){

        this.activity=activity;
        this.binding=binding;

        initiate();

        DATA data=new DATA();
        data.setUid(new AppSharedPref(activity).getUserData().getId());

        Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_ACC_LIST,data);
        String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
        Log.d(TAG,jsonBody);
        RequestBody param=extentionUtils.toRequestBody(jsonBody);



        getAccList(param);





        return accDetail;
    }

    public LiveData<String> deleteAcc(MainActivity activity, PaymentDetailFragmentBinding binding,String itemId){

        this.activity=activity;
        this.binding=binding;

        initiate();

        DATA data=new DATA();
        data.setId(itemId);

        Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_DELETE_ACC,data);
        String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
        Log.d(TAG,jsonBody);
        RequestBody param=extentionUtils.toRequestBody(jsonBody);



        getAccDeleteList(param);





        return delete_msg;
    }

    public LiveData<String> preferredAcc(MainActivity activity, PaymentDetailFragmentBinding binding,String itemId,String paymentMethodId){

        this.activity=activity;
        this.binding=binding;

        initiate();

        DATA data=new DATA();
        data.setId(itemId);
        data.setUid(new AppSharedPref(activity).getUserData().getId());
        data.setPaymentmethodid(paymentMethodId);
        data.setStatus("1");


        Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_PREFERRED_ACC,data);
        String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
        Log.d(TAG,jsonBody);
        RequestBody param=extentionUtils.toRequestBody(jsonBody);



        getAccPreferredList(param);





        return preferred_msg;
    }




    public void  getPaymentList(RequestBody param){

        detail.clear();
//        detail.add(new UserDetail("Savan","8839891738"));
//        userDetail.setValue(detail);

        Call<com.example.spider.model.Response> call =retrofitInterface.getLogin(param);

        call.enqueue(new Callback<com.example.spider.model.Response>() {
            @Override
            public void onResponse(Call<com.example.spider.model.Response> call, Response<com.example.spider.model.Response> response) {

                if(response.body().getMsgCode()==1){

                userDetail.setValue(response.body().getPymentmethodlist());
                }else {
                    userDetail.setValue(null);
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

    public void  getAccList(RequestBody param){

        detail.clear();
//        detail.add(new UserDetail("Savan","8839891738"));
//        userDetail.setValue(detail);

        Call<com.example.spider.model.Response> call =retrofitInterface.getLogin(param);

        call.enqueue(new Callback<com.example.spider.model.Response>() {
            @Override
            public void onResponse(Call<com.example.spider.model.Response> call, Response<com.example.spider.model.Response> response) {

                if(response.body().getMsgCode()==1){

                    accDetail.setValue(response.body().getAccountlist());
                }else {
                    accDetail.setValue(null);
                    binding.progressBar.setVisibility(View.GONE);
//                    extentionUtils.showToast(activity,response.body().getMessage());
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
    public void  getAccDeleteList(RequestBody param){

        detail.clear();
//        detail.add(new UserDetail("Savan","8839891738"));
//        userDetail.setValue(detail);

        Call<com.example.spider.model.Response> call =retrofitInterface.getLogin(param);

        call.enqueue(new Callback<com.example.spider.model.Response>() {
            @Override
            public void onResponse(Call<com.example.spider.model.Response> call, Response<com.example.spider.model.Response> response) {

                if(response.body().getMsgCode()==1){

                    delete_msg.setValue(response.body().getMsgCode().toString());
                    extentionUtils.showToast(activity,response.body().getMessage());
                }else {
                    accDetail.setValue(null);
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

    public void  getAccPreferredList(RequestBody param){

        detail.clear();
//        detail.add(new UserDetail("Savan","8839891738"));
//        userDetail.setValue(detail);

        Call<com.example.spider.model.Response> call =retrofitInterface.getLogin(param);

        call.enqueue(new Callback<com.example.spider.model.Response>() {
            @Override
            public void onResponse(Call<com.example.spider.model.Response> call, Response<com.example.spider.model.Response> response) {

                if(response.body().getMsgCode()==1){

                    preferred_msg.setValue(response.body().getMsgCode().toString());
                    extentionUtils.showToast(activity,response.body().getMessage());
                }else {
                    accDetail.setValue(null);
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
