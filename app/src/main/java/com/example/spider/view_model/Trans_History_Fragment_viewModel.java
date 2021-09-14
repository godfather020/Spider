package com.example.spider.view_model;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spider.MainActivity;
import com.example.spider.api.RetrofitInstance;
import com.example.spider.api.RetrofitInterface;
import com.example.spider.databinding.HomeFragmentBinding;
import com.example.spider.databinding.PassbookFragmentBinding;
import com.example.spider.model.Approvedrequestidlist;
import com.example.spider.model.Banner;
import com.example.spider.model.DATA;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.Transactionhistory;
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

public class Trans_History_Fragment_viewModel extends ViewModel {

    private String TAG ="Home_Fragment_viewModel";
   private MutableLiveData<List<Transactionhistory>> userDetail=new MutableLiveData<>();
    private MutableLiveData<List<Banner>> bannerList=new MutableLiveData<>();
    private MutableLiveData<Walletlist> walletlist=new MutableLiveData<>();
   private  List <Website_item> detail=new ArrayList<>();
   MainActivity activity;
   PassbookFragmentBinding binding;
   RetrofitInterface retrofitInterface;
    ExtentionUtils extentionUtils=new ExtentionUtils();

    public LiveData<List<Transactionhistory>> getTransList(MainActivity activity, PassbookFragmentBinding binding){

        this.activity=activity;
        this.binding=binding;
       binding.progressBar.setVisibility(View.VISIBLE);

        initiate();


            Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_TRANSACTION_HISTORY,new DATA(new AppSharedPref(activity).getUserData().getId(),null,null));
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






    public void  loadUsers(RequestBody param){

        detail.clear();
//        detail.add(new UserDetail("Savan","8839891738"));
//        userDetail.setValue(detail);

        Call<com.example.spider.model.Response> call =retrofitInterface.getMyIdList(param);

        call.enqueue(new Callback<com.example.spider.model.Response>() {
            @Override
            public void onResponse(Call<com.example.spider.model.Response> call, Response<com.example.spider.model.Response> response) {


                if(response.body().getMsgCode()==1){

                userDetail.setValue(response.body().getTransactionhistorylist());
                }else {
                    binding.progressBar.setVisibility(View.GONE);
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
