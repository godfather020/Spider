package com.example.spider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.spider.api.RetrofitInstance;
import com.example.spider.api.RetrofitInterface;
import com.example.spider.databinding.ActivityMainBinding;
import com.example.spider.firebase.MyFirebaseMessagingService;
import com.example.spider.model.DATA;
import com.example.spider.model.Noti_Data;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.Response;
import com.example.spider.model.UserDetail;
import com.example.spider.ui.activity.Login_Activity;
import com.example.spider.ui.fragment.Concern_List_Fragment;
import com.example.spider.ui.fragment.Deposite_Fragment;
import com.example.spider.ui.fragment.Home_Fragment;
import com.example.spider.ui.fragment.My_Id_Fragment;
import com.example.spider.ui.fragment.Notification_Fragment;
import com.example.spider.ui.fragment.Offer_Fragment;
import com.example.spider.ui.fragment.Passbook_Fragment;
import com.example.spider.ui.fragment.Paymet_Detail_Fragment;
import com.example.spider.ui.fragment.Profile_Frament;
import com.example.spider.ui.fragment.Refer_Earn_Fragment;
import com.example.spider.ui.fragment.Term_Condition_Fragment;
import com.example.spider.ui.fragment.Website_List_Fragment;
import com.example.spider.ui.fragment.Withdraw_Fragment;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.Constant;
import com.example.spider.utils.ExtentionUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private AppSharedPref mAppSharePref;
    private ExtentionUtils utils = new ExtentionUtils();
    public ActivityMainBinding activityMainBinding;
    Toolbar toolbar;
    public TextView toolbar_title;
    ActionBarDrawerToggle toggle;
    FragmentManager fragmentManager = getSupportFragmentManager();
    private final static int HOME_ID = 1;
    private final static int OFFER_ID = 2;
    private final static int PASSBOOK_ID = 3;
    private final static int IDS_ID = 4;
    private Fragment fragment;
    ExtentionUtils extentionUtils=new ExtentionUtils();
    RetrofitInterface retrofitInterface;
    Intent intentNotification;
    boolean comeFromNoti =false;
    Noti_Data noti_data;
    private long pressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* ComponentName componentName = new ComponentName(
                getApplicationContext(),
                MyFirebaseMessagingService.class);

        getApplicationContext().getPackageManager().setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);*/
        Log.e("onCreate","Runnnnnnnn........MainActivity");

        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
//        activityMainBinding.setLifecycleOwner(this);


        mAppSharePref = new AppSharedPref(getApplicationContext());
        mAppSharePref.saveInteger("entry_exit_fragment",2);
        fragment = new Home_Fragment();
        utils.loadFragment(fragmentManager, fragment, R.id.main_container, true,getResources().getString(R.string.home), null,MainActivity.this);

        UserDetail userDetail = mAppSharePref.getUserData();

        intentNotification=getIntent();
        if(intentNotification!=null){

            if(intentNotification.hasExtra("notification")){
                comeFromNoti=true;
                noti_data= (Noti_Data) intentNotification.getSerializableExtra("noti_data");
            }
        }

//       utils.showToast(getApplicationContext(),userDetail.getName()+" "+userDetail.getMobNO());


        toolbar = activityMainBinding.toolbarLayout.findViewById(R.id.toolbar);
        toolbar_title = activityMainBinding.toolbarLayout.findViewById(R.id.txt_toolbar_title);

        toolbar_title.setText(getResources().getString(R.string.dashboard));

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toggle = new ActionBarDrawerToggle(this, activityMainBinding.drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        activityMainBinding.drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);

        activityMainBinding.bottomNavigationView.add(new MeowBottomNavigation.Model(HOME_ID, R.drawable.ic_baseline_home_24));
        activityMainBinding.bottomNavigationView.add(new MeowBottomNavigation.Model(OFFER_ID, R.drawable.ic_baseline_local_offer_24));
        activityMainBinding.bottomNavigationView.add(new MeowBottomNavigation.Model(PASSBOOK_ID, R.drawable.ic_baseline_passbook_24));
        activityMainBinding.bottomNavigationView.add(new MeowBottomNavigation.Model(IDS_ID, R.drawable.ic_baseline_my_id_24));



