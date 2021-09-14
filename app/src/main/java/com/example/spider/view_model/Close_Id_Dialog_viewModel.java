package com.example.spider.view_model;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spider.MainActivity;
import com.example.spider.api.RetrofitInstance;
import com.example.spider.api.RetrofitInterface;
import com.example.spider.databinding.AddAccDialogFragmentBinding;
import com.example.spider.databinding.CloseIdDialogfragmentBinding;
import com.example.spider.databinding.IdDetailFragmentBinding;
import com.example.spider.model.DATA;
import com.example.spider.model.Ifsc_Bank_Detail;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.Response;
import com.example.spider.model.UserDetail;
import com.example.spider.model.Websitedetail;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.Constant;
import com.example.spider.utils.ExtentionUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Close_Id_Dialog_viewModel extends ViewModel {

    MainActivity activity;
    RetrofitInterface retrofitInterface;
    CloseIdDialogfragmentBinding binding;
    ExtentionUtils extentionUtils = new ExtentionUtils();
    String TAG = "Add_Acc_Dialog_viewModel";
    private MutableLiveData<String> userDetail = new MutableLiveData<>();
    private MutableLiveData<List<Websitedetail>> websiteDetail=new MutableLiveData<>();
    private List<UserDetail> detail = new ArrayList<>();


    public LiveData<List<Websitedetail>> getMyIdList(MainActivity activity, CloseIdDialogfragmentBinding binding, String id){

        this.activity=activity;
        this.binding=binding;
        binding.progressBar.setVisibility(View.VISIBLE);

        initiate();

        DATA data=new DATA();
        data.setUid(new AppSharedPref(activity).getUserData().getId());
        data.setRequestid(id);

        Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_WEBSITEDETAIL,data);
        String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
        Log.d("loginResponse",jsonBody);
        RequestBody param=extentionUtils.toRequestBody(jsonBody);

//            if (userDetail == null) {

        loadUsers(param);
//            }
//            detail.clear();
//            detail.add(new UserDetail("Savan","8839891738"));
//            userDetail.setValue(detail);



        return websiteDetail;
    }










    public LiveData<String> sendCloesIdRequest(MainActivity activity, CloseIdDialogfragmentBinding binding,String  totalbalanceless,
                                               String noactivebets,
                                               String withdraw,
                                               String reason,
                                               String otherissue,
                                               String requestId) {


        this.activity = activity;
        this.binding = binding;
        binding.progressBar.setVisibility(View.VISIBLE);
        userDetail = new MutableLiveData<String>();
        initiate();



            DATA data = new DATA();
            data.setUserid(new AppSharedPref(activity).getUserData().getId());
            data.setRequestid(requestId);
            data.setTotalbalanceless(totalbalanceless);
            data.setNoactivebets(noactivebets);
            data.setWithdraw(withdraw);
            data.setReason(reason);
            data.setOtherissue(otherissue);


            Organisation_Pojo organisation_pojo = new Organisation_Pojo(Constant.FUNC_CLOSE_ID, data);
            String jsonBody = new Gson().toJson(organisation_pojo, Organisation_Pojo.class);
            Log.d(TAG, jsonBody);
            RequestBody param = extentionUtils.toRequestBody(jsonBody);

   closeId(param);



        return userDetail;
    }





    public void closeId(RequestBody param) {


//        detail.add(new UserDetail("Savan","8839891738"));
//        userDetail.setValue(detail);

        Call<com.example.spider.model.Response> call =retrofitInterface.addBankDetail(param);

        call.enqueue(new Callback<com.example.spider.model.Response>() {
            @Override
            public void onResponse(Call<com.example.spider.model.Response> call, retrofit2.Response<com.example.spider.model.Response> response) {

                if(response.body().getMsgCode()==1){

                    userDetail.setValue(response.body().getMsgCode().toString());
                    extentionUtils.showToast(activity,response.body().getMessage());
                }else {
                    userDetail.setValue(null);
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

    public void  loadUsers(RequestBody param){

        detail.clear();
//        detail.add(new UserDetail("Savan","8839891738"));
//        userDetail.setValue(detail);

        Call<com.example.spider.model.Response> call =retrofitInterface.getMyIdList(param);

        call.enqueue(new Callback<com.example.spider.model.Response>() {
            @Override
            public void onResponse(Call<com.example.spider.model.Response> call, retrofit2.Response<Response> response) {


                if(response.body().getMsgCode()==1){
                    websiteDetail.setValue(response.body().getWebsitedetail());
                }else {
                    websiteDetail.setValue(null);
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

    void initiate() {
        retrofitInterface = RetrofitInstance.getmRetrofitInstance().create(RetrofitInterface.class);


    }

}
