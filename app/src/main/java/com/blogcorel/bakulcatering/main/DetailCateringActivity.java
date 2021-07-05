package com.blogcorel.bakulcatering.main;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.adapter.tab_layout.TabAdapter;
import com.blogcorel.bakulcatering.model.DetailCateringModel;
import com.blogcorel.bakulcatering.model.MenuModel;
import com.blogcorel.bakulcatering.ui.detail.DetailFragment;
import com.blogcorel.bakulcatering.ui.menu.MenuFragment;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class DetailCateringActivity extends AppCompatActivity {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    DetailCateringModel dcm = new DetailCateringModel();
    MenuModel menuModel;

    TextView catname, dc_order;
    ImageView dc_profile, dc_back, dc_like;
    ConstraintLayout dc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_catering);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().setStatusBarColor(Color.TRANSPARENT);
            this.getWindow().setNavigationBarColor(Color.WHITE);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);
        dc_order = findViewById(R.id.dc_order);
        dc_profile = findViewById(R.id.dc_profil);
        catname = findViewById(R.id.dc_title);
        dc_back = findViewById(R.id.dc_back);
        dc_like = findViewById(R.id.dc_like);
        dc = findViewById(R.id.layout_cat_detail);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new MenuFragment(), "Menu");
        adapter.addFragment(new DetailFragment(), "Detail");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        catname.setText(dcm.getCatname());
        dc_order.setText(String.valueOf(dcm.getOrder()));
        Glide.with(this).load(dcm.getLink()).centerCrop().into(dc_profile);

        dc_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler hh = new Handler();
                hh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                },150);
            }
        });

        dc_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar
                        .make(dc, "Fitur ini masih dalam tahap pengembangan", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

    }

}