//        activityMainBinding.bottomNavigationView.show(1,true);


        activityMainBinding.bottomNavigationView.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

//                Toast.makeText(MainActivity.this, " "+item.getId(), Toast.LENGTH_SHORT).show();
                switch (item.getId()) {

                    case HOME_ID:
                        mAppSharePref.saveInteger("entry_exit_fragment",2);
                        fragment = new Home_Fragment();
                        utils.loadFragment(fragmentManager, fragment, R.id.main_container, true, getResources().getString(R.string.home), null,MainActivity.this);
                        for(int i=0; i<fragmentManager.getBackStackEntryCount();i++){

                            fragmentManager.popBackStackImmediate();
                        }
                        mAppSharePref.saveInteger("bottomMenuNo",1);
                        break;

                    case OFFER_ID:
                        if(mAppSharePref.getInteger("bottomMenuNo")>=2){
                            mAppSharePref.saveInteger("entry_exit_fragment",2);
                        }else
                            mAppSharePref.saveInteger("entry_exit_fragment",1);
                        fragment = new Offer_Fragment();
                        utils.loadFragment(fragmentManager, fragment, R.id.main_container, true, getResources().getString(R.string.offer), null,MainActivity.this);
                        mAppSharePref.saveInteger("bottomMenuNo",2);
                        break;

                    case PASSBOOK_ID:

                        if(mAppSharePref.getInteger("bottomMenuNo")>=3){
                            mAppSharePref.saveInteger("entry_exit_fragment",2);
                        }else
                            mAppSharePref.saveInteger("entry_exit_fragment",1);
                        fragment = new Passbook_Fragment();
                        utils.loadFragment(fragmentManager, fragment, R.id.main_container, true, getResources().getString(R.string.tran_history), null,MainActivity.this);
                        mAppSharePref.saveInteger("bottomMenuNo",3);
                        break;

                    case IDS_ID:
                        mAppSharePref.saveInteger("entry_exit_fragment",1);
                        fragment = new My_Id_Fragment();
                        utils.loadFragment(fragmentManager, fragment, R.id.main_container, true, getResources().getString(R.string.my_id), null,MainActivity.this);
                        mAppSharePref.saveInteger("bottomMenuNo",4);
                        break;


                }

            }
        });
        activityMainBinding.bottomNavigationView.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });

        activityMainBinding.bottomNavigationView.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
