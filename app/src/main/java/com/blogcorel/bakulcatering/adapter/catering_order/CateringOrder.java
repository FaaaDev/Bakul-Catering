package com.blogcorel.bakulcatering.adapter.catering_order;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogcorel.bakulcatering.main.DetailCatorderActivity;
import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.model.DetailOrder;
import com.blogcorel.bakulcatering.model.OrderData;

import java.util.List;


public class CateringOrder extends RecyclerView.Adapter<CateringOrder.MyViewHolder> {

    Context mContext;
    List<OrderData> mData;
    DetailOrder detailOrder = new DetailOrder();

    public CateringOrder(Context mContext, List<OrderData> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.order_catering, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.oc_menu.setText(mData.get(position).getMenu());
        holder.oc_tpesan.setText(mData.get(position).getTorder());
        holder.oc_nama.setText("Nama Pengorder : "+mData.get(position).getUsername());
        holder.oc_alamat.setText("Alamat : "+mData.get(position).getAlamat());
        holder.oc_dipesan.setText("Tanggal Orderan : "+mData.get(position).getTproses());
        holder.oc_hp.setText("Nomor HP : "+mData.get(position).getHp());

        if (mData.get(position).getStatus().equals("waiting")){
            holder.oc_status.setText("Status : Menunggu Konfirmasi");
        } else if (mData.get(position).getStatus().equals("accept")){
            holder.oc_status.setText("Status : Menunggu diproses");
        } else if (mData.get(position).getStatus().equals("process")){
            holder.oc_status.setText("Status : Sedang Diproses");
        } else if (mData.get(position).getStatus().equals("done")){
            holder.oc_status.setText("Status : Pesanan Telah selesai");
        } else if (mData.get(position).getStatus().equals("reject")){
            holder.oc_status.setText("Status : Pesanan Ditolak");
        }

        holder.oc_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailOrder.setId(mData.get(position).getId());
                detailOrder.setMenu(mData.get(position).getMenu());
                detailOrder.setCatname(mData.get(position).getCatname());
                detailOrder.setAlamat(mData.get(position).getAlamat());
                detailOrder.setHp(mData.get(position).getHp());
                detailOrder.setStatus(mData.get(position).getStatus());
                detailOrder.setTorder(mData.get(position).getTorder());
                detailOrder.setTotal(mData.get(position).getTotal());
                detailOrder.setTproses(mData.get(position).getTproses());
                detailOrder.setUsername(mData.get(position).getUsername());
                detailOrder.setEmail(mData.get(position).getEmail());

                mContext.startActivity(new Intent(mContext, DetailCatorderActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView oc_menu, oc_tpesan, oc_nama, oc_dipesan, oc_total, oc_hp, oc_alamat, oc_status;
        LinearLayout oc_con;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            oc_menu = itemView.findViewById(R.id.oc_menu);
            oc_tpesan = itemView.findViewById(R.id.oc_tpesan);
            oc_nama = itemView.findViewById(R.id.oc_name);
            oc_dipesan = itemView.findViewById(R.id.oc_dipesan);
            oc_total = itemView.findViewById(R.id.oc_total);
            oc_hp = itemView.findViewById(R.id.oc_hp);
            oc_alamat = itemView.findViewById(R.id.oc_alamat);
            oc_status = itemView.findViewById(R.id.oc_status);
            oc_con = itemView.findViewById(R.id.oc_con);

        }
    }
}
