package com.example.spider.view_model;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spider.api.RetrofitInstance;
import com.example.spider.api.RetrofitInterface;
import com.example.spider.databinding.ForgotActivityBinding;
import com.example.spider.databinding.RegistrationActivityBinding;
import com.example.spider.model.DATA;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.Response;
import com.example.spider.model.UserDetail;
import com.example.spider.ui.activity.Forgot_Activity;
import com.example.spider.ui.activity.Registration_Activity;
import com.example.spider.utils.Constant;
import com.example.spider.utils.ExtentionUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Forgot_viewModel extends ViewModel {

   private MutableLiveData<String> userDetail=new MutableLiveData<String>();
   private  List <UserDetail> detail=new ArrayList<>();
   private ForgotActivityBinding binding;
   Forgot_Activity activity;
    RetrofitInterface retrofitInterface= RetrofitInstance.getmRetrofitInstance().create(RetrofitInterface.class);
    ExtentionUtils extentionUtils=new ExtentionUtils();
   boolean empty_Field=false,mismatch_Password=false;

    public MutableLiveData<String> forgotPassword(Forgot_Activity activity, ForgotActivityBinding binding, String mob_no, String password, String cnf_password){

        this.activity=activity;
        this.binding=binding;


        if(Forgot_Validation(mob_no,password,cnf_password)){

//            if (userDetail == null) {

            Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_RESET_PASSWORD,new DATA(null,mob_no,
                    password,
                    null));
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

    public boolean Forgot_Validation(String mob_no, String password, String cnf_password){

        boolean valid=false;

        if(mob_no.isEmpty()||password.isEmpty()||cnf_password.isEmpty()){
            empty_Field=true;
        }else if(!password.equals(cnf_password)){


            binding.edtCnfPassword.getText().clear();
            mismatch_Password=true;

        }else valid=true;
        return valid;
    }
// && Patterns.EMAIL_ADDRESS.matcher(mob_no).matches()
    public void  loadUsers(RequestBody param){

        Call<Response> call =retrofitInterface.changePassword(param);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                if(response.body().getMsgCode()==1){

                    userDetail.setValue(response.body().getMsgCode().toString());
                }else {
                    activity.binding.progressBar.setVisibility(View.GONE);
                    userDetail.setValue(response.body().getMsgCode().toString());
                    extentionUtils.showToast(activity,response.body().getMessage());
                }


            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
//                Log.e("loginResponse","error=== "+t.getMessage().toString());
                activity.binding.progressBar.setVisibility(View.GONE);
                extentionUtils.showToast(activity,t.getMessage());

            }
        });
    }

}
