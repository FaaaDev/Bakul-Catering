package com.blogcorel.bakulcatering.adapter.most_adapter;

import androidx.cardview.widget.CardView;

public interface MostInterface {
    public final int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
