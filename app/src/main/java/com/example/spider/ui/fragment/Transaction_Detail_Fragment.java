package com.example.spider.ui.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.example.spider.databinding.TransactionDetailBinding;
import com.example.spider.databinding.WithdrawIdFragmentBinding;
import com.example.spider.model.Approvedrequestidlist;
import com.example.spider.model.Transactionhistory;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Withdraw_id_Fragment_viewModel;

public class Transaction_Detail_Fragment extends Fragment {

    TransactionDetailBinding binding;
    Withdraw_id_Fragment_viewModel viewModel;
    MainActivity activity=new MainActivity();
    ExtentionUtils utils=new ExtentionUtils();
    String withdrawAmt,walletAmt,min_withdrawAmt;
    boolean withdraw;
    Transactionhistory transactionhistory;
    Bundle bundle1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity= (MainActivity) getActivity();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.transaction_detail,container,false);

        activity.toolbar_title.setText(getResources().getString(R.string.tran_detail));
        activity.hideBottomNavigation();

        View view =binding.getRoot();
        viewModel=new ViewModelProvider(activity).get(Withdraw_id_Fragment_viewModel.class);
        init();

        return view;
    }

    public void init(){
        Bundle bundle=getArguments();
        if(bundle!=null){

            transactionhistory=(Transactionhistory) bundle.getSerializable("detail");
        }


if(transactionhistory.getType().equals("withdraw")){

    binding.txtTranTitle.setText(transactionhistory.getTitle());
    binding.txtStatus.setText(transactionhistory.getStatus());
    if(transactionhistory.getTitle().equals("Withdraw from ID to bank")||transactionhistory.getTitle().equals("Withdraw from ID to wallet")) {

        binding.txtWebsiteName.setText(transactionhistory.getDetailData().get(0).getWebsitename());
        binding.txtWebsiteNameLink.setText(transactionhistory.getDetailData().get(0).getWebsiteurl());
        binding.txtUserIdName.setText(transactionhistory.getDetailData().get(0).getUsername());
        if(transactionhistory.getDetailData().get(0).getWebsitephoto()!=null && !transactionhistory.getDetailData().get(0).getWebsitephoto().isEmpty()) {

            utils.setCircleImage(transactionhistory.getDetailData().get(0).getWebsitephoto(), binding.imgAppLogo, activity.getResources().getDrawable(R.drawable.app_logo));

        }
    }else {

        binding.txtWebsiteName.setVisibility(View.GONE);
        binding.txtWebsiteNameLink.setVisibility(View.GONE);
        binding.txtUserIdName.setVisibility(View.GONE);
        binding.imgAppLogo.setVisibility(View.GONE);
    }


    if(transactionhistory.getStatus().equals("Pending")){
        binding.txtStatus.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.orange)));
        binding.txtApprovedCoins.setVisibility(View.GONE);
        binding.txtApprovedCoinsHeader.setVisibility(View.GONE);
        binding.txtCoins.setVisibility(View.GONE);
        binding.txtCoinsHeader.setVisibility(View.GONE);
        binding.txtPaymentMode.setVisibility(View.GONE);
        binding.txtPaymentModeHeader.setVisibility(View.GONE);
        binding.txtApprovedOn.setVisibility(View.GONE);
        binding.txtApprovedOnHeader.setVisibility(View.GONE);
        binding.txtRejectedOn.setVisibility(View.GONE);
        binding.txtRejectedOnHeader.setVisibility(View.GONE);
        binding.clRemark.setVisibility(View.GONE);
        binding.btnConcern.setVisibility(View.GONE);
        binding.btnCancelWithdraw.setVisibility(View.VISIBLE);

        binding.txtRequestedCoins.setText(transactionhistory.getCoins());
        binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
        binding.txtRequestDate.setText(transactionhistory.getDetailData().get(0).getCreatedDate());
        binding.txtRejectedOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());

        if(transactionhistory.getDetailData().get(0).getPaymentscreenshot()!=null && !transactionhistory.getDetailData().get(0).getPaymentscreenshot().isEmpty()){

            binding.txtTranImage.setVisibility(View.VISIBLE);
            binding.clTakeScreenshot.setVisibility(View.VISIBLE);

            utils.setImage(transactionhistory.getDetailData().get(0).getPaymentscreenshot(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));


        }else {

            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }


    }
    else if(transactionhistory.getStatus().equals("Rejected")){
        binding.txtStatus.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.red)));
        binding.txtApprovedCoins.setVisibility(View.GONE);
        binding.txtApprovedCoinsHeader.setVisibility(View.GONE);
        binding.txtCoins.setVisibility(View.GONE);
        binding.txtCoinsHeader.setVisibility(View.GONE);
        binding.txtPaymentMode.setVisibility(View.GONE);
        binding.txtPaymentModeHeader.setVisibility(View.GONE);
        binding.txtApprovedOn.setVisibility(View.GONE);
        binding.txtApprovedOnHeader.setVisibility(View.GONE);
        binding.btnCancelWithdraw.setVisibility(View.GONE);
        binding.btnConcern.setVisibility(View.VISIBLE);

        binding.txtRequestedCoins.setText(transactionhistory.getCoins());
        binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
        binding.txtRequestDate.setText(transactionhistory.getDetailData().get(0).getCreatedDate());
        binding.txtRejectedOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());

        if(transactionhistory.getDetailData().get(0).getAdminremark()!=null && !transactionhistory.getDetailData().get(0).getAdminremark().isEmpty()){

            binding.txtRemarkMsg.setText(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getDescription());

           if(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto()!=null && !transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto().isEmpty()){

                binding.txtTranImage.setVisibility(View.VISIBLE);
                binding.clTakeScreenshot.setVisibility(View.VISIBLE);

                utils.setImage(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));
           }else {

               binding.txtTranImage.setVisibility(View.GONE);
               binding.clTakeScreenshot.setVisibility(View.GONE);
           }
        }
        else {

            binding.clRemark.setVisibility(View.GONE);
            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }





    }
    else if(transactionhistory.getStatus().equals("Cancelled")){
        binding.txtStatus.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.red)));
        binding.txtApprovedCoins.setVisibility(View.GONE);
        binding.txtApprovedCoinsHeader.setVisibility(View.GONE);
        binding.txtCoins.setVisibility(View.GONE);
        binding.txtCoinsHeader.setVisibility(View.GONE);
        binding.txtPaymentMode.setVisibility(View.GONE);
        binding.txtPaymentModeHeader.setVisibility(View.GONE);
        binding.txtApprovedOn.setVisibility(View.GONE);
        binding.txtApprovedOnHeader.setVisibility(View.GONE);
        binding.btnCancelWithdraw.setVisibility(View.GONE);
        binding.txtRejectedOn.setVisibility(View.GONE);
        binding.txtRejectedOnHeader.setVisibility(View.GONE);
        binding.btnConcern.setVisibility(View.VISIBLE);
        binding.txtCanceledOn.setVisibility(View.VISIBLE);
        binding.txtCanceledOnHeader.setVisibility(View.VISIBLE);
        binding.clRemark.setVisibility(View.GONE);

        binding.txtRequestedCoins.setText(transactionhistory.getCoins());
        binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
        binding.txtRequestDate.setText(transactionhistory.getDetailData().get(0).getCreatedDate());
        binding.txtCanceledOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());

        if(transactionhistory.getDetailData().get(0).getPaymentscreenshot()!=null && !transactionhistory.getDetailData().get(0).getPaymentscreenshot().isEmpty()){

            binding.txtTranImage.setVisibility(View.VISIBLE);
            binding.clTakeScreenshot.setVisibility(View.VISIBLE);

            utils.setImage(transactionhistory.getDetailData().get(0).getPaymentscreenshot(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));


        }else {

            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }


    }
    else {

        binding.txtPaymentMode.setVisibility(View.GONE);
        binding.txtPaymentModeHeader.setVisibility(View.GONE);
        binding.txtRequestedCoins.setVisibility(View.GONE);
        binding.txtRequestedCoinsHeader.setVisibility(View.GONE);
        binding.txtRejectedOn.setVisibility(View.GONE);
        binding.txtRejectedOnHeader.setVisibility(View.GONE);
        binding.clRemark.setVisibility(View.GONE);
        binding.btnConcern.setVisibility(View.VISIBLE);
        binding.btnCancelWithdraw.setVisibility(View.GONE);


        binding.txtApprovedCoins.setText(transactionhistory.getCoins());
        binding.txtCoins.setText(transactionhistory.getCoins());
        binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
        binding.txtRequestDate.setText(transactionhistory.getDetailData().get(0).getCreatedDate());
        binding.txtApprovedOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());


        if(transactionhistory.getDetailData().get(0).getAdminremark()!=null && !transactionhistory.getDetailData().get(0).getAdminremark().isEmpty()){

            binding.txtTranImage.setVisibility(View.VISIBLE);
            binding.clTakeScreenshot.setVisibility(View.VISIBLE);

           /* if(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getDescription()!=null && !transactionhistory.getDetailData().get(0).getAdminremark().get(0).getDescription().isEmpty()) {
                binding.clRemark.setVisibility(View.VISIBLE);
                binding.txtRemarkMsg.setText(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getDescription());
            }*/

            utils.setImage(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));
        }else {

            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }

    }

}
else if(transactionhistory.getType().equals("deposit")){

    binding.txtTranTitle.setText(transactionhistory.getTitle());
    binding.txtStatus.setText(transactionhistory.getStatus());
    if(transactionhistory.getTitle().equals("Deposit to ID from bank")||transactionhistory.getTitle().equals("Deposit to ID from wallet")) {

        binding.txtWebsiteName.setText(transactionhistory.getDetailData().get(0).getWebsitename());
        binding.txtWebsiteNameLink.setText(transactionhistory.getDetailData().get(0).getWebsiteurl());
        binding.txtUserIdName.setText(transactionhistory.getDetailData().get(0).getUsername());
        if(transactionhistory.getDetailData().get(0).getWebsitephoto()!=null && !transactionhistory.getDetailData().get(0).getWebsitephoto().isEmpty()) {

            utils.setCircleImage(transactionhistory.getDetailData().get(0).getWebsitephoto(), binding.imgAppLogo, activity.getResources().getDrawable(R.drawable.app_logo));
        }
    }else {

        binding.txtWebsiteName.setVisibility(View.GONE);
        binding.txtWebsiteNameLink.setVisibility(View.GONE);
        binding.txtUserIdName.setVisibility(View.GONE);
        binding.imgAppLogo.setVisibility(View.GONE);
    }


    if(transactionhistory.getStatus().equals("Pending")){
        binding.txtStatus.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.orange)));
        binding.txtApprovedCoins.setVisibility(View.GONE);
        binding.txtApprovedCoinsHeader.setVisibility(View.GONE);
        binding.txtCoins.setVisibility(View.GONE);
        binding.txtCoinsHeader.setVisibility(View.GONE);
        binding.txtPaymentMode.setVisibility(View.GONE);
        binding.txtPaymentModeHeader.setVisibility(View.GONE);
        binding.txtApprovedOn.setVisibility(View.GONE);
        binding.txtApprovedOnHeader.setVisibility(View.GONE);
        binding.txtRejectedOn.setVisibility(View.GONE);
        binding.txtRejectedOnHeader.setVisibility(View.GONE);
        binding.clRemark.setVisibility(View.GONE);
        binding.btnConcern.setVisibility(View.GONE);

        binding.txtRequestedCoins.setText(transactionhistory.getCoins());
        binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
        binding.txtRequestDate.setText(transactionhistory.getDetailData().get(0).getCreatedDate());
        binding.txtRejectedOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());

        if(transactionhistory.getDetailData().get(0).getPaymentscreenshot()!=null && !transactionhistory.getDetailData().get(0).getPaymentscreenshot().isEmpty()){

            binding.txtTranImage.setVisibility(View.VISIBLE);
            binding.clTakeScreenshot.setVisibility(View.VISIBLE);

            utils.setImage(transactionhistory.getDetailData().get(0).getPaymentscreenshot(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));


        }else {

            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }


    }
    else if(transactionhistory.getStatus().equals("Rejected")) {
        binding.txtStatus.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.red)));
        binding.txtApprovedCoins.setVisibility(View.GONE);
        binding.txtApprovedCoinsHeader.setVisibility(View.GONE);
        binding.txtCoins.setVisibility(View.GONE);
        binding.txtCoinsHeader.setVisibility(View.GONE);
        binding.txtPaymentMode.setVisibility(View.GONE);
        binding.txtPaymentModeHeader.setVisibility(View.GONE);
        binding.txtApprovedOn.setVisibility(View.GONE);
        binding.txtApprovedOnHeader.setVisibility(View.GONE);

        binding.txtRequestedCoins.setText(transactionhistory.getCoins());
        binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
        binding.txtRequestDate.setText(transactionhistory.getDetailData().get(0).getCreatedDate());
        binding.txtRejectedOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());

        if (transactionhistory.getDetailData().get(0).getAdminremark() != null && !transactionhistory.getDetailData().get(0).getAdminremark().isEmpty()) {

            binding.txtRemarkMsg.setText(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getDescription());

            if (transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto() != null && !transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto().isEmpty()) {

                binding.txtTranImage.setVisibility(View.VISIBLE);
                binding.clTakeScreenshot.setVisibility(View.VISIBLE);

                utils.setImage(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));
            } else {

                binding.txtTranImage.setVisibility(View.GONE);
                binding.clTakeScreenshot.setVisibility(View.GONE);
            }
        }
        else {

            binding.clRemark.setVisibility(View.GONE);
            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }
    }

    else {

        binding.txtPaymentMode.setVisibility(View.GONE);
        binding.txtPaymentModeHeader.setVisibility(View.GONE);
        binding.txtRequestedCoins.setVisibility(View.GONE);
        binding.txtRequestedCoinsHeader.setVisibility(View.GONE);
        binding.txtRejectedOn.setVisibility(View.GONE);
        binding.txtRejectedOnHeader.setVisibility(View.GONE);
        binding.clRemark.setVisibility(View.GONE);
        binding.btnConcern.setVisibility(View.VISIBLE);


        binding.txtApprovedCoins.setText(transactionhistory.getCoins());
        binding.txtCoins.setText(transactionhistory.getCoins());
        binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
        binding.txtRequestDate.setText(transactionhistory.getDetailData().get(0).getCreatedDate());
        binding.txtApprovedOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());


        if(transactionhistory.getDetailData().get(0).getPaymentscreenshot()!=null && !transactionhistory.getDetailData().get(0).getPaymentscreenshot().isEmpty()){

            binding.txtTranImage.setVisibility(View.VISIBLE);
            binding.clTakeScreenshot.setVisibility(View.VISIBLE);

            utils.setImage(transactionhistory.getDetailData().get(0).getPaymentscreenshot(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));
        }else {

            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }

    }

}
else if(transactionhistory.getType().equals("closeid")){

    binding.txtTranTitle.setText(transactionhistory.getTitle());
    binding.txtStatus.setText(transactionhistory.getStatus());

    binding.txtWebsiteName.setText(transactionhistory.getDetailData().get(0).getWebsitename());
    binding.txtWebsiteNameLink.setText(transactionhistory.getDetailData().get(0).getWebsiteurl());
    binding.txtUserIdName.setText(transactionhistory.getDetailData().get(0).getUsername());
    if(transactionhistory.getDetailData().get(0).getWebsitephoto()!=null && !transactionhistory.getDetailData().get(0).getWebsitephoto().isEmpty()) {

        utils.setCircleImage(transactionhistory.getDetailData().get(0).getWebsitephoto(), binding.imgAppLogo, activity.getResources().getDrawable(R.drawable.app_logo));
    }



    if(transactionhistory.getStatus().equals("Pending")){
        binding.txtStatus.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.orange)));
        binding.txtApprovedCoins.setVisibility(View.GONE);
        binding.txtApprovedCoinsHeader.setVisibility(View.GONE);
        binding.txtCoins.setVisibility(View.GONE);
        binding.txtCoinsHeader.setVisibility(View.GONE);
        binding.txtPaymentMode.setVisibility(View.GONE);
        binding.txtPaymentModeHeader.setVisibility(View.GONE);
        binding.txtApprovedOn.setVisibility(View.GONE);
        binding.txtApprovedOnHeader.setVisibility(View.GONE);
        binding.txtRejectedOn.setVisibility(View.GONE);
        binding.txtRejectedOnHeader.setVisibility(View.GONE);
        binding.clRemark.setVisibility(View.GONE);
        binding.btnConcern.setVisibility(View.GONE);

        binding.txtRequestedCoins.setText(transactionhistory.getCoins());
        binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
        binding.txtRequestDate.setText(transactionhistory.getDetailData().get(0).getCloseiddate());
        binding.txtRejectedOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());

        if(transactionhistory.getDetailData().get(0).getPaymentscreenshot()!=null && !transactionhistory.getDetailData().get(0).getPaymentscreenshot().isEmpty()){

            binding.txtTranImage.setVisibility(View.VISIBLE);
            binding.clTakeScreenshot.setVisibility(View.VISIBLE);

            utils.setImage(transactionhistory.getDetailData().get(0).getPaymentscreenshot(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));


        }else {

            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }


    }
    else if(transactionhistory.getStatus().equals("Rejected")){
        binding.txtStatus.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.red)));
        binding.txtApprovedCoins.setVisibility(View.GONE);
        binding.txtApprovedCoinsHeader.setVisibility(View.GONE);
        binding.txtCoins.setVisibility(View.GONE);
        binding.txtCoinsHeader.setVisibility(View.GONE);
        binding.txtPaymentMode.setVisibility(View.GONE);
        binding.txtPaymentModeHeader.setVisibility(View.GONE);
        binding.txtApprovedOn.setVisibility(View.GONE);
        binding.txtApprovedOnHeader.setVisibility(View.GONE);

        binding.txtRequestedCoins.setText(transactionhistory.getCoins());
        binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
        binding.txtRequestDate.setText(transactionhistory.getDetailData().get(0).getCloseiddate());
        binding.txtRejectedOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());

        if(transactionhistory.getDetailData().get(0).getAdminremark()!=null && !transactionhistory.getDetailData().get(0).getAdminremark().isEmpty()){

            binding.txtRemarkMsg.setText(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getDescription());

            if(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto()!=null && !transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto().isEmpty()){

                binding.txtTranImage.setVisibility(View.VISIBLE);
                binding.clTakeScreenshot.setVisibility(View.VISIBLE);

                utils.setImage(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));
            }else {

                binding.txtTranImage.setVisibility(View.GONE);
                binding.clTakeScreenshot.setVisibility(View.GONE);
            }
        }
        else {

            binding.clRemark.setVisibility(View.GONE);
            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }

    }else {

        binding.txtPaymentMode.setVisibility(View.GONE);
        binding.txtPaymentModeHeader.setVisibility(View.GONE);
        binding.txtRequestedCoins.setVisibility(View.GONE);
        binding.txtRequestedCoinsHeader.setVisibility(View.GONE);
        binding.txtRejectedOn.setVisibility(View.GONE);
        binding.txtRejectedOnHeader.setVisibility(View.GONE);
        binding.clRemark.setVisibility(View.GONE);
        binding.btnConcern.setVisibility(View.VISIBLE);


        binding.txtApprovedCoins.setText(transactionhistory.getCoins());
        binding.txtCoins.setText(transactionhistory.getCoins());
        binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
        binding.txtRequestDate.setText(transactionhistory.getDetailData().get(0).getCloseiddate());
        binding.txtApprovedOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());


        if(transactionhistory.getDetailData().get(0).getPaymentscreenshot()!=null && !transactionhistory.getDetailData().get(0).getPaymentscreenshot().isEmpty()){

            binding.txtTranImage.setVisibility(View.VISIBLE);
            binding.clTakeScreenshot.setVisibility(View.VISIBLE);

            utils.setImage(transactionhistory.getDetailData().get(0).getPaymentscreenshot(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));
        }else {

            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }

        if(transactionhistory.getDetailData().get(0).getAdminremark()!=null && !transactionhistory.getDetailData().get(0).getAdminremark().isEmpty()){

            binding.txtRemarkMsg.setText(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getDescription());

            if(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto()!=null && !transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto().isEmpty()){

                binding.txtTranImage.setVisibility(View.VISIBLE);
                binding.clTakeScreenshot.setVisibility(View.VISIBLE);

                utils.setImage(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));
            }else {

                binding.txtTranImage.setVisibility(View.GONE);
                binding.clTakeScreenshot.setVisibility(View.GONE);
            }
        }
        else {

            binding.clRemark.setVisibility(View.GONE);
            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }

    }

}
else if(transactionhistory.getType().equals("requestid")){

    binding.txtTranTitle.setText(transactionhistory.getTitle());
    binding.txtStatus.setText(transactionhistory.getStatus());

    binding.txtWebsiteName.setText(transactionhistory.getDetailData().get(0).getWebsitename());
    binding.txtWebsiteNameLink.setText(transactionhistory.getDetailData().get(0).getWebsiteurl());
    binding.txtUserIdName.setText(transactionhistory.getDetailData().get(0).getUsername());
    if(transactionhistory.getDetailData().get(0).getWebsitephoto()!=null && !transactionhistory.getDetailData().get(0).getWebsitephoto().isEmpty()) {

        utils.setCircleImage(transactionhistory.getDetailData().get(0).getWebsitephoto(), binding.imgAppLogo, activity.getResources().getDrawable(R.drawable.app_logo));
    }


    if(transactionhistory.getStatus().equals("Pending")){
        binding.txtStatus.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.orange)));
        binding.txtApprovedCoins.setVisibility(View.GONE);
        binding.txtApprovedCoinsHeader.setVisibility(View.GONE);
        binding.txtCoins.setVisibility(View.GONE);
        binding.txtCoinsHeader.setVisibility(View.GONE);
        binding.txtPaymentMode.setVisibility(View.GONE);
        binding.txtPaymentModeHeader.setVisibility(View.GONE);
        binding.txtApprovedOn.setVisibility(View.GONE);
        binding.txtApprovedOnHeader.setVisibility(View.GONE);
        binding.txtRejectedOn.setVisibility(View.GONE);
        binding.txtRejectedOnHeader.setVisibility(View.GONE);
        binding.clRemark.setVisibility(View.GONE);
        binding.btnConcern.setVisibility(View.GONE);

        binding.txtRequestedCoins.setText(transactionhistory.getCoins());
        binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
        binding.txtRequestDate.setText(transactionhistory.getDetailData().get(0).getCreatedDate());
        binding.txtRejectedOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());

        if(transactionhistory.getDetailData().get(0).getPaymentscreenshot()!=null && !transactionhistory.getDetailData().get(0).getPaymentscreenshot().isEmpty()){

            binding.txtTranImage.setVisibility(View.VISIBLE);
            binding.clTakeScreenshot.setVisibility(View.VISIBLE);

            utils.setImage(transactionhistory.getDetailData().get(0).getPaymentscreenshot(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));


        }else {

            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }


    }
    else if(transactionhistory.getStatus().equals("Rejected")){
        binding.txtStatus.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.red)));
        binding.txtApprovedCoins.setVisibility(View.GONE);
        binding.txtApprovedCoinsHeader.setVisibility(View.GONE);
        binding.txtCoins.setVisibility(View.GONE);
        binding.txtCoinsHeader.setVisibility(View.GONE);
        binding.txtPaymentMode.setVisibility(View.GONE);
        binding.txtPaymentModeHeader.setVisibility(View.GONE);
        binding.txtApprovedOn.setVisibility(View.GONE);
        binding.txtApprovedOnHeader.setVisibility(View.GONE);

        binding.txtRequestedCoins.setText(transactionhistory.getCoins());
        binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
        binding.txtRequestDate.setText(transactionhistory.getDetailData().get(0).getCreatedDate());
        binding.txtRejectedOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());

        if(transactionhistory.getDetailData().get(0).getAdminremark()!=null && !transactionhistory.getDetailData().get(0).getAdminremark().isEmpty()){

            binding.txtRemarkMsg.setText(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getDescription());

            if(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto()!=null && !transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto().isEmpty()){

                binding.txtTranImage.setVisibility(View.VISIBLE);
                binding.clTakeScreenshot.setVisibility(View.VISIBLE);

                utils.setImage(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));
            }else {

                binding.txtTranImage.setVisibility(View.GONE);
                binding.clTakeScreenshot.setVisibility(View.GONE);
            }
        }
        else {

            binding.clRemark.setVisibility(View.GONE);
            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }


    }else {

        binding.txtPaymentMode.setVisibility(View.GONE);
        binding.txtPaymentModeHeader.setVisibility(View.GONE);
        binding.txtRequestedCoins.setVisibility(View.GONE);
        binding.txtRequestedCoinsHeader.setVisibility(View.GONE);
        binding.txtRejectedOn.setVisibility(View.GONE);
        binding.txtRejectedOnHeader.setVisibility(View.GONE);
        binding.clRemark.setVisibility(View.GONE);
        binding.btnConcern.setVisibility(View.GONE);


        binding.txtApprovedCoins.setText(transactionhistory.getCoins());
        binding.txtCoins.setText(transactionhistory.getCoins());
        binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
        binding.txtRequestDate.setText(transactionhistory.getDetailData().get(0).getCreatedDate());
        binding.txtApprovedOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());


        if(transactionhistory.getDetailData().get(0).getPaymentscreenshot()!=null && !transactionhistory.getDetailData().get(0).getPaymentscreenshot().isEmpty()){

            binding.txtTranImage.setVisibility(View.VISIBLE);
            binding.clTakeScreenshot.setVisibility(View.VISIBLE);

            utils.setImage(transactionhistory.getDetailData().get(0).getPaymentscreenshot(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));
        }else {

            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }

    }

}
else if(transactionhistory.getType().equals("changeidpassword")){

    binding.txtTranTitle.setText(transactionhistory.getTitle());
    binding.txtStatus.setText(transactionhistory.getStatus());

    binding.txtWebsiteName.setText(transactionhistory.getDetailData().get(0).getWebsitename());
    binding.txtWebsiteNameLink.setText(transactionhistory.getDetailData().get(0).getWebsiteurl());
    binding.txtUserIdName.setText(transactionhistory.getDetailData().get(0).getUsername());
    if(transactionhistory.getDetailData().get(0).getWebsitephoto()!=null && !transactionhistory.getDetailData().get(0).getWebsitephoto().isEmpty()) {

        utils.setCircleImage(transactionhistory.getDetailData().get(0).getWebsitephoto(), binding.imgAppLogo, activity.getResources().getDrawable(R.drawable.app_logo));
    }


    if(transactionhistory.getStatus().equals("Pending")){
        binding.txtStatus.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.orange)));
        binding.txtApprovedCoins.setVisibility(View.GONE);
        binding.txtApprovedCoinsHeader.setVisibility(View.GONE);
        binding.txtCoins.setVisibility(View.GONE);
        binding.txtCoinsHeader.setVisibility(View.GONE);
        binding.txtPaymentMode.setVisibility(View.GONE);
        binding.txtPaymentModeHeader.setVisibility(View.GONE);
        binding.txtApprovedOn.setVisibility(View.GONE);
        binding.txtApprovedOnHeader.setVisibility(View.GONE);
        binding.txtRejectedOn.setVisibility(View.GONE);
        binding.txtRejectedOnHeader.setVisibility(View.GONE);
        binding.clRemark.setVisibility(View.GONE);
        binding.btnConcern.setVisibility(View.GONE);

        binding.txtRequestedCoins.setText(transactionhistory.getCoins());
        binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
        binding.txtRequestDate.setText(transactionhistory.getDetailData().get(0).getRequestdate());


        if(transactionhistory.getDetailData().get(0).getPaymentscreenshot()!=null && !transactionhistory.getDetailData().get(0).getPaymentscreenshot().isEmpty()){

            binding.txtTranImage.setVisibility(View.VISIBLE);
            binding.clTakeScreenshot.setVisibility(View.VISIBLE);

            utils.setImage(transactionhistory.getDetailData().get(0).getPaymentscreenshot(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));


        }else {

            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }


    }
    else if(transactionhistory.getStatus().equals("Rejected")){
        binding.txtStatus.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.red)));
        binding.txtApprovedCoins.setVisibility(View.GONE);
        binding.txtApprovedCoinsHeader.setVisibility(View.GONE);
        binding.txtCoins.setVisibility(View.GONE);
        binding.txtCoinsHeader.setVisibility(View.GONE);
        binding.txtPaymentMode.setVisibility(View.GONE);
        binding.txtPaymentModeHeader.setVisibility(View.GONE);
        binding.txtApprovedOn.setVisibility(View.GONE);
        binding.txtApprovedOnHeader.setVisibility(View.GONE);
        binding.txtRequestedCoinsHeader.setVisibility(View.GONE);
        binding.txtRequestedCoins.setVisibility(View.GONE);



        binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
        binding.txtRequestDate.setText(transactionhistory.getDetailData().get(0).getRequestdate());
        binding.txtRejectedOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());

        if(transactionhistory.getDetailData().get(0).getAdminremark()!=null && !transactionhistory.getDetailData().get(0).getAdminremark().isEmpty()){

            binding.txtRemarkMsg.setText(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getDescription());

            if(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto()!=null && !transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto().isEmpty()){

                binding.txtTranImage.setVisibility(View.VISIBLE);
                binding.clTakeScreenshot.setVisibility(View.VISIBLE);

                utils.setImage(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));
            }else {

                binding.txtTranImage.setVisibility(View.GONE);
                binding.clTakeScreenshot.setVisibility(View.GONE);
            }
        }
        else {

            binding.clRemark.setVisibility(View.GONE);
            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }

    }else {

        binding.txtPaymentMode.setVisibility(View.GONE);
        binding.txtPaymentModeHeader.setVisibility(View.GONE);
        binding.txtRequestedCoins.setVisibility(View.GONE);
        binding.txtRequestedCoinsHeader.setVisibility(View.GONE);
        binding.txtRejectedOn.setVisibility(View.GONE);
        binding.txtRejectedOnHeader.setVisibility(View.GONE);

        binding.btnConcern.setVisibility(View.GONE);
        binding.txtApprovedCoinsHeader.setVisibility(View.GONE);
        binding.txtApprovedCoins.setVisibility(View.GONE);
        binding.txtCoinsHeader.setVisibility(View.GONE);
        binding.txtCoins.setVisibility(View.GONE);
        binding.txtRemarkMsg.setVisibility(View.GONE);
        binding.txtUserIdHeader.setVisibility(View.VISIBLE);
        binding.txtUserId.setVisibility(View.VISIBLE);
        binding.txtUserPassHeader.setVisibility(View.VISIBLE);
        binding.txtUserPass.setVisibility(View.VISIBLE);

        binding.txtRemarkTitle.setText("User ID Detail");
        binding.txtUserId.setText(transactionhistory.getDetailData().get(0).getUsername());
        binding.txtUserPass.setText("1234567890");
        binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
        binding.txtRequestDate.setText(transactionhistory.getDetailData().get(0).getRequestdate());
        binding.txtApprovedOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());


        if(transactionhistory.getDetailData().get(0).getPaymentscreenshot()!=null && !transactionhistory.getDetailData().get(0).getPaymentscreenshot().isEmpty()){

            binding.txtTranImage.setVisibility(View.VISIBLE);
            binding.clTakeScreenshot.setVisibility(View.VISIBLE);

            utils.setImage(transactionhistory.getDetailData().get(0).getPaymentscreenshot(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));
        }else {

            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }

        if(transactionhistory.getDetailData().get(0).getAdminremark()!=null && !transactionhistory.getDetailData().get(0).getAdminremark().isEmpty()){

            binding.txtRemarkMsg.setText(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getDescription());

            if(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto()!=null && !transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto().isEmpty()){

                binding.txtTranImage.setVisibility(View.VISIBLE);
                binding.clTakeScreenshot.setVisibility(View.VISIBLE);

                utils.setImage(transactionhistory.getDetailData().get(0).getAdminremark().get(0).getPhoto(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));
            }else {

                binding.txtTranImage.setVisibility(View.GONE);
                binding.clTakeScreenshot.setVisibility(View.GONE);
            }
        }
        else {

            binding.clRemark.setVisibility(View.GONE);
            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }

    }

}
else if(transactionhistory.getType().equals("reward")){

    binding.txtTranTitle.setText(transactionhistory.getTitle());


    binding.txtStatus.setVisibility(View.GONE);
    binding.txtWebsiteName.setVisibility(View.GONE);
    binding.txtWebsiteNameLink.setVisibility(View.GONE);
    binding.txtUserIdName.setVisibility(View.GONE);
    binding.imgAppLogo.setVisibility(View.GONE);




        binding.txtPaymentMode.setVisibility(View.GONE);
        binding.txtPaymentModeHeader.setVisibility(View.GONE);
        binding.txtRequestedCoins.setVisibility(View.GONE);
        binding.txtRequestedCoinsHeader.setVisibility(View.GONE);
        binding.txtRejectedOn.setVisibility(View.GONE);
        binding.txtRejectedOnHeader.setVisibility(View.GONE);

        binding.btnConcern.setVisibility(View.GONE);
        binding.txtApprovedCoinsHeader.setVisibility(View.GONE);
        binding.txtApprovedCoins.setVisibility(View.GONE);
        binding.clRemark.setVisibility(View.GONE);
        binding.txtRequestDateHeader.setVisibility(View.GONE);
        binding.txtRequestDate.setVisibility(View.GONE);




        binding.txtCoins.setText(transactionhistory.getCoins());
        binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
        binding.txtApprovedOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());


        if(transactionhistory.getDetailData().get(0).getPaymentscreenshot()!=null && !transactionhistory.getDetailData().get(0).getPaymentscreenshot().isEmpty()){

            binding.txtTranImage.setVisibility(View.VISIBLE);
            binding.clTakeScreenshot.setVisibility(View.VISIBLE);

            utils.setImage(transactionhistory.getDetailData().get(0).getPaymentscreenshot(), binding.imgScreenshot, activity.getResources().getDrawable(R.drawable.app_logo));
        }else {

            binding.txtTranImage.setVisibility(View.GONE);
            binding.clTakeScreenshot.setVisibility(View.GONE);
        }



}
/*else {

    binding.txtWebsiteName.setText(transactionhistory.getDetailData().get(0).getWebsitename());
    binding.txtWebsiteNameLink.setText(transactionhistory.getDetailData().get(0).getWebsiteurl());
    binding.txtUserIdName.setText(transactionhistory.getDetailData().get(0).getUsername());
    binding.txtTranTitle.setText(transactionhistory.getTitle());
    binding.txtStatus.setText(transactionhistory.getStatus());
    binding.txtApprovedCoins.setText(transactionhistory.getCoins());
    binding.txtCoins.setText(transactionhistory.getCoins());
    binding.txtPaymentMode.setText(transactionhistory.getDetailData().get(0).getPaymentmethod());
    binding.txtRequestedCoins.setText(transactionhistory.getCoins());
    binding.txtReferenceNo.setText(transactionhistory.getReferenceno());
    binding.txtRequestDate.setText(transactionhistory.getDetailData().get(0).getCreatedDate());
    binding.txtRejectedOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());
    binding.txtApprovedOn.setText(transactionhistory.getDetailData().get(0).getAcceptrejectdate());
}*/


//        binding.txtWebsiteNameLink.setText(approvedrequestidlist.getWebsitename());
//        binding.txtUserIdName.setText(approvedrequestidlist.getUsername());
//        utils.setCircleImage(approvedrequestidlist.getWebsitephoto(),binding.imgAppLogo,activity.getResources().getDrawable(R.drawable.app_logo));


        binding.btnConcern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle1=new Bundle();
                bundle1.putSerializable("item_trans_detail",transactionhistory);
                utils.loadFragment(activity.getSupportFragmentManager(),new Concern_Fragment(),R.id.main_container,false,activity.getResources().getString(R.string.raise_a_concern),bundle1);
            }
        });


binding.btnCancelWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 bundle1=new Bundle();

                bundle1.putString("withdrawId",transactionhistory.getMainid());
                utils.loadFragment(activity.getSupportFragmentManager(),new Concern_Fragment(),R.id.main_container,false,activity.getResources().getString(R.string.cancelation),bundle1);
            }
        });
    }
}
