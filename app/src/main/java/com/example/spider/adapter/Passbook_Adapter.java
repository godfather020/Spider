package com.example.spider.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.BR;
import com.example.spider.R;
import com.example.spider.model.Transactionhistory;
import com.example.spider.model.Website;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Passbook_Adapter extends RecyclerView.Adapter<Passbook_Adapter.MyViewHolder> {


    Context context;
    boolean clicked=false;
    List<Transactionhistory> list_Website ;
    List<Transactionhistory> ticketList;
    List<Transactionhistory> ticketListType=new ArrayList<>();
    List<Transactionhistory> ticketListTitle=new ArrayList<>();
    int position1;
    ClickListener clickListener;

    public Passbook_Adapter() {
    }

    public Passbook_Adapter(Context context, List<Transactionhistory> list_Website, ClickListener clickListener) {

        
        this.context = context;
        this.ticketList=list_Website;
        this.list_Website=new ArrayList<Transactionhistory>();
        this.list_Website.addAll(list_Website);
        this.clickListener=clickListener;
    }
    public Passbook_Adapter(Context context, List<Transactionhistory> list_Website) {
        this.context = context;
        this.ticketList=list_Website;
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


        holder.viewDataBinding.setVariable(BR.trans_item, ticketList.get(holder.getAdapterPosition()));
        holder.viewDataBinding.executePendingBindings();

        if(ticketList.get(holder.getAdapterPosition()).getType().equals("changeidpassword")){

            holder.txtAmt.setVisibility(View.GONE);
        }else  holder.txtAmt.setVisibility(View.VISIBLE);

        if(ticketList.get(holder.getAdapterPosition()).getStatus().equals("Approved")){
            holder.txtStatus.setTextColor(context.getResources().getColor(R.color.green));

        }else if(ticketList.get(holder.getAdapterPosition()).getStatus().equals("Rejected")){

            holder.txtStatus.setTextColor(context.getResources().getColor(R.color.red));

        }else if(ticketList.get(holder.getAdapterPosition()).getStatus().equals("Pending")){

            holder.txtStatus.setTextColor(context.getResources().getColor(R.color.orange));

        }else if(ticketList.get(holder.getAdapterPosition()).getStatus().equals("Cancelled")){

            holder.txtStatus.setTextColor(context.getResources().getColor(R.color.red));

        }
      /*  holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ticketList.get(position1).isClicked()){
                    ticketList.get(position).setClicked(false);
                    holder.ll.setVisibility(View.GONE);
                    holder.cl.setVisibility(View.GONE);


                }else {
                    ticketList.get(position).setClicked(true);
                    holder.ll.setVisibility(View.VISIBLE);
                    holder.cl.setVisibility(View.VISIBLE);

                }


            }
        });*/

        holder.cl_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickListener.tran_detail(ticketList.get(holder.getAdapterPosition()),holder.getAdapterPosition());
            }
        });


    }

        @Override
        public int getItemCount() {
            return ticketList.size();
        }


    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ticketList.clear();
        if (charText.length() == 0) {
            ticketList.addAll(list_Website);
        } else {
            for (Transactionhistory wp : list_Website) {
                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    ticketList.add(wp);
                }
               /* else if(wp.getType().toLowerCase(Locale.getDefault()).contains(charText)){
                    ticketList.add(wp);
                }else {

                }*/
            }
        }
        notifyDataSetChanged();
    }
    public void filterDateRange(Date startDate, Date endDate,String type) {
        ticketList.clear();
        if ( startDate==null||startDate.equals("") ) {


            if (type != null && !type.isEmpty()) {


                for (Transactionhistory wp : list_Website) {
                    if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(type.toLowerCase())) {
                        ticketList.add(wp);
                    }

                }

            }else {

                ticketList.addAll(list_Website);
            }

            notifyDataSetChanged();
        } else {
            try {
            for (Transactionhistory wp : list_Website) {

                SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM yy");
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy hh:mm");
                Date d = sdf.parse(wp.getNotificationDate());
                String date=sdf1.format(d);
                Date strDate=sdf1.parse(date);
                Log.d("date","Date===  " +d +" DateStr=== "+ strDate);

                    if (startDate.equals(strDate) || endDate.equals(strDate)) {
                        ticketList.add(wp);
                    }else  if (startDate.before(strDate) && endDate.after(strDate)) {
                        ticketList.add(wp);
                    } else {

                    }

                }
            } catch (ParseException e) {
                e.printStackTrace();

            }
            if (type != null && !type.isEmpty()) {

                ticketListType.clear();
                for (Transactionhistory wp : ticketList) {
                    if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(type.toLowerCase())) {
                        ticketListType.add(wp);
                    }

                }
                ticketList.clear();
                ticketList.addAll(ticketListType);

            }

            notifyDataSetChanged();
        }
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{


       /* ImageView img;
        LinearLayout ll;*/
        ConstraintLayout cl_parent;
        ViewDataBinding viewDataBinding;
        TextView txtStatus,txtAmt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           /* img=itemView.findViewById(R.id.img_btn_option);
            ll=itemView.findViewById(R.id.ll_img);*/
            cl_parent=itemView.findViewById(R.id.cl_parent);
            viewDataBinding= DataBindingUtil.bind(itemView);
            txtStatus=itemView.findViewById(R.id.txt_tran_amt_status);
            txtAmt=itemView.findViewById(R.id.txt_tran_amt);
        }
    }


    public  interface ClickListener{

        public void tran_detail(Transactionhistory transactionhistory,int position);
    }
    }
















