package com.example.spider.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.adapter.Home_Adapter;
import com.example.spider.adapter.Refer_Earn_Adapter;
import com.example.spider.databinding.HomeFragmentBinding;
import com.example.spider.databinding.ReferEarnFragmentBinding;
import com.example.spider.model.Website;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Refer_earn_viewModel;

import java.util.ArrayList;
import java.util.List;

public class Refer_Earn_Fragment extends Fragment {

    ReferEarnFragmentBinding binding;
    Refer_Earn_Adapter adapter;
    Refer_earn_viewModel refer_earn_viewModel;
    RecyclerView.LayoutManager manager;
    MainActivity activity;
    String TAG;
    ExtentionUtils utils=new ExtentionUtils();
    String referalCode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.refer_earn_fragment, container, false);

        activity = (MainActivity) getActivity();
        View view = binding.getRoot();
        initView();



        return view;
    }

    public void initView() {


        TAG = Refer_Earn_Fragment.this.getTag();
        refer_earn_viewModel = new ViewModelProvider(this).get(Refer_earn_viewModel.class);

        refer_earn_viewModel.checkReferalCode(activity,binding).observe(getViewLifecycleOwner(),referalcodes -> {
            binding.progressBar.setVisibility(View.GONE);
            if(referalcodes!=null){

                binding.txtRefercode.setText(referalcodes.get(0).getCode());
                referalCode=referalcodes.get(0).getCode();
            }


        });
        binding.btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            textCopy(binding.txtRefercode.getText().toString());

            }


        });


        binding.imgBtnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hey Buddy! \n" +
                        "\n" +
                        "Did you hear about Spider?\n" +
                        "\n" +
                        "Its an awesome Betting Portal for self ID creation, self deposit, self withdrawal with many more exciting features\n" +
                        "\n" +
                        "Register with my referral code "+ referalCode+", remember to apply the code during signup\n" +
                        "\n" +
                        "\uD83C\uDF10 Test.com");


// Try to invoke the intent.
                try {
                    startActivity(whatsappIntent);
                } catch (ActivityNotFoundException e) {
                    // Define what your app should do if no activity can handle the intent.
                }
            }
        });

        binding.imgBtnTeligram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("org.telegram.messenger");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hey Buddy! \n" +
                        "\n" +
                        "Did you hear about Spider?\n" +
                        "\n" +
                        "Its an awesome Betting Portal for self ID creation, self deposit, self withdrawal with many more exciting features\n" +
                        "\n" +
                        "Register with my referral code "+ referalCode+", remember to apply the code during signup\n" +
                        "\n" +
                        "\uD83C\uDF10 Test.com");


// Try to invoke the intent.
                try {
                    startActivity(whatsappIntent);
                } catch (ActivityNotFoundException e) {
                    // Define what your app should do if no activity can handle the intent.
                }
            }
        });

        binding.imgBtnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hey Buddy! \n" +
                        "\n" +
                        "Did you hear about Spider?\n" +
                        "\n" +
                        "Its an awesome Betting Portal for self ID creation, self deposit, self withdrawal with many more exciting features\n" +
                        "\n" +
                        "Register with my referral code "+ referalCode+", remember to apply the code during signup\n" +
                        "\n" +
                        "\uD83C\uDF10 Test.com");
                // Try to invoke the intent.
                try {
                    startActivity(whatsappIntent);
                } catch (ActivityNotFoundException e) {
                    // Define what your app should do if no activity can handle the intent.
                }
            }
        });

        utils.setSearchView(activity,binding.searchView,searchView -> {

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    adapter.getFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    adapter.getFilter().filter(newText);
                    return false;
                }
            });
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showContactList();
            }
        },800);
    }
    public void showContactList(){

        binding.progressBar.setVisibility(View.VISIBLE);

        refer_earn_viewModel.getContactList(activity,binding).observe(getViewLifecycleOwner(), userDetails -> {


            if(userDetails!=null) {

                manager = new LinearLayoutManager(getContext());
                adapter = new Refer_Earn_Adapter(getContext(), userDetails, new Refer_Earn_Adapter.ClickListner() {
                    @Override
                    public void click_Listener(String mobNo) {
                        //open whatsapp chat window with send msg
                        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                        whatsappIntent.setType("text/plain");
                        whatsappIntent.setPackage("com.whatsapp");
                        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hey Buddy! \n" +
                                "\n" +
                                "Did you hear about Spider?\n" +
                                "\n" +
                                "Its an awesome Betting Portal for self ID creation, self deposit, self withdrawal with many more exciting features\n" +
                                "\n" +
                                "Register with my referral code "+ referalCode+", remember to apply the code during signup\n" +
                                "\n" +
                                "\uD83C\uDF10 Test.com");

                        String smsNumber = mobNo; //Number without with country code and without '+' prifix
                        whatsappIntent.putExtra("jid", smsNumber + "@s.whatsapp.net");


// Try to invoke the intent.
                        try {
                            startActivity(whatsappIntent);
                        } catch (ActivityNotFoundException e) {
                            // Define what your app should do if no activity can handle the intent.
                        }
                    }
                });
                binding.rvContactList.setLayoutManager(manager);
                binding.rvContactList.setAdapter(adapter);
                binding.progressBar.setVisibility(View.GONE);
            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();
        activity.toolbar_title.setText(Refer_Earn_Fragment.this.getTag());
        activity.hideBottomNavigation();

    }

    public void textCopy(String text){

        utils.copyText(activity,text);
        utils.showToast(activity,"Copied");
    }
}
