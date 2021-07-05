package com.blogcorel.bakulcatering.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.adapter.image_adapter.GlideServices;
import com.blogcorel.bakulcatering.adapter.image_adapter.MainSliderAdapter;
import com.blogcorel.bakulcatering.adapter.image_adapter.PicassoImageLoadingService;
import com.blogcorel.bakulcatering.model.CateringModel;
import com.blogcorel.bakulcatering.model.DetailCateringModel;
import com.blogcorel.bakulcatering.model.DetailMenuModel;
import com.blogcorel.bakulcatering.model.ProcessModel;
import com.blogcorel.bakulcatering.model.UserModel;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ss.com.bannerslider.Slider;

public class DetailMenuActivity extends AppCompatActivity {

    DetailMenuModel dmm = new DetailMenuModel();
    CateringModel cm = new CateringModel();
    DetailCateringModel dcm = new DetailCateringModel();
    MainSliderAdapter sliderAdapter = new MainSliderAdapter();
    ProcessModel pm = new ProcessModel();
    UserModel um = new UserModel();
    ConfigServices cs = new ConfigServices();

    TextView dm_menu, dm_catname, dm_city, dm_catorder, dm_price, dm_fee, dm_porsi, dm_harga, tv_total;
    ImageView dm_img, md_back, md_fav, btn_plus, btn_minus;
    LinearLayout pbmd, ctmd;
    private Slider slider;
    EditText et_porsi;
    Button btn_order;

    int count = 0;
    String url,email;
    int porsi = dmm.getM_portion();
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DetailMenuActivity.this.getWindow().setStatusBarColor(Color.TRANSPARENT);
            this.getWindow().setNavigationBarColor(Color.WHITE);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


        dm_menu = findViewById(R.id.dm_menu);
        dm_catname = findViewById(R.id.dm_catname);
        dm_catorder = findViewById(R.id.dm_catorder);
        dm_city = findViewById(R.id.dm_city);
        dm_price = findViewById(R.id.dm_price);
        dm_fee = findViewById(R.id.dm_fee);
        ctmd = findViewById(R.id.ctmd);
        pbmd = findViewById(R.id.pbmd);
        dm_img = findViewById(R.id.dm_img);
        md_back = findViewById(R.id.md_back);
        md_fav = findViewById(R.id.md_fav);
        dm_porsi = findViewById(R.id.dm_porsi);
        dm_harga = findViewById(R.id.dm_harga);
        et_porsi = findViewById(R.id.et_porsi);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        tv_total = findViewById(R.id.tv_total);
        btn_order = findViewById(R.id.btn_order);

        ctmd.setVisibility(View.GONE);
        dm_menu.setText(dmm.getM_name());
        dm_catname.setText(dmm.getOwner());
        dm_price.setText("IDR "+dmm.getM_price()*dmm.getM_portion()+" / "+dmm.getM_portion()+" Porsi");
        dm_fee.setText("Biaya penanganan : IDR "+dmm.getM_fee());
        dm_harga.setText("Harga Per-Porsi : IDR "+dmm.getM_price());
        dm_porsi.setText("Porsi Minimal : "+dmm.getM_portion()+" Porsi");
        et_porsi.setText(String.valueOf(porsi));
        total = dmm.getM_price()*porsi;
        tv_total.setText("IDR "+total);

        md_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (dmm.isFav()){
            md_fav.setImageResource(R.drawable.addfav);
        } else {
            md_fav.setImageResource(R.drawable.like_white);
        }
        md_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dmm.isFav()){
                    md_fav.setImageResource(R.drawable.like_white);
                } else {
                    new AddFavorite(um.getUser(), dmm.getId(), "t_favmenu", DetailMenuActivity.this);
                    md_fav.setImageResource(R.drawable.addfav);
                }

            }
        });
        ctmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dcm.setOwner(dmm.getOwner());
                dcm.setCatname(cm.getCatname());
                dcm.setCity(cm.getCity());
                dcm.setAddress(cm.getAddress());
                dcm.setLink(cm.getLink());
                dcm.setOrder(cm.getOrder());
                dcm.setDesc(cm.getDesc());
                startActivity(new Intent(DetailMenuActivity.this, DetailCateringActivity.class));
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                porsi++;
                total = total + dmm.getM_price();
                tv_total.setText("IDR "+total);
                et_porsi.setText(String.valueOf(porsi));
            }
        });
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (porsi>dmm.getM_portion()){
                    porsi--;
                    total = total - dmm.getM_price();
                    tv_total.setText("IDR "+total);
                    et_porsi.setText(String.valueOf(porsi));
                }
            }
        });

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pm.setMenu(dmm.getM_name()+" "+porsi+",");
                pm.setHarga(total);
                pm.setOwner(dmm.getOwner());
                pm.setEmail(um.getUser());
                pm.setNama(um.getUsername());
                startActivity(new Intent(DetailMenuActivity.this, OrderActivity.class));
            }
        });

        if(!TextUtils.isEmpty(dmm.getM_link1())){
            count++;
        }
        if(!TextUtils.isEmpty(dmm.getM_link2())){
            count++;
        }
        if(!TextUtils.isEmpty(dmm.getM_link3())){
            count++;
        }
        sliderAdapter.setCount(count);
        System.out.println(count);
        setupViews();
        new getCatering().execute();
    }

    private void setupViews() {
        slider = findViewById(R.id.banner_slider1);

        //delay for testing empty view functionality
        slider.postDelayed(new Runnable() {
            @Override
            public void run() {
                slider.setAdapter(sliderAdapter);
                Slider.init(new GlideServices(DetailMenuActivity.this));
                slider.setSelectedSlide(0);
            }
        }, 1500);

    }

    private class getCatering extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            email = dmm.getOwner();

            url = cs.getUrl()+"getcatering.php?email="+email;

        }
        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray user = jsonObj.getJSONArray("result");

                    for (int i = 0; i < user.length(); i++) {
                        JSONObject c = user.getJSONObject(i);

                        String catname = c.getString("catname");
                        String link = c.getString("link");
                        String address = c.getString("address");
                        String city = c.getString("city");
                        String desc = c.getString("desc");
                        int total = c.getInt("total");

                        cm.setCatname(catname);
                        cm.setLink(link);
                        cm.setAddress(address);
                        cm.setCity(city);
                        cm.setDesc(desc);
                        cm.setOrder(total);

                    }

                } catch (final JSONException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dm_catname.setText(cm.getCatname());
            dm_city.setText(cm.getCity());
            dm_catorder.setText(cm.getOrder()+" Order Diterima");
            ctmd.setVisibility(View.VISIBLE);
            pbmd.setVisibility(View.GONE);
            Glide.with(DetailMenuActivity.this).load(cm.getLink()).centerCrop().into(dm_img);

        }
    }

}
