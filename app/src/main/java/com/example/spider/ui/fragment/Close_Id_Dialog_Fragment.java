package com.example.spider.ui.fragment;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.adapter.Acc_Detail_Adapter;
import com.example.spider.adapter.Payment_Detail_Adapter;

import com.example.spider.databinding.CloseIdDialogfragmentBindingImpl;
import com.example.spider.model.Accountdetail;
import com.example.spider.model.Approvedrequestidlist;
import com.example.spider.model.Pymentmethod;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Add_Acc_Dialog_viewModel;
import com.example.spider.view_model.Close_Id_Dialog_viewModel;

public class Close_Id_Dialog_Fragment extends DialogFragment {

    public CloseIdDialogfragmentBindingImpl binding;
    Close_Id_Dialog_viewModel viewModel;
    MainActivity activity;
    String TAG = "Add_Acc_Dialog_Fragment";
    Pymentmethod pymentmethod;
    Accountdetail accountdetail;
    String totalbalanceless="0",
            noactivebets="0",
            withdraw="bank",
            reason,
           otherissue;

    ExtentionUtils utils=new ExtentionUtils();
    Approvedrequestidlist approvedrequestidlist;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        activity = (MainActivity) getActivity();
        Bundle bundle=getArguments();
        if(bundle!=null){
            approvedrequestidlist=(Approvedrequestidlist) bundle.getSerializable("cloesId");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.close_id_dialogfragment, container, false);
        viewModel = new ViewModelProvider(activity).get(Close_Id_Dialog_viewModel.class);
        View view = binding.getRoot();
        init();
        return view;

    }

    public void init() {

        viewModel.getMyIdList(activity,binding,approvedrequestidlist.getId()).observe(activity,websitedetails -> {

            binding.progressBar.setVisibility(View.GONE);
            if(websitedetails!=null) {
                binding.txtWebsiteName.setText(websitedetails.get(0).getWebsitename().toString());
                binding.txtWebsiteNameLink.setText(websitedetails.get(0).getWebsiteurl().toString());
                binding.txtUserIdName.setText(websitedetails.get(0).getUsername());
                utils.setCircleImage(websitedetails.get(0).getPhoto(), binding.imgAppLogo, activity.getResources().getDrawable(R.drawable.app_logo));
            }

        });



        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1){

            binding.cbLessWithdrawAmt.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
            binding.cbNoActiveBet.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
            binding.rdBtnBank.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
            binding.rdBtnWallet.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
            binding.rdBtnAppIssue.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
            binding.rdBtnWebsiteIssue.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
            binding.rdBtnOtheIssue.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
        }

        binding.cbNoActiveBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.cbNoActiveBet.isChecked()){

                    noactivebets="1";
                }else {
                    noactivebets="0";
                }
            }
        });

        binding.cbLessWithdrawAmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.cbLessWithdrawAmt.isChecked()){

                    totalbalanceless="1";
                }else {
                    totalbalanceless="0";
                }
            }
        });


        binding.rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioGroup.getCheckedRadioButtonId()==R.id.rd_btn_wallet){

                    withdraw="wallet";
                }else if(radioGroup.getCheckedRadioButtonId()==R.id.rd_btn_bank){

                    withdraw="bank";
                }
            }
        });
        binding.rdGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioGroup.getCheckedRadioButtonId()==R.id.rd_btn_app_issue){

                    reason="app";
                    binding.tilReason.setVisibility(View.GONE);
                }else if(radioGroup.getCheckedRadioButtonId()==R.id.rd_btn_website_issue){

                    reason="website";
                    binding.tilReason.setVisibility(View.GONE);
                }else if(radioGroup.getCheckedRadioButtonId()==R.id.rd_btn_othe_issue){
                    reason="other";
                    binding.tilReason.setVisibility(View.VISIBLE);
                }
            }
        });



binding.btnCloseId.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if(reason!=null){
            if(binding.rdBtnOtheIssue.isChecked()){

                otherissue=binding.edtReason.getText().toString();

                if(otherissue!=null&&!otherissue.isEmpty()){
                    reason="";

                    CancelId(totalbalanceless,noactivebets,withdraw,reason,otherissue);

                }else {

                    utils.showToast(activity,"Please enter reason");
                    binding.edtReason.setFocusable(true);
                    return;
                }


            }else {
                otherissue = "";
                CancelId(totalbalanceless, noactivebets, withdraw, reason, otherissue);
            }

        }else utils.showToast(activity,"Please enter reason");

//        utils.showToast(activity,"totalbalanceless = "+totalbalanceless+" withdraw = "+withdraw+" reason = "+reason +" noactivebets = "+noactivebets);
    }
});


   }

   public void CancelId(String  totalbalanceless,
                        String noactivebets,
                        String withdraw,
                        String reason,
                        String otherissue){



        viewModel.sendCloesIdRequest(activity,binding,totalbalanceless,noactivebets,withdraw,reason,otherissue,approvedrequestidlist.getId()).observe(activity, it ->{


            binding.progressBar.setVisibility(View.GONE);

            if(it=="1"){

                dismiss();
            }

        } );
   }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        params.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

}
