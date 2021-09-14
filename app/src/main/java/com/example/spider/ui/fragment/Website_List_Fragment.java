package com.example.spider.ui.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.spider.adapter.Website_List_Adapter;
import com.example.spider.databinding.HomeFragmentBinding;
import com.example.spider.databinding.WebsiteListFragmentBinding;
import com.example.spider.model.Website_item;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Home_Fragment_viewModel;
import com.example.spider.view_model.Website_List_Fragment_viewModel;

import java.util.ArrayList;
import java.util.List;

public class Website_List_Fragment extends Fragment  {

    public WebsiteListFragmentBinding binding;
    Website_List_Adapter adapter =new Website_List_Adapter();
    Website_List_Fragment_viewModel home_fragment_viewModel;
    RecyclerView.LayoutManager manager;
    List<Website_item> list=new ArrayList<>();
    MainActivity activity;
    Website_List_Adapter.ClickListner clickListner;
    ExtentionUtils utils=new ExtentionUtils();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.website_list_fragment,container,false);
        activity=(MainActivity) getActivity();
        home_fragment_viewModel=new ViewModelProvider(requireActivity()).get(Website_List_Fragment_viewModel.class);
        View view =binding.getRoot();
        initView();
        Log.e("onCreate","Runnnnnnnn........HomeFragment");
//        Log.d("lifecycle","onCreateView invoked");
        return view;
    }

    public void initView(){




        manager= new LinearLayoutManager(getContext());
//        adapter=new Home_Adapter(getContext(),list);

//        binding.rvWebsite.setLayoutManager(manager);
//        binding.rvWebsite.setAdapter(adapter);

      /* binding.rvWebsite.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
       });*/

       home_fragment_viewModel.getWebsiteLIst(activity,binding).observe(requireActivity(),website_items -> {

           if(website_items!=null){

            binding.progressBar.setVisibility(View.GONE);
               adapter=new Website_List_Adapter(getContext(),website_items,clickListner=new Website_List_Adapter.ClickListner() {
                   @Override
                   public void createID(Website_item website_items) {

                       CreateId(website_items);
                   }
               });
               binding.rvWebsite.setLayoutManager(manager);
               binding.rvWebsite.setAdapter(adapter);
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
        activity.toolbar_title.setText(Website_List_Fragment.this.getTag());
        Log.d("lifecycle","onResume invoked");
        activity.hideBottomNavigation();

    }




}