//                Toast.makeText(MainActivity.this, "Clicked === "+item.getId(), Toast.LENGTH_SHORT).show();

              /*  switch (item.getId()) {

                    case HOME_ID:
                        fragment = new Home_Fragment();
                        utils.loadFragment(getSupportFragmentManager(), fragment, R.id.main_container, true, getResources().getString(R.string.home), null);
                        for(int i=0; i<fragmentManager.getBackStackEntryCount();i++){

                            fragmentManager.popBackStackImmediate();
                        }

                        break;

                    case OFFER_ID:
                        fragment = new Offer_Fragment();
                        utils.loadFragment(getSupportFragmentManager(), fragment, R.id.main_container, true, getResources().getString(R.string.offer), null);
                        break;

                    case PASSBOOK_ID:

                        fragment = new Passbook_Fragment();
                        utils.loadFragment(getSupportFragmentManager(), fragment, R.id.main_container, true, getResources().getString(R.string.tran_history), null);
                        break;

                    case IDS_ID:
                        fragment = new My_Id_Fragment();
                        utils.loadFragment(getSupportFragmentManager(), fragment, R.id.main_container, true, getResources().getString(R.string.create_id), null);
                        break;


                }*/

            }
        });
        activityMainBinding.bottomNavigationView.show(1,true);

        View view=activityMainBinding.navView.getHeaderView(0);

        TextView txtUserName=view.findViewById(R.id.txt_user_name);
        TextView txtUserContact=view.findViewById(R.id.txt_user_mob_no);

        txtUserName.setText(mAppSharePref.getUserData().getName());
        txtUserContact.setText(mAppSharePref.getUserData().getContact());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new Profile_Frament();
                utils.loadFragment(fragmentManager, fragment, R.id.main_container, false, "Profile", null);
                closeDrawer();
            }
        });

        activityMainBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_payment_detail:
                        fragment = new Paymet_Detail_Fragment();

                        Bundle bundle=new Bundle();
                        bundle.putBoolean("sideNav",true);
                        fragment.setArguments(bundle);
                        utils.loadFragment(fragmentManager, fragment, R.id.main_container, false, getResources().getString(R.string.payment_detail), null);
                        closeDrawer();
                        item.setCheckable(true);
                        break;
                    case R.id.nav_create_id:
                        fragment = new Website_List_Fragment();
                        utils.loadFragment(fragmentManager, fragment, R.id.main_container, false, getResources().getString(R.string.create_id), null);
                        closeDrawer();
                        item.setCheckable(true);
                        break;
                    case R.id.nav_my_id:
                        fragment = new My_Id_Fragment();
                        Bundle bundle1=new Bundle();
                        bundle1.putBoolean("sideNav",true);
                        fragment.setArguments(bundle1);
                        utils.loadFragment(fragmentManager, fragment, R.id.main_container, false, getResources().getString(R.string.my_id), bundle1);

                        closeDrawer();
                        item.setCheckable(true);
                        break;
                    case R.id.nav_deposite:
                        fragment = new Deposite_Fragment();
                        utils.loadFragment(fragmentManager, fragment, R.id.main_container, false, getResources().getString(R.string.deposit), null);
                        closeDrawer();
                        item.setCheckable(true);
                        break;
                    case R.id.nav_withdraw:
                        fragment = new Withdraw_Fragment();
                        utils.loadFragment(fragmentManager, fragment, R.id.main_container, false, getResources().getString(R.string.withdraw), null);
                        closeDrawer();
                        item.setCheckable(true);
                        break;
                    case R.id.nav_tran_history:
                        fragment = new Passbook_Fragment();
                        Bundle bundle2=new Bundle();
                        bundle2.putBoolean("sideNav",true);
                        fragment.setArguments(bundle2);
                        utils.loadFragment(fragmentManager, fragment, R.id.main_container, false, getResources().getString(R.string.tran_history), bundle2);
