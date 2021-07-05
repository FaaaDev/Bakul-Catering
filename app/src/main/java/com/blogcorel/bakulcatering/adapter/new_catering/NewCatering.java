package com.blogcorel.bakulcatering.adapter.new_catering;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.main.DetailCateringActivity;
import com.blogcorel.bakulcatering.model.CateringModel;
import com.blogcorel.bakulcatering.model.DetailCateringModel;
import com.blogcorel.bakulcatering.model.MenuModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class NewCatering extends RecyclerView.Adapter<NewCatering.MyViewHolder> {

    private Context mContext;
    private List<CateringModel> mData;
    DetailCateringModel dcm = new DetailCateringModel();

    public NewCatering(Context mContext, List<CateringModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.new_catering, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.newcat_name.setText(mData.get(position).getCatname());
        holder.newcat_city.setText(mData.get(position).getCity());
        holder.newcat_order.setText(mData.get(position).getOrder()+" Order Diterima");
        Glide.with(mContext)
                .load(mData.get(position).getLink())
                .centerCrop()
                .into(holder.newcat_img);

        holder.newcat_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dcm.setOwner(mData.get(position).getOwner());
                dcm.setCatname(mData.get(position).getCatname());
                dcm.setCity(mData.get(position).getCity());
                dcm.setAddress(mData.get(position).getAddress());
                dcm.setLink(mData.get(position).getLink());
                dcm.setOrder(mData.get(position).getOrder());
                dcm.setDesc(mData.get(position).getDesc());
                mContext.startActivity(new Intent(mContext, DetailCateringActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView newcat_name, newcat_order, newcat_city;
        ImageView newcat_img;
        CardView newcat_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            newcat_name = itemView.findViewById(R.id.newcat_name);
            newcat_city = itemView.findViewById(R.id.newcat_city);
            newcat_order = itemView.findViewById(R.id.newcat_order);
            newcat_img = itemView.findViewById(R.id.newcat_img);
            newcat_card = itemView.findViewById(R.id.newcat_card);
        }
    }
}
