package com.example.spider.view_model;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spider.MainActivity;
import com.example.spider.api.RetrofitInstance;
import com.example.spider.api.RetrofitInterface;
import com.example.spider.databinding.AddAccDialogFragmentBinding;
import com.example.spider.model.DATA;
import com.example.spider.model.Ifsc_Bank_Detail;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.UserDetail;
import com.example.spider.ui.activity.Login_Activity;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.Constant;
import com.example.spider.utils.ExtentionUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class Add_Acc_Dialog_viewModel extends ViewModel {

    MainActivity activity;
    RetrofitInterface retrofitInterface;
    AddAccDialogFragmentBinding binding;
    ExtentionUtils extentionUtils = new ExtentionUtils();
    String TAG = "Add_Acc_Dialog_viewModel";
    private MutableLiveData<String> userDetail = new MutableLiveData<>();
    private List<UserDetail> detail = new ArrayList<>();
    boolean upi =false;

    public void getBankDetail(MainActivity activity, AddAccDialogFragmentBinding binding, String IFSC) {

        this.activity = activity;
        this.binding = binding;
        binding.progressBar.setVisibility(View.VISIBLE);
        userDetail = new MutableLiveData<String>();
        initiate();


        getBankDetail(IFSC);
//


    }

    public LiveData<String> addtBankDetail(MainActivity activity, AddAccDialogFragmentBinding binding) {

        this.activity = activity;
        this.binding = binding;
        binding.progressBar.setVisibility(View.VISIBLE);
        userDetail = new MutableLiveData<String>();
        initiate();
        upi=false;

        if (validation()) {

            String bankType;
            if(binding.rbtnSavingAcc.isChecked()){
              bankType=binding.rbtnSavingAcc.getText().toString();
            }else {
                bankType=binding.rbtnCurrentAcc.getText().toString();
            }
            DATA data = new DATA();
            data.setAccountholder(binding.edtAccHolderName.getText().toString());
            data.setAccountno(binding.edtAccNO.getText().toString());
            data.setAccounttype(bankType);
            data.setBankname(binding.edtBankName.getText().toString());
            data.setIfsc(binding.edtIfsc.getText().toString());
            data.setBranch(binding.edtBankeAdd.getText().toString());
            data.setUid(new AppSharedPref(activity).getUserData().getId().toString());


            Organisation_Pojo organisation_pojo = new Organisation_Pojo(Constant.FUNC_CREATEACC, data);
            String jsonBody = new Gson().toJson(organisation_pojo, Organisation_Pojo.class);
            Log.d(TAG, jsonBody);
            RequestBody param = extentionUtils.toRequestBody(jsonBody);
   addBankDetail(param);
        } else {
            binding.progressBar.setVisibility(View.GONE);

            extentionUtils.showToast(activity, "Please Fill All Mandatory Fields!");
        }


        return userDetail;
    }

    public LiveData<String> updateBankDetail(MainActivity activity, AddAccDialogFragmentBinding binding,String id) {

        this.activity = activity;
        this.binding = binding;
        binding.progressBar.setVisibility(View.VISIBLE);
        userDetail = new MutableLiveData<String>();
        initiate();
        upi=false;

        if (validation()) {

            String bankType;
            if(binding.rbtnSavingAcc.isChecked()){
                bankType=binding.rbtnSavingAcc.getText().toString();
            }else {
                bankType=binding.rbtnCurrentAcc.getText().toString();
            }
            DATA data = new DATA();
            data.setId(id);
            data.setAccountholder(binding.edtAccHolderName.getText().toString());
            data.setAccountno(binding.edtAccNO.getText().toString());
            data.setAccounttype(bankType);
            data.setBankname(binding.edtBankName.getText().toString());
            data.setIfsc(binding.edtIfsc.getText().toString());
            data.setBranch(binding.edtBankeAdd.getText().toString());
            data.setUid(new AppSharedPref(activity).getUserData().getId().toString());



            Organisation_Pojo organisation_pojo = new Organisation_Pojo(Constant.FUNC_UPDATE_ACC, data);
            String jsonBody = new Gson().toJson(organisation_pojo, Organisation_Pojo.class);
            Log.d(TAG, jsonBody);
            RequestBody param = extentionUtils.toRequestBody(jsonBody);
            addBankDetail(param);
        } else {
            binding.progressBar.setVisibility(View.GONE);

            extentionUtils.showToast(activity, "Please Fill All Mandatory Fields!");
        }


        return userDetail;
    }

    public LiveData<String> addtUpiDetail(MainActivity activity,AddAccDialogFragmentBinding binding,boolean upi ,String methodId) {

        this.activity = activity;
        this.binding = binding;
        this.upi=upi;
        binding.progressBar.setVisibility(View.VISIBLE);
        userDetail = new MutableLiveData<String>();
        initiate();


        if (validation()) {

            DATA data = new DATA();
            data.setDisplayname(binding.edtName.getText().toString());
            data.setNumber(binding.edtUpiNo.getText().toString());
            data.setPaymentmethodid(methodId);
            data.setUid(new AppSharedPref(activity).getUserData().getId());



            Organisation_Pojo organisation_pojo = new Organisation_Pojo(Constant.FUNC_CREATE_UPI_ACC, data);
            String jsonBody = new Gson().toJson(organisation_pojo, Organisation_Pojo.class);
            Log.d(TAG, jsonBody);
            RequestBody param = extentionUtils.toRequestBody(jsonBody);
            addBankDetail(param);
        } else {
            binding.progressBar.setVisibility(View.GONE);

            extentionUtils.showToast(activity, "Please Fill All Mandatory Fields!");
        }


        return userDetail;
    }
    public LiveData<String> updateUpiDetail(MainActivity activity,AddAccDialogFragmentBinding binding,boolean upi ,String methodId,String itemId) {

        this.activity = activity;
        this.binding = binding;
        this.upi=upi;
        binding.progressBar.setVisibility(View.VISIBLE);
        userDetail = new MutableLiveData<String>();
        initiate();


        if (validation()) {

            DATA data = new DATA();
            data.setId(itemId);
            data.setDisplayname(binding.edtName.getText().toString());
            data.setNumber(binding.edtUpiNo.getText().toString());
            data.setPaymentmethodid(methodId);
            data.setUid(new AppSharedPref(activity).getUserData().getId());



            Organisation_Pojo organisation_pojo = new Organisation_Pojo(Constant.FUNC_UPDATE_UPI_ACC, data);
            String jsonBody = new Gson().toJson(organisation_pojo, Organisation_Pojo.class);
            Log.d(TAG, jsonBody);
            RequestBody param = extentionUtils.toRequestBody(jsonBody);
            addBankDetail(param);
        } else {
            binding.progressBar.setVisibility(View.GONE);

            extentionUtils.showToast(activity, "Please Fill All Mandatory Fields!");
        }


        return userDetail;
    }

    public boolean validation() {

if(upi){
    return !binding.edtName.getText().toString().isEmpty()
            && !binding.edtUpiNo.getText().toString().isEmpty() ;
}else
        return !binding.edtAccNO.getText().toString().isEmpty()
                && !binding.edtAccHolderName.getText().toString().isEmpty() && !binding.edtIfsc.getText().toString().isEmpty();


    }

    // && Patterns.EMAIL_ADDRESS.matcher(mob_no).matches()
    public void getBankDetail(String param) {


//        detail.add(new UserDetail("Savan","8839891738"));
//        userDetail.setValue(detail);
        String url = "https://ifsc.razorpay.com/" + param;
        Call<Ifsc_Bank_Detail> call = retrofitInterface.getBankDetail(url);


        call.enqueue(new Callback<Ifsc_Bank_Detail>() {
            @Override
            public void onResponse(Call<Ifsc_Bank_Detail> call, retrofit2.Response<Ifsc_Bank_Detail> response) {

                if (response.isSuccessful()) {
                    binding.progressBar.setVisibility(View.GONE);

                    Log.d(TAG, response.body().getBank());
                    binding.textInputLayout4.setVisibility(View.VISIBLE);
                    binding.textInputLayout5.setVisibility(View.VISIBLE);
                    binding.rgAcc.setVisibility(View.VISIBLE);
                    binding.btnAddBank.setVisibility(View.VISIBLE);
                    binding.edtBankName.setText(response.body().getBank());
                    binding.edtBankeAdd.setText(response.body().getAddress());

                } else {
                    userDetail.setValue(null);
                    binding.progressBar.setVisibility(View.GONE);
                    extentionUtils.showToast(activity, response.message());
                }


            }

            @Override
            public void onFailure(Call<Ifsc_Bank_Detail> call, Throwable t) {
//                Log.e("loginResponse","error=== "+t.getMessage().toString());
                binding.progressBar.setVisibility(View.GONE);
                extentionUtils.showToast(activity, t.getMessage());

            }
        });
    }

    public void addBankDetail(RequestBody param) {


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

    void initiate() {
        retrofitInterface = RetrofitInstance.getmRetrofitInstance().create(RetrofitInterface.class);


    }

}
