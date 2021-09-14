package com.example.spider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.R;
import com.example.spider.model.Transactionhistory;

import java.util.List;

public class Id_Trans_History_Adapter extends RecyclerView.Adapter<Id_Trans_History_Adapter.MyViewHolder> {


    Context context;
    boolean clicked=false;
    List<Transactionhistory> list_Website;
    int position1;
    public Id_Trans_History_Adapter(Context context, List<Transactionhistory> list_Website) {
        this.context = context;
        this.list_Website=list_Website;
    }

    @Override
    public int getItemViewType(int position) {
        position1=position;
        return position;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_trans_history_layout,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.img.setVisibility(View.GONE);
        holder.viewDataBinding.setVariable(BR.trans_item,list_Website.get(position));
        holder.viewDataBinding.executePendingBindings();
      /*  holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list_Website.get(position1).isClicked()){
                    list_Website.get(position).setClicked(false);
                    holder.ll.setVisibility(View.GONE);
                    holder.cl.setVisibility(View.GONE);


                }else {
                    list_Website.get(position).setClicked(true);
                    holder.ll.setVisibility(View.VISIBLE);
                    holder.cl.setVisibility(View.VISIBLE);

                }


            }
        });*/

    }

        @Override
        public int getItemCount() {
            return list_Website.size();
        }

    public class  MyViewHolder extends RecyclerView.ViewHolder{


        ImageView img;
        /* LinearLayout ll;
         ConstraintLayout cl;*/
        ViewDataBinding viewDataBinding;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           img=itemView.findViewById(R.id.img_btn_option);
            /* ll=itemView.findViewById(R.id.ll_img);
            cl=itemView.findViewById(R.id.cl_option);*/
            viewDataBinding= DataBindingUtil.bind(itemView);

        }
    }

    }
















