package com.blogcorel.bakulcatering.ui.favorit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogcorel.bakulcatering.R;

public class FavmenuFragment extends Fragment {


    public static FavmenuFragment newInstance() {
        return new FavmenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favmenu_fragment, container, false);
    }

}
