package com.example.spider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.R;
import com.example.spider.model.Pymentmethod;
import com.example.spider.utils.ExtentionUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Payment_Method_Adapter extends RecyclerView.Adapter<Payment_Method_Adapter.MyViewHolder> {


    Context context;
    boolean clicked=false;
    List<Pymentmethod> list;
    ExtentionUtils extentionUtils=new ExtentionUtils();
    ClickListiner clickListiner;
    boolean deposit;
    int position1;
    public Payment_Method_Adapter(Context context, boolean deposit,List<Pymentmethod> list,ClickListiner clickListiner) {
        this.context = context;
        this.list=list;
        this.clickListiner=clickListiner;
        this.deposit=deposit;
    }

    @Override
    public int getItemViewType(int position) {
        position1=position;
        return position;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_payment_method_layout,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        if(deposit){
            if(list.get(holder.getPosition()).getStatus().equals("0")){
                holder.cl_parent.setVisibility(View.GONE);
            }
        }
        holder.txt_payment_method.setText(list.get(position).getName());

        extentionUtils.setCircleImage(list.get(position).getPhoto(),holder.img,context.getResources().getDrawable(R.mipmap.ic_launcher_round));

        holder.cl_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListiner.getPaymentMethod(list.get(position));

            }
        });


    }

        @Override
        public int getItemCount() {
            return list.size();
        }

    public class  MyViewHolder extends RecyclerView.ViewHolder{



        TextView txt_payment_method;
        CircleImageView img;
        ConstraintLayout cl_parent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_payment_method=itemView.findViewById(R.id.txt_payment_method);
            img=itemView.findViewById(R.id.img_payment_method);
            cl_parent=itemView.findViewById(R.id.cl_payment_method_parent_layout);
        }
    }

    public interface ClickListiner{

        public void getPaymentMethod(Pymentmethod pymentmethod);
    }

    }
















