package com.example.spider.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ListChangeRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.databinding.LoginActivityBinding;
import com.example.spider.model.DATA;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.UserDetail;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.Constant;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Login_viewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Login_Activity extends AppCompatActivity {

    public LoginActivityBinding binding;
    public Login_viewModel viewModel;
    private AppSharedPref mAppShredPref;
    ExtentionUtils utils =new ExtentionUtils();
    boolean forgot_password=false;
    String TAG="Login_Activity";
    String phone_no;
    int country_code;
    FirebaseAuth mAuth;
    String Token;
    String MobNo,device_id;
    List<String> permission=new ArrayList<String>();
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS= 3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("onCreate","Runnnnnnnn........loginActivity");
         binding= DataBindingUtil.setContentView(this, R.layout.login_activity);
         viewModel= new ViewModelProvider(this).get(Login_viewModel.class);
         mAppShredPref=new AppSharedPref(getApplicationContext());

//        TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
//                device_id = TelephonyMgr.getDeviceId();
//                Log.d("Android","Android ID : "+device_id);

                device_id= Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

//      init();

        if (check_Permission()) {
            init();
        } else {
            requestPermission();
        }


    }

    public void init(){

        mAuth=FirebaseAuth.getInstance();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        Token = task.getResult();

                        // Log and toast

                        Log.d(TAG, Token);

                    }
                });

        Intent intent =getIntent();
        if(intent!=null){

            if(intent.hasExtra("country_code")) {
                country_code = Integer.parseInt(intent.getStringExtra("country_code"));
                phone_no = intent.getStringExtra("phone_no");
            }
        }

        binding.edtMobNo.setText(phone_no);
        binding.txtOtp.setText(Html.fromHtml(getResources().getString(R.string.sign_in_with_otp)));

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                binding.progressBar.setVisibility(View.VISIBLE);

                viewModel.userLogin(Login_Activity.this,binding.edtMobNo.getText().toString(),binding.edtPassword.getText().toString(),Token,device_id).observe(Login_Activity.this,userDetails -> {

                    ExtentionUtils utils=new ExtentionUtils();
                    binding.progressBar.setVisibility(View.GONE);
                    if(userDetails!=null){


//                         utils.showToast(getApplicationContext(),"Login Successful \n Welcome "+userDetails.get(0).getName());

                        mAppShredPref.saveUserData(userDetails.get(0));
                        mAppShredPref.saveBoolean("login",true);
                        Intent intent=new Intent(Login_Activity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }

                });

               /*  Intent intent=new Intent(Login_Activity.this, MainActivity.class);
                 startActivity(intent);
                 finish();*/
            }
        });

        binding.txtOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                forgot_password=false;
                if(binding.edtMobNo.getText().toString()!=null && !binding.edtMobNo.getText().toString().isEmpty()){

                    getOtp();
                }else {
                    utils.showToast(Login_Activity.this,"Please enter valid mobile number.");
                    binding.edtMobNo.setFocusable(true);
                }

            }
        });

        binding.txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                forgot_password=true;
                if(binding.edtMobNo.getText().toString()!=null && !binding.edtMobNo.getText().toString().isEmpty()){

                    getOtp();
                }else {
                    utils.showToast(Login_Activity.this,"Please enter valid mobile number.");
                    binding.edtMobNo.setFocusable(true);
                }

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
               Toast.makeText(Login_Activity.this, "Code has Sent to given mobile number.", Toast.LENGTH_SHORT).show();
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
               Login_Activity.this,
               mCallBacks //Object of OnVerificationStateChangedCallbacks
       );

//                 PhoneAuthProvider.verifyPhoneNumber(options);


       final Dialog dialog = new Dialog(Login_Activity.this);
       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       dialog.setCancelable(true);
       dialog.setContentView(R.layout.dialog_otp);

       EditText edtOtp =  dialog.findViewById(R.id.edt_otp);
        TextView txt =dialog.findViewById(R.id.txt_otp);

        txt.setText(getResources().getString(R.string.enter_otp_title)+" "+country_code+"-"+phone_no);
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

   public boolean check_Permission(){

        int camera= ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA);
        int gallery=ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
       int gallery1=ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int contact=ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS);
        int phone=ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE);

        if(camera!= PackageManager.PERMISSION_GRANTED){

            permission.add(Manifest.permission.CAMERA);
        }

        if(gallery!=PackageManager.PERMISSION_GRANTED){

            permission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if(gallery1!=PackageManager.PERMISSION_GRANTED){

            permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(contact!=PackageManager.PERMISSION_GRANTED){

            permission.add(Manifest.permission.READ_CONTACTS);
        }
        if(phone!=PackageManager.PERMISSION_GRANTED){

            permission.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(!permission.isEmpty()){


            return false;
        }


    return true;
   }
   public void  requestPermission(){
       ActivityCompat.requestPermissions(this,permission.toArray(new String[permission.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);

   }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

            mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    ExtentionUtils utils = new ExtentionUtils();
                    if (task.isSuccessful()) {

                        if(!forgot_password){
                        viewModel.userOtpLogin(Login_Activity.this, binding.edtMobNo.getText().toString(), binding.edtPassword.getText().toString(),Token,device_id).observe(Login_Activity.this, userDetails -> {


                            binding.progressBar.setVisibility(View.GONE);
                            if (userDetails != null) {


//                         utils.showToast(getApplicationContext(),"Login Successful \n Welcome "+userDetails.get(0).getName());

                                mAppShredPref.saveUserData(userDetails.get(0));
                                mAppShredPref.saveBoolean("login",true);
                                Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }

                        });

                        }else {

                            Intent intent = new Intent(Login_Activity.this, Forgot_Activity.class);
                            intent.putExtra("country_code",""+country_code);
                            intent.putExtra("phone_no",binding.edtMobNo.getText().toString());
                            startActivity(intent);
//                            finish();
                        }

                    }else {
                        utils.showToast(getApplicationContext(),"Something went wrong");

                    }
                }

            });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case REQUEST_ID_MULTIPLE_PERMISSIONS:

                Map<String,Integer> perm=new HashMap<>();

//                perm.put(Manifest.permission.CAMERA,PackageManager.PERMISSION_GRANTED);
//                perm.put(Manifest.permission.READ_EXTERNAL_STORAGE,PackageManager.PERMISSION_GRANTED);
//                perm.put(Manifest.permission.READ_CONTACTS,PackageManager.PERMISSION_GRANTED);

                if(grantResults.length > 0){

                    for(int i=0;i <permissions.length;i++)
                        perm.put(permissions[i],grantResults[i]);



                        if(perm.get(Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED
                                &&perm.get(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
                    &&perm.get(Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_GRANTED
                    &&perm.get(Manifest.permission.READ_PHONE_STATE)==PackageManager.PERMISSION_GRANTED
                    &&perm.get(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){

                            init();

                        }else {

                       if( ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)||
                               ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)||
                               ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)||
                               ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_PHONE_STATE)||
                               ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){

                           showDialogOK("Camera ,Gallery and Contact Permission required for this app", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {
                                   switch (i) {
                                       case DialogInterface.BUTTON_POSITIVE:
                                           requestPermission();
                                           break;
                                       case DialogInterface.BUTTON_NEGATIVE:
                                           // proceed with logic by disabling the related features or quit the app.
                                           init();
                                           break;
                                   }
                               }
                           });

                       }

                        }



                }
                break;
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
}
