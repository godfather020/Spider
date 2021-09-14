package com.example.spider.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.databinding.DepositeFragmentBinding;
import com.example.spider.databinding.HomeFragmentBinding;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Deposite_Fragment_viewModel;

public class Deposite_Fragment extends Fragment {

    DepositeFragmentBinding binding;
    Deposite_Fragment_viewModel viewModel;
    MainActivity activity;
    ExtentionUtils utils =new ExtentionUtils();
    String depositeAmt,min_depositAmt,walletAmt;
    boolean comefromid=false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity= (MainActivity) getActivity();
        Bundle bundle=getArguments();

        if(bundle!=null){
            bundle.getBoolean("comefromid",false);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.deposite_fragment,container,false);


//        activity.toolbar_title.setText(this.getTag());
        viewModel=new ViewModelProvider(activity).get(Deposite_Fragment_viewModel.class);
        activity.hideBottomNavigation();
        View view =binding.getRoot();

        binding.progressBar.setVisibility(View.VISIBLE);
        viewModel.getWalletList(activity,binding).observe(activity,walletlist -> {

            if(walletlist!=null){
                binding.progressBar.setVisibility(View.GONE);
                walletAmt=walletlist.getTotal().toString();
                min_depositAmt=walletlist.getMindeposit().toString();
                binding.txtWalletAmt.setText(walletAmt);
                binding.textView3.setText(activity.getResources().getString(R.string.min_deposit_amt)+" "+min_depositAmt+" coins.");

            }
        });
        binding.btnDeposite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                depositeAmt = binding.edtDepositeCoin.getText().toString();
                if (depositeAmt != null && !depositeAmt.isEmpty()) {

                    if (Integer.parseInt(depositeAmt) >= Integer.parseInt(min_depositAmt)) {
                        Fragment fragment = new Paymet_Method_Fragment();

                        Bundle arg = new Bundle();


                        arg.putString("depositAmt", binding.edtDepositeCoin.getText().toString());
                        arg.putBoolean("deposit", true);
                        utils.loadFragment(activity.getSupportFragmentManager(), fragment, R.id.main_container,
                                false, activity.getResources().getString(R.string.payment_method), arg);
                    } else {
                            utils.showToast(activity, "You are not deposit less than " + min_depositAmt + " coins.");

                        }
                }else {

                    utils.showToast(activity,"Please fill mandatory field !");
                }
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
