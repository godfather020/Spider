package com.example.spider.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.databinding.LoginActivityBinding;
import com.example.spider.databinding.OtpActivityBinding;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Login_viewModel;
import com.example.spider.view_model.Otp_viewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Otp_Activity extends AppCompatActivity {

    public OtpActivityBinding binding;
    public Otp_viewModel viewModel;
    private AppSharedPref mAppShredPref;
    String phone_No ;
    ExtentionUtils utils=new ExtentionUtils();
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate","Runnnnnnnn........otpActivity");

         binding= DataBindingUtil.setContentView(this, R.layout.otp_activity);
         viewModel= new ViewModelProvider(this).get(Otp_viewModel.class);
         mAppShredPref=new AppSharedPref(getApplicationContext());
        mAuth=FirebaseAuth.getInstance();





         binding.btnOtpSubmit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {




                viewModel.checkPhoneNo(Otp_Activity.this,binding.edtMobNo.getText().toString()).observe(Otp_Activity.this,exist -> {

                    binding.progressBar.setVisibility(View.GONE);
                    if(exist){


                        Intent intent=new Intent(Otp_Activity.this, Login_Activity.class);
                        intent.putExtra("country_code",binding.countryCodePicker.getSelectedCountryCode());
                        intent.putExtra("phone_no",binding.edtMobNo.getText().toString());
                        startActivity(intent);
                        finish();

                    }else{

                        if(binding.edtMobNo.getText().toString()!=null && !binding.edtMobNo.getText().toString().isEmpty()){

                            getOtp();
                        }else {
                            utils.showToast(Otp_Activity.this,"Please enter valid mobile number.");
                            binding.edtMobNo.setFocusable(true);
                        }




                    }

                });



             }
         });

    }

    public void getOtp(){

        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks = null;
        final String[] code = new String[1];
        final String[] mVerifiction = new String[1];

        mCallBacks =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                //Getting the code sent by SMS
                code[0] = phoneAuthCredential.getSmsCode();

                //sometime the code is not detected automatically
                //in this case the code will be null
                //so user has to manually enter the code


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Log.e("login",e.getMessage());

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(Otp_Activity.this, "Code has Sent to given mobile number.", Toast.LENGTH_SHORT).show();
                mVerifiction[0] =s;
            }
        };
                /* PhoneAuthOptions options =
                         PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                                 .setPhoneNumber("+91" + binding.edtMobNo.getText().toString())       // Phone number to verify
                                 .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                 .setActivity(Login_Activity.this)                 // Activity (for callback binding)
                                 .setCallbacks(mCallBacks)          // OnVerificationStateChangedCallbacks
                                 .build();*/

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" +  binding.edtMobNo.getText().toString() ,
                10,
                TimeUnit.SECONDS,
                Otp_Activity.this,
                mCallBacks //Object of OnVerificationStateChangedCallbacks
        );

//                 PhoneAuthProvider.verifyPhoneNumber(options);


        final Dialog dialog = new Dialog(Otp_Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_otp);

        EditText edtOtp =  dialog.findViewById(R.id.edt_otp);
        TextView txt =dialog.findViewById(R.id.txt_otp);

        txt.setText(getResources().getString(R.string.enter_otp_title)+" "+binding.countryCodePicker.getSelectedCountryCode()+"-"+binding.edtMobNo.getText().toString());
        Button dialogButton =  dialog.findViewById(R.id.btn_otp_submit);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code[0]=edtOtp.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerifiction[0], code[0]);

                //signing the user
                signInWithPhoneAuthCredential(credential);

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                ExtentionUtils utils = new ExtentionUtils();
                if (task.isSuccessful()) {

                    Intent intent=new Intent(Otp_Activity.this, Registration_Activity.class);
                    intent.putExtra("country_code",binding.countryCodePicker.getSelectedCountryCode());
                    intent.putExtra("phone_no",binding.edtMobNo.getText().toString());
                    startActivity(intent);
                    finish();

                }else {
                    utils.showToast(getApplicationContext(),"Something went wrong");

                }
            }

        });
    }
}
