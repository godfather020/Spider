package com.example.spider.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.spider.databinding.WithdrawFragmentBinding;
import com.example.spider.databinding.WithdrawIdFragmentBinding;
import com.example.spider.model.Approvedrequestidlist;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Withdraw_Fragment_viewModel;
import com.example.spider.view_model.Withdraw_id_Fragment_viewModel;

public class Withdraw_id_Fragment extends Fragment {

    WithdrawIdFragmentBinding binding;
    Withdraw_id_Fragment_viewModel viewModel;
    MainActivity activity=new MainActivity();
    ExtentionUtils utils=new ExtentionUtils();
    String withdrawAmt,walletAmt,min_withdrawAmt;
    boolean withdraw;
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

        binding= DataBindingUtil.inflate(inflater,R.layout.withdraw_id_fragment,container,false);

        activity.toolbar_title.setText(getResources().getString(R.string.withdraw));
        activity.hideBottomNavigation();

        View view =binding.getRoot();
        viewModel=new ViewModelProvider(activity).get(Withdraw_id_Fragment_viewModel.class);
        init();

        return view;
    }

    public void init(){


        binding.txtWebsiteName.setText(approvedrequestidlist.getWebsitename());
        binding.txtWebsiteNameLink.setText(approvedrequestidlist.getWebsitename());
        binding.txtUserIdName.setText(approvedrequestidlist.getUsername());
        binding.textView3.setText(activity.getResources().getString(R.string.min_withdraw_amt)+" "+approvedrequestidlist.getMinwithdraw());
        binding.txtWithdrawLimit.setText(activity.getResources().getString(R.string.withdraw_limit)+" "+approvedrequestidlist.getMaxwithdraw());
        utils.setCircleImage(approvedrequestidlist.getWebsitephoto(),binding.imgAppLogo,activity.getResources().getDrawable(R.drawable.app_logo));

        binding.btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withdrawAmt = binding.edtWithdrawCoin.getText().toString();
                if(!withdrawAmt.equals("0")) {
                    if (withdrawAmt != null && !withdrawAmt.isEmpty()) {


                        if(binding.rdBtnToWallet.isChecked()){

                            binding.progressBar.setVisibility(View.VISIBLE);
                            viewModel.withdraw(activity,binding,withdrawAmt,null,approvedrequestidlist.getId()).observe(activity,success -> {


                                if(success.equals("1")){
                                    binding.progressBar.setVisibility(View.GONE);
                                    Fragment fragment =new Home_Fragment();
                                    utils.loadFragment(activity.getSupportFragmentManager(), fragment, R.id.main_container, true,
                                            activity.getResources().getString(R.string.home), null);
                                }
                            });

                        } else if(binding.rdBtnToBank.isChecked()) {
                            Fragment fragment = new Paymet_Method_Fragment();

                            Bundle arg = new Bundle();
                            arg.putString("withdrawAmt", withdrawAmt);
                            arg.putBoolean("withdraw", true);
                            arg.putString("website_id", approvedrequestidlist.getId());
                            utils.loadFragment(activity.getSupportFragmentManager(), fragment, R.id.main_container,
                                    false, activity.getResources().getString(R.string.payment_method), arg);
                        }else {

                            utils.showToast(activity, "Please select withdraw method.");
                        }

                    } else {

                        utils.showToast(activity, "Please fill mandatory field !");
                    }
                }else {

                    utils.showToast(activity, "Please enter valid amount.");
                }
            }
        });

        binding.edtWithdrawCoin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

               /* if(binding.cbDepositeReward.isChecked()){

                    if(binding.edtWithdrawCoin.getText().toString()!=null&&!binding.edtWithdrawCoin.getText().toString().isEmpty())
                    {
                        total_depositeAmt = Integer.parseInt(binding.edtWithdrawCoin.getText().toString()) + rewards;
                    }else total_depositeAmt=rewards;
                    binding.textTotalDeposit.setVisibility(View.VISIBLE);
                    binding.textTotalDeposit.setText("Total Deposit = "+total_depositeAmt);
                }*/

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (s.toString().length() >= approvedrequestidlist.getMinwithdraw().toString().length()) {
                            if (binding.edtWithdrawCoin.getText().toString() != null && !binding.edtWithdrawCoin.getText().toString().isEmpty() &&
                                    !binding.edtWithdrawCoin.getText().toString().equals("")) {

                                if (Integer.parseInt(binding.edtWithdrawCoin.getText().toString()) <= Integer.parseInt(approvedrequestidlist.getMaxwithdraw().toString())) {

                                if (Integer.parseInt(binding.edtWithdrawCoin.getText().toString()) < Integer.parseInt(approvedrequestidlist.getMinwithdraw().toString())) {

                                    utils.showToast(activity, "Please withdraw min amount " + approvedrequestidlist.getMinwithdraw().toString());
                                    binding.btnWithdraw.setEnabled(false);
                                    binding.btnWithdraw.setAlpha(0.5f);
                                    binding.edtWithdrawCoin.setFocusable(true);
                                } else {

                                    binding.btnWithdraw.setEnabled(true);
                                    binding.btnWithdraw.setAlpha(1f);
                                }
                                }else {

                                    utils.showToast(activity, "Please withdraw max amount " + approvedrequestidlist.getMaxwithdraw().toString());
                                    binding.btnWithdraw.setEnabled(false);
                                    binding.btnWithdraw.setAlpha(0.5f);
                                    binding.edtWithdrawCoin.setFocusable(true);
                                }
                            }

                        } else {
                            utils.showToast(activity, "Please withdraw min amount " + approvedrequestidlist.getMinwithdraw().toString());
                            binding.btnWithdraw.setEnabled(false);
                            binding.btnWithdraw.setAlpha(0.5f);
                            binding.edtWithdrawCoin.setFocusable(true);
                        }
                    }
                }, 1500);

            }
        });
    }
}
