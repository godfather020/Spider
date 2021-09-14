package com.example.spider.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spider.BR;
import com.example.spider.R;
import com.example.spider.model.Approvedrequestidlist;
import com.example.spider.model.Website_item;
import com.example.spider.ui.fragment.Create_Id_Fragment;
import com.example.spider.utils.ExtentionUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_Adapter extends RecyclerView.Adapter<Home_Adapter.MyViewHolder> {


    Context context;
    boolean clicked=false;
    List<Approvedrequestidlist> list_Website;
    int position1;
    ClickListner clickListner;
    ExtentionUtils utils=new ExtentionUtils();

    public Home_Adapter(Context context,List<Approvedrequestidlist> list_Website,ClickListner clickListner) {
        this.context = context;
        this.list_Website=list_Website;
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


         holder.viewDataBinding.setVariable(BR.my_id, list_Website.get(position));
        holder.viewDataBinding.executePendingBindings();

        holder.btnOption.setVisibility(View.GONE);
        utils.setCircleImage(list_Website.get(position).getWebsitephoto(),holder.img_website,context.getResources().getDrawable(R.drawable.app_logo));
        holder.btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.withdraw(list_Website.get(position));
            }
        });
        holder.btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.deposite(list_Website.get(position));
            }
        });

    }

        @Override
        public int getItemCount() {
            return list_Website.size();
        }

    public class  MyViewHolder extends RecyclerView.ViewHolder{


        CircleImageView img_website;
        ImageView btnDeposit,btnWithdraw,btnOption;
        LinearLayout ll;
        ConstraintLayout cl;
        ViewDataBinding viewDataBinding;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ll=itemView.findViewById(R.id.ll_img);
            cl=itemView.findViewById(R.id.cl_option);

            img_website=itemView.findViewById(R.id.img_website);
            btnDeposit=itemView.findViewById(R.id.img_btn_deposit);
            btnWithdraw=itemView.findViewById(R.id.img_btn_withdraw);
            btnOption=itemView.findViewById(R.id.img_btn_option);

            viewDataBinding= DataBindingUtil.bind(itemView);

        }

    }

    public interface ClickListner{

        public void createID(Website_item website_items);
        public  void  deposite(Approvedrequestidlist approvedrequestidlist);
        public  void  withdraw(Approvedrequestidlist approvedrequestidlist);
    }

    }
















