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
import com.example.spider.databinding.WebsiteListFragmentBinding;
import com.example.spider.model.DATA;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.Website_item;
import com.example.spider.utils.Constant;
import com.example.spider.utils.ExtentionUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Website_List_Fragment_viewModel extends ViewModel {

    private String TAG ="Home_Fragment_viewModel";
   private MutableLiveData<List<Website_item>> userDetail=new MutableLiveData<>();
   private  List <Website_item> detail=new ArrayList<>();
   MainActivity activity;
   WebsiteListFragmentBinding binding;
   RetrofitInterface retrofitInterface;
    ExtentionUtils extentionUtils=new ExtentionUtils();

    public LiveData<List<Website_item>> getWebsiteLIst(MainActivity activity, WebsiteListFragmentBinding binding){

        this.activity=activity;
        this.binding=binding;
       binding.progressBar.setVisibility(View.VISIBLE);

        initiate();


            Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_WEBSITE_LIST,new DATA());
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

        Call<com.example.spider.model.Response> call =retrofitInterface.getWebsiteList(param);

        call.enqueue(new Callback<com.example.spider.model.Response>() {
            @Override
            public void onResponse(Call<com.example.spider.model.Response> call, Response<com.example.spider.model.Response> response) {

                try {



                    Log.e(TAG,response.body().toString());


//                    detail.addAll(jsonObject.getJSONArray("data"));
//                    detail.add(new Website_item("savann"));
                    userDetail.setValue(response.body().getWebsitelist());
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

    void initiate(){
retrofitInterface=RetrofitInstance.getmRetrofitInstance().create(RetrofitInterface.class);


    }



}
