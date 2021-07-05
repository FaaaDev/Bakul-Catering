package com.blogcorel.bakulcatering.adapter.user_order;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.main.DetailUserorderActivity;
import com.blogcorel.bakulcatering.model.OrderData;

import java.util.List;

public class UserOrder extends RecyclerView.Adapter<UserOrder.MyViewHolder> {

    private Context mContext;
    private List<OrderData> mData;

    public UserOrder(Context mContext, List<OrderData> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.order_list, parent, false);
        final UserOrder.MyViewHolder viewHolder = new MyViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.o_menu.setText(mData.get(position).getMenu());
        holder.o_catname.setText("Nama Catering : "+mData.get(position).getCatname());
        holder.o_tpesan.setText("Tanggal Pemesanan : "+mData.get(position).getTorder());
        holder.o_tdipesan.setText("Tanggal Pesanan Selesai : "+mData.get(position).getTproses());
        holder.o_total.setText("Total Biaya : IDR "+mData.get(position).getTotal());

        if (mData.get(position).getStatus().equals("waiting")){
            holder.o_status.setText("Status : Menunggu Konfirmasi");
        } else if (mData.get(position).getStatus().equals("accept")){
            holder.o_status.setText("Status : Menunggu diproses");
        } else if (mData.get(position).getStatus().equals("process")){
            holder.o_status.setText("Status : Sedang Diproses");
        } else if (mData.get(position).getStatus().equals("done")){
            holder.o_status.setText("Status : Pesanan Telah selesai");
        } else if (mData.get(position).getStatus().equals("reject")){
            holder.o_status.setText("Status : Pesanan Ditolak");
        }

        holder.box_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailUserorderActivity.class);
                intent.putExtra("catname", mData.get(position).getCatname());
                intent.putExtra("t_masuk", mData.get(position).getTorder());
                intent.putExtra("t_order", mData.get(position).getTproses());
                intent.putExtra("total", mData.get(position).getTotal());
                intent.putExtra("alamat", mData.get(position).getAlamat());
                intent.putExtra("nama", mData.get(position).getUsername());
                intent.putExtra("hp", mData.get(position).getHp());
                intent.putExtra("menu", mData.get(position).getMenu());
                intent.putExtra("status", mData.get(position).getStatus());
                intent.putExtra("total", mData.get(position).getTotal());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView o_menu, o_catname, o_tpesan, o_tdipesan, o_total, o_status;
        LinearLayout box_order;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            o_menu = itemView.findViewById(R.id.o_menu);
            o_catname = itemView.findViewById(R.id.o_catname);
            o_tpesan = itemView.findViewById(R.id.o_tpesan);
            o_tdipesan = itemView.findViewById(R.id.o_dipesan);
            o_total = itemView.findViewById(R.id.o_total);
            o_status = itemView.findViewById(R.id.o_status);
            box_order = itemView.findViewById(R.id.box_order);
        }
    }
}
