package com.example.spider.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.R;
import com.example.spider.model.Approvedrequestidlist;
import com.example.spider.model.UserDetail;
import com.example.spider.model.Website;

import java.util.ArrayList;
import java.util.List;

public class Refer_Earn_Adapter extends RecyclerView.Adapter<Refer_Earn_Adapter.MyViewHolder> implements Filterable {


    Context context;
    boolean clicked=false;
    List<UserDetail> userDetail;
    List<UserDetail> filter_userDetail;
    int position1;
    Refer_Earn_Adapter.ClickListner click_Listener;
    public Refer_Earn_Adapter(Context context,  List<UserDetail> userDetail,Refer_Earn_Adapter.ClickListner click_Listener) {
        this.context = context;
        this.userDetail=userDetail;
        this.filter_userDetail=userDetail;
        this.click_Listener=click_Listener;
    }

    @Override
    public int getItemViewType(int position) {
        position1=position;
        return position;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_contact_list,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.name.setText(filter_userDetail.get(position).getName());
        holder.mobNo.setText(filter_userDetail.get(position).getContact());

        holder.btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                click_Listener.click_Listener( filter_userDetail.get(position).getContact());

            }
        });
       /* holder.img.setOnClickListener(new View.OnClickListener() {
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
        });
*/
    }

        @Override
        public int getItemCount() {
            return filter_userDetail.size();
        }

    public class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,mobNo;
        ImageView btn_send;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.txt_name);
            mobNo=itemView.findViewById(R.id.txt_mob_no);
            btn_send=itemView.findViewById(R.id.img_btn_option);


        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filter_userDetail = userDetail;
                } else {
                    List<UserDetail> filteredList = new ArrayList<>();
                    for (UserDetail row : userDetail) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())|| row.getContact().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }

                    filter_userDetail = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filter_userDetail;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filter_userDetail = (ArrayList<UserDetail>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

   public interface  ClickListner{

      public  void   click_Listener(String mobNo);
    }

    }
















