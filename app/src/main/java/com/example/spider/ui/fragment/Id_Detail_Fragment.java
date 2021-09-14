package com.example.spider.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.adapter.Acc_Detail_Adapter;
import com.example.spider.adapter.Id_Trans_History_Adapter;
import com.example.spider.adapter.Passbook_Adapter;
import com.example.spider.adapter.Payment_Detail_Adapter;
import com.example.spider.databinding.IdDetailFragmentBinding;
import com.example.spider.databinding.PaymentDetailFragmentBinding;
import com.example.spider.inter_face.ClickListner;
import com.example.spider.model.Accountdetail;
import com.example.spider.model.Approvedrequestidlist;
import com.example.spider.model.Pymentmethod;
import com.example.spider.model.Website;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Id_Detail_Fragment_viewModel;
import com.example.spider.view_model.Paymetn_Detail_viewModel;

import java.util.ArrayList;
import java.util.List;

public class Id_Detail_Fragment extends Fragment  {

    IdDetailFragmentBinding binding;

    Id_Trans_History_Adapter adapter;
    RecyclerView.LayoutManager manager;
    MainActivity activity=new MainActivity();
    AppSharedPref mAppSharedPref;
    Add_Acc_Dialog_Fragment add_acc_dialog_fragment;
    FragmentTransaction ft;
    Id_Detail_Fragment_viewModel viewModel;
    ExtentionUtils utils=new ExtentionUtils();
    Approvedrequestidlist approvedrequestidlist;
    List<Website> list=new ArrayList<>();

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


        binding= DataBindingUtil.inflate(inflater,R.layout.id_detail_fragment,container,false);

        mAppSharedPref=new AppSharedPref(activity);
        viewModel=new ViewModelProvider(activity).get(Id_Detail_Fragment_viewModel.class);
        View view =binding.getRoot();
        initView();
        return view;
    }

    public void initView(){
        manager= new LinearLayoutManager(getContext());
        binding.rvIdTranHistory.setLayoutManager(manager);

        viewModel.getMyIdList(activity,binding,approvedrequestidlist.getId()).observe(activity,websitedetails -> {

            binding.progressBar.setVisibility(View.GONE);
            if(websitedetails!=null){

                binding.txtIdWebsiteUrl.setText(websitedetails.get(0).getWebsiteurl());
                binding.txtIdWebsite.setText(websitedetails.get(0).getWebsitename());
                binding.txtIdUserName.setText("User Name : "+websitedetails.get(0).getUsername());
                binding.txtIdUserPass.setText("Password : "+websitedetails.get(0).getPassword());
                binding.txtIdCreatedDate.setText(websitedetails.get(0).getNotificationDate());
                utils.setCircleImage(websitedetails.get(0).getPhoto(),binding.imageView,activity.getResources().getDrawable(R.drawable.app_logo));

                adapter=new Id_Trans_History_Adapter(getContext(),websitedetails.get(0).getTransactiondetail());

                binding.rvIdTranHistory.setAdapter(adapter);

            }

        } );

       /* binding.txtIdWebsiteUrl.setText(approvedrequestidlist.getWebsitename());
        binding.txtIdWebsite.setText(approvedrequestidlist.getWebsitename());
        binding.txtIdUserName.setText("User Name : "+approvedrequestidlist.getUsername());
        binding.txtIdUserPass.setText("Password : "+"123465789");
        binding.txtIdCreatedDate.setText("23 jun 2021 11:45 PM");
        utils.setCircleImage(approvedrequestidlist.getWebsitephoto(),binding.imageView,activity.getResources().getDrawable(R.drawable.app_logo));

        for (int i=0; i<10;i++){

            list.add(new Website(false));
        }*/





    }

    @Override
    public void onResume() {
        super.onResume();
        activity.toolbar_title.setText(Id_Detail_Fragment.this.getTag());
        activity.hideBottomNavigation();


    }


}
