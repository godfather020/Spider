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
import com.example.spider.model.Account;
import com.example.spider.model.DATA;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.Pymentmethod;
import com.example.spider.model.UserDetail;
import com.example.spider.ui.activity.Login_Activity;
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

public class Paymetn_Method_viewModel extends ViewModel {

   private MutableLiveData<List<Pymentmethod>> userDetail=new MutableLiveData<>();
   private MutableLiveData<String> withdraw=new MutableLiveData<>();
   private  List <UserDetail> detail=new ArrayList<>();
   private List<Pymentmethod> pymentmethodsList=new ArrayList<>();
   private MutableLiveData<List<Account>> accDetail=new MutableLiveData<List<Account>>();
   MainActivity activity;
   String TAG="Paymetn_Method_viewModel";
   RetrofitInterface retrofitInterface;
   PaymentMethodFragmentBinding binding;
    ExtentionUtils extentionUtils=new ExtentionUtils();
    boolean deposit;

    public LiveData<List<Pymentmethod>> getPaymetMethodList(MainActivity activity,PaymentMethodFragmentBinding binding,boolean deposite){

        this.activity=activity;
        this.binding=binding;
        userDetail = new MutableLiveData<List<Pymentmethod>>();
        initiate();
        String funcName;
        deposit=deposite;

        if(deposite){
            funcName=Constant.FUNC_ADMIN_PAYMENT_METHOD_LIST;
        }else funcName=Constant.FUNC_PAYMENT_METHOD_LIST;

            Organisation_Pojo organisation_pojo=new Organisation_Pojo(funcName,new DATA());
            String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
            Log.d(TAG,jsonBody);
            RequestBody param=extentionUtils.toRequestBody(jsonBody);



                getPaymentList(param);





       return userDetail;
    }
    public LiveData<List<Account>> getAccList(MainActivity activity, PaymentMethodFragmentBinding binding){

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
    public LiveData<String> withdraw(MainActivity activity,PaymentMethodFragmentBinding binding,String coins,String accId,String websiteId){

        this.activity=activity;
        this.binding=binding;
        userDetail = new MutableLiveData<List<Pymentmethod>>();
        initiate();

        DATA data=new DATA();

        if(websiteId!=null) {
            data.setUserid(new AppSharedPref(activity).getUserData().getId());
            data.setRequestid(websiteId);
            data.setCoins(coins);
            data.setPaymentmethodid(accId);

        }else {
            data.setUserid(new AppSharedPref(activity).getUserData().getId());
            data.setCoins(coins);
            data.setPaymentmethodid(accId);
            data.setPay_wallet("1");
        }

        Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_WITHDRAW,data);
        String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
        Log.d(TAG,jsonBody);
        RequestBody param=extentionUtils.toRequestBody(jsonBody);



        sendDataForWithdraw(param);





        return withdraw;
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

                    if(deposit){
                        pymentmethodsList.clear();
                        for (int i=0;i<response.body().getAdminpaymentmethodlist().size();i++){

                            if(response.body().getAdminpaymentmethodlist().get(i).getStatus().equals("1")){

                                pymentmethodsList.add(response.body().getAdminpaymentmethodlist().get(i));
                            }
                        }
                        userDetail.setValue(pymentmethodsList);
                    }else
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
    public void  sendDataForWithdraw(RequestBody param){

        detail.clear();
//        detail.add(new UserDetail("Savan","8839891738"));
//        userDetail.setValue(detail);

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

    void initiate(){
retrofitInterface=RetrofitInstance.getmRetrofitInstance().create(RetrofitInterface.class);


    }

}
