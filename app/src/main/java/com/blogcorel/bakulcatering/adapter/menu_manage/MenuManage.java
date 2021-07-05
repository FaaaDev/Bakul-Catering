package com.blogcorel.bakulcatering.adapter.menu_manage;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.main.DeleteMenu;
import com.blogcorel.bakulcatering.main.MainActivity;
import com.blogcorel.bakulcatering.model.MenuModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class MenuManage extends RecyclerView.Adapter<MenuManage.MyViewHolder> {

    private Context mContext;
    private List<MenuModel> mData;

    public MenuManage(Context mContext, List<MenuModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MenuManage.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.menu_manage, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuManage.MyViewHolder holder, final int position) {
        holder.listname.setText(mData.get(position).getM_name());
        holder.listprice.setText(("Harga Per Porsi : IDR "+mData.get(position).getM_price()));
        holder.listporsi.setText(("Porsi Minimal : "+mData.get(position).getM_portion()));
        holder.listfee.setText(("Biaya Penanganan : IDR "+mData.get(position).getM_fee()));
        holder.listtotal.setText(mData.get(position).getM_order()+" Kali Diorder");
        if(TextUtils.isEmpty(mData.get(position).getM_link1())){
            if(TextUtils.isEmpty(mData.get(position).getM_link2())){
                if(TextUtils.isEmpty(mData.get(position).getM_link3())){
                    holder.img_menu.setImageResource(R.drawable.ic_launcher_background);
                }else{
                    Glide.with(mContext)
                            .load(mData.get(position).getM_link3())
                            .centerCrop()
                            .into(holder.img_menu);
                }
            }else{
                Glide.with(mContext)
                        .load(mData.get(position).getM_link2())
                        .centerCrop()
                        .into(holder.img_menu);
            }
        }else{
            Glide.with(mContext)
                    .load(mData.get(position).getM_link1())
                    .centerCrop()
                    .into(holder.img_menu);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Hapus")
                        .setMessage("Anda yakin ingin menghapus menu ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                new DeleteMenu(mContext, mData.get(position).getId());
                                mData.remove(position);
                                notifyDataSetChanged();
                                notifyItemRemoved(position);
                            }})
                        .setNegativeButton("No", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView listname, listprice, listporsi, listfee, listtotal;
        ImageView img_menu, delete;
        RelativeLayout listmenu;
        View itemView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            listname = itemView.findViewById(R.id.listname);
            listprice = itemView.findViewById(R.id.listprice);
            listporsi = itemView.findViewById(R.id.listporsi);
            listfee = itemView.findViewById(R.id.listfee);
            listtotal = itemView.findViewById(R.id.listtotal);
            img_menu = itemView.findViewById(R.id.img_menu);
            delete = itemView.findViewById(R.id.delete);
            listmenu = itemView.findViewById(R.id.menu_list);
        }
    }


}
