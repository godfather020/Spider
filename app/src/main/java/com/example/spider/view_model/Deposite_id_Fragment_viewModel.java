package com.example.spider.view_model;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spider.MainActivity;
import com.example.spider.api.RetrofitInstance;
import com.example.spider.api.RetrofitInterface;
import com.example.spider.databinding.DepositIdFragmentBinding;
import com.example.spider.databinding.DepositeFragmentBinding;
import com.example.spider.databinding.PaymentFragmentBinding;
import com.example.spider.model.DATA;
import com.example.spider.model.Organisation_Pojo;
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

public class Deposite_id_Fragment_viewModel extends ViewModel {

    private String TAG ="Home_Fragment_viewModel";
    private MutableLiveData<String> userDetail=new MutableLiveData<String>();
    private MutableLiveData<Walletlist> walletlist=new MutableLiveData<>();
   private  List <Website_item> detail=new ArrayList<>();
   MainActivity activity;
   DepositIdFragmentBinding binding;
   RetrofitInterface retrofitInterface;
    ExtentionUtils extentionUtils=new ExtentionUtils();



    public LiveData<Walletlist> getWalletList(MainActivity activity, DepositIdFragmentBinding binding){

        this.activity=activity;
        this.binding=binding;
//        binding.progressBar.setVisibility(View.VISIBLE);

        initiate();

        DATA data =new DATA();
        data.setUid(new AppSharedPref(activity).getUserData().getId());
        Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_WALLET_LIST,data);
        String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
        Log.d("loginResponse",jsonBody);
        RequestBody param=extentionUtils.toRequestBody(jsonBody);

//            if (userDetail == null) {

        get_wallet_list(param);
//            }
//            detail.clear();
//            detail.add(new UserDetail("Savan","8839891738"));
//            userDetail.setValue(detail);



        return walletlist;
    }

    public MutableLiveData<String> userDeposite(MainActivity activity, DepositIdFragmentBinding binding, String userId
            , String coins, String paymentMethod, String paymentScreenshot, String websiteId,String rewards){

        this.activity=activity;
        this.binding=binding;
        Organisation_Pojo organisation_pojo;



            DATA data=new DATA();
            data.setUserid(userId);
            data.setRequestid(websiteId);
            data.setCoins(coins);
            data.setPay_wallet("1");
            data.setPaymentmethod("");
            data.setPaymentscreenshot("");
            data.setReward(rewards);
            organisation_pojo=new Organisation_Pojo(Constant.FUNC_DEPOIT,data);




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



    public void  get_wallet_list(RequestBody param){

        detail.clear();
//        detail.add(new UserDetail("Savan","8839891738"));
//        userDetail.setValue(detail);

        Call<com.example.spider.model.Response> call =retrofitInterface.getWalletList(param);

        call.enqueue(new Callback<com.example.spider.model.Response>() {
            @Override
            public void onResponse(Call<com.example.spider.model.Response> call, Response<com.example.spider.model.Response> response) {

                try {



                    Log.e(TAG,response.body().toString());


//                    detail.addAll(jsonObject.getJSONArray("data"));
//                    detail.add(new Website_item("savann"));
                    walletlist.setValue(response.body().getWalletlist());
                } catch ( Exception e) {
                    e.printStackTrace();
                }
               /* if(response.body().getMsgCode()==1){
                detail.add(new UserDetail(response.body().getData().getName(),response.body().getData().getId(),response.body().getData().getEmail()
                        ,response.body().getData().getContact(),response.body().getData().getCreatedDate(),response.body().getData().getRole()));
                userDetail.setValue(detail);
                }else {
                    userDetail.setValue(null);
                    extentionUtils.showToast(activity,response.body().getMessage());
                }*/


            }

            @Override
            public void onFailure(Call<com.example.spider.model.Response> call, Throwable t) {
//                Log.e("loginResponse","error=== "+t.getMessage().toString());
                binding.progressBar.setVisibility(View.GONE);
                extentionUtils.showToast(activity,t.getMessage());

            }
        });
    }

    public void  deposite(RequestBody param){

        Call<com.example.spider.model.Response> call =retrofitInterface.deposite(param);

        call.enqueue(new Callback<com.example.spider.model.Response>() {
            @Override
            public void onResponse(Call<com.example.spider.model.Response> call, retrofit2.Response<com.example.spider.model.Response> response) {

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
            public void onFailure(Call<com.example.spider.model.Response> call, Throwable t) {
//                Log.e("loginResponse","error=== "+t.getMessage().toString());
                binding.progressBar.setVisibility(View.GONE);
                extentionUtils.showToast(activity,t.getMessage());

            }
        });
    }
    void initiate(){
retrofitInterface=RetrofitInstance.getmRetrofitInstance().create(RetrofitInterface.class);


    }



}
