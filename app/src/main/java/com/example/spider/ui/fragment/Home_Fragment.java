package com.example.spider.ui.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.adapter.Home_Adapter;
import com.example.spider.adapter.MainSliderAdapter;
import com.example.spider.databinding.HomeFragmentBinding;
import com.example.spider.model.Approvedrequestidlist;
import com.example.spider.model.Banner;
import com.example.spider.model.Website_item;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.utils.PicassoImageLoadingService;
import com.example.spider.view_model.Home_Fragment_viewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import ss.com.bannerslider.Slider;

public class Home_Fragment extends Fragment  {

    public HomeFragmentBinding binding;
    Home_Adapter adapter;
    Home_Fragment_viewModel home_fragment_viewModel;
    RecyclerView.LayoutManager manager;
    List<Website_item> list=new ArrayList<>();
    List<Banner> banners=new ArrayList<>();
    MainActivity activity;
    Home_Adapter.ClickListner clickListner;
    Animation bounce_anim,LR_anim,RL_anim,rotate_zoom,blink;
    ExtentionUtils utils=new ExtentionUtils();
    Fragment fragment;
    public String REWARD_AMT ="rewardAmt", REFERAL_USER ="referalUser";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.home_fragment,container,false);
        activity=(MainActivity) getActivity();
        bounce_anim= AnimationUtils.loadAnimation(activity,R.anim.bounce_anim);
        LR_anim= AnimationUtils.loadAnimation(activity,R.anim.left_to_right_anim);
        RL_anim= AnimationUtils.loadAnimation(activity,R.anim.right_to_left_anim);
        rotate_zoom=AnimationUtils.loadAnimation(activity,R.anim.zoom_in_rotate_anim);
        blink=AnimationUtils.loadAnimation(activity,R.anim.blink_anim);
        home_fragment_viewModel=new ViewModelProvider(requireActivity()).get(Home_Fragment_viewModel.class);
        View view =binding.getRoot();
        initView();
        Log.e("onCreate","Runnnnnnnn........HomeFragment");
//        Log.d("lifecycle","onCreateView invoked");
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
                fragment = new Notification_Fragment();
                utils.loadFragment(activity.getSupportFragmentManager(), fragment, R.id.main_container, false, getResources().getString(R.string.notifiction), null);
                break;
        }
        return true;
    }

    public void initView(){



        manager= new LinearLayoutManager(getContext());
//        adapter=new Home_Adapter(getContext(),list);

//        binding.rvWebsite.setLayoutManager(manager);
//        binding.rvWebsite.setAdapter(adapter);


//        binding.txtWalletAmt.startAnimation(animation);

//        binding.imgHomeApp.setAnimation(rotate_zoom);

        binding.clDepoiste.setAnimation(RL_anim);
        binding.clWithdraw.setAnimation(LR_anim);
        binding.imgCreateid.setAnimation(blink);
        binding.txtCreateid.setAnimation(blink);

        home_fragment_viewModel.getWalletList(activity,binding).observe(activity,walletlist -> {

            if(walletlist!=null){

//                String total=String.valueOf(Integer.parseInt(walletlist.getTotal().toString())+Integer.parseInt(walletlist.getReward().toString()));
                binding.txtWalletAmt.setText(walletlist.getTotal().toString());
//                new AppSharedPref(activity).saveString(REWARD_AMT,walletlist.getReward().toString());
//                new AppSharedPref(activity).saveString(REFERAL_USER,walletlist.getReward().toString());
            }

        });

        home_fragment_viewModel.getRewardtList(activity,binding).observe(activity,referaldatum -> {

            if(referaldatum!=null){


                new AppSharedPref(activity).saveString(REWARD_AMT,referaldatum.getReward().toString());
                new AppSharedPref(activity).saveString(REFERAL_USER,referaldatum.getTotaluser().toString());
            }

        });

        home_fragment_viewModel.getBannerList(activity,binding).observe(requireActivity(),bannersList -> {

            binding.progressBar.setVisibility(View.GONE);

                Slider.init(new PicassoImageLoadingService(activity));
                binding.bannerSlider.setAdapter(new MainSliderAdapter(bannersList));
                binding.bannerSlider.setLoopSlides(true);
                binding.bannerSlider.setInterval(5000);



        });

        binding.clDepoiste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment deposite_fragment=new Deposite_Fragment();

                utils.loadFragment(activity.getSupportFragmentManager(),deposite_fragment,R.id.main_container,false,
                        activity.getResources().getString(R.string.deposit),null);
            }
        });

        binding.clWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment withdraw_fragment=new Withdraw_Fragment();

                utils.loadFragment(activity.getSupportFragmentManager(),withdraw_fragment,R.id.main_container,false,
                        activity.getResources().getString(R.string.withdraw),null);
            }
        });
        binding.clBtnCreateId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment website_list_fragment=new Website_List_Fragment();

                utils.loadFragment(activity.getSupportFragmentManager(),website_list_fragment,R.id.main_container,false,
                        activity.getResources().getString(R.string.create_id),null);
            }
        });





        /*Slider.init(new PicassoImageLoadingService(activity));
        binding.bannerSlider.setAdapter(new MainSliderAdapter());*/


      /*  Handler handler=new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.txtWalletHeader.setVisibility(View.VISIBLE);
                binding.txtWalletAmt.setVisibility(View.VISIBLE);
                binding.txtWalletAmt.setAnimation(bounce_anim);
            }
        },1500);*/
        binding.svHome.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {


                if( scrollY > oldScrollY) {

                    activity.activityMainBinding.bottomNavigationView.setTranslationY(250);
                }  else  {
                    activity.activityMainBinding.bottomNavigationView.setTranslationY(0);
//                   activity.showBottomNavigation();

                }

            }
        });

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.svHome.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {


                }
            });
        }
