package com.example.spider.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.adapter.Acc_Detail_Adapter;
import com.example.spider.adapter.Home_Adapter;
import com.example.spider.adapter.Payment_Detail_Adapter;
import com.example.spider.databinding.HomeFragmentBinding;
import com.example.spider.databinding.PaymentDetailFragmentBinding;
import com.example.spider.inter_face.ClickListner;
import com.example.spider.model.Accountdetail;
import com.example.spider.model.Pymentmethod;
import com.example.spider.model.Website;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Paymetn_Detail_viewModel;
import com.example.spider.view_model.Paymetn_Method_viewModel;

import java.util.ArrayList;
import java.util.List;

public class Paymet_Detail_Fragment extends Fragment implements ClickListner {

    PaymentDetailFragmentBinding binding;

    Payment_Detail_Adapter adapter;
    RecyclerView.LayoutManager manager;

    MainActivity activity=new MainActivity();
    AppSharedPref mAppSharedPref;
    Add_Acc_Dialog_Fragment add_acc_dialog_fragment;
    FragmentTransaction ft;
    Paymetn_Detail_viewModel viewModel;
    ExtentionUtils utils=new ExtentionUtils();
    List<Pymentmethod> pymentmethods_list;
    Parcelable rv_save_state;
    FragmentManager fm;
    ActivityResultLauncher<Intent> resultLauncherDialog;

    public static String REQUEST_KEY= "requesteKey";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=(MainActivity) getActivity();

         fm=getParentFragmentManager();
        fm.setFragmentResultListener(REQUEST_KEY, Paymet_Detail_Fragment.this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                if(requestKey==REQUEST_KEY){
                    if(result!=null){
                        Log.d("result",result.toString());


                        if(result.getString("add","not_done").equals("done")){

                            viewModel.getAccList(activity,binding).observe(activity,accounts -> {



                            });
                            int payment_item_position = result.getInt("payment_item_position");
                            int acc_item_position = result.getInt("acc_item_position");

                            mAppSharedPref.saveBoolean("payment_updateClick", true);
                            mAppSharedPref.saveInteger("payment_updatePosition", payment_item_position);
                            mAppSharedPref.saveBoolean("acc_updateClick", true);
                            mAppSharedPref.saveInteger("acc_updatePosition", acc_item_position);
                            new Acc_Detail_Adapter().notifyItemChanged(acc_item_position);
                            new Payment_Detail_Adapter().notifyItemChanged(payment_item_position);

                            binding.rvPaymentDetail.scrollToPosition(payment_item_position);
                            fm.clearFragmentResult(REQUEST_KEY);


                        } else if(result.getString("update","not_done").equals("done")) {

                            viewModel.getAccList(activity, binding).observe(activity, accounts -> {


                                if (accounts != null) {


                                }
                            });

                            int payment_item_position = result.getInt("payment_item_position");
                            int acc_item_position = result.getInt("acc_item_position");

                            mAppSharedPref.saveBoolean("payment_updateClick", true);
                            mAppSharedPref.saveInteger("payment_updatePosition", payment_item_position);
                            mAppSharedPref.saveBoolean("acc_updateClick", true);
                            mAppSharedPref.saveInteger("acc_updatePosition", acc_item_position);
                            new Acc_Detail_Adapter().notifyItemChanged(acc_item_position);
                            new Payment_Detail_Adapter().notifyItemChanged(payment_item_position);

                            binding.rvPaymentDetail.scrollToPosition(payment_item_position);
                            fm.clearFragmentResult(REQUEST_KEY);


                            Log.d("resultClear",result.toString());
                        }
                    }
                }
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding= DataBindingUtil.inflate(inflater,R.layout.payment_detail_fragment,container,false);
        viewModel=new ViewModelProvider(activity).get(Paymetn_Detail_viewModel.class);
        mAppSharedPref=new AppSharedPref(activity);


        binding.txtUserName.setText(new AppSharedPref(activity).getUserData().getName());
        binding.txtUserMobNo.setText(new AppSharedPref(activity).getUserData().getContact());



        mAppSharedPref.saveBoolean("payment_updateClick",false);
        mAppSharedPref.saveInteger("payment_updatePosition",-1);
        mAppSharedPref.saveBoolean("acc_updateClick",false);
        mAppSharedPref.saveInteger("acc_updatePosition",-1);





        View view =binding.getRoot();
        initView();
        return view;
    }