//                        activityMainBinding.bottomNavigationView.show(3,true);
                        closeDrawer();
                        item.setCheckable(true);
                        break;
                    case R.id.nav_refer_earn:
                        fragment = new Refer_Earn_Fragment();
                        utils.loadFragment(fragmentManager, fragment, R.id.main_container, false, getResources().getString(R.string.refer_earn), null);
                        closeDrawer();
                        item.setCheckable(true);
                        break;
                    case R.id.nav_noti:
                        fragment = new Notification_Fragment();
                        utils.loadFragment(fragmentManager, fragment, R.id.main_container, false, getResources().getString(R.string.notifiction), null);
                        closeDrawer();
                        item.setCheckable(true);
                        break;
                    case R.id.nav_help:
                        fragment = new Concern_List_Fragment();
                        utils.loadFragment(fragmentManager, fragment, R.id.main_container, false, getResources().getString(R.string.raise_a_concern), null);
                        closeDrawer();
                        item.setCheckable(true);
                        break;
                    case R.id.nav_term_condition:
                        fragment = new Term_Condition_Fragment();
                        utils.loadFragment(fragmentManager, fragment, R.id.main_container, false, getResources().getString(R.string.term_condition), null);
                        closeDrawer();
                        item.setCheckable(true);
                        break;
                    case R.id.nav_sign_out:

                        AlertDialog.Builder  builder=new AlertDialog.Builder(MainActivity.this);
                        builder.setCancelable(false);
                        builder.setTitle("Are you want to logout");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                sign_out();
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
        });


        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(fragmentManager.getBackStackEntryCount()>0){

                    lockDrawer();
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            fragmentManager.popBackStack();
                        }
                    });
                toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24));
               /* int index=fragmentManager.getBackStackEntryCount()-1;
                FragmentManager.BackStackEntry backStackEntry=fragmentManager.getBackStackEntryAt(index);
                if(!TextUtils.isEmpty(backStackEntry.getName())){
                    String tag=backStackEntry.getName();
               fragment= fragmentManager.findFragmentByTag(tag);
                }*/


                }else {

                    unlockDrawer();
                    showBottomNavigation();
                    toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        openDrawer();
                        }
                    });




                }
            }
        });


        if(comeFromNoti){


            if(noti_data.getType().equals(getResources().getString(R.string.noti_withdraw))){

                Fragment fragment=new Passbook_Fragment();

                utils.loadFragment(fragmentManager,fragment,R.id.main_container,false,getResources().getString(R.string.tran_history),null);
            }else  if(noti_data.getType().equals(getResources().getString(R.string.noti_deposite))){

                Fragment fragment=new Passbook_Fragment();

                utils.loadFragment(fragmentManager,fragment,R.id.main_container,false,getResources().getString(R.string.tran_history),null);
            }else  if(noti_data.getType().equals(getResources().getString(R.string.noti_clientId))){

                Fragment fragment=new My_Id_Fragment();

                utils.loadFragment(fragmentManager,fragment,R.id.main_container,false,getResources().getString(R.string.my_id),null);
            }
        }
    }

    @Override
    public void onBackPressed() {

        if (fragmentManager.getBackStackEntryCount() > 0) {

            super.onBackPressed();
            /*lockDrawer();
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragmentManager.popBackStack();
                }
            });
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24));
               *//* int index=fragmentManager.getBackStackEntryCount()-1;
                FragmentManager.BackStackEntry backStackEntry=fragmentManager.getBackStackEntryAt(index);
                if(!TextUtils.isEmpty(backStackEntry.getName())){
                    String tag=backStackEntry.getName();
               fragment= fragmentManager.findFragmentByTag(tag);
                }*/

        } else {

            if (activityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {

                closeDrawer();
            } else {
           /* unlockDrawer();
            showBottomNavigation();
            toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDrawer();
                }
            });*/
                if (pressedTime + 2000 > System.currentTimeMillis()) {
                    super.onBackPressed();
                    finish();
                } else {
                    utils.showToast(this, "Press back again to exit");
                }
                pressedTime = System.currentTimeMillis();
            }
        }
    }

    private void sign_out(){
       retrofitInterface= RetrofitInstance.getmRetrofitInstance().create(RetrofitInterface.class);
       Organisation_Pojo organisation_pojo=new Organisation_Pojo(Constant.FUNC_LOGOUT,new DATA(mAppSharePref.getUserData().getId(),
               "12345",null));
       String jsonBody=new Gson().toJson(organisation_pojo,Organisation_Pojo.class);
       Log.d("loginResponse",jsonBody);
       RequestBody param=extentionUtils.toRequestBody(jsonBody);

       Call<Response> call =retrofitInterface.logout(param);

       call.enqueue(new Callback<Response>() {
           @Override
           public void onResponse(Call<com.example.spider.model.Response> call, retrofit2.Response<Response> response) {

               if(response.body().getMsgCode()==1){
                   extentionUtils.showToast(getApplicationContext(),response.body().getMessage());
                   mAppSharePref.clearData();
                   Intent logoutIntent=new Intent(MainActivity.this, Login_Activity.class);
                   startActivity(logoutIntent);
                   finish();

               }else {

                   extentionUtils.showToast(getApplicationContext(),response.body().getMessage());
               }


           }

           @Override
           public void onFailure(Call<com.example.spider.model.Response> call, Throwable t) {
//                Log.e("loginResponse","error=== "+t.getMessage().toString());
               extentionUtils.showToast(getApplicationContext(),t.getMessage());

           }
       });

    }
    public void closeDrawer(){


        activityMainBinding.drawerLayout.closeDrawer(Gravity.LEFT,true);
    }
    public void openDrawer(){


        activityMainBinding.drawerLayout.openDrawer(Gravity.LEFT,true);
    }
    public void lockDrawer(){

        activityMainBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    } public void unlockDrawer(){

        activityMainBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

    }
    public void showBottomNavigation(){

        activityMainBinding.bottomNavigationView.setVisibility(View.VISIBLE);
    }
    public void hideBottomNavigation(){

        activityMainBinding.bottomNavigationView.setVisibility(View.GONE);
    }

public void setBottomBarTab(int tab_no){

        activityMainBinding.bottomNavigationView.show(tab_no,true);
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