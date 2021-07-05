package com.blogcorel.bakulcatering.main;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.model.ProcessModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProcessActivity extends AppCompatActivity {

    private String TAG = ProcessActivity.class.getSimpleName();
    Button p_ok, p_back;
    LinearLayout p_con, p_pb, p_error;
    String value, url;
    ProcessModel pm = new ProcessModel();
    ConfigServices cs = new ConfigServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View main = findViewById(R.id.p_main);
            int flags = main.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            main.setSystemUiVisibility(flags);
            this.getWindow().setStatusBarColor(Color.WHITE);
            this.getWindow().setNavigationBarColor(Color.WHITE);
        }

        p_ok = findViewById(R.id.p_ok);
        p_con = findViewById(R.id.p_con);
        p_pb = findViewById(R.id.p_pb);
        p_error = findViewById(R.id.p_error);
        p_back = findViewById(R.id.p_cancel);

        p_con.setVisibility(View.GONE);
        p_error.setVisibility(View.GONE);

        p_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        p_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        new addOrder().execute();
    }

    @Override
    public void onBackPressed() {

    }

    private class addOrder extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            url = cs.getUrl()+"postorder.php";
        }
        @Override
        protected Void doInBackground(Void... voids) {
            PostHandler sh = new PostHandler();

            JSONObject data = new JSONObject();
            try {
                data.put("email", pm.getEmail());
                data.put("owner", pm.getOwner());
                data.put("menu", pm.getMenu());
                data.put("total", pm.getHarga());
                data.put("alamat", pm.getAlamat());
                data.put("pesanan", pm.getTanggal_m());
                data.put("dipesan", pm.getTanggal_k());
                data.put("hp", pm.getHp());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String jsonStr = sh.makeServiceCall(url, data);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    value = jsonObj.getString("value");

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
            if(("S1").equals(value)){
                new pushNotif().execute();
            }else {
                p_pb.setVisibility(View.GONE);
                p_error.setVisibility(View.VISIBLE);
            }
        }
    }

    private class pushNotif extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            url = cs.getUrl()+"gettoken.php?email="+pm.getOwner();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray user = jsonObj.getJSONArray("result");

                    for (int i = 0; i < user.length(); i++) {
                        JSONObject c = user.getJSONObject(i);

                        String token = c.getString("token");

                        Notify notif = new Notify();
                        notif.sendNotif(token, "Order Catering", "Hai, ada order catering baru nih, buruan konfirmasi yuk..");

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
                p_pb.setVisibility(View.GONE);
                p_con.setVisibility(View.VISIBLE);
        }
    }
}
