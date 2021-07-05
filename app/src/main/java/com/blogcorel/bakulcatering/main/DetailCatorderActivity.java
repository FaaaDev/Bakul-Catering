package com.blogcorel.bakulcatering.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.model.CateringModel;
import com.blogcorel.bakulcatering.model.DetailOrder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailCatorderActivity extends AppCompatActivity {

    LinearLayout oca_remind, con_tos, oca_waiting, oca_proses, oca_selesai;
    DetailOrder dto = new DetailOrder();
    TextView do_alamat, do_nama, do_hp, do_menu, do_total, do_tanggal, do_masuk, do_ts4;
    Button do_accept, do_reject, do_call1, do_process, do_call2, do_call0, do_done;
    RelativeLayout bottom_section, do_status1, do_status2, do_status3;
    ImageView do_iv1, do_iv2, do_iv3, do_iv4, back_ocat;
    CheckBox tos;
    String stat, id, url, value;
    ProgressDialog dialog;
    CateringModel cm = new CateringModel();
    ConfigServices cs = new ConfigServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_catorder);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View main = findViewById(R.id.detailcatorder_con);
            int flags = main.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            main.setSystemUiVisibility(flags);
            this.getWindow().setStatusBarColor(Color.WHITE);
            this.getWindow().setNavigationBarColor(Color.WHITE);
        }

        _init();
        _prepare();

    }

    private void _init() {
        con_tos = findViewById(R.id.con_tos);
        oca_remind = findViewById(R.id.oca_remind);
        do_alamat = findViewById(R.id.do_alamat);
        do_nama = findViewById(R.id.do_nama);
        do_hp = findViewById(R.id.do_hp);
        do_menu = findViewById(R.id.do_menu);
        do_total = findViewById(R.id.do_total);
        do_tanggal = findViewById(R.id.do_tanggal);
        do_masuk = findViewById(R.id.do_masuk);
        do_ts4 = findViewById(R.id.do_ts4);
        do_iv1 = findViewById(R.id.do_iv1);
        do_iv2 = findViewById(R.id.do_iv2);
        do_iv3 = findViewById(R.id.do_iv3);
        do_iv4 = findViewById(R.id.do_iv4);
        bottom_section = findViewById(R.id.bottom_section);
        do_call1 = findViewById(R.id.do_call1);
        do_call2 = findViewById(R.id.do_call2);
        do_call0 = findViewById(R.id.do_call0);
        do_accept = findViewById(R.id.do_accept);
        do_reject = findViewById(R.id.do_reject);
        do_done = findViewById(R.id.do_done);
        do_process = findViewById(R.id.do_process);
        do_status1 = findViewById(R.id.do_status1);
        do_status2 = findViewById(R.id.do_status2);
        do_status3 = findViewById(R.id.do_status3);
        oca_proses = findViewById(R.id.oca_process);
        oca_selesai = findViewById(R.id.oca_selesai);
        oca_waiting = findViewById(R.id.oca_waiting);
        tos = findViewById(R.id.tos);
        back_ocat = findViewById(R.id.back_ocat);
    }

    private void _prepare() {
        do_masuk.setText("(" + dto.getTorder() + ")");
        do_alamat.setText(dto.getAlamat());
        do_nama.setText("Nama Pemesan : " + dto.getUsername());
        do_hp.setText("Nomor HP : " + dto.getHp());
        do_menu.setText("Menu : " + dto.getMenu());
        do_total.setText("Total Biaya : IDR " + dto.getTotal());
        do_tanggal.setText("Tanggal Pesanan : " + dto.getTproses());
        String status = dto.getStatus();

        setStatus(status);

        back_ocat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        do_call0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + dto.getHp()));
                startActivity(intent);
            }
        });

        do_call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + dto.getHp()));
                startActivity(intent);
            }
        });

        do_call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + dto.getHp()));
                startActivity(intent);
            }
        });

        do_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tos.isChecked()) {
                    stat = "accept";
                    new updateStatus().execute();
                } else {
                    Toast.makeText(DetailCatorderActivity.this, "Ceklis bahwa anda telah melakukan konfirmasi", Toast.LENGTH_LONG).show();
                }
            }
        });

        do_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tos.isChecked()) {
                    stat = "reject";
                    new updateStatus().execute();
                } else {
                    Toast.makeText(DetailCatorderActivity.this, "Ceklis bahwa anda telah melakukan konfirmasi", Toast.LENGTH_LONG).show();
                }
            }
        });
        do_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stat = "process";
                new updateStatus().execute();
            }
        });
        do_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tos.isChecked()) {
                    stat = "done";
                    new updateStatus().execute();
                } else {
                    Toast.makeText(DetailCatorderActivity.this, "Ceklis bahwa anda telah menyelesaikan pesanan", Toast.LENGTH_LONG).show();
                }
            }
        });


        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading Data");
        dialog.setCancelable(false);

    }

    private void setStatus(String status) {
        if ("waiting".equals(status)) {
            do_iv1.setImageResource(R.drawable.done);
            do_iv2.setImageResource(R.drawable.no);
            do_iv3.setImageResource(R.drawable.no);
            do_iv4.setImageResource(R.drawable.no);
            oca_waiting.setVisibility(View.VISIBLE);
            oca_proses.setVisibility(View.GONE);
            oca_selesai.setVisibility(View.GONE);
        } else if ("accept".equals(status)) {
            do_iv1.setImageResource(R.drawable.done);
            do_iv2.setImageResource(R.drawable.done);
            do_iv3.setImageResource(R.drawable.no);
            do_iv4.setImageResource(R.drawable.no);
            oca_proses.setVisibility(View.VISIBLE);
            oca_waiting.setVisibility(View.GONE);
            oca_selesai.setVisibility(View.GONE);
            oca_remind.setVisibility(View.GONE);
            con_tos.setVisibility(View.GONE);
        } else if ("process".equals(status)) {
            do_iv1.setImageResource(R.drawable.done);
            do_iv2.setImageResource(R.drawable.done);
            do_iv3.setImageResource(R.drawable.done);
            do_iv4.setImageResource(R.drawable.no);
            oca_selesai.setVisibility(View.VISIBLE);
            oca_waiting.setVisibility(View.GONE);
            oca_proses.setVisibility(View.GONE);
            oca_remind.setVisibility(View.GONE);
            con_tos.setVisibility(View.VISIBLE);
            tos.setText("Saya telah benar benar menyelesaikan orderan ini");
        } else if ("done".equals(status)) {
            do_iv1.setImageResource(R.drawable.done);
            do_iv2.setImageResource(R.drawable.done);
            do_iv3.setImageResource(R.drawable.done);
            do_iv4.setImageResource(R.drawable.done);
            bottom_section.setVisibility(View.GONE);
            oca_remind.setVisibility(View.GONE);
            con_tos.setVisibility(View.GONE);
        } else {
            do_status1.setVisibility(View.GONE);
            do_status2.setVisibility(View.GONE);
            do_status3.setVisibility(View.GONE);
            do_ts4.setText("Pesanan Anda Ditolak");
            bottom_section.setVisibility(View.GONE);
            oca_remind.setVisibility(View.GONE);
            con_tos.setVisibility(View.GONE);
        }
    }

    private class updateStatus extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.show();

            url = cs.getUrl()+"updatestatus.php";
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PostHandler sh = new PostHandler();

            JSONObject data = new JSONObject();
            try {
                data.put("status", stat);
                data.put("id", dto.getId());
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
                Log.e("UPDATE", "Couldn't get json from server.");
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
            if (("S1").equals(value)) {
                new pushNotif().execute();
            } else {
                dialog.dismiss();
                Toast.makeText(DetailCatorderActivity.this, "Gagal ! Coba Lagi Nanti", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class pushNotif extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            url = cs.getUrl()+"gettoken.php?email=" + dto.getEmail();
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
                        notif.sendNotif(token, "Status Pemesanan", "Hai, " + cm.getNama() + " telah memperbarui status order kamu nih..");

                    }

                } catch (final JSONException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                }
            } else {
                Log.e("NOTIF", "Couldn't get json from server.");
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
            dialog.dismiss();
            Toast.makeText(DetailCatorderActivity.this, "Berhasil Memperbarui", Toast.LENGTH_LONG).show();
            setStatus(stat);
            tos.setChecked(false);
        }
    }
}
