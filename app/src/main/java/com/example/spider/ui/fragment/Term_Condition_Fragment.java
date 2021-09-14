package com.example.spider.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.databinding.HomeFragmentBinding;
import com.example.spider.databinding.TermConditionFragmentBinding;

public class Term_Condition_Fragment extends Fragment {

    TermConditionFragmentBinding binding;
    MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.term_condition_fragment,container,false);
        activity=(MainActivity)getActivity();

        View view =binding.getRoot();
        binding.webView.getSettings().setJavaScriptEnabled(true);
       binding.webView.loadUrl("https://impetrosys.com/spiderapp/terms.php");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.toolbar_title.setText(this.getTag());
        activity.hideBottomNavigation();

    }
}
