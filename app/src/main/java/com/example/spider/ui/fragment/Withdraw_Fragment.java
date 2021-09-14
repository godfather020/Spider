package com.example.spider.ui.fragment;

import android.os.Bundle;
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
import com.example.spider.databinding.HomeFragmentBinding;
import com.example.spider.databinding.WithdrawFragmentBinding;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Withdraw_Fragment_viewModel;

import java.util.Locale;

public class Withdraw_Fragment extends Fragment {

    WithdrawFragmentBinding binding;
    Withdraw_Fragment_viewModel viewModel;
    MainActivity activity=new MainActivity();
    ExtentionUtils utils=new ExtentionUtils();
    String withdrawAmt,walletAmt,min_withdrawAmt,reward;
    int totalAmt,withdraw_totalAmt;
    boolean withdraw;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.withdraw_fragment,container,false);
        activity= (MainActivity) getActivity();
        activity.toolbar_title.setText(getResources().getString(R.string.withdraw));
        activity.hideBottomNavigation();

        View view =binding.getRoot();
        viewModel=new ViewModelProvider(activity).get(Withdraw_Fragment_viewModel.class);
        init();

        return view;
    }

    public void init(){

        binding.progressBar.setVisibility(View.VISIBLE);
        viewModel.getWalletList(activity,binding).observe(activity,walletlist -> {

            if(walletlist!=null){
                binding.progressBar.setVisibility(View.GONE);
                walletAmt=walletlist.getTotal().toString();
                min_withdrawAmt=walletlist.getMinwithdraw().toString();
                reward=walletlist.getReward().toString();
                totalAmt=Integer.parseInt(walletAmt)+Integer.parseInt(reward);
                binding.txtWalletAmt.setText(walletAmt);
                binding.textView3.setText(activity.getResources().getString(R.string.min_withdraw_amt)+" "+min_withdrawAmt+" coins.");

                withdraw_totalAmt=totalAmt-Integer.parseInt(new AppSharedPref(activity).getString(new Home_Fragment().REWARD_AMT));


            }
        });

        binding.btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                withdrawAmt = binding.edtDepositeCoin.getText().toString();

                if(Integer.parseInt(withdrawAmt)>totalAmt){

                    utils.showToast(activity, "Insufficient balance");


                }
                else {
                if(!withdrawAmt.equals("0")) {
                    if (withdrawAmt != null && !withdrawAmt.isEmpty()) {

                        if (Integer.parseInt(withdrawAmt) >= Integer.parseInt(min_withdrawAmt)) {

                            if(Integer.parseInt(withdrawAmt)<=withdraw_totalAmt){

                            Fragment fragment = new Paymet_Method_Fragment();

                            Bundle arg = new Bundle();


                            arg.putString("withdrawAmt", withdrawAmt);
                            arg.putBoolean("withdraw", true);
                            utils.loadFragment(activity.getSupportFragmentManager(), fragment, R.id.main_container,
                                    false, activity.getResources().getString(R.string.payment_method), arg);
                            }
                            else {
                                utils.showToast(activity, "You can not withdraw reward amount (" +  new AppSharedPref(activity).getString(new Home_Fragment().REWARD_AMT) + ") you can withdraw only "+withdraw_totalAmt+" coins.");
                            }
                        }
                        else {
                            utils.showToast(activity, "You are not withdraw less than " + min_withdrawAmt + " coins.");

                        }
                    }
                    else {

                        utils.showToast(activity, "Please fill mandatory field !");
                    }
                }
                else {

                    utils.showToast(activity, "Please enter valid amount.");
                }
                }
            }
        });
    }
}