    public void initView(){


        manager= new LinearLayoutManager(getContext());
        binding.progressBar.setVisibility(View.VISIBLE);



        viewModel.getPaymetMethodList(activity,binding).observe(activity,pymentmethods -> {

            if(pymentmethods!=null) {

                pymentmethods_list=pymentmethods;

                viewModel.getAccList(activity,binding).observe(activity,accounts -> {

                    binding.progressBar.setVisibility(View.GONE);

                    if(accounts!=null){
                        adapter = new Payment_Detail_Adapter(activity, pymentmethods,accounts,binding.getRoot(),new Payment_Detail_Adapter.ClickListner() {
                            @Override
                            public void dilogFragment(Pymentmethod pymentmethod, int position, int payment_item_postion) {


                                add_acc_dialog_fragment = new Add_Acc_Dialog_Fragment();
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("payment_method",pymentmethod);
                                bundle.putInt("payment_item_postion",payment_item_postion);
                                bundle.putInt("acc_item_postion",position);
                                add_acc_dialog_fragment.setArguments(bundle);
                                ft = activity.getSupportFragmentManager().beginTransaction();
                                Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("dialog");
                                if (prev != null) {
                                    ft.remove(prev);
                                }
                                ft.addToBackStack(null);

                                add_acc_dialog_fragment.show(ft, "dialog");

                            }
                        });
                        binding.rvPaymentDetail.setLayoutManager(manager);
                        binding.rvPaymentDetail.setAdapter(adapter);
                    }
                    else {


                        adapter = new Payment_Detail_Adapter(activity, pymentmethods, new Payment_Detail_Adapter.ClickListner() {
                            @Override
                            public void dilogFragment(Pymentmethod pymentmethod, int position, int payment_item_postion) {


                                add_acc_dialog_fragment = new Add_Acc_Dialog_Fragment();
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("payment_method",pymentmethod);
                                bundle.putInt("payment_item_postion",payment_item_postion);
                                bundle.putInt("acc_item_postion",position);
                                add_acc_dialog_fragment.setArguments(bundle);
                                ft = activity.getSupportFragmentManager().beginTransaction();
                                Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("dialog");
                                if (prev != null) {
                                    ft.remove(prev);
                                }
                                ft.addToBackStack(null);
                                add_acc_dialog_fragment.show(ft, "dialog");

                            }
                        });
                        binding.rvPaymentDetail.setLayoutManager(manager);
                        binding.rvPaymentDetail.setAdapter(adapter);
                    }
                });
                }
        });

        binding.rvPaymentDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    activity.activityMainBinding.bottomNavigationView.setTranslationY(150);
                } else if (dy < 0 ) {
                    activity.activityMainBinding.bottomNavigationView.setTranslationY(0);
//                   activity.showBottomNavigation();

                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        activity.toolbar_title.setText(Paymet_Detail_Fragment.this.getTag());
        activity.hideBottomNavigation();


    }

    @Override
    public void editAcc(MainActivity activity, Accountdetail accountdetail,View view,int position,int payment_item_postion) {



        binding=DataBindingUtil.getBinding(view);
        rv_save_state=binding.rvPaymentDetail.getLayoutManager().onSaveInstanceState();

        add_acc_dialog_fragment = new Add_Acc_Dialog_Fragment();
        FragmentManager fragmentManager=activity.getSupportFragmentManager();
        Bundle bundle=new Bundle();
        bundle.putSerializable("edit_acc",accountdetail);
        bundle.putInt("payment_item_postion",payment_item_postion);
        bundle.putInt("acc_item_postion",position);
        add_acc_dialog_fragment.setArguments(bundle);
         ft = activity.getSupportFragmentManager().beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        add_acc_dialog_fragment.show(ft, "dialog");
    }

    @Override
    public void deleteAcc(MainActivity activity,Accountdetail accountdetail,View view,Acc_Detail_Adapter adapter,int position,int payment_item_postion) {

        binding=DataBindingUtil.getBinding(view);
      binding.progressBar.setVisibility(View.VISIBLE);
      viewModel=new ViewModelProvider(activity).get(Paymetn_Detail_viewModel.class);
        viewModel.deleteAcc(activity,binding,accountdetail.getId()).observe(activity,accounts -> {

            if(accounts!=null){

                if(accounts.equals("1")){




                    viewModel.getAccList(activity,binding).observe(activity,accounts1 -> {

                        binding.progressBar.setVisibility(View.GONE);

                    });

                    new AppSharedPref(activity).saveBoolean("payment_updateClick", true);
                    new AppSharedPref(activity).saveInteger("payment_updatePosition", payment_item_postion);
                    new AppSharedPref(activity).saveBoolean("acc_updateClick", true);
                    new AppSharedPref(activity).saveInteger("acc_updatePosition", position);
                    new Acc_Detail_Adapter().notifyItemChanged(position);
                    new Payment_Detail_Adapter().notifyItemChanged(payment_item_postion);

                }
            }
                }


                );

    }

    @Override
    public void preferredAcc(MainActivity activity, Accountdetail accountdetail, View view, Acc_Detail_Adapter acc_detail_adapter, int position, int payment_item_postion) {

        binding=DataBindingUtil.getBinding(view);
        binding.progressBar.setVisibility(View.VISIBLE);
        viewModel=new ViewModelProvider(activity).get(Paymetn_Detail_viewModel.class);
        viewModel.preferredAcc(activity,binding,accountdetail.getId(),accountdetail.getPaymentmethodid()).observe(activity,accounts -> {

                    if(accounts!=null){

                        if(accounts.equals("1")){




                            viewModel.getAccList(activity,binding).observe(activity,accounts1 -> {

                                binding.progressBar.setVisibility(View.GONE);

                            });

                            new AppSharedPref(activity).saveBoolean("payment_updateClick", true);
                            new AppSharedPref(activity).saveInteger("payment_updatePosition", payment_item_postion);
                            new AppSharedPref(activity).saveBoolean("acc_updateClick", true);
                            new AppSharedPref(activity).saveInteger("acc_updatePosition", position);
                            new Acc_Detail_Adapter().notifyItemChanged(position);
                            new Payment_Detail_Adapter().notifyItemChanged(payment_item_postion);

                        }
                    }
                }


        );
    }
    /*@Override
    public void editAcc(Accountdetail accountdetail) {

        add_acc_dialog_fragment = new Add_Acc_Dialog_Fragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("edit_acc",accountdetail);
        add_acc_dialog_fragment.setArguments(bundle);
        ft = activity.getSupportFragmentManager().beginTransaction();
        Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        add_acc_dialog_fragment.show(ft, "dialog");

    }

    @Override
    public void deleteAcc(Accountdetail accountdetail) {

    }*/

    @Override
    public void onStart() {
        super.onStart();





}

    @Override
    public void onDetach() {
        super.onDetach();
        fm.clearFragmentResult(REQUEST_KEY);
        fm.clearFragmentResultListener(REQUEST_KEY);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
