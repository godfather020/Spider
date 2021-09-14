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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.adapter.Home_Adapter;
import com.example.spider.adapter.Notification_Adapter;
import com.example.spider.adapter.Passbook_Adapter;
import com.example.spider.databinding.HomeFragmentBinding;
import com.example.spider.databinding.NotificationFragmentBinding;
import com.example.spider.model.Transactionhistory;
import com.example.spider.model.Website;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Notification_Fragment_viewModel;

import java.util.ArrayList;
import java.util.List;

public class Notification_Fragment extends Fragment {

    NotificationFragmentBinding binding;
    Notification_Adapter adapter;
    Notification_Fragment_viewModel viewModel;
    RecyclerView.LayoutManager manager;
    List<Website> list=new ArrayList<>();
    MainActivity activity;
    ExtentionUtils utils=new ExtentionUtils();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.notification_fragment,container,false);

        View view =binding.getRoot();
        activity=(MainActivity) getActivity();
        viewModel=new ViewModelProvider(activity).get(Notification_Fragment_viewModel.class);

        initView();
        return view;
    }

    public void initView(){



        manager= new LinearLayoutManager(getContext());

        viewModel.getNotiList(activity,binding).observe(activity,transactionhistories -> {

            if(transactionhistories!=null) {

                binding.progressBar.setVisibility(View.GONE);

                adapter=new Notification_Adapter(getContext(), transactionhistories, new Notification_Adapter.ClickListener() {
                    @Override
                    public void tran_detail(Transactionhistory transactionhistory) {
                        Fragment fragment = new Transaction_Detail_Fragment();
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("detail",transactionhistory);
                        utils.loadFragment(activity.getSupportFragmentManager(), fragment, R.id.main_container, false, activity.getResources().getString(R.string.tran_detail), bundle);

                    }
                });
                binding.rvNotification.setLayoutManager(manager);
                binding.rvNotification.setAdapter(adapter);

            }

        });


    }

    @Override
    public void onResume() {
        super.onResume();
        activity.toolbar_title.setText(Notification_Fragment.this.getTag());
        activity.hideBottomNavigation();

    }
}
