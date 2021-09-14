package com.example.spider.adapter;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.BR;
import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.model.Account;
import com.example.spider.model.Accountdetail;
import com.example.spider.model.Pymentmethod;
import com.example.spider.model.Website;
import com.example.spider.ui.fragment.Paymet_Detail_Fragment;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.ExtentionUtils;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Payment_Detail_Adapter extends RecyclerView.Adapter<Payment_Detail_Adapter.MyViewHolder> implements Acc_Detail_Adapter.ClickListner {


    MainActivity activity;
    boolean clicked = false,payment_updateClick=false;
    List<Pymentmethod> list_Website;
    List<Account> list_acc;
    int position1;
    ClickListner clickListner;
    ExtentionUtils utils = new ExtentionUtils();
    Acc_Detail_Adapter adapter;
    LinearLayoutManager manager;
    View view;
    int payment_updatePositon=-1,acc_updatePosition=-1;

    public Payment_Detail_Adapter() {


    }

    public Payment_Detail_Adapter(MainActivity activity, List<Pymentmethod> list_Website, ClickListner clickListner) {
        this.activity = activity;
        this.list_Website = list_Website;
        this.clickListner = clickListner;
    }



    public Payment_Detail_Adapter(MainActivity activity, List<Pymentmethod> list_Website, List<Account> list_acc, View view,ClickListner clickListner) {
        this.activity = activity;
        this.list_Website = list_Website;
        this.list_acc = list_acc;
        this.clickListner = clickListner;
        this.view=view;
    }



    @Override
    public int getItemViewType(int position) {
        position1 = position;
        return position;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.rv_payment_detail_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        AppSharedPref appSharedPref=new AppSharedPref(activity);

        payment_updateClick=appSharedPref.getBoolean("payment_updateClick");
        payment_updatePositon=appSharedPref.getInteger("payment_updatePosition");
        acc_updatePosition=appSharedPref.getInteger("acc_item_position");

        if(payment_updatePositon==holder.getAdapterPosition()){
            if(payment_updateClick){


                holder.rv_accDetail.setVisibility(View.VISIBLE);
                holder.btn_into_rv_Add.setVisibility(View.VISIBLE);
                holder.rv_accDetail.scrollToPosition(acc_updatePosition);
                appSharedPref.saveBoolean("payment_updateClick",false);
                appSharedPref.saveInteger("payment_updatePosition",-1);

            }
        }else {
            holder.rv_accDetail.setVisibility(View.GONE);
            holder.btn_into_rv_Add.setVisibility(View.GONE);

        }

        holder.binding.setVariable(BR.paymentDetail,list_Website.get(holder.getAdapterPosition()));
        holder.binding.executePendingBindings();
        if (list_acc != null&& list_acc.size()>0) {

            manager = new LinearLayoutManager(activity);
            holder.rv_accDetail.setLayoutManager(manager);
            if (list_Website.get(holder.getAdapterPosition()).getId().equals("1")) {

                if (list_acc.get(0).getAccountdetail().getPaytmUPI() != null && !list_acc.get(0).getAccountdetail().getPaytmUPI().isEmpty()) {
                    holder.btnAdd.setVisibility(View.GONE);
                    holder.img.setVisibility(View.VISIBLE);
                    adapter = new Acc_Detail_Adapter(activity, list_acc.get(0).getAccountdetail().getPaytmUPI(),view,holder.getAdapterPosition());
                    holder.rv_accDetail.setAdapter(adapter);
                }
            }
            if (list_Website.get(holder.getAdapterPosition()).getId().equals("2")) {

                if (list_acc.get(0).getAccountdetail().getGooglePay() != null && !list_acc.get(0).getAccountdetail().getGooglePay().isEmpty()) {
                    holder.btnAdd.setVisibility(View.GONE);
                    holder.img.setVisibility(View.VISIBLE);
                    adapter = new Acc_Detail_Adapter(activity, list_acc.get(0).getAccountdetail().getGooglePay(),view,holder.getAdapterPosition());
                    holder.rv_accDetail.setAdapter(adapter);
                }
            }
            if (list_Website.get(holder.getAdapterPosition()).getId().equals("3")) {

                if (list_acc.get(0).getAccountdetail().getPhonePay() != null && !list_acc.get(0).getAccountdetail().getPhonePay().isEmpty()) {
                    holder.btnAdd.setVisibility(View.GONE);
                    holder.img.setVisibility(View.VISIBLE);
                    adapter = new Acc_Detail_Adapter(activity, list_acc.get(0).getAccountdetail().getPhonePay(),view,holder.getAdapterPosition());
                    holder.rv_accDetail.setAdapter(adapter);
                }
            }
            if (list_Website.get(holder.getAdapterPosition()).getId().equals("4")) {

                if (list_acc.get(0).getAccountdetail().getBankTransfer() != null && !list_acc.get(0).getAccountdetail().getBankTransfer().isEmpty()) {
                    holder.btnAdd.setVisibility(View.GONE);
                    holder.img.setVisibility(View.VISIBLE);
                    adapter = new Acc_Detail_Adapter(activity, list_acc.get(0).getAccountdetail().getBankTransfer(),view,holder.getAdapterPosition());
                    holder.rv_accDetail.setAdapter(adapter);
                }
            }
            if (list_Website.get(holder.getAdapterPosition()).getId().equals("5")) {

                if (list_acc.get(0).getAccountdetail().getPaytmWallet() != null && !list_acc.get(0).getAccountdetail().getPaytmWallet().isEmpty()) {
                    holder.btnAdd.setVisibility(View.GONE);
                    holder.img.setVisibility(View.VISIBLE);
                    adapter = new Acc_Detail_Adapter(activity, list_acc.get(0).getAccountdetail().getPaytmWallet(),view,holder.getAdapterPosition());
                    holder.rv_accDetail.setAdapter(adapter);
                }
            }
//                holder.btnAdd.setVisibility(View.GONE);
//                holder.img.setVisibility(View.VISIBLE);
//            }
        }



//        holder.txt_payment_method.setText(list_Website.get(holder.getAdapterPosition()).getName());

        utils.setCircleImage(list_Website.get(holder.getAdapterPosition()).getPhoto().toString(), holder.img_payment_mode, activity.getResources().getDrawable(R.mipmap.ic_launcher_round));

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list_acc!=null) {
                clickListner.dilogFragment(list_Website.get(holder.getAdapterPosition()),list_acc.size()-1,holder.getAdapterPosition());
                }else clickListner.dilogFragment(list_Website.get(holder.getAdapterPosition()), 0, holder.getAdapterPosition());
            }
        });

        holder.btn_into_rv_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(list_acc!=null) {
                    clickListner.dilogFragment(list_Website.get(holder.getAdapterPosition()), list_acc.size() - 1, holder.getAdapterPosition());
                }else clickListner.dilogFragment(list_Website.get(holder.getAdapterPosition()), 0, holder.getAdapterPosition());
            }
        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clicked) {
                    clicked = false;
                    holder.rv_accDetail.setVisibility(View.GONE);
                    holder.btn_into_rv_Add.setVisibility(View.GONE);


                } else {
                    clicked = true;
                    holder.rv_accDetail.setVisibility(View.VISIBLE);
                    holder.btn_into_rv_Add.setVisibility(View.VISIBLE);


                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return list_Website.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView img;
        LinearLayout ll;
        ConstraintLayout cl;
        TextView txt_payment_method;
        Button btnAdd, btn_into_rv_Add;
        CircleImageView img_payment_mode;
        RecyclerView rv_accDetail;
        ViewDataBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_btn_option);
            ll = itemView.findViewById(R.id.ll_img);
            cl = itemView.findViewById(R.id.cl_option);
            txt_payment_method = itemView.findViewById(R.id.txt_payment_method);
            btnAdd = itemView.findViewById(R.id.btn_add_bank);
            rv_accDetail = itemView.findViewById(R.id.rv_accDetail);
            btn_into_rv_Add = itemView.findViewById(R.id.btn_add_bank1);
            img_payment_mode = itemView.findViewById(R.id.img_website);
            binding= DataBindingUtil.bind(itemView);

        }
    }

    public interface ClickListner {

        public void dilogFragment(Pymentmethod pymentmethod,int position,int payment_item_position);
    }

    @Override
    public void editAcc(Accountdetail accountdetail) {

    }

    @Override
    public void deleteAcc(Accountdetail accountdetail) {

    }


}










 /*class Acc_Detail_Adapter extends RecyclerView.Adapter<Acc_Detail_Adapter.MyViewHolder> {


    activity activity;
    boolean clicked=false;
    List<Pymentmethod> list_Website;
    List<Accountdetail> list_acc;
    int position1;
    ClickListner clickListner;
    ExtentionUtils utils=new ExtentionUtils();
    public Acc_Detail_Adapter(activity activity, List<Accountdetail> list_acc) {//,ClickListner clickListner
        this.activity = activity;
        this.list_acc=list_acc;
//        this.clickListner=clickListner;
    }
   *//* public Acc_Detail_Adapter(activity activity, List<Pymentmethod> list_Website,List<Account> list_acc,ClickListner clickListner) {
        this.activity = activity;
        this.list_Website=list_Website;
        this.list_acc=list_acc;
        this.clickListner=clickListner;
    }*//*

    @Override
    public int getItemViewType(int position) {
        position1=position;
        return position;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
//        if(list_acc.get(position1).getPaymentmethodid().equals("4")) {
//
             view = LayoutInflater.from(activity).inflate(R.layout.rv_acc_detail_layout, parent, false);
//        }
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(list_acc.get(position1).getPaymentmethodid().equals("4")) {
            holder.cl_bank.setVisibility(View.VISIBLE);
            holder.cl_upi.setVisibility(View.GONE);
            holder.txt_bank_name.setText(list_acc.get(position).getBankname());
            holder.txt_bank_name1.setText(list_acc.get(position).getBankname());
            holder.txt_acc_no.setText(list_acc.get(position).getAccountNo());
            holder.txt_acc_holderName.setText(list_acc.get(position).getAccountholder());
            holder.txt_ifsc.setText(list_acc.get(position).getIfsc());
            holder.txt_branch.setText(list_acc.get(position).getBranch());
            holder.txt_acc_type.setText(list_acc.get(position).getBankType());
        }else {
            holder.cl_upi.setVisibility(View.VISIBLE);
            holder.cl_bank.setVisibility(View.GONE);
            holder.txt_bank_name.setText(list_acc.get(position).getDisplayname());
            holder.txt_upi.setText(list_acc.get(position).getPaymentmethod());
            holder.txt_upi_acc.setText(list_acc.get(position).getNumber());
            holder.txt_upiHolderName.setText(list_acc.get(position).getDisplayname());
        }

    holder.img.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(clicked){
                clicked=false;
                holder.cl.setVisibility(View.GONE);
            }else {

                clicked=true;
                holder.cl.setVisibility(View.VISIBLE);
            }

        }
    });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
  holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return list_acc.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{


        ImageView img;
        LinearLayout ll;
        ConstraintLayout cl,cl_bank,cl_upi;
        TextView txt_bank_name,txt_bank_name1,txt_acc_no,txt_acc_holderName,txt_ifsc,txt_branch,txt_acc_type,txt_upi,txt_upi_acc,txt_upiHolderName;
        Button btnEdit,btnDelete;
        CircleImageView img_payment_mode;
        RecyclerView rv_accDetail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img_btn_option);
            ll=itemView.findViewById(R.id.ll_img);
            cl=itemView.findViewById(R.id.cl_option);
            cl_bank=itemView.findViewById(R.id.cl_bank);
            cl_upi=itemView.findViewById(R.id.cl_upi);
            txt_bank_name=itemView.findViewById(R.id.txt_payment_method);
            btnEdit=itemView.findViewById(R.id.btn_edit_acc);
            rv_accDetail=itemView.findViewById(R.id.rv_accDetail);
            btnDelete=itemView.findViewById(R.id.btn_delete_acc);
            img_payment_mode=itemView.findViewById(R.id.img_website);
            txt_bank_name1=itemView.findViewById(R.id.txt_bank_name);
            txt_acc_no=itemView.findViewById(R.id.txt_acc_number);
            txt_acc_holderName=itemView.findViewById(R.id.txt_acc_holder_name);
            txt_ifsc=itemView.findViewById(R.id.txt_ifsc_code);
            txt_branch=itemView.findViewById(R.id.txt_branch_add);
            txt_acc_type=itemView.findViewById(R.id.txt_acc_type);
            txt_upi=itemView.findViewById(R.id.txt_acc_name);
            txt_upi_acc=itemView.findViewById(R.id.txt_acc);
            txt_upiHolderName=itemView.findViewById(R.id.txt_acc_holderName);

        }
    }

    public interface ClickListner{

        public void editAcc(Pymentmethod pymentmethod);
        public void deleteAcc(Pymentmethod pymentmethod);
    }
}*/
































