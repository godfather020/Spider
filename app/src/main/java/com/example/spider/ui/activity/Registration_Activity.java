package com.example.spider.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.databinding.LoginActivityBinding;
import com.example.spider.databinding.RegistrationActivityBinding;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Login_viewModel;
import com.example.spider.view_model.Registration_viewModel;

public class Registration_Activity extends AppCompatActivity {

    public RegistrationActivityBinding binding;
    public Registration_viewModel viewModel;
    private AppSharedPref mAppShredPref;
    ExtentionUtils utils=new ExtentionUtils();
    Otp_Activity otp_activity=new Otp_Activity();
    String phone_no;
    int country_code;
    String referalCodeId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         binding= DataBindingUtil.setContentView(this, R.layout.registration_activity);
         viewModel= new ViewModelProvider(this).get(Registration_viewModel.class);
         mAppShredPref=new AppSharedPref(getApplicationContext());

         Intent intent =getIntent();
         if(intent!=null){
           country_code=Integer.parseInt(intent.getStringExtra("country_code"));
           phone_no=intent.getStringExtra("phone_no");
         }


         binding.countryCodePicker.setCountryForPhoneCode(country_code);


         binding.edtMobNo.setText(phone_no);

        binding.edtReferlCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.d("Registration_Activity","before");

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() >= 5) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.progressBar.setVisibility(View.VISIBLE);

                            viewModel.checkReferalCode(Registration_Activity.this, binding, binding.edtReferlCode.getText().toString()).observe(Registration_Activity.this,checkCode -> {

                                if(checkCode!=null){

                                    binding.progressBar.setVisibility(View.GONE);
                                    referalCodeId=checkCode;

                                }

                            });
                        }
                    }, 500);


                }

                Log.d("Registration_Activity","onTExt");
            }

            @Override
            public void afterTextChanged(Editable editable) {

                Log.d("Registration_Activity","after");


            }
        });

         binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 if(binding.cbTermCondition.isChecked()){
                     String referlCode="";


                     if(!binding.edtReferlCode.getText().toString().isEmpty()){

                         referlCode=binding.edtReferlCode.getText().toString();
                         if(referlCode.length()<5){
                             binding.edtReferlCode.getText().clear();
                             binding.edtReferlCode.setFocusable(true);
                             utils.showToast(Registration_Activity.this,"Code is Invalid or Inactive.");
                             return;
                         }else {

                             referlCode=referalCodeId;
                         }
                     }

                     binding.progressBar.setVisibility(View.VISIBLE);
                 viewModel.userRegistration(Registration_Activity.this,binding,binding.edtName.getText().toString(),binding.edtMobNo.getText().toString(),binding.edtPassword.getText().toString(),binding.edtCnfPassword.getText().toString(),referlCode).observe(Registration_Activity.this, signUp -> {

                     ExtentionUtils utils=new ExtentionUtils();
                     if(signUp.equals("1")){


//                         utils.showToast(getApplicationContext(),"Login Successful \n Welcome "+userDetails.get(0).getName());
                         binding.progressBar.setVisibility(View.GONE);
                         otp_activity.finish();
                         Intent intent=new Intent(Registration_Activity.this, Login_Activity.class);
                         intent.putExtra("country_code",""+country_code);
                         intent.putExtra("phone_no",phone_no);
                         startActivity(intent);
                         finish();

                     }

                 });


             }else utils.showToast(getApplicationContext(),"Please accept term & condition");
             }
         });


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
