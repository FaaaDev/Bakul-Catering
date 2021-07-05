package com.blogcorel.bakulcatering.main;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blogcorel.bakulcatering.R;

public class DetailUserorderActivity extends AppCompatActivity {

    TextView do_tittle, do_masuk, do_alamat, do_ts4, do_nama, do_hp, do_menu, do_total, do_tanggal;
    ImageView back_order, do_iv1, do_iv2, do_iv3, do_iv4;
    RelativeLayout do_status1, do_status2, do_status3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_userorder);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View main = findViewById(R.id.detail_user_con);
            int flags = main.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            main.setSystemUiVisibility(flags);
            this.getWindow().setStatusBarColor(Color.WHITE);
            this.getWindow().setNavigationBarColor(Color.WHITE);
        }

        init();
        prep();

    }

    private void init(){
        do_tittle = findViewById(R.id.do_tittle);
        do_masuk = findViewById(R.id.do_masuk);
        do_alamat = findViewById(R.id.do_alamat);
        do_ts4 = findViewById(R.id.do_ts4);
        do_nama = findViewById(R.id.do_nama);
        do_hp = findViewById(R.id.do_hp);
        do_menu = findViewById(R.id.do_menu);
        do_total = findViewById(R.id.do_total);
        do_tanggal = findViewById(R.id.do_tanggal);
        back_order = findViewById(R.id.back_order);
        do_iv1 = findViewById(R.id.do_iv1);
        do_iv2 = findViewById(R.id.do_iv2);
        do_iv3 = findViewById(R.id.do_iv3);
        do_iv4 = findViewById(R.id.do_iv4);
        do_status1 = findViewById(R.id.do_status1);
        do_status2 = findViewById(R.id.do_status2);
        do_status3 = findViewById(R.id.do_status3);
    }

    private void prep(){
        String catname,torder,alamat,status,nama,hp,menu,tproses;
        int total;
        catname = getIntent().getExtras().getString("catname");
        torder = getIntent().getExtras().getString("t_masuk");
        alamat = getIntent().getExtras().getString("alamat");
        status = getIntent().getExtras().getString("status");
        nama = getIntent().getExtras().getString("nama");
        hp = getIntent().getExtras().getString("hp");
        menu = getIntent().getExtras().getString("menu");
        tproses = getIntent().getExtras().getString("t_order");
        total = getIntent().getExtras().getInt("total");

        do_tittle.setText(catname);
        do_masuk.setText("("+torder+")");
        do_alamat.setText(alamat);
        do_nama.setText("Nama Pemesan : "+nama);
        do_hp.setText("Nomor HP : "+hp);
        do_menu.setText("Menu : "+menu);
        do_tanggal.setText("Tanggal Pesanan : "+tproses);
        do_total.setText("Total Biaya : "+total);
        setStatus(status);

        back_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setStatus(String status) {
        if ("waiting".equals(status)) {
            do_iv1.setImageResource(R.drawable.done);
            do_iv2.setImageResource(R.drawable.no);
            do_iv3.setImageResource(R.drawable.no);
            do_iv4.setImageResource(R.drawable.no);
        } else if ("accept".equals(status)) {
            do_iv1.setImageResource(R.drawable.done);
            do_iv2.setImageResource(R.drawable.done);
            do_iv3.setImageResource(R.drawable.no);
            do_iv4.setImageResource(R.drawable.no);
        } else if ("process".equals(status)) {
            do_iv1.setImageResource(R.drawable.done);
            do_iv2.setImageResource(R.drawable.done);
            do_iv3.setImageResource(R.drawable.done);
            do_iv4.setImageResource(R.drawable.no);
        } else if ("done".equals(status)) {
            do_iv1.setImageResource(R.drawable.done);
            do_iv2.setImageResource(R.drawable.done);
            do_iv3.setImageResource(R.drawable.done);
            do_iv4.setImageResource(R.drawable.done);
        } else {
            do_status1.setVisibility(View.GONE);
            do_status2.setVisibility(View.GONE);
            do_status3.setVisibility(View.GONE);
            do_ts4.setText("Pesanan Anda Ditolak");
        }
    }
}
