package com.blogcorel.bakulcatering.adapter.cart_adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.model.MenuModel;
import com.blogcorel.bakulcatering.model.OrderModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context mContext;
    private List<OrderModel> mData;



    public CartAdapter(Context mContext, List<OrderModel> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.cart_layout, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.cart_menu.setText(mData.get(position).getMenu());
        holder.cart_price.setText("Harga :"+mData.get(position).getHarga());
        holder.cart_fee.setText("Pajak :"+mData.get(position).getPajak());
        holder.cart_total.setText("IDR "+mData.get(position).getTotal());
        holder.cart_porsi.setText(String.valueOf(mData.get(position).getPorsi()));
        if(TextUtils.isEmpty(mData.get(position).getLink1())){
            if(TextUtils.isEmpty(mData.get(position).getLink2())){
                if(TextUtils.isEmpty(mData.get(position).getLink3())){
                    holder.cart_img.setImageResource(R.drawable.ic_launcher_background);
                }else{
                    Glide.with(mContext)
                            .load(mData.get(position).getLink3())
                            .centerCrop()
                            .into(holder.cart_img);
                }
            }else{
                Glide.with(mContext)
                        .load(mData.get(position).getLink2())
                        .centerCrop()
                        .into(holder.cart_img);
            }
        }else{
            Glide.with(mContext)
                    .load(mData.get(position).getLink1())
                    .centerCrop()
                    .into(holder.cart_img);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cart_menu, cart_price, cart_fee, cart_total;
        ImageView cart_img, cart_plus, cart_minus, cart_delete;
        EditText cart_porsi;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            cart_menu = itemView.findViewById(R.id.cart_menu);
            cart_price = itemView.findViewById(R.id.cart_price);
            cart_fee = itemView.findViewById(R.id.cart_fee);
            cart_total = itemView.findViewById(R.id.cart_total);
            cart_img = itemView.findViewById(R.id.cart_img);
            cart_plus = itemView.findViewById(R.id.cart_plus);
            cart_minus = itemView.findViewById(R.id.cart_minus);
            cart_delete = itemView.findViewById(R.id.cart_delete);
            cart_porsi = itemView.findViewById(R.id.cart_portion);

        }
    }


}
