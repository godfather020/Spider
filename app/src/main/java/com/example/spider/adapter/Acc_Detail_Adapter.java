package com.example.spider.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.model.Accountdetail;
import com.example.spider.model.Pymentmethod;
import com.example.spider.ui.fragment.Paymet_Detail_Fragment;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.ExtentionUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Acc_Detail_Adapter extends RecyclerView.Adapter<Acc_Detail_Adapter.MyViewHolder> {


      MainActivity activity;
      boolean clicked = false,acc_updateClick=false;
      List<Pymentmethod> list_Website;
      List<Accountdetail> list_acc;
      int payment_item_position,position1;
      ClickListner clickListner;
      ExtentionUtils utils = new ExtentionUtils();
      View view1;
    int acc_updatePositon=-1;

    public Acc_Detail_Adapter() {

    }

    public Acc_Detail_Adapter(MainActivity activity, List<Accountdetail> list_acc) {//,ClickListner clickListner
          this.activity = activity;
          this.list_acc = list_acc;
//        this.clickListner=clickListner;
      }
      public Acc_Detail_Adapter(MainActivity activity, List<Accountdetail> list_acc,View view) {//,ClickListner clickListner
          this.activity = activity;
          this.list_acc = list_acc;
          this.view1=view;
//        this.clickListner=clickListner;
      }
      public Acc_Detail_Adapter(MainActivity activity, List<Accountdetail> list_acc,View view,int payment_item_position) {//,ClickListner clickListner
          this.activity = activity;
          this.list_acc = list_acc;
          this.view1=view;
          this.payment_item_position=payment_item_position;
//        this.clickListner=clickListner;
      }
 /* public Acc_Detail_Adapter(activity activity, List<Pymentmethod> list_Website,List<Account> list_acc,ClickListner clickListner) {
      this.activity = activity;
      this.list_Website=list_Website;
      this.list_acc=list_acc;
      this.clickListner=clickListner;
  }*/

      @Override
      public int getItemViewType(int position) {
          position1 = position;
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
          MyViewHolder myViewHolder = new MyViewHolder(view);
          return myViewHolder;
      }

      @Override
      public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

          AppSharedPref appSharedPref=new AppSharedPref(activity);

          acc_updateClick=appSharedPref.getBoolean("acc_updateClick");
          acc_updatePositon=appSharedPref.getInteger("acc_updatePosition");

          if(acc_updatePositon==position){

              if(acc_updateClick){

                  holder.cl.setVisibility(View.VISIBLE);

                  appSharedPref.saveBoolean("acc_updateClick",false);
                  appSharedPref.saveInteger("acc_updatePosition",-1);

              }
          }else {

              holder.cl.setVisibility(View.GONE);
          }

          if(list_acc.get(holder.getLayoutPosition()).getPreferstatus().toString().equals("1")) {
              holder.btnPreferred.setVisibility(View.GONE);
              holder.txt_preferred.setVisibility(View.VISIBLE);
          }else {
              holder.btnPreferred.setVisibility(View.VISIBLE);
              holder.txt_preferred.setVisibility(View.GONE);
          }
          if (list_acc.get(position1).getPaymentmethodid().equals("4")) {
              holder.cl_bank.setVisibility(View.VISIBLE);
              holder.cl_upi.setVisibility(View.GONE);
              holder.txt_bank_name.setText(list_acc.get(position).getBankname());
              holder.txt_bank_name1.setText(list_acc.get(position).getBankname());
              holder.txt_acc_no.setText(list_acc.get(position).getAccountNo());
              holder.txt_acc_holderName.setText(list_acc.get(position).getAccountholder());
              holder.txt_ifsc.setText(list_acc.get(position).getIfsc());
              holder.txt_acc_type.setText(list_acc.get(position).getBankType());
              holder.txt_branch.setText(list_acc.get(position).getBranch());
              holder.txt_acc_type.setText(list_acc.get(position).getBankType());
          } else {
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

                  if (clicked) {
                      clicked = false;
                      holder.cl.setVisibility(View.GONE);
                  } else {

                      clicked = true;
                      holder.cl.setVisibility(View.VISIBLE);
                  }

              }
          });
          holder.btnEdit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  new Paymet_Detail_Fragment().editAcc(activity, list_acc.get(position),view1,position,payment_item_position);
              }
          });
          holder.btnDelete.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  AlertDialog.Builder  builder=new AlertDialog.Builder(activity);
                  builder.setCancelable(false);
                  builder.setTitle("Are you want to delete it?");

                  builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {

                          new Paymet_Detail_Fragment().deleteAcc(activity, list_acc.get(position),view1,Acc_Detail_Adapter.this,position,payment_item_position);
                      }
                  });
                  builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          dialogInterface.dismiss();
                      }
                  });

                  AlertDialog alertDialog=builder.create();
                  alertDialog.show();
              }



          });

          holder.btnPreferred.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  AlertDialog.Builder  builder=new AlertDialog.Builder(activity);
                  builder.setCancelable(false);
                  builder.setTitle("Are you want to preferred it?");

                  builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {

                          new Paymet_Detail_Fragment().preferredAcc(activity, list_acc.get(position),view1,Acc_Detail_Adapter.this,position,payment_item_position);
                      }
                  });
                  builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          dialogInterface.dismiss();
                      }
                  });

                  AlertDialog alertDialog=builder.create();
                  alertDialog.show();
              }



          });


      }

      @Override
      public int getItemCount() {
          return list_acc.size();
      }

      public class MyViewHolder extends RecyclerView.ViewHolder {


          ImageView img;
          LinearLayout ll;
          ConstraintLayout cl, cl_bank, cl_upi;
          TextView txt_bank_name, txt_bank_name1, txt_acc_no, txt_acc_holderName, txt_ifsc, txt_branch, txt_acc_type, txt_upi, txt_upi_acc, txt_upiHolderName,txt_preferred;
          Button btnEdit, btnDelete,btnPreferred;
          CircleImageView img_payment_mode;
          RecyclerView rv_accDetail;

          public MyViewHolder(@NonNull View itemView) {
              super(itemView);
              img = itemView.findViewById(R.id.img_btn_option);
              ll = itemView.findViewById(R.id.ll_img);
              cl = itemView.findViewById(R.id.cl_option);
              cl_bank = itemView.findViewById(R.id.cl_bank);
              cl_upi = itemView.findViewById(R.id.cl_upi);
              txt_bank_name = itemView.findViewById(R.id.txt_payment_method);
              btnEdit = itemView.findViewById(R.id.btn_edit_acc);
              rv_accDetail = itemView.findViewById(R.id.rv_accDetail);
              btnDelete = itemView.findViewById(R.id.btn_delete_acc);
              btnPreferred = itemView.findViewById(R.id.btn_preferred_acc);
              img_payment_mode = itemView.findViewById(R.id.img_website);
              txt_bank_name1 = itemView.findViewById(R.id.txt_bank_name);
              txt_acc_no = itemView.findViewById(R.id.txt_acc_number);
              txt_acc_holderName = itemView.findViewById(R.id.txt_acc_holder_name);
              txt_ifsc = itemView.findViewById(R.id.txt_ifsc_code);
              txt_branch = itemView.findViewById(R.id.txt_branch_add);
              txt_acc_type = itemView.findViewById(R.id.txt_acc_type);
              txt_upi = itemView.findViewById(R.id.txt_acc_name);
              txt_upi_acc = itemView.findViewById(R.id.txt_acc);
              txt_upiHolderName = itemView.findViewById(R.id.txt_acc_holderName);
              txt_preferred = itemView.findViewById(R.id.txt_acc_preferred_status);

          }
      }

      public interface ClickListner {

          public void editAcc(Accountdetail accountdetail);

          public void deleteAcc(Accountdetail accountdetail);
      }



  }
