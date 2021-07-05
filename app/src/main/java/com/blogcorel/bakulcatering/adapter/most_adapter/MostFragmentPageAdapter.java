package com.blogcorel.bakulcatering.adapter.most_adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MostFragmentPageAdapter extends FragmentStatePagerAdapter implements MostInterface {

    private List<MostFragment> fragments;
    private float baseElevation;


    public MostFragmentPageAdapter(@NonNull FragmentManager fm, float baseElevation) {
        super(fm);
        fragments = new ArrayList<>();
        this.baseElevation = baseElevation;

        for(int i = 0; i< 10; i++){
            addCardFragment(new MostFragment());
        }
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return MostFragment.getInstance(position);
    }

    @Override
    public float getBaseElevation() {
        return baseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return fragments.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        fragments.set(position, (MostFragment) fragment);
        return fragment;
    }

    public void addCardFragment(MostFragment fragment) {
        fragments.add(fragment);
    }
}
