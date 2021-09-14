package com.example.spider.inter_face;

import android.app.Activity;
import android.view.View;

import com.example.spider.MainActivity;
import com.example.spider.adapter.Acc_Detail_Adapter;
import com.example.spider.model.Accountdetail;

public interface ClickListner {

    public  void editAcc(MainActivity activity, Accountdetail accountdetail,View view,int position,int payment_item_position);
    public  void  deleteAcc(MainActivity activity, Accountdetail accountdetail, View view, Acc_Detail_Adapter acc_detail_adapter,int position,int payment_item_position);
    public  void  preferredAcc(MainActivity activity, Accountdetail accountdetail, View view, Acc_Detail_Adapter acc_detail_adapter,int position,int payment_item_position);
}
