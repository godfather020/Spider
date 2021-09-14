package com.example.spider.view_model;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spider.MainActivity;
import com.example.spider.api.RetrofitInstance;
import com.example.spider.api.RetrofitInterface;
import com.example.spider.databinding.PaymentFragmentBinding;
import com.example.spider.databinding.RegistrationActivityBinding;
import com.example.spider.model.Adminaccount;
import com.example.spider.model.DATA;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.Response;
import com.example.spider.model.UserDetail;
import com.example.spider.ui.activity.Registration_Activity;
import com.example.spider.utils.Constant;
import com.example.spider.utils.ExtentionUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Payment_viewModel extends ViewModel {

   private MutableLiveData<String> userDetail=new MutableLiveData<String>();
    private MutableLiveData<List<Adminaccount>> accDetail=new MutableLiveData<>();
   private  List <UserDetail> detail=new ArrayList<>();
   private PaymentFragmentBinding binding;
   MainActivity activity;
    RetrofitInterface retrofitInterface= RetrofitInstance.getmRetrofitInstance().create(RetrofitInterface.class);
    ExtentionUtils extentionUtils=new ExtentionUtils();
   boolean empty_Field=false,mismatch_Password=false;

    public MutableLiveData<String> userCreateId(MainActivity activity, PaymentFragmentBinding binding, String userName, String userId
            , String websiteId, String coins, String paymentMethod, String paymentScreenshot){

        this.activity=activity;
        this.binding=binding;




//            if (userDetail == null) {
        DATA data =new DATA();
        data.setUsername(userName);
        data.setUserid(userId);
        data.setWebsiteid(websiteId);
        data.setCoins(coins);
        data.setPaymentmethod(paymentMethod);
        data.setPaymentscreenshot(paymentScreenshot);
        data.setPay_wallet("0");
        data.setReward("0");

            Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_REQUEST_ID,data);
            String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
            Log.d("createID",jsonBody);
            RequestBody param=extentionUtils.toRequestBody(jsonBody);

            createId(param);
//            }
//            detail.clear();
//            detail.add(new UserDetail("Savan","8839891738"));
//            userDetail.setValue(detail);




       return userDetail;
    }

    public MutableLiveData<List<Adminaccount>> getAccDetail(MainActivity activity, PaymentFragmentBinding binding, String paymentMethod){

        this.activity=activity;
        this.binding=binding;




//            if (userDetail == null) {

        DATA data=new DATA();

        data.setId(paymentMethod);


        Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_PAYMENT_ACCOUNT_LIST,data);
        String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
        Log.d("createID",jsonBody);
        RequestBody param=extentionUtils.toRequestBody(jsonBody);

            getAccDetail(param);
//            }
//            detail.clear();
//            detail.add(new UserDetail("Savan","8839891738"));
//            userDetail.setValue(detail);




        return accDetail;
    }

    public MutableLiveData<String> userDeposite(MainActivity activity, PaymentFragmentBinding binding, String userId
            , String coins, String paymentMethod, String paymentScreenshot,String websiteId,String rewards){

        this.activity=activity;
        this.binding=binding;
        Organisation_Pojo organisation_pojo;
        DATA data=new DATA();
        if(websiteId!=null){


            data.setUserid(userId);
            data.setRequestid(websiteId);
            data.setCoins(coins);
            data.setPaymentmethod(paymentMethod);
            data.setPaymentscreenshot(paymentScreenshot);
            data.setReward(rewards);
           organisation_pojo=new Organisation_Pojo(Constant.FUNC_DEPOIT,data);


        }else {
            data.setUserid(userId);
            data.setCoins(coins);
            data.setPaymentmethod(paymentMethod);
            data.setPaymentscreenshot(paymentScreenshot);
            data.setPay_wallet("1");
            data.setReward(rewards);
            organisation_pojo=new Organisation_Pojo(Constant.FUNC_DEPOIT,data);

        }
        String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
        Log.d("createID",jsonBody);
        RequestBody param=extentionUtils.toRequestBody(jsonBody);

        deposite(param);
//            }
//            detail.clear();
//            detail.add(new UserDetail("Savan","8839891738"));
//            userDetail.setValue(detail);




        return userDetail;
    }

   /* public boolean Reg_Validation(String name,String mob_no, String password, String cnf_password){

        boolean valid=false;

        if(name.isEmpty()||mob_no.isEmpty()||password.isEmpty()||cnf_password.isEmpty()){
            empty_Field=true;
        }else if(!password.equals(cnf_password)){


            binding.edtCnfPassword.getText().clear();
            mismatch_Password=true;

        }else valid=true;
        return valid;
    }*/
// && Patterns.EMAIL_ADDRESS.matcher(mob_no).matches()
    public void  createId(RequestBody param){

        Call<Response> call =retrofitInterface.createId(param);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

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

    public void  deposite(RequestBody param){

        Call<Response> call =retrofitInterface.deposite(param);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

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

    public void  getAccDetail(RequestBody param){

        Call<Response> call =retrofitInterface.createId(param);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                if(response.body().getMsgCode()==1){

                    accDetail.setValue(response.body().getAdminaccountlist());
                }else {
                    binding.progressBar.setVisibility(View.GONE);

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
