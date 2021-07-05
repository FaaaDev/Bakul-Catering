package com.blogcorel.bakulcatering.adapter.dc_menu;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.adapter.cart_adapter.CartAdapter;
import com.blogcorel.bakulcatering.main.AddFavorite;
import com.blogcorel.bakulcatering.main.OrderActivity;
import com.blogcorel.bakulcatering.model.DetailCateringModel;
import com.blogcorel.bakulcatering.model.MenuModel;
import com.blogcorel.bakulcatering.model.OrderModel;
import com.blogcorel.bakulcatering.model.ProcessModel;
import com.blogcorel.bakulcatering.model.UserModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private Context mContext;
    private List<MenuModel> mData;
    private List<OrderModel> mData1;
    ProcessModel pm = new ProcessModel();
    UserModel um = new UserModel();

    int totalHarga = 0;
    int hargaxporsi = 0;
    String menu="";
    String tmp="";


    public MenuAdapter(Context mContext, List<MenuModel> mData, List<OrderModel> mData1) {
        this.mContext = mContext;
        this.mData = mData;
        this.mData1 = mData1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.dc_menu, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        viewHolder.menu_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = mData.get(viewHolder.getAdapterPosition()).isCheck();
                if (check==false){
                    mData.get(viewHolder.getAdapterPosition()).setCheck(true);
                    viewHolder.selected.setVisibility(View.VISIBLE);
                    viewHolder.con_porsi.setVisibility(View.VISIBLE);
                    viewHolder.cart_porsi.setText(String.valueOf(mData1.get(viewHolder.getAdapterPosition()).getPorsi()));
                    hargaxporsi = (mData.get(viewHolder.getAdapterPosition()).getM_price() *
                            mData1.get(viewHolder.getAdapterPosition()).getPorsi()) +
                            mData.get(viewHolder.getAdapterPosition()).getM_fee();
                    mData1.get(viewHolder.getAdapterPosition()).setTotal(hargaxporsi);
                    viewHolder.cart_total.setText("IDR "+mData1.get(viewHolder.getAdapterPosition()).getTotal());
                    tmp = mData.get(viewHolder.getAdapterPosition()).getM_name()+" "+mData.get(viewHolder.getAdapterPosition()).getM_portion()+" Porsi";
                    menu = menu+tmp+", ";
                    totalHarga = totalHarga+hargaxporsi;
                    System.out.println(menu);
                    System.out.println("TOTAL = "+totalHarga);
                    pm.setMenu(menu);
                    pm.setHarga(totalHarga);

                    viewHolder.cart_plus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int porsi = mData1.get(viewHolder.getAdapterPosition()).getPorsi();

                            if(mData1.get(viewHolder.getAdapterPosition()).getPorsi() == mData.get(viewHolder.getAdapterPosition()).getM_portion()){

                                mData1.get(viewHolder.getAdapterPosition()).setPorsi(porsi+1);
                                viewHolder.cart_porsi.setText(String.valueOf(mData1.get(viewHolder.getAdapterPosition()).getPorsi()));
                                hargaxporsi = (mData.get(viewHolder.getAdapterPosition()).getM_price() *
                                        mData1.get(viewHolder.getAdapterPosition()).getPorsi()) +
                                        mData.get(viewHolder.getAdapterPosition()).getM_fee();
                                mData1.get(viewHolder.getAdapterPosition()).setTotal(hargaxporsi);
                                viewHolder.cart_total.setText("IDR "+mData1.get(viewHolder.getAdapterPosition()).getTotal());
                                tmp = mData.get(viewHolder.getAdapterPosition()).getM_name()+" "
                                        +mData.get(viewHolder.getAdapterPosition()).getM_portion()+" Porsi";
                                menu = menu.replaceAll(tmp+", ", "");
                                String newtmp = mData.get(viewHolder.getAdapterPosition()).getM_name()+" "
                                        +mData1.get(viewHolder.getAdapterPosition()).getPorsi()+" Porsi";
                                menu = menu+newtmp+", ";
                                totalHarga = totalHarga+mData.get(viewHolder.getAdapterPosition()).getM_price();
                                System.out.println(menu);
                                System.out.println("TOTAL = "+totalHarga);
                                pm.setMenu(menu);
                                pm.setHarga(totalHarga);

                            } else{

                                int oldporsi = porsi;
                                mData1.get(viewHolder.getAdapterPosition()).setPorsi(porsi+1);
                                viewHolder.cart_porsi.setText(String.valueOf(mData1.get(viewHolder.getAdapterPosition()).getPorsi()));
                                hargaxporsi = (mData.get(viewHolder.getAdapterPosition()).getM_price() *
                                        mData1.get(viewHolder.getAdapterPosition()).getPorsi()) +
                                        mData.get(viewHolder.getAdapterPosition()).getM_fee();
                                mData1.get(viewHolder.getAdapterPosition()).setTotal(hargaxporsi);
                                viewHolder.cart_total.setText("IDR "+mData1.get(viewHolder.getAdapterPosition()).getTotal());
                                tmp = mData.get(viewHolder.getAdapterPosition()).getM_name()+" "
                                        +oldporsi+" Porsi";
                                menu = menu.replaceAll(tmp+", ", "");
                                String newtmp = mData.get(viewHolder.getAdapterPosition()).getM_name()+" "
                                        +mData1.get(viewHolder.getAdapterPosition()).getPorsi()+" Porsi";
                                menu = menu+newtmp+", ";
                                totalHarga = totalHarga+mData.get(viewHolder.getAdapterPosition()).getM_price();
                                System.out.println(menu);
                                System.out.println("TOTAL = "+totalHarga);
                                pm.setMenu(menu);
                                pm.setHarga(totalHarga);

                            }
                        }
                    });
                    viewHolder.cart_minus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int porsi = mData1.get(viewHolder.getAdapterPosition()).getPorsi();
                            if(porsi <= mData.get(viewHolder.getAdapterPosition()).getM_portion()){

                            }else{
                                if(mData1.get(viewHolder.getAdapterPosition()).getPorsi() == mData.get(viewHolder.getAdapterPosition()).getM_portion()){
                                    viewHolder.cart_porsi.setText(String.valueOf(mData1.get(viewHolder.getAdapterPosition()).getPorsi()));
                                    hargaxporsi = (mData.get(viewHolder.getAdapterPosition()).getM_price() *
                                            mData.get(viewHolder.getAdapterPosition()).getM_portion()) +
                                            mData.get(viewHolder.getAdapterPosition()).getM_fee();
                                    mData1.get(viewHolder.getAdapterPosition()).setTotal(hargaxporsi);
                                    viewHolder.cart_total.setText("IDR "+mData1.get(viewHolder.getAdapterPosition()).getTotal());
                                    tmp = mData.get(viewHolder.getAdapterPosition()).getM_name()+" "
                                            +mData.get(viewHolder.getAdapterPosition()).getM_portion()+" Porsi";
                                    menu = menu.replaceAll(tmp+", ", "");
                                    String newtmp = mData.get(viewHolder.getAdapterPosition()).getM_name()+" "
                                            +mData1.get(viewHolder.getAdapterPosition()).getPorsi()+" Porsi";
                                    menu = menu+newtmp+", ";
                                    totalHarga = totalHarga-mData.get(viewHolder.getAdapterPosition()).getM_price();
                                    System.out.println(menu);
                                    System.out.println("TOTAL = "+totalHarga);
                                    pm.setMenu(menu);
                                    pm.setHarga(totalHarga);
                                } else{
                                    int oldporsi = porsi;
                                    mData1.get(viewHolder.getAdapterPosition()).setPorsi(porsi-1);
                                    viewHolder.cart_porsi.setText(String.valueOf(mData1.get(viewHolder.getAdapterPosition()).getPorsi()));
                                    hargaxporsi = (mData.get(viewHolder.getAdapterPosition()).getM_price() *
                                            mData1.get(viewHolder.getAdapterPosition()).getPorsi()) +
                                            mData.get(viewHolder.getAdapterPosition()).getM_fee();
                                    mData1.get(viewHolder.getAdapterPosition()).setTotal(hargaxporsi);
                                    viewHolder.cart_total.setText("IDR "+mData1.get(viewHolder.getAdapterPosition()).getTotal());
                                    tmp = mData.get(viewHolder.getAdapterPosition()).getM_name()+" "
                                            +oldporsi+" Porsi";
                                    menu = menu.replaceAll(tmp+", ", "");
                                    String newtmp = mData.get(viewHolder.getAdapterPosition()).getM_name()+" "
                                            +mData1.get(viewHolder.getAdapterPosition()).getPorsi()+" Porsi";
                                    menu = menu+newtmp+", ";
                                    totalHarga = totalHarga-mData.get(viewHolder.getAdapterPosition()).getM_price();
                                    System.out.println(menu);
                                    System.out.println("TOTAL = "+totalHarga);
                                    pm.setMenu(menu);
                                    pm.setHarga(totalHarga);
                                }
                            }

                        }
                    });
                }else{
                    mData.get(viewHolder.getAdapterPosition()).setCheck(false);
                    viewHolder.selected.setVisibility(View.GONE);
                    viewHolder.con_porsi.setVisibility(View.GONE);
                    int oldporsi = mData1.get(viewHolder.getAdapterPosition()).getPorsi();
                    tmp = mData.get(viewHolder.getAdapterPosition()).getM_name()+" "
                            +oldporsi+" Porsi";
                    menu = menu.replaceAll(tmp+", ", "");
                    hargaxporsi = (mData.get(viewHolder.getAdapterPosition()).getM_price() *
                            mData1.get(viewHolder.getAdapterPosition()).getPorsi()) +
                            mData.get(viewHolder.getAdapterPosition()).getM_fee();
                    mData1.get(viewHolder.getAdapterPosition()).setTotal(hargaxporsi);
                    mData1.get(viewHolder.getAdapterPosition()).setPorsi(mData.get(viewHolder.getAdapterPosition()).getM_portion());
                    totalHarga = totalHarga-hargaxporsi;
                    System.out.println(menu);
                    System.out.println("TOTAL = "+totalHarga);
                    pm.setMenu(menu);
                    pm.setHarga(totalHarga);
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.dc_menu.setText(mData.get(position).getM_name());
        holder.dc_price.setText(("Harga Per Porsi : IDR "+mData.get(position).getM_price()));
        holder.dc_porsi.setText(("Porsi Minimal : "+mData.get(position).getM_portion()));
        holder.dc_pajak.setText(("Biaya Penanganan : IDR "+mData.get(position).getM_fee()));
        holder.dc_total.setText(mData.get(position).getM_order()+" Kali Diorder");
        if(TextUtils.isEmpty(mData.get(position).getM_link1())){
            if(TextUtils.isEmpty(mData.get(position).getM_link2())){
                if(TextUtils.isEmpty(mData.get(position).getM_link3())){
                    holder.dc_img.setImageResource(R.drawable.ic_launcher_background);
                }else{
                    Glide.with(mContext)
                            .load(mData.get(position).getM_link3())
                            .centerCrop()
                            .into(holder.dc_img);
                }
            }else{
                Glide.with(mContext)
                        .load(mData.get(position).getM_link2())
                        .centerCrop()
                        .into(holder.dc_img);
            }
        }else{
            Glide.with(mContext)
                    .load(mData.get(position).getM_link1())
                    .centerCrop()
                    .into(holder.dc_img);
        }
        if (!mData.get(position).isCheck()){
            holder.selected.setVisibility(View.GONE);
            holder.con_porsi.setVisibility(View.GONE);
        }else{
            holder.selected.setVisibility(View.VISIBLE);
            holder.con_porsi.setVisibility(View.VISIBLE);
            holder.cart_porsi.setText(String.valueOf(mData1.get(position).getPorsi()));
            hargaxporsi = (mData.get(position).getM_price() *
                    mData1.get(position).getPorsi()) +
                    mData.get(position).getM_fee();
            mData1.get(position).setTotal(hargaxporsi);
            holder.cart_total.setText("IDR "+mData1.get(position).getTotal());
        }
        holder.con_porsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mData.get(position).notFav()){
                    holder.img_fav.setImageResource(R.drawable.unfav);
                } else {
                    new AddFavorite(um.getUser(), mData.get(position).getId(), "t_favmenu", mContext);
                    holder.img_fav.setImageResource(R.drawable.addfav);
                }

            }
        });
        holder.con_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (!mData.get(position).notFav()){
            holder.img_fav.setImageResource(R.drawable.unfav);
        } else {
            holder.img_fav.setImageResource(R.drawable.addfav);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public int getSelectedCount(){
        return mData1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dc_menu, dc_price, dc_pajak, dc_porsi, dc_total, cart_total;
        ImageView selected, dc_img, cart_plus, cart_minus, img_fav;
        RelativeLayout menu_list;
        LinearLayout con_porsi, con_fav;
        EditText cart_porsi;
        CardView add_fav;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            dc_menu = itemView.findViewById(R.id.dc_menu);
            dc_price = itemView.findViewById(R.id.dc_price);
            dc_pajak = itemView.findViewById(R.id.dc_fee);
            dc_porsi = itemView.findViewById(R.id.dc_porsi);
            dc_total = itemView.findViewById(R.id.dc_total);
            selected = itemView.findViewById(R.id.dc_select);
            dc_img = itemView.findViewById(R.id.dc_img);
            menu_list = itemView.findViewById(R.id.dc_list);
            con_porsi = itemView.findViewById(R.id.con_porsi);
            cart_porsi = itemView.findViewById(R.id.cart_portion);
            cart_plus = itemView.findViewById(R.id.cart_plus);
            cart_minus = itemView.findViewById(R.id.cart_minus);
            cart_total = itemView.findViewById(R.id.cart_total);
            add_fav = itemView.findViewById(R.id.addfav);
            con_fav = itemView.findViewById(R.id.con_fav);
            img_fav = itemView.findViewById(R.id.img_fav);
        }
    }


}
