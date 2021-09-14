package com.example.spider.view_model;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spider.MainActivity;
import com.example.spider.api.RetrofitInstance;
import com.example.spider.api.RetrofitInterface;
import com.example.spider.databinding.AddAccDialogFragmentBinding;
import com.example.spider.databinding.RegistrationActivityBinding;
import com.example.spider.model.DATA;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.Response;
import com.example.spider.model.UserDetail;
import com.example.spider.ui.activity.Registration_Activity;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.Constant;
import com.example.spider.utils.ExtentionUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Registration_viewModel extends ViewModel {

   private MutableLiveData<String> userDetail=new MutableLiveData<String>();
   private MutableLiveData<String> checkReferalCode=new MutableLiveData<String>();
   private  List <UserDetail> detail=new ArrayList<>();
   private RegistrationActivityBinding binding;
   Registration_Activity activity;
    RetrofitInterface retrofitInterface= RetrofitInstance.getmRetrofitInstance().create(RetrofitInterface.class);
    ExtentionUtils extentionUtils=new ExtentionUtils();
   boolean empty_Field=false,mismatch_Password=false;

    public MutableLiveData<String> userRegistration(Registration_Activity activity, RegistrationActivityBinding binding, String name, String mob_no, String password, String cnf_password,String referalCode){

        this.activity=activity;
        this.binding=binding;


        if(Reg_Validation(name,mob_no,password,cnf_password)){

//            if (userDetail == null) {
            DATA data=new DATA();

            data.setName(name);
            data.setContact(mob_no);
            data.setPassword(password);
            data.setRole("User");
            data.setCodeid(referalCode);
            Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_SIGNUP,data);
            String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
            Log.d("loginResponse",jsonBody);
            RequestBody param=extentionUtils.toRequestBody(jsonBody);

            loadUsers(param);
//            }
//            detail.clear();
//            detail.add(new UserDetail("Savan","8839891738"));
//            userDetail.setValue(detail);

        }else {
            activity.binding.progressBar.setVisibility(View.GONE);

            if(empty_Field) {

                extentionUtils.showToast(activity, "Please fill all mandatory fields.");

            }else if(mismatch_Password){

                extentionUtils.showToast(activity, "Please enter same password.");
            }

        }


       return userDetail;
    }

    public LiveData<String> checkReferalCode(Registration_Activity activity, RegistrationActivityBinding binding,String code) {

        this.activity = activity;
        this.binding = binding;
        binding.progressBar.setVisibility(View.VISIBLE);
        userDetail = new MutableLiveData<String>();



            DATA data = new DATA();
            data.setCode(code);



            Organisation_Pojo organisation_pojo = new Organisation_Pojo(Constant.FUNC_CHECK_REFERAL_CODE, data);
            String jsonBody = new Gson().toJson(organisation_pojo, Organisation_Pojo.class);
            Log.d("Registration", jsonBody);
            RequestBody param = extentionUtils.toRequestBody(jsonBody);
            runapi_checkReferCode(param);



        return checkReferalCode;
    }

    public boolean Reg_Validation(String name,String mob_no, String password, String cnf_password){

        boolean valid=false;

        if(name.isEmpty()||mob_no.isEmpty()||password.isEmpty()||cnf_password.isEmpty()){
            empty_Field=true;
        }else if(!password.equals(cnf_password)){


            binding.edtCnfPassword.getText().clear();
            mismatch_Password=true;

        }else valid=true;
        return valid;
    }
// && Patterns.EMAIL_ADDRESS.matcher(mob_no).matches()
    public void  loadUsers(RequestBody param){

        Call<Response> call =retrofitInterface.signUp(param);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<com.example.spider.model.Response> call, retrofit2.Response<Response> response) {

                if(response.body().getMsgCode()==1){

                    userDetail.setValue(response.body().getMsgCode().toString());
                }else {
                    activity.binding.progressBar.setVisibility(View.GONE);
                    userDetail.setValue(response.body().getMsgCode().toString());
                    extentionUtils.showToast(activity,response.body().getMessage());
                }


            }

            @Override
            public void onFailure(Call<com.example.spider.model.Response> call, Throwable t) {
//                Log.e("loginResponse","error=== "+t.getMessage().toString());
                activity.binding.progressBar.setVisibility(View.GONE);
                extentionUtils.showToast(activity,t.getMessage());

            }
        });
    }
    public void  runapi_checkReferCode(RequestBody param){

        Call<Response> call =retrofitInterface.signUp(param);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<com.example.spider.model.Response> call, retrofit2.Response<Response> response) {

                if(response.body().getMsgCode()==1){

                    checkReferalCode.setValue(response.body().getCodeid().toString());
                }else {
                    activity.binding.progressBar.setVisibility(View.GONE);
                    binding.edtReferlCode.getText().clear();
                    binding.edtReferlCode.setFocusable(true);
                    extentionUtils.showToast(activity,response.body().getMessage());
                }


            }

            @Override
            public void onFailure(Call<com.example.spider.model.Response> call, Throwable t) {
//                Log.e("loginResponse","error=== "+t.getMessage().toString());
                activity.binding.progressBar.setVisibility(View.GONE);
                extentionUtils.showToast(activity,t.getMessage());

            }
        });
    }


}
