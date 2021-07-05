package com.blogcorel.bakulcatering.adapter.popular_adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.main.DetailMenuActivity;
import com.blogcorel.bakulcatering.model.DetailMenuModel;
import com.blogcorel.bakulcatering.model.MenuModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyViewHolder> {

    private Context mContext;
    private List<MenuModel> mData;
    private DetailMenuModel dmm = new DetailMenuModel();

    public PopularAdapter(Context mContext, List<MenuModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.popular_view, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.popmenu_name.setText(mData.get(position).getM_name());
        holder.popmenu_order.setText(mData.get(position).getM_order()+" Kali Diorder");
        if(TextUtils.isEmpty(mData.get(position).getM_link1())){
            if(TextUtils.isEmpty(mData.get(position).getM_link2())){
                if(TextUtils.isEmpty(mData.get(position).getM_link3())){
                    holder.popmenu_img.setImageResource(R.drawable.ic_launcher_background);
                }else{
                    Glide.with(mContext)
                            .load(mData.get(position).getM_link3())
                            .centerCrop()
                            .into(holder.popmenu_img);
                }
            }else{
                Glide.with(mContext)
                        .load(mData.get(position).getM_link2())
                        .centerCrop()
                        .into(holder.popmenu_img);
            }
        }else{
            Glide.with(mContext)
                    .load(mData.get(position).getM_link1())
                    .centerCrop()
                    .into(holder.popmenu_img);
        }

        holder.popmenu_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dmm.setOwner(mData.get(position).getOwner());
                dmm.setM_name(mData.get(position).getM_name());
                dmm.setM_price(mData.get(position).getM_price());
                dmm.setM_portion(mData.get(position).getM_portion());
                dmm.setM_fee(mData.get(position).getM_fee());
                dmm.setM_link1(mData.get(position).getM_link1());
                dmm.setM_link2(mData.get(position).getM_link2());
                dmm.setM_link3(mData.get(position).getM_link3());
                dmm.setId(mData.get(position).getId());
                dmm.setFav(mData.get(position).notFav());
                mContext.startActivity(new Intent(mContext, DetailMenuActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView popmenu_name, popmenu_order;
        ImageView popmenu_img;
        CardView popmenu_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            popmenu_img = itemView.findViewById(R.id.popmenu_img);
            popmenu_name = itemView.findViewById(R.id.popmenu_name);
            popmenu_order = itemView.findViewById(R.id.popmenu_order);
            popmenu_card = itemView.findViewById(R.id.popmenu_card);
        }
    }
}
