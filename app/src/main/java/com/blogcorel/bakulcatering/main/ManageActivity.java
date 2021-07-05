package com.blogcorel.bakulcatering.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.adapter.menu_manage.MenuManage;
import com.blogcorel.bakulcatering.model.CateringModel;
import com.blogcorel.bakulcatering.model.MenuModel;
import com.blogcorel.bakulcatering.model.UserModel;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageActivity extends AppCompatActivity {

    private String TAG = ManageActivity.class.getSimpleName();
    MenuModel menuModel;
    RecyclerView manageRV;
    private List<MenuModel> model;
    MenuManage la;
    LinearLayoutManager llm;
    ProgressDialog dialog;

    ConstraintLayout manage_container;
    TextView catering_name, catering_city, catering_order;
    ImageView catering_profile;
    Button go_addmenu;
    ImageView back_manage, manage_notif;

    String email, url;
    UserModel um;
    CateringModel cm;
    ConfigServices cs = new ConfigServices();

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View main = findViewById(R.id.manage_container);
            int flags = main.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            main.setSystemUiVisibility(flags);
            this.getWindow().setStatusBarColor(Color.WHITE);
            this.getWindow().setNavigationBarColor(Color.WHITE);
        }

        um = new UserModel();
        cm = new CateringModel();

        manageRV = findViewById(R.id.manageRV);
        manage_container = findViewById(R.id.manage_container);
        catering_name = findViewById(R.id.catering_name);
        catering_city = findViewById(R.id.catering_city);
        catering_order = findViewById(R.id.catering_order);
        catering_profile = findViewById(R.id.catering_profil);
        go_addmenu = findViewById(R.id.go_addmenu);
        back_manage = findViewById(R.id.back_manage);
        manage_notif = findViewById(R.id.manage_notif);

        go_addmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageActivity.this, AddMenuActivity.class));
                finish();
            }
        });

        back_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        manage_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler hh = new Handler();
                hh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(ManageActivity.this, NotifActivity.class));
                    }
                },500);
            }
        });

        model = new ArrayList<>();

        la = new MenuManage(this, model);
        llm = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        manageRV.setLayoutManager(llm);
        manageRV.setAdapter(la);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading Data");
        dialog.setCancelable(false);

        new getCatering().execute();


    }

    private class getCatering extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.show();
            manage_container.setVisibility(View.GONE);

            email = um.getUser();

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
                        cm.setNama(catname);
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
                Log.e(TAG, "Couldn't get json from server.");
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
            catering_name.setText(cm.getCatname());
            catering_city.setText(cm.getCity());
            catering_order.setText(cm.getOrder()+" Order Diterima");
            Glide.with(ManageActivity.this)
                    .load(cm.getLink())
                    .centerCrop()
                    .into(catering_profile);
            new getMenu().execute();
        }
    }

    private class getMenu extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url = cs.getUrl()+"getmenu.php?owner="+email;

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("result");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        menuModel = new MenuModel();
                        JSONObject c = contacts.getJSONObject(i);
                        menuModel.setM_name(c.getString("menu"));
                        menuModel.setM_price(c.getInt("harga"));
                        menuModel.setM_portion(c.getInt("porsi"));
                        menuModel.setM_fee(c.getInt("pajak"));
                        menuModel.setM_order(c.getInt("jum_order"));
                        menuModel.setM_link1(c.getString("link1"));
                        menuModel.setM_link2(c.getString("link2"));
                        menuModel.setM_link3(c.getString("link3"));
                        menuModel.setId(c.getInt("id"));

                        model.add(menuModel);

                        la.notifyDataSetChanged();

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dialog.dismiss();
            manage_container.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ManageActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
