package com.example.spider.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.databinding.DepositIdFragmentBinding;


import com.example.spider.model.Approvedrequestidlist;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Deposite_Fragment_viewModel;
import com.example.spider.view_model.Deposite_id_Fragment_viewModel;

public class Deposite_id_Fragment extends Fragment {

    DepositIdFragmentBinding binding;
    Deposite_id_Fragment_viewModel viewModel;
    MainActivity activity;
    ExtentionUtils utils =new ExtentionUtils();
    String depositeAmt,min_depositAmt;
    int walletAmt,rewards,total_depositeAmt=0;
    boolean comefromid=false;
    Approvedrequestidlist approvedrequestidlist;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity= (MainActivity) getActivity();
        Bundle bundle=getArguments();
        if(bundle!=null){

            approvedrequestidlist=(Approvedrequestidlist) bundle.getSerializable("id_data");
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.deposit_id_fragment,container,false);


//        activity.toolbar_title.setText(this.getTag());
        viewModel=new ViewModelProvider(activity).get(Deposite_id_Fragment_viewModel.class);
        activity.hideBottomNavigation();
        View view =binding.getRoot();

        binding.progressBar.setVisibility(View.VISIBLE);
        viewModel.getWalletList(activity,binding).observe(activity,walletlist -> {

            if(walletlist!=null){
            binding.progressBar.setVisibility(View.GONE);
                walletAmt=walletlist.getTotal();
                rewards=Integer.parseInt(walletlist.getReward());
                if(walletAmt>0) {
                    binding.cbDepositeFromWallet.setVisibility(View.VISIBLE);
                    binding.cbDepositeFromWallet.setText("Pay from wallet \n(Current Wallet Balance " + walletAmt+")");
                }else {

                    binding.cbDepositeFromWallet.setVisibility(View.GONE);
                }
            /*    if(rewards>0) {
                    binding.cbDepositeReward.setVisibility(View.VISIBLE);
                    binding.cbDepositeReward.setText("Use Rewards \n(Current Rewards " + rewards+")");
                }else {

                    binding.cbDepositeReward.setVisibility(View.GONE);
                }*/


            }
        });

//        binding.cbDepositeFromWallet.setVisibility(View.VISIBLE);
        binding.txtWebsiteName.setText(approvedrequestidlist.getWebsitename());
        binding.txtWebsiteNameLink.setText(approvedrequestidlist.getWebsitename());
        binding.txtUserIdName.setText(approvedrequestidlist.getUsername());
        binding.textView3.setText(activity.getResources().getString(R.string.min_deposit_amt)+" "+approvedrequestidlist.getMinrefil());
        utils.setCircleImage(approvedrequestidlist.getWebsitephoto(),binding.imgAppLogo,activity.getResources().getDrawable(R.drawable.app_logo));

        binding.btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                depositeAmt = binding.edtDepositeCoin.getText().toString();
                if (depositeAmt != null && !depositeAmt.isEmpty()) {


                    if(!binding.cbDepositeFromWallet.isChecked()) {
                        if(!binding.cbDepositeReward.isChecked()){
                            rewards=0;
                        }
                            Fragment fragment = new Paymet_Method_Fragment();

                        Bundle arg = new Bundle();


                        arg.putString("depositAmt", binding.edtDepositeCoin.getText().toString());
                        arg.putBoolean("deposit", true);
                        arg.putString("website_id", approvedrequestidlist.getId());
                        arg.putString("reward", ""+rewards);
                        utils.loadFragment(activity.getSupportFragmentManager(), fragment, R.id.main_container,
                                false, activity.getResources().getString(R.string.payment_method), arg);


                    }
                    else {

                        if (Integer.parseInt(depositeAmt) > walletAmt) {
                            utils.showToast(activity, "Insufficient balance");
                        } else {
                            binding.progressBar.setVisibility(View.VISIBLE);
                            if(!binding.cbDepositeReward.isChecked()){
                                rewards=0;
                            }
                            viewModel.userDeposite(activity, binding, new AppSharedPref(activity).getUserData().getId(), depositeAmt,
                                    null, null, approvedrequestidlist.getId(),""+rewards).observe(activity, s -> {

                                if (s != null) {

                                    if (s.equals("1")) {

                                        binding.progressBar.setVisibility(View.GONE);
                                        Fragment fragment = new Home_Fragment();
                                        utils.loadFragment(activity.getSupportFragmentManager(), fragment, R.id.main_container, true, activity.getResources().getString(R.string.home), null);
                                    }
                                }


                            });
                        }
                    }

                }else {

                    utils.showToast(activity,"Please fill mandatory field !");
                }
            }
        });

        binding.cbDepositeReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.cbDepositeReward.isChecked()){
                    if(binding.edtDepositeCoin.getText().toString()!=null&&!binding.edtDepositeCoin.getText().toString().isEmpty())
                    {
                        total_depositeAmt = Integer.parseInt(binding.edtDepositeCoin.getText().toString()) + rewards;
                    }else total_depositeAmt=rewards;
                    binding.textTotalDeposit.setVisibility(View.VISIBLE);
                    binding.textTotalDeposit.setText("Total Deposit = "+total_depositeAmt);
                }else {

                    binding.textTotalDeposit.setVisibility(View.GONE);
                }
            }
        });

        binding.edtDepositeCoin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

               /* if(binding.cbDepositeReward.isChecked()){

                    if(binding.edtDepositeCoin.getText().toString()!=null&&!binding.edtDepositeCoin.getText().toString().isEmpty())
                    {
                        total_depositeAmt = Integer.parseInt(binding.edtDepositeCoin.getText().toString()) + rewards;
                    }else total_depositeAmt=rewards;
                    binding.textTotalDeposit.setVisibility(View.VISIBLE);
                    binding.textTotalDeposit.setText("Total Deposit = "+total_depositeAmt);
                }*/

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (s.toString().length() >= approvedrequestidlist.getMinrefil().toString().length()) {
                            if (binding.edtDepositeCoin.getText().toString() != null && !binding.edtDepositeCoin.getText().toString().isEmpty() &&
                                    !binding.edtDepositeCoin.getText().toString().equals("")) {

                                if (Integer.parseInt(binding.edtDepositeCoin.getText().toString()) < Integer.parseInt(approvedrequestidlist.getMinrefil().toString())) {

                                    utils.showToast(activity, "Please deposit min amount " + approvedrequestidlist.getMinrefil().toString());
                                    binding.btnDeposit.setEnabled(false);
                                    binding.btnDeposit.setAlpha(0.5f);
                                    binding.edtDepositeCoin.getText().clear();
                                    binding.edtDepositeCoin.setFocusable(true);
                                } else {

                                    binding.btnDeposit.setEnabled(true);
                                    binding.btnDeposit.setAlpha(1f);
                                }
                            }

                        } else {
                            utils.showToast(activity, "Please deposit min amount " + approvedrequestidlist.getMinrefil().toString());
                            binding.btnDeposit.setEnabled(false);
                            binding.btnDeposit.setAlpha(0.5f);
                            binding.edtDepositeCoin.setFocusable(true);
                        }
                    }
                }, 1000);

            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        activity.toolbar_title.setText(this.getTag());

    }
}
