package com.example.spider.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
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
import com.example.spider.model.Offer;
import com.example.spider.model.Website_item;
import com.example.spider.utils.ExtentionUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Offer_List_Adapter extends RecyclerView.Adapter<Offer_List_Adapter.MyViewHolder> implements Filterable {


    Context context;
    boolean clicked=false;
    List<Offer> list_Website;
    List<Offer> filter_list_Website;
    int position1;
    ClickListner clickListner;
    ExtentionUtils utils =new ExtentionUtils();

    public Offer_List_Adapter() {
    }

    public Offer_List_Adapter(Context context, List<Offer> list_Website, ClickListner clickListner) {
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

        View view = LayoutInflater.from(context).inflate(R.layout.rv_offer,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



            utils.setImage(filter_list_Website.get(position).getPhoto(), holder.img, context.getResources().getDrawable(R.drawable.app_logo));


    }

        @Override
        public int getItemCount() {
            return filter_list_Website.size();
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
                    List<Offer> filteredList = new ArrayList<>();
                    for (Offer row : list_Website) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) ) {
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
                filter_list_Website = (ArrayList<Offer>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{


        ImageView img;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img_offer);


//            viewDataBinding= DataBindingUtil.bind(itemView);

        }



    }

    public interface ClickListner{

        public void createID(Offer website_items);
    }

    }
















