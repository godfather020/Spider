package com.example.spider.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.adapter.Home_Adapter;
import com.example.spider.adapter.My_Id_Adapter;
import com.example.spider.databinding.HomeFragmentBinding;
import com.example.spider.databinding.MyIdFragmentBinding;
import com.example.spider.model.Approvedrequestidlist;
import com.example.spider.model.Website;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.My_id_Fragment_viewModel;

import java.util.ArrayList;
import java.util.List;

public class My_Id_Fragment extends Fragment {

    MyIdFragmentBinding binding;
    My_id_Fragment_viewModel viewModel;
    My_Id_Adapter adapter;
    RecyclerView.LayoutManager manager;
    List<Website> list=new ArrayList<>();
    MainActivity activity;
    ExtentionUtils utils=new ExtentionUtils();
    boolean sideNav=false;
    FragmentTransaction ft;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.my_id_fragment,container,false);
        activity=(MainActivity) getActivity();
        viewModel=new ViewModelProvider(activity).get(My_id_Fragment_viewModel.class);
        View view =binding.getRoot();
        Bundle bundle=getArguments();
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
        binding.rvMyId.setLayoutManager(manager);
        binding.progressBar.setVisibility(View.VISIBLE);
        viewModel.getMyIdList(activity,binding).observe(activity,myId_List -> {

            binding.progressBar.setVisibility(View.GONE);
            if(myId_List!=null){

                adapter=new My_Id_Adapter(getContext(), myId_List, new My_Id_Adapter.OnClickListner() {
                    @Override
                    public void deposite(Approvedrequestidlist approvedrequestidlist) {
                       depositAmt(approvedrequestidlist);
                    }

                    @Override
                    public void withdraw(Approvedrequestidlist approvedrequestidlist) {
                       withdrawAmt(approvedrequestidlist);
                    }

                    @Override
                    public void optionMenu(View view,Approvedrequestidlist approvedrequestidlist) {
                        PopupMenu popupMenu;
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){

                            popupMenu=new PopupMenu(activity,view,Gravity.END,0,R.style.MyPopupMenu);
                        }else
                         popupMenu=new PopupMenu(activity,view,Gravity.END);

                        popupMenu.getMenuInflater().inflate(R.menu.my_id_menu,popupMenu.getMenu());

                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {


                                switch (menuItem.getItemId()){

                                    case R.id.my_id_om_deposit :
                                        depositAmt(approvedrequestidlist);
                                        break;
                                    case R.id.my_id_om_withdraw:
                                        withdrawAmt(approvedrequestidlist);
                                        break;
                                    case R.id.my_id_detail:
                                        idDetail(approvedrequestidlist);
                                        break;
                                    case R.id.my_id_om_change_pass:
                                        AlertDialog.Builder  builder=new AlertDialog.Builder(activity);
                                        builder.setCancelable(false);
                                        builder.setTitle("Are you want to change your password?");

                                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                changeIdPass(approvedrequestidlist);
                                            }
                                        });
                                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });

                                        AlertDialog alertDialog=builder.create();
                                        alertDialog.show();
                                        break;
                                    case R.id.my_id_om_close_id:

                                        closeId(approvedrequestidlist);
                                        break;
                                }
                                return true;
                            }
                        });


                        popupMenu.show();

                    }
                });

                binding.rvMyId.setAdapter(adapter);
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

        binding.rvMyId.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    activity.activityMainBinding.bottomNavigationView.setTranslationY(250);
                } else if (dy < 0 ) {
                    activity.activityMainBinding.bottomNavigationView.setTranslationY(0);
//                   activity.showBottomNavigation();

                }
            }
        });

    }

    public void depositAmt(Approvedrequestidlist approvedrequestidlist){

        Fragment deposite_fragment=new Deposite_id_Fragment();

        Bundle bundle=new Bundle();
        bundle.putSerializable("id_data",approvedrequestidlist);

        utils.loadFragment(activity.getSupportFragmentManager(),deposite_fragment,R.id.main_container,false,
                activity.getResources().getString(R.string.deposit),bundle);
    }
    public void withdrawAmt(Approvedrequestidlist approvedrequestidlist){

        Fragment deposite_fragment=new Withdraw_id_Fragment();

        Bundle bundle=new Bundle();
        bundle.putSerializable("id_data",approvedrequestidlist);
        utils.loadFragment(activity.getSupportFragmentManager(),deposite_fragment,R.id.main_container,false,
                activity.getResources().getString(R.string.withdraw),bundle);
    }
     public void idDetail(Approvedrequestidlist approvedrequestidlist){

        Fragment deposite_fragment=new Id_Detail_Fragment();

        Bundle bundle=new Bundle();
        bundle.putSerializable("id_data",approvedrequestidlist);
        utils.loadFragment(activity.getSupportFragmentManager(),deposite_fragment,R.id.main_container,false,
                activity.getResources().getString(R.string.id_detail),bundle);
    }
     public void changeIdPass(Approvedrequestidlist approvedrequestidlist){

        binding.progressBar.setVisibility(View.VISIBLE);

        viewModel.sendRequestForChangePassword(activity,binding,approvedrequestidlist.getId()).observe(activity,it -> {

            binding.progressBar.setVisibility(View.GONE);
            if(it.equals(1)){

                Fragment deposite_fragment=new Home_Fragment();


                utils.loadFragment(activity.getSupportFragmentManager(),deposite_fragment,R.id.main_container,true,
                        activity.getResources().getString(R.string.home),null);
            }

        });

    }
     public void closeId(Approvedrequestidlist approvedrequestidlist){

         DialogFragment close_id_dialog_fragment=new Close_Id_Dialog_Fragment();

            Bundle bundle=new Bundle();
            bundle.putSerializable("cloesId",approvedrequestidlist);
            close_id_dialog_fragment.setArguments(bundle);
         ft = activity.getSupportFragmentManager().beginTransaction();
         Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("dialog");
         if (prev != null) {
             ft.remove(prev);
         }
         ft.addToBackStack(null);

         close_id_dialog_fragment.show(ft, "dialog");


    }



    @Override
    public void onResume() {
        super.onResume();
        activity.toolbar_title.setText(My_Id_Fragment.this.getTag());
//        activity.hideBottomNavigation();

    }
}
