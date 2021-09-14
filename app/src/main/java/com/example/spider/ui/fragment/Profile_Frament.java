package com.example.spider.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.api.RetrofitInstance;
import com.example.spider.api.RetrofitInterface;
import com.example.spider.databinding.ProfileFragmentBinding;
import com.example.spider.model.DATA;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.Response;
import com.example.spider.ui.activity.Forgot_Activity;
import com.example.spider.ui.activity.Login_Activity;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.Constant;
import com.example.spider.utils.ExtentionUtils;
import com.google.gson.Gson;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Profile_Frament extends Fragment {

    MainActivity activity;
    ExtentionUtils utils=new ExtentionUtils();
    ProfileFragmentBinding binding;
    RetrofitInterface retrofitInterface;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=(MainActivity)getActivity();
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater, R.layout.profile_fragment,container,false);
        View view=binding.getRoot();


        init();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.option_menu_sign_out,menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.signOut:
//                fragment = new Notification_Fragment();
//                utils.loadFragment(activity.getSupportFragmentManager(), fragment, R.id.main_container, false, getResources().getString(R.string.notifiction), null);


                AlertDialog.Builder  builder=new AlertDialog.Builder(activity);
                builder.setCancelable(false);
                builder.setTitle("Are you want to logout");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        signOut();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog=builder.create();
                alertDialog.show();

                break;
        }
        return true;
    }
    public void init(){
        binding.textUserName.setText(new AppSharedPref(activity).getUserData().getName());
        binding.textUserMobNo.setText(new AppSharedPref(activity).getUserData().getContact());
        binding.textRewardAmt.setText(new AppSharedPref(activity).getString(new Home_Fragment().REWARD_AMT));
        binding.textReferredAmt.setText(new AppSharedPref(activity).getString(new Home_Fragment().REFERAL_USER));

        binding.txtSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder  builder=new AlertDialog.Builder(activity);
                builder.setCancelable(false);
                builder.setTitle("Are you want to logout");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        signOut();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

        binding.btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        binding.imgReferBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new Refer_Earn_Fragment();
                utils.loadFragment(activity.getSupportFragmentManager(), fragment, R.id.main_container, false, getResources().getString(R.string.refer_earn), null);
            }
        });
    }

    public void changePassword(){


        Intent intent = new Intent(activity, Forgot_Activity.class);

        intent.putExtra("phone_no",new AppSharedPref(activity).getUserData().getContact());
        startActivity(intent);

    }

    public void signOut(){

        retrofitInterface= RetrofitInstance.getmRetrofitInstance().create(RetrofitInterface.class);
        Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_LOGOUT,new DATA(new AppSharedPref(activity).getUserData().getId(),
                "12345",null));
        String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
        Log.d("loginResponse",jsonBody);
        RequestBody param=utils.toRequestBody(jsonBody);

        Call<Response> call =retrofitInterface.logout(param);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<com.example.spider.model.Response> call, retrofit2.Response<Response> response) {

                if(response.body().getMsgCode()==1){
                    utils.showToast(activity,response.body().getMessage());
                    new AppSharedPref(activity).clearData();
                    Intent logoutIntent=new Intent(activity, Login_Activity.class);
                    startActivity(logoutIntent);
                    activity.finish();

                }else {

                    utils.showToast(activity,response.body().getMessage());
                }


            }

            @Override
            public void onFailure(Call<com.example.spider.model.Response> call, Throwable t) {
//                Log.e("loginResponse","error=== "+t.getMessage().toString());
                utils.showToast(activity,t.getMessage());

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.toolbar_title.setText(Profile_Frament.this.getTag());
        activity.hideBottomNavigation();
        Log.d("lifecycle","onResume invoked");

    }
}
