package com.blogcorel.bakulcatering.ui.favorit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.adapter.tab_layout.TabAdapter;
import com.blogcorel.bakulcatering.main.LoginActivity;
import com.blogcorel.bakulcatering.model.UserModel;
import com.blogcorel.bakulcatering.ui.detail.DetailFragment;
import com.blogcorel.bakulcatering.ui.menu.MenuFragment;
import com.google.android.material.tabs.TabLayout;

public class FavoritFragment extends Fragment {

    private FavoritViewModel favoritViewModel;
    private UserModel um = new UserModel();
    private String user = um.getUser();
    private RelativeLayout fav_content, fav_warning;
    private Button fav_login;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritViewModel =
                ViewModelProviders.of(this).get(FavoritViewModel.class);
        View root = inflater.inflate(R.layout.fragment_fav, container, false);

        fav_content = root.findViewById(R.id.fav_content);
        fav_warning = root.findViewById(R.id.fav_warning);
        fav_login = root.findViewById(R.id.fav_login);
        viewPager = root.findViewById(R.id.viewpager);
        tabLayout = root.findViewById(R.id.tablayout);

        fav_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        fav_content.setVisibility(View.GONE);


        if(!TextUtils.isEmpty(user)){
            fav_content.setVisibility(View.VISIBLE);
            fav_warning.setVisibility(View.GONE);
        }
        else {
            fav_content.setVisibility(View.GONE);
            fav_warning.setVisibility(View.VISIBLE);
        }

        adapter = new TabAdapter(getFragmentManager());
        adapter.addFragment(new FavmenuFragment(), "Menu");
        adapter.addFragment(new FavcateringFragment(), "Catering");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);



        return root;
    }
}