package com.example.spider.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
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
import com.example.spider.databinding.CreateIdFragmentBinding;
import com.example.spider.databinding.DepositeFragmentBinding;
import com.example.spider.model.Website_item;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Create_Id_Fragment_viewModel;

public class Create_Id_Fragment extends Fragment {

    CreateIdFragmentBinding binding;
    MainActivity activity;
    Website_item website_item;
    ExtentionUtils utils = new ExtentionUtils();
    Create_Id_Fragment_viewModel viewModel;
    int walletAmt, rewards, total_depositeAmt = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.create_id_fragment, container, false);
        activity = (MainActivity) getActivity();
        viewModel = new ViewModelProvider(activity).get(Create_Id_Fragment_viewModel.class);
        Bundle bundle = getArguments();
        if (bundle != null) {

            website_item = (Website_item) bundle.getSerializable("selected_website_item");
        }

//        activity.toolbar_title.setText(this.getTag());
        binding.txtWebsiteName.setText(website_item.getName());
        binding.txtWebsiteNameLink.setText(website_item.getUrl());
        binding.txtMinRefil.setText(website_item.getMinrefil().toString());
        binding.txtMinWithdrawValue.setText(website_item.getMinwithdraw().toString());
        binding.txtMinMaintainBalance.setText(website_item.getMinmaintainbal().toString());
        binding.txtMaxWithdraw.setText(website_item.getMaxwithdraw().toString());
        binding.textView3.setText(activity.getResources().getString(R.string.min_deposit_amt) + " " + website_item.getMinrefil().toString());


        viewModel.getWalletList(activity, binding).observe(activity, walletlist -> {

            if (walletlist != null) {
                binding.progressBar.setVisibility(View.GONE);
                walletAmt = walletlist.getTotal();
                rewards = Integer.parseInt(walletlist.getReward());
                if (walletAmt > 0) {
                    binding.cbDepositeFromWallet.setVisibility(View.VISIBLE);
                    binding.cbDepositeFromWallet.setText("Pay from wallet \n(Current Wallet Balance " + walletAmt + ")");
                } else {

                    binding.cbDepositeFromWallet.setVisibility(View.GONE);
                }
               /* if(rewards>0) {
                    binding.cbDepositeReward.setVisibility(View.VISIBLE);
                    binding.cbDepositeReward.setText("Use Rewards \n(Current Rewards " + rewards+")");
                }else {

                    binding.cbDepositeReward.setVisibility(View.GONE);
                }*/


            }
        });

        binding.btnCreateId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Validation(binding.edtUserName.getText().toString(), binding.edtDepositeCoin.getText().toString())) {

                    if (!binding.cbDepositeFromWallet.isChecked()) {

                        Fragment fragment = new Paymet_Method_Fragment();
                        Bundle arg = new Bundle();

                        arg.putString("userName", binding.edtUserName.getText().toString());
                        arg.putString("depositAmt", binding.edtDepositeCoin.getText().toString());

                        arg.putSerializable("selected_website_item", website_item);

                        utils.loadFragment(activity.getSupportFragmentManager(), fragment, R.id.main_container,
                                false, activity.getResources().getString(R.string.payment_method), arg);
                    } else {
                        int depostiAmt = Integer.parseInt(binding.edtDepositeCoin.getText().toString());
                        if (depostiAmt > walletAmt) {

                            utils.showToast(activity, "Insufficient balance in your wallet");

                        } else {

                            viewModel.userCreateId(activity, binding, binding.edtUserName.getText().toString(), new AppSharedPref(activity).getUserData().getId(),
                                    website_item.getId(), binding.edtDepositeCoin.getText().toString(), "", "").observe(activity, it -> {

                                if (it.equals("1")) {

                                    binding.progressBar.setVisibility(View.GONE);
                                    Fragment fragment = new Home_Fragment();
                                    utils.loadFragment(activity.getSupportFragmentManager(),
                                            fragment, R.id.main_container, true, activity.getResources().getString(R.string.home), null);


                                }

                            });
                        }
                    }
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


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (s.toString().length() >= website_item.getMinrefil().toString().length()) {
                            if (binding.edtDepositeCoin.getText().toString() != null && !binding.edtDepositeCoin.getText().toString().isEmpty() &&
                                    !binding.edtDepositeCoin.getText().toString().equals("")) {

                                if (Integer.parseInt(binding.edtDepositeCoin.getText().toString()) < Integer.parseInt(website_item.getMinrefil().toString())) {

                                    utils.showToast(activity, "Please deposit min amount " + website_item.getMinrefil().toString());
                                    binding.btnCreateId.setEnabled(false);
                                    binding.btnCreateId.setAlpha(0.5f);
                                    binding.edtDepositeCoin.getText().clear();
                                    binding.edtDepositeCoin.setFocusable(true);
                                } else {

                                    binding.btnCreateId.setEnabled(true);
                                    binding.btnCreateId.setAlpha(1f);
                                }
                            }

                        } else {
                            utils.showToast(activity, "Please deposit min amount " + website_item.getMinrefil().toString());
                            binding.btnCreateId.setEnabled(false);
                            binding.btnCreateId.setAlpha(0.5f);
                            binding.edtDepositeCoin.setFocusable(true);
                        }
                    }
                }, 1000);


            }
        });
        activity.hideBottomNavigation();
        View view = binding.getRoot();


        return view;
    }


    public boolean Validation(String userName, String depositeAmt) {
        boolean valid = false;
        if (userName.isEmpty() || depositeAmt.isEmpty()) {
            valid = false;
            utils.showToast(activity, "Please filled all mandatory fields");
        } else valid = true;

        return valid;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.toolbar_title.setText(this.getTag());

    }
}
