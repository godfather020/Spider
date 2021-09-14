package com.example.spider.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.adapter.Notification_Adapter;
import com.example.spider.adapter.Passbook_Adapter;
import com.example.spider.databinding.ConcernListFragmentBinding;
import com.example.spider.databinding.NotificationFragmentBinding;
import com.example.spider.model.Transactionhistory;
import com.example.spider.model.Website;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Notification_Fragment_viewModel;

import java.util.ArrayList;
import java.util.List;

public class Concern_List_Fragment extends Fragment {

    ConcernListFragmentBinding binding;
    Notification_Adapter adapter;
    Notification_Fragment_viewModel viewModel;
    RecyclerView.LayoutManager manager;
    List<Website> list=new ArrayList<>();
    MainActivity activity;
    ExtentionUtils utils=new ExtentionUtils();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.concern_list_fragment,container,false);

        View view =binding.getRoot();
        activity=(MainActivity) getActivity();
        this.getParentFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

               initView();

            }
        });
        viewModel=new ViewModelProvider(activity).get(Notification_Fragment_viewModel.class);

        initView();
        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.option_menu_concern,menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.add_concern:
                Bundle bundle=new Bundle();
                bundle.putBoolean("menu",true);
                utils.loadFragment(activity.getSupportFragmentManager(), new Concern_Fragment(), R.id.main_container, false, getResources().getString(R.string.raise_a_concern), bundle);
                break;
        }
        return true;
    }
    public void initView(){



        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.loadUrl("https://impetrosys.com/spiderapp/Ticket/concernlist/"+new AppSharedPref(activity).getUserData().getId());
        binding.webView.setWebViewClient(new WebViewClient());


    }

    @Override
    public void onResume() {
        super.onResume();
        activity.toolbar_title.setText(Concern_List_Fragment.this.getTag());
        activity.hideBottomNavigation();

    }
}
