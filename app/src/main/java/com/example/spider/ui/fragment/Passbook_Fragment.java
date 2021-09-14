package com.example.spider.ui.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;

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
import com.example.spider.adapter.Home_Adapter;
import com.example.spider.adapter.Passbook_Adapter;
import com.example.spider.databinding.HomeFragmentBinding;
import com.example.spider.databinding.PassbookFragmentBinding;
import com.example.spider.inter_face.DatePickerListener;
import com.example.spider.model.Transactionhistory;
import com.example.spider.model.Website;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Trans_History_Fragment_viewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Passbook_Fragment extends Fragment implements View.OnClickListener {

    PassbookFragmentBinding binding;
    boolean sideNav=false;
    MainActivity activity;
    Passbook_Adapter adapter;
    Trans_History_Fragment_viewModel viewModel;
    RecyclerView.LayoutManager manager;
    List<Transactionhistory> list=new ArrayList<>();
    Passbook_Adapter.ClickListener clickListener;
    ExtentionUtils utils=new ExtentionUtils();
    int UpdatePosition=-1;
    String filterType;
    Date fromDate,toDate;
    boolean showFilterLayout=false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.passbook_fragment,container,false);
        activity=(MainActivity) getActivity();
        viewModel=new ViewModelProvider(activity).get(Trans_History_Fragment_viewModel.class);
        this.getParentFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                if(UpdatePosition!=-1){

                    viewModel.getTransList(activity,binding).observe(activity,transactionhistories -> {});

                    new Passbook_Adapter().notifyItemChanged(UpdatePosition);
                    binding.rvTranHistory.scrollToPosition(UpdatePosition);
                    Log.d("onBackStackChanged", "onBackStackChanged======");

                }

            }
        });

        View view =binding.getRoot();
        Bundle bundle =getArguments();

        if(bundle!=null){
            sideNav= bundle.getBoolean("sideNav");
        }
        if(sideNav){
            activity.hideBottomNavigation();
            setHasOptionsMenu(false);

        }
        initView();
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

    public void initView(){


        manager= new LinearLayoutManager(getContext());

        viewModel.getTransList(activity,binding).observe(activity,transactionhistories -> {

            if(transactionhistories!=null) {

                list.addAll(transactionhistories);
                binding.progressBar.setVisibility(View.GONE);

                adapter = new Passbook_Adapter(getContext(), transactionhistories, new Passbook_Adapter.ClickListener() {
                    @Override
                    public void tran_detail(Transactionhistory transactionhistory,int position) {
                        UpdatePosition=position;
                        Fragment fragment = new Transaction_Detail_Fragment();
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("detail",transactionhistory);
                        utils.loadFragment(activity.getSupportFragmentManager(), fragment, R.id.main_container, false, activity.getResources().getString(R.string.tran_detail), bundle);

                    }
                });

                binding.rvTranHistory.setLayoutManager(manager);
                binding.rvTranHistory.setAdapter(adapter);

            }

        });
        binding.rvTranHistory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    activity.activityMainBinding.bottomNavigationView.setTranslationY(250);
                    binding.fbBtnFilter.setVisibility(View.GONE);
                } else if (dy < 0 ) {
                    activity.activityMainBinding.bottomNavigationView.setTranslationY(0);
//                   activity.showBottomNavigation();
                    binding.fbBtnFilter.setVisibility(View.VISIBLE);

                }
            }
        });


//        binding.edtFromDate.setOnClickListener(this);

        binding.edtFromDate.setOnClickListener(this);
        binding.edtToDate.setOnClickListener(this);
        binding.btnFilter.setOnClickListener(this);
        binding.btnFilterCancel.setOnClickListener(this);
        binding.fbBtnFilter.setOnClickListener(this);

    }


    @Override
    public void onResume() {
        super.onResume();
        activity.toolbar_title.setText(Passbook_Fragment.this.getTag());
//        activity.showBottomNavigation();
//        activity.setBottomBarTab(3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edt_from_date:
                utils.showDatePicker(activity, new DatePickerListener() {
                    @Override
                    public void getDate(Date date) {
                        setDate(date,binding.edtFromDate);
                        fromDate=date;
                    }
                });
                break;
            case R.id.edt_to_date:
                utils.showDatePicker(activity, new DatePickerListener() {
                    @Override
                    public void getDate(Date date) {
                        setDate(date,binding.edtToDate);
                        toDate=date;
                    }
                });
                break;
            case R.id.btn_filter:

                filterList();
                break;
            case R.id.btn_filter_cancel:
                adapter.filterDateRange(null,null ,null);
                setFilter();
                break;
            case R.id.fb_btn_filter:

                if(!showFilterLayout) {
                    showFilterLayout=true;
                    setFilter();
                    binding.clFilter.setVisibility(View.VISIBLE);
                }else {
                    showFilterLayout=false;
                    binding.clFilter.setVisibility(View.GONE);
                }
                break;

        }
    }

    public void setFilter(){
        binding.edtToDate.getText().clear();
        binding.edtFromDate.getText().clear();
        final List<String> plantsList = new ArrayList<String>(Arrays.asList(activity.getResources().getStringArray(R.array.drop_down_filter_array)));
        utils.setSpinnerAdapter(activity, binding.spinnerFilterType, plantsList, new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position>0) {
                    filterType=plantsList.get(position);

                }else {
                    filterType=null;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void setDate(Date date, EditText editText){


        String myFormat = "dd MMM yy"; //Change as you need
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        editText.setText(sdf.format(date));

    }
    public void filterList(){

        if(fromDate!=null&&!fromDate.toString().isEmpty()&& toDate!=null&&!toDate.toString().isEmpty()){
            if(!toDate.before(fromDate)){
                adapter.filterDateRange(fromDate,toDate,filterType);

            }else {

                utils.showToast(activity,"To date should not be before from From date ");
            }
        }else {

            if(filterType!=null&&!filterType.isEmpty()){

                adapter.filterDateRange(fromDate,toDate,filterType);

            }else {

                utils.showToast(activity,"Please select date range or type ");
            }
        }

    }


}
