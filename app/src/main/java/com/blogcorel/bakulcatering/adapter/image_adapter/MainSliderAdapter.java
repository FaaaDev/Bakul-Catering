package com.blogcorel.bakulcatering.adapter.image_adapter;

import android.text.TextUtils;

import com.blogcorel.bakulcatering.model.DetailMenuModel;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

/**
 * @author S.Shahini
 * @since 2/12/18
 */

public class MainSliderAdapter extends SliderAdapter {

    DetailMenuModel ddm = new DetailMenuModel();
    int count;

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {

        switch (position) {
            case 0:
                viewHolder.bindImageSlide(ddm.getM_link1());
                break;
            case 1:
                viewHolder.bindImageSlide(ddm.getM_link2());
                break;
            case 2:
                viewHolder.bindImageSlide(ddm.getM_link3());
                break;
        }
    }

}
