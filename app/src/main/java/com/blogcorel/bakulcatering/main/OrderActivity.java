package com.blogcorel.bakulcatering.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.model.ProcessModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class OrderActivity extends AppCompatActivity{

    TextView p_menu, p_total;
    EditText p_alamat, p_phone;
    Button p_order;
    DatePicker p_date;
    ProcessModel pm = new ProcessModel();
    ImageButton p_back;
    String t_masuk, t_keluar;
    ConstraintLayout container_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View main = findViewById(R.id.container_cart);
            int flags = main.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            main.setSystemUiVisibility(flags);
            this.getWindow().setStatusBarColor(Color.WHITE);
            this.getWindow().setNavigationBarColor(Color.WHITE);
        }

        p_menu = findViewById(R.id.p_menu);
        p_total = findViewById(R.id.p_total);
        p_alamat = findViewById(R.id.p_alamat);
        p_phone = findViewById(R.id.p_phone);
        p_order = findViewById(R.id.p_order);
        p_date = findViewById(R.id.date);
        p_back = findViewById(R.id.p_back);
        container_cart = findViewById(R.id.container_cart);

        p_menu.setText("Menu yang dipesan : "+pm.getMenu());
        p_total.setText("IDR "+pm.getHarga());

        p_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONTH)+1;
                t_masuk = calendar.get(Calendar.DAY_OF_MONTH)+"/"+month+"/"+calendar.get(Calendar.YEAR);
                t_keluar = p_date.getDayOfMonth()+"/"+(p_date.getMonth()+1)+"/"+p_date.getYear();

                if (TextUtils.isEmpty(p_alamat.getText()) || TextUtils.isEmpty(p_phone.getText())){
                    if (TextUtils.isEmpty(p_alamat.getText())){
                        p_alamat.setError("Alamat Harus Diisi");
                    }
                    if (TextUtils.isEmpty(p_phone.getText())){
                        p_phone.setError("Nomor Handphone Harus Diisi");
                    }
                } else if(t_masuk.equals(t_keluar)){
                    Snackbar snackbar = Snackbar
                            .make(container_cart, "Pilih tanggal selain hari ini", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    pm.setAlamat(p_alamat.getText().toString());
                    pm.setHp(p_phone.getText().toString());
                    pm.setTanggal_k(t_keluar);
                    pm.setTanggal_m(t_masuk);
                    System.out.println("Keluar "+t_keluar);
                    System.out.println("Masuk "+t_masuk);
                    startActivity(new Intent(OrderActivity.this, ProcessActivity.class));
                    finish();
                }

            }
        });

        p_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
