package com.blogcorel.bakulcatering.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.adapter.catering_order.CateringOrder;
import com.blogcorel.bakulcatering.model.OrderData;
import com.blogcorel.bakulcatering.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotifActivity extends AppCompatActivity {

    CateringOrder cateringOrder;
    List<OrderData> order = new ArrayList<>();
    RecyclerView cat_order;
    LinearLayoutManager llm;
    OrderData orderData;
    String url;
    UserModel um = new UserModel();
    String user = um.getUser();
    LinearLayout notif_load, notif_done, notif_empty;
    ImageView back_notif;
    ConfigServices cs = new ConfigServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View main = findViewById(R.id.notif_con);
            int flags = main.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            main.setSystemUiVisibility(flags);
            this.getWindow().setStatusBarColor(Color.WHITE);
            this.getWindow().setNavigationBarColor(Color.WHITE);
        }

        cat_order = findViewById(R.id.cat_order);
        notif_load = findViewById(R.id.notif_load);
        notif_done = findViewById(R.id.notif_done);
        notif_empty = findViewById(R.id.notif_empty);
        back_notif = findViewById(R.id.back_notif);

        back_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        notif_empty.setVisibility(View.GONE);
        notif_done.setVisibility(View.GONE);


        new getOrder().execute();

    }

    private class getOrder extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            order = new ArrayList<>();

            url = cs.getUrl()+"getcatorder.php?email="+user;

        }

        @SuppressLint("WrongThread")
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
                        String menu = c.getString("menu");
                        String username = c.getString("username");
                        String alamat = c.getString("alamat");
                        String hp = c.getString("hp");
                        String tpesan = c.getString("tpesan");
                        String tdipesan = c.getString("tdipesan");
                        String status = c.getString("status");
                        String id = c.getString("id");
                        String email = c.getString("email");
                        int total = c.getInt("total");

                        orderData = new OrderData(id, catname, menu, username, alamat, hp, tpesan, tdipesan, status, email, total);

                        order.add(orderData);

                    }

                } catch (final JSONException e) {

                }
            } else {
                Log.e("error", "Couldn't get json from server.");

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            cateringOrder = new CateringOrder(NotifActivity.this, order);
            llm = new LinearLayoutManager(NotifActivity.this);
            cat_order.setLayoutManager(llm);
            cat_order.setAdapter(cateringOrder);
            if (cateringOrder.getItemCount()==0){
                notif_load.setVisibility(View.GONE);
                notif_empty.setVisibility(View.VISIBLE);
            }else {
                notif_load.setVisibility(View.GONE);
                notif_done.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new getOrder().execute();
    }
}
