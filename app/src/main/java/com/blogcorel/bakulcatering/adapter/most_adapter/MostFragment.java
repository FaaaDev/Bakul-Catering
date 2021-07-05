package com.blogcorel.bakulcatering.adapter.most_adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.model.MenuModel;

import java.util.List;

public class MostFragment extends Fragment {
    private CardView cardView;
    private List<MenuModel> mm;

    public static Fragment getInstance(int position) {
        MostFragment f = new MostFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        f.setArguments(args);

        return f;
    }

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.most_view, container, false);

        cardView =  view.findViewById(R.id.cardView);
        ((CardView) cardView).setMaxCardElevation(((CardView) cardView).getCardElevation() * MostInterface.MAX_ELEVATION_FACTOR);


        return view;
    }

    public CardView getCardView() {
        return (CardView) cardView;
    }
}
