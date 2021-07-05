package com.blogcorel.bakulcatering.adapter.most_adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.main.DetailMenuActivity;
import com.blogcorel.bakulcatering.model.DetailMenuModel;
import com.blogcorel.bakulcatering.model.MenuModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements MostInterface {

    private List<CardView> mViews;
    private List<MenuModel> mData;
    private float mBaseElevation;
    TextView new_title, new_order;
    ImageView new_img;
    CardView cardView;
    DetailMenuModel dmm = new DetailMenuModel();

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(MenuModel item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.most_view, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(final MenuModel item, final View view) {
        new_title = view.findViewById(R.id.new_title);
        new_order = view.findViewById(R.id.new_order);
        new_img = view.findViewById(R.id.new_img);
        cardView = view.findViewById(R.id.cardView);

        new_title.setText(item.getM_name());
        new_order.setText(item.getM_order()+" Kali Diorder");
        final Context context = view.getContext();

        if(TextUtils.isEmpty(item.getM_link1())){
            if(TextUtils.isEmpty(item.getM_link2())){
                if(TextUtils.isEmpty(item.getM_link3())){
                    new_img.setImageResource(R.drawable.ic_launcher_background);
                }else{
                    Glide.with(view)
                            .load(item.getM_link3())
                            .centerCrop()
                            .into(new_img);
                }
            }else{
                Glide.with(view)
                        .load(item.getM_link2())
                        .centerCrop()
                        .into(new_img);
            }
        }else{
            Glide.with(view)
                    .load(item.getM_link1())
                    .centerCrop()
                    .into(new_img);
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dmm.setOwner(item.getOwner());
                dmm.setM_name(item.getM_name());
                dmm.setM_price(item.getM_price());
                dmm.setM_portion(item.getM_portion());
                dmm.setM_fee(item.getM_fee());
                dmm.setM_link1(item.getM_link1());
                dmm.setM_link2(item.getM_link2());
                dmm.setM_link3(item.getM_link3());
                dmm.setId(item.getId());
                dmm.setFav(item.notFav());
                context.startActivity(new Intent(context, DetailMenuActivity.class));
            }
        });
    }

}