*/
        binding.rvWebsite.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

       home_fragment_viewModel.getMyIdList(activity,binding).observe(requireActivity(),my_id_list -> {

           binding.progressBar.setVisibility(View.GONE);
           if(my_id_list!=null){


               adapter=new Home_Adapter(getContext(),my_id_list,clickListner=new Home_Adapter.ClickListner() {
                   @Override
                   public void createID(Website_item website_items) {

                       CreateId(website_items);
                   }

                   @Override
                   public void deposite(Approvedrequestidlist approvedrequestidlist) {
                       Fragment deposite_fragment=new Deposite_id_Fragment();

                       Bundle bundle=new Bundle();
                       bundle.putSerializable("id_data",approvedrequestidlist);

                       utils.loadFragment(activity.getSupportFragmentManager(),deposite_fragment,R.id.main_container,false,
                               activity.getResources().getString(R.string.deposit),bundle);
                   }

                   @Override
                   public void withdraw(Approvedrequestidlist approvedrequestidlist) {
                       Fragment deposite_fragment=new Withdraw_id_Fragment();

                       Bundle bundle=new Bundle();
                       bundle.putSerializable("id_data",approvedrequestidlist);
                       utils.loadFragment(activity.getSupportFragmentManager(),deposite_fragment,R.id.main_container,false,
                               activity.getResources().getString(R.string.withdraw),bundle);
                   }
               });
               binding.rvWebsite.setLayoutManager(manager);
               binding.rvWebsite.setAdapter(adapter);
           }

       });
    }

    private void CreateId(Website_item website_items) {
        Fragment fragment= new Create_Id_Fragment();
        Bundle bundle=new Bundle();

        bundle.putSerializable("selected_website_item",  website_items);

        new ExtentionUtils().loadFragment(activity.getSupportFragmentManager(),fragment,R.id.main_container,false,activity.getResources().getString(R.string.create_id),bundle);
    }


    @Override
    public void onResume() {
        super.onResume();
        activity.toolbar_title.setText(Home_Fragment.this.getTag());
        activity.showBottomNavigation();

        Log.d("lifecycle","onResume invoked");

    }




}
