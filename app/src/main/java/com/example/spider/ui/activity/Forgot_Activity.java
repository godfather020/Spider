package com.example.spider.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.spider.R;
import com.example.spider.databinding.ForgotActivityBinding;
import com.example.spider.databinding.RegistrationActivityBinding;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Forgot_viewModel;
import com.example.spider.view_model.Registration_viewModel;

public class Forgot_Activity extends AppCompatActivity {

    public ForgotActivityBinding binding;
    public Forgot_viewModel viewModel;
    private AppSharedPref mAppShredPref;
    ExtentionUtils utils=new ExtentionUtils();
    Otp_Activity otp_activity=new Otp_Activity();
    String phone_no;
    int country_code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         binding= DataBindingUtil.setContentView(this, R.layout.forgot_activity);
         viewModel= new ViewModelProvider(this).get(Forgot_viewModel.class);
         mAppShredPref=new AppSharedPref(getApplicationContext());

         Intent intent =getIntent();
         if(intent!=null){

             if(intent.hasExtra("country_code")) {

                 country_code = Integer.parseInt(intent.getStringExtra("country_code"));

             }
           phone_no=intent.getStringExtra("phone_no");
         }





         binding.edtContact.setText(phone_no);

         binding.btnChangePassword.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {


                     binding.progressBar.setVisibility(View.VISIBLE);

                 viewModel.forgotPassword(Forgot_Activity.this,binding,binding.edtContact.getText().toString(),binding.edtPassword.getText().toString(),binding.edtCnfPassword.getText().toString()).observe(Forgot_Activity.this, forgotPassword -> {

                     ExtentionUtils utils=new ExtentionUtils();
                     if(forgotPassword!=null) {
                         if (forgotPassword.equals("1")) {


//                         utils.showToast(getApplicationContext(),"Login Successful \n Welcome "+userDetails.get(0).getName());
                             binding.progressBar.setVisibility(View.GONE);
//                             otp_activity.finish();
//                             Intent intent = new Intent(Forgot_Activity.this, Login_Activity.class);
//                             intent.putExtra("country_code", "" + country_code);
//                             intent.putExtra("phone_no", phone_no);
//                             startActivity(intent);
                             finish();

                         }
                     }

                 });



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
