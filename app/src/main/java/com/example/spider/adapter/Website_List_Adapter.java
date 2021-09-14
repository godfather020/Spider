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
import com.example.spider.model.Website_item;
import com.example.spider.utils.ExtentionUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Website_List_Adapter extends RecyclerView.Adapter<Website_List_Adapter.MyViewHolder> implements Filterable {


    Context context;
    boolean clicked=false;
    List<Website_item> list_Website;
    List<Website_item> filter_list_Website;
    int position1;
    ClickListner clickListner;
    ExtentionUtils utils =new ExtentionUtils();

    public Website_List_Adapter() {
    }

    public Website_List_Adapter(Context context, List<Website_item> list_Website, ClickListner clickListner) {
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

        View view = LayoutInflater.from(context).inflate(R.layout.rv_website_layout,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


         holder.viewDataBinding.setVariable(BR.website_item, filter_list_Website.get(position));
        holder.viewDataBinding.executePendingBindings();
        if(filter_list_Website.get(position).getPhoto()!=null && !filter_list_Website.get(position).getPhoto().isEmpty() && !filter_list_Website.get(position).getPhoto().equals("")) {
            utils.setCircleImage(filter_list_Website.get(position).getPhoto(), holder.img_website, context.getResources().getDrawable(R.drawable.app_logo));
        }

        for(int i=0;i<filter_list_Website.get(position).getGames().size();i++){
               String name=filter_list_Website.get(position).getGames().get(i).getName();
            if(name.equalsIgnoreCase(context.getResources().getString(R.string.cricket))){

                if(filter_list_Website.get(position).getGames().get(i).getStatus()==1){
                       holder.txtCricket.setVisibility(View.VISIBLE);
                       holder.txtCricket_minBet.setVisibility(View.VISIBLE);

                       holder.imgCricket.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.app_theme_color)));
                       holder.txtCricket_minBet.setText(filter_list_Website.get(position).getGames().get(i).getPrice().toString());
                }
            }
            if(name.equalsIgnoreCase(context.getResources().getString(R.string.football))){

                if(filter_list_Website.get(position).getGames().get(i).getStatus()==1){
                    holder.txtFootball.setVisibility(View.VISIBLE);
                    holder.txtFootball_minBet.setVisibility(View.VISIBLE);
                    holder.imgFootball.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.app_theme_color)));
                    holder.txtFootball_minBet.setText(filter_list_Website.get(position).getGames().get(i).getPrice().toString());
                }
            }
            if(name.equalsIgnoreCase(context.getResources().getString(R.string.tenis))) {

                if (filter_list_Website.get(position).getGames().get(i).getStatus() == 1) {
                    holder.txtTenis.setVisibility(View.VISIBLE);
                    holder.txtTenis_minBet.setVisibility(View.VISIBLE);
                    holder.imgTenis.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.app_theme_color)));
                    holder.txtTenis_minBet.setText(filter_list_Website.get(position).getGames().get(i).getPrice().toString());
                }
            }
            if(name.equalsIgnoreCase(context.getResources().getString(R.string.horse_racing))) {

                if (filter_list_Website.get(position).getGames().get(i).getStatus() == 1) {
                    holder.txtHorseRacing.setVisibility(View.VISIBLE);
                    holder.txtHorseRacing_minBet.setVisibility(View.VISIBLE);
                    holder.imgHorseRacing.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.app_theme_color)));
                    holder.txtHorseRacing_minBet.setText(filter_list_Website.get(position).getGames().get(i).getPrice().toString());
                }
            }
            if(name.equalsIgnoreCase(context.getResources().getString(R.string.live_casino))) {

                if (filter_list_Website.get(position).getGames().get(i).getStatus() == 1) {
                    holder.txtLiveCasino.setVisibility(View.VISIBLE);
                    holder.txtLiveCasino_minBet.setVisibility(View.VISIBLE);
                    holder.imgLiveCasino.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.app_theme_color)));
                    holder.txtLiveCasino_minBet.setText(filter_list_Website.get(position).getGames().get(i).getPrice().toString());
                }
            }
            if(name.equalsIgnoreCase(context.getResources().getString(R.string.cards))) {

                if (filter_list_Website.get(position).getGames().get(i).getStatus() == 1) {
                    holder.txtCards.setVisibility(View.VISIBLE);
                    holder.txtCards_minBet.setVisibility(View.VISIBLE);
                    holder.imgCards.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.app_theme_color)));
                    holder.txtCards_minBet.setText(filter_list_Website.get(position).getGames().get(i).getPrice().toString());
                }
            }
            if(name.equalsIgnoreCase(context.getResources().getString(R.string.politics))) {

                if (filter_list_Website.get(position).getGames().get(i).getStatus() == 1) {
                    holder.txtPolitics.setVisibility(View.VISIBLE);
                    holder.txtPolitics_minBet.setVisibility(View.VISIBLE);
                    holder.imgPolitics.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.app_theme_color)));
                    holder.txtPolitics_minBet.setText(filter_list_Website.get(position).getGames().get(i).getPrice().toString());
                }
            }
        }
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if(filter_list_Website.get(position1).isClicked()){
                    filter_list_Website.get(position).setClicked(false);
                    holder.ll.setVisibility(View.GONE);
                    holder.cl.setVisibility(View.GONE);


                }else {
                    filter_list_Website.get(position).setClicked(true);
                    holder.ll.setVisibility(View.VISIBLE);
                    holder.cl.setVisibility(View.VISIBLE);

                }*/
                if(clicked){
                    clicked=false;
                    holder.ll.setVisibility(View.GONE);
                    holder.cl.setVisibility(View.GONE);


                }else {
                    clicked=true;
                    holder.ll.setVisibility(View.VISIBLE);
                    holder.cl.setVisibility(View.VISIBLE);

                }

            }
        });

        holder.btn_create_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickListner.createID(filter_list_Website.get(position));
            }
        });

        holder.txt_user_demo_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.copyText(context,holder.txt_user_demo_id.getText().toString());
                utils.showToast(context,"Copied");
            }
        });
        holder.txt_user_demo_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.copyText(context,holder.txt_user_demo_password.getText().toString());
                utils.showToast(context,"Copied");
            }
        });

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
                    List<Website_item> filteredList = new ArrayList<>();
                    for (Website_item row : list_Website) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) ) {
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
                filter_list_Website = (ArrayList<Website_item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{


        ImageView img,imgCricket,imgFootball,imgTenis,
                imgHorseRacing,imgLiveCasino,
                imgCards,imgPolitics;
        CircleImageView img_website;
        LinearLayout ll;
        ConstraintLayout cl;
        ViewDataBinding viewDataBinding;
        TextView txtCricket,txtCricket_minBet,txtFootball,txtFootball_minBet,txtTenis,txtTenis_minBet,
                txtHorseRacing,txtHorseRacing_minBet,txtLiveCasino,txtLiveCasino_minBet,
                txtCards,txtCards_minBet,txtPolitics,txtPolitics_minBet,txt_user_demo_id,txt_user_demo_password;
        Button btn_create_client;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img_btn_option);
            img_website=itemView.findViewById(R.id.img_website);
            ll=itemView.findViewById(R.id.ll_img);
            cl=itemView.findViewById(R.id.cl_option);
            txtCricket=itemView.findViewById(R.id.txt_cricket_header);
            txtCricket_minBet=itemView.findViewById(R.id.txt_cricket_minBet);
            txtFootball=itemView.findViewById(R.id.txt_football_header);
            txtFootball_minBet=itemView.findViewById(R.id.txt_football_minBet);
            txtTenis=itemView.findViewById(R.id.txt_tenis_header);
            txtTenis_minBet=itemView.findViewById(R.id.txt_tenis_minBet);
            txtHorseRacing=itemView.findViewById(R.id.txt_horseRacing_header);
            txtHorseRacing_minBet=itemView.findViewById(R.id.txt_horseRacing_minBet);
            txtLiveCasino=itemView.findViewById(R.id.txt_liveCasino_header);
            txtLiveCasino_minBet=itemView.findViewById(R.id.txt_liveCasino_minBet);
            txtCards=itemView.findViewById(R.id.txt_cards_header);
            txtCards_minBet=itemView.findViewById(R.id.txt_cards_minBet);
            txtPolitics=itemView.findViewById(R.id.txt_politics_header);
            txtPolitics_minBet=itemView.findViewById(R.id.txt_politics_minBet);
            imgCricket=itemView.findViewById(R.id.img_cricket);
            imgFootball=itemView.findViewById(R.id.img_football);
            imgTenis=itemView.findViewById(R.id.img_tenis);
            imgHorseRacing=itemView.findViewById(R.id.img_horse);
            imgLiveCasino=itemView.findViewById(R.id.img_casino);
            imgCards=itemView.findViewById(R.id.img_cards);
            imgPolitics=itemView.findViewById(R.id.img_politics);
            btn_create_client=itemView.findViewById(R.id.btn_create_client);
            txt_user_demo_id=itemView.findViewById(R.id.txt_name_demoId);
            txt_user_demo_password=itemView.findViewById(R.id.txt_name_demoPassword);


            viewDataBinding= DataBindingUtil.bind(itemView);

        }



    }

    public interface ClickListner{

        public void createID(Website_item website_items);
    }

    }
















