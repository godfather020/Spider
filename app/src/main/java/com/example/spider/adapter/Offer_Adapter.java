package com.example.spider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.BR;
import com.example.spider.R;
import com.example.spider.model.Approvedrequestidlist;
import com.example.spider.model.Offer;
import com.example.spider.utils.ExtentionUtils;

import java.util.List;

public class Offer_Adapter extends RecyclerView.Adapter<Offer_Adapter.MyViewHolder> {


    Context context;
    boolean clicked=false;
    List<Offer> offer_list;
    int position1;
    ClickListner clickListner;
    ExtentionUtils utils =new ExtentionUtils();

    public Offer_Adapter(Context context) {
        this.context = context;
    }

    public Offer_Adapter(Context context, List<Offer> offer_list, Offer_Adapter.ClickListner clickListner) {
        this.context = context;
        this.offer_list=offer_list;
        this.clickListner=clickListner;
    }

    @Override
    public int getItemViewType(int position) {
        position1=position;
        return position;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_offer,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        utils.setImage(offer_list.get(position).getPhoto(), holder.img, context.getResources().getDrawable(R.drawable.app_logo));


    }

        @Override
        public int getItemCount() {
            return offer_list.size();
        }

    public class  MyViewHolder extends RecyclerView.ViewHolder{

        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img_offer);

        }
    }

    public interface ClickListner{

        public void getCode(Offer offer_item);
    }

    }
















