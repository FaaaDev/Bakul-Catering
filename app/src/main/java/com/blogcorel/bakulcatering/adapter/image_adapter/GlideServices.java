package com.blogcorel.bakulcatering.adapter.image_adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ss.com.bannerslider.ImageLoadingService;

public class GlideServices implements ImageLoadingService {
    public Context context;

    public GlideServices(Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    @Override
    public void loadImage(int resource, ImageView imageView) {
        Glide.with(context).load(resource).centerCrop().into(imageView);
    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
        Glide.with(context).load(url).placeholder(placeHolder).error(errorDrawable).centerCrop().into(imageView);
    }
}
