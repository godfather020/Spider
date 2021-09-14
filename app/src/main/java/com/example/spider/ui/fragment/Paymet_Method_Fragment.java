package com.example.spider.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.adapter.Payment_Detail_Adapter;
import com.example.spider.adapter.Payment_Method_Adapter;
import com.example.spider.databinding.PaymentDetailFragmentBinding;
import com.example.spider.databinding.PaymentMethodFragmentBinding;
import com.example.spider.model.Account;
import com.example.spider.model.DATA;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.Pymentmethod;
import com.example.spider.model.Website_item;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.Constant;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Paymetn_Method_viewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

public class Paymet_Method_Fragment extends Fragment {

    PaymentMethodFragmentBinding binding;
    Paymetn_Method_viewModel paymetn_method_viewModel;
    Payment_Method_Adapter adapter;
    RecyclerView.LayoutManager manager;
    Payment_Method_Adapter.ClickListiner clickListiner;
    MainActivity activity;
    AppSharedPref mAppSharedPref;
    Website_item website_item;
    ExtentionUtils utils =new ExtentionUtils();
    String userName,depositeAmt,withdrawAmt,websiteId,rewards="0";
    boolean deposite=false,withdraw=false,adminAcc=false;
    List<Account> user_account_detail=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.payment_method_fragment,container,false);
        activity=(MainActivity) getActivity();
        mAppSharedPref=new AppSharedPref(activity);
        paymetn_method_viewModel=new ViewModelProvider(activity).get(Paymetn_Method_viewModel.class);
        View view =binding.getRoot();
        initView();
        return view;
    }

    public void initView(){

        Bundle bundle=getArguments();
        if(bundle!=null){
            website_item = (Website_item) bundle.getSerializable("selected_website_item");
            userName=bundle.getString("userName");
            depositeAmt=bundle.getString("depositAmt");
            deposite=bundle.getBoolean("deposit",false);
            withdraw=bundle.getBoolean("withdraw",false);
            withdrawAmt=bundle.getString("withdrawAmt");
            websiteId=bundle.getString("website_id",null);
            rewards=bundle.getString("reward","0");
        }
        if(deposite){
            adminAcc=deposite;
        }else if(website_item!=null){
            adminAcc=true;
        }else adminAcc=false;
        binding.progressBar.setVisibility(View.VISIBLE);
        paymetn_method_viewModel.getPaymetMethodList(activity,binding,adminAcc).observe(activity,pymentmethods -> {

            if(pymentmethods!=null) {
//                binding.progressBar.setVisibility(View.GONE);
                paymetn_method_viewModel.getAccList(activity,binding).observe(activity,accounts -> {

                    if(accounts!=null) {
                        binding.progressBar.setVisibility(View.GONE);

                        user_account_detail=accounts;
                    }
                });

                manager = new LinearLayoutManager(getContext());
                adapter = new Payment_Method_Adapter(getContext(), adminAcc,pymentmethods, clickListiner = new Payment_Method_Adapter.ClickListiner() {
                    @Override
                    public void getPaymentMethod(Pymentmethod pymentmethod) {


                        if (!withdraw) {
                            Fragment fragment = new Payment_Fragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("pymentmethod", pymentmethod);
                            bundle.putString("reward", rewards);
                            if (!deposite) {
                                bundle.putString("userName", userName);
                                bundle.putString("depositAmt", depositeAmt);
                                bundle.putSerializable("selected_website_item", website_item);
                            } else {
                                bundle.putString("depositAmt", depositeAmt);
                                bundle.putBoolean("deposit", true);
                                if (websiteId != null) {
                                    bundle.putString("website_id", websiteId);
                                }
                            }

                            utils.loadFragment(activity.getSupportFragmentManager(), fragment, R.id.main_container, false,
                                    getResources().getString(R.string.payment), bundle);
                        } else {

                            if (accValid(user_account_detail, pymentmethod)) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setCancelable(false);
                            builder.setTitle("Are you want to withdraw your " + pymentmethod.getName() + " account?");

                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    if (websiteId != null) {
                                        withdraw(pymentmethod.getId(), websiteId);
                                    } else {

                                        withdraw(pymentmethod.getId(), null);
                                    }

                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        }
                    }


                    }
                });


                binding.rvPaymentMethod.setLayoutManager(manager);
                binding.rvPaymentMethod.setAdapter(adapter);

            }

        });


        binding.rvPaymentMethod.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    activity.activityMainBinding.bottomNavigationView.setTranslationY(150);
                } else if (dy < 0 ) {
                    activity.activityMainBinding.bottomNavigationView.setTranslationY(0);
//                   activity.showBottomNavigation();

                }
            }
        });

    }

    private boolean accValid(List<Account> accounts, Pymentmethod pymentmethod) {
        boolean valid=false;

        if(accounts!=null && !accounts.isEmpty()) {
            if (pymentmethod.getId().equals("1")) {

                if (accounts.get(0).getAccountdetail().getPaytmUPI() != null && !accounts.get(0).getAccountdetail().getPaytmUPI().isEmpty()) {

                    valid = true;
                } else {
                    valid = false;
                    utils.showToast(activity, "Please add your " + pymentmethod.getName() + " acc detail.");
                }

            } else if (pymentmethod.getId().equals("2")) {

                if (accounts.get(0).getAccountdetail().getGooglePay() != null && !accounts.get(0).getAccountdetail().getGooglePay().isEmpty()) {

                    valid = true;
                } else {
                    valid = false;
                    utils.showToast(activity, "Please add your " + pymentmethod.getName() + " acc detail.");
                }

            } else if (pymentmethod.getId().equals("3")) {

                if (accounts.get(0).getAccountdetail().getPhonePay() != null && !accounts.get(0).getAccountdetail().getPhonePay().isEmpty()) {

                    valid = true;
                } else {
                    valid = false;
                    utils.showToast(activity, "Please add your " + pymentmethod.getName() + " acc detail.");
                }

            } else if (pymentmethod.getId().equals("4")) {

                if (accounts.get(0).getAccountdetail().getBankTransfer() != null && !accounts.get(0).getAccountdetail().getBankTransfer().isEmpty()) {

                    valid = true;
                } else {
                    valid = false;
                    utils.showToast(activity, "Please add your " + pymentmethod.getName() + " acc detail.");
                }

            } else if (pymentmethod.getId().equals("5")) {

                if (accounts.get(0).getAccountdetail().getPaytmWallet() != null && !accounts.get(0).getAccountdetail().getPaytmWallet().isEmpty()) {

                    valid = true;
                } else {
                    valid = false;
                    utils.showToast(activity, "Please add your " + pymentmethod.getName() + " acc detail.");
                }

            }
        }else {
            valid = false;
            utils.showToast(activity, "Please add your any one acc detail.");
            utils.loadFragment(activity.getSupportFragmentManager(), new Paymet_Detail_Fragment(), R.id.main_container, false,
                    getResources().getString(R.string.payment_detail), null);
        }
        return valid;
    }

    private void withdraw(String id,String websiteId) {

        binding.progressBar.setVisibility(View.VISIBLE);
        paymetn_method_viewModel.withdraw(activity,binding,withdrawAmt,id,websiteId).observe(activity,success -> {


            if(success.equals("1")){
                binding.progressBar.setVisibility(View.GONE);
                Fragment fragment =new Home_Fragment();
                utils.loadFragment(activity.getSupportFragmentManager(), fragment, R.id.main_container, true,
                        activity.getResources().getString(R.string.home), null);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.toolbar_title.setText(Paymet_Method_Fragment.this.getTag());

    }
}
