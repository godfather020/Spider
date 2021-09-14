package com.example.spider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.BR;
import com.example.spider.R;
import com.example.spider.model.Approvedrequestidlist;
import com.example.spider.model.Website;
import com.example.spider.model.Website_item;
import com.example.spider.utils.ExtentionUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class My_Id_Adapter extends RecyclerView.Adapter<My_Id_Adapter.MyViewHolder> implements Filterable {


    Context context;
    boolean clicked=false;
    List<Approvedrequestidlist> list_Website;
    List<Approvedrequestidlist> filter_list_Website;
    int position1;
    OnClickListner clickListner;
    ExtentionUtils utils=new ExtentionUtils();
    public My_Id_Adapter(Context context, List<Approvedrequestidlist> list_Website, OnClickListner clickListner) {
        this.context = context;
        this.list_Website=list_Website;
        this.filter_list_Website=list_Website;
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

        View view = LayoutInflater.from(context).inflate(R.layout.rv_my_id_layout,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.viewDataBinding.setVariable(BR.my_id, filter_list_Website.get(position));
        holder.viewDataBinding.executePendingBindings();

      /*  holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filter_list_Website.get(position1).isClicked()){
                    filter_list_Website.get(position).setClicked(false);
                    holder.ll.setVisibility(View.GONE);
                    holder.cl.setVisibility(View.GONE);


                }else {
                    filter_list_Website.get(position).setClicked(true);
                    holder.ll.setVisibility(View.VISIBLE);
                    holder.cl.setVisibility(View.VISIBLE);

                }


            }
        });*/
        utils.setCircleImage(filter_list_Website.get(position).getWebsitephoto(),holder.imgWebsite,context.getResources().getDrawable(R.drawable.app_logo));
        holder.btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.withdraw(filter_list_Website.get(position));
            }
        });
holder.btnDeposite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.deposite(filter_list_Website.get(position));
            }
        });
holder.btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.optionMenu(view,filter_list_Website.get(position));
            }
        });

    }

        @Override
        public int getItemCount() {
            return filter_list_Website.size();
        }

    public class  MyViewHolder extends RecyclerView.ViewHolder{


       /* ImageView img;
        LinearLayout ll;
        ConstraintLayout cl;*/
       ViewDataBinding viewDataBinding;
       ImageView btnDeposite,btnWithdraw,btnOption;
       CircleImageView imgWebsite;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           /* img=itemView.findViewById(R.id.img_btn_option);
            ll=itemView.findViewById(R.id.ll_img);
            cl=itemView.findViewById(R.id.cl_option);*/
            viewDataBinding= DataBindingUtil.bind(itemView);
            btnDeposite= itemView.findViewById(R.id.img_btn_deposit);
            btnWithdraw= itemView.findViewById(R.id.img_btn_withdraw);
            btnOption= itemView.findViewById(R.id.img_btn_option);
            imgWebsite= itemView.findViewById(R.id.img_website);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filter_list_Website = list_Website;
                } else {
                    List<Approvedrequestidlist> filteredList = new ArrayList<>();
                    for (Approvedrequestidlist row : list_Website) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getWebsitename().toLowerCase().contains(charString.toLowerCase())|| row.getUsername().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }

                    filter_list_Website = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filter_list_Website;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filter_list_Website = (ArrayList<Approvedrequestidlist>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnClickListner{

        public  void  deposite(Approvedrequestidlist approvedrequestidlist);
        public  void  withdraw(Approvedrequestidlist approvedrequestidlist);
        public  void  optionMenu(View view,Approvedrequestidlist approvedrequestidlist);

    }

    }
















