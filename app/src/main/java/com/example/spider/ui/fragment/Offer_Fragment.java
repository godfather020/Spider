package com.example.spider.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.adapter.Offer_Adapter;
import com.example.spider.databinding.HomeFragmentBinding;
import com.example.spider.databinding.OfferFragmentBinding;
import com.example.spider.model.Offer;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Offer_Fragment_viewModel;
import com.example.spider.view_model.Withdraw_Fragment_viewModel;

public class Offer_Fragment extends Fragment {

    OfferFragmentBinding binding;
    MainActivity activity;
    Offer_Adapter adapter;
    LinearLayoutManager manager;
    Offer_Fragment_viewModel viewModel;
    ExtentionUtils utils=new ExtentionUtils();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.offer_fragment,container,false);
        activity =(MainActivity) getActivity();
        viewModel=new ViewModelProvider(activity).get(Offer_Fragment_viewModel.class);
        Log.d("lifecycle","onCreateView invoked");
        View view =binding.getRoot();
        init();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.option_menu,menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.noti:
                Fragment fragment = new Notification_Fragment();
                utils.loadFragment(activity.getSupportFragmentManager(), fragment, R.id.main_container, false, getResources().getString(R.string.notifiction), null);
                break;
        }
        return true;
    }

    public void init() {


        viewModel.getWalletList(activity,binding).observe(activity,offerList -> {

            binding.progressBar.setVisibility(View.GONE);
            if(offerList!=null){

                adapter = new Offer_Adapter(activity, offerList, new Offer_Adapter.ClickListner() {
                    @Override
                    public void getCode(Offer offer_item) {

                    }
                });
                manager = new LinearLayoutManager(activity);
                binding.rvOffer.setLayoutManager(manager);
                binding.rvOffer.setAdapter(adapter);


            }else utils.showToast(activity,"No offer available.");
        });



        binding.rvOffer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    activity.activityMainBinding.bottomNavigationView.setTranslationY(250);
                } else if (dy < 0) {
                    activity.activityMainBinding.bottomNavigationView.setTranslationY(0);
//                   activity.showBottomNavigation();

                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();

        Log.d("lifecycle","onResume invoked");
        activity.toolbar_title.setText(this.getTag());

    }




}
