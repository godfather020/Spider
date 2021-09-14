package com.example.spider.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.spider.R;
import com.example.spider.databinding.HomeFragmentBinding;
import com.example.spider.databinding.IdsFragmentBinding;

public class Ids_Fragment extends Fragment {

    IdsFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.ids_fragment,container,false);

        View view =binding.getRoot();
        return view;
    }
}
