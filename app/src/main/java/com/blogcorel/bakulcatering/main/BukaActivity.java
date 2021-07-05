package com.blogcorel.bakulcatering.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BukaActivity extends AppCompatActivity {
    private String TAG = BukaActivity.class.getSimpleName();

    ImageButton caim_catering, add_catering_img, back_cat;
    ImageView img_catering;
    EditText catname, address, city, desc;
    Button addcatering;
    ConstraintLayout buka_catering;

    String check, value;
    String nama, jalan, kota, deskripsi, email, url, link;
    Uri uri;

    UserModel um;
    StorageReference sf;
    ConfigServices cs = new ConfigServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buka);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View main = findViewById(R.id.buka_catering);
            int flags = main.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            main.setSystemUiVisibility(flags);
            this.getWindow().setStatusBarColor(Color.WHITE);
            this.getWindow().setNavigationBarColor(Color.WHITE);
        }

        um = new UserModel();

        caim_catering = findViewById(R.id.caim_catering);
        add_catering_img = findViewById(R.id.add_catering_img);
        back_cat = findViewById(R.id.back_cat);
        img_catering = findViewById(R.id.img_catering);
        catname = findViewById(R.id.catname);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        desc = findViewById(R.id.desc);
        addcatering = findViewById(R.id.addcatering);
        buka_catering = findViewById(R.id.buka_catering);

        caim_catering.setVisibility(View.GONE);
        add_catering_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
            }
        });

        back_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        caim_catering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_catering.setImageURI(null);
                uri = null;
                caim_catering.setVisibility(View.GONE);
                add_catering_img.setVisibility(View.VISIBLE);
                check = "";
            }
        });

        addcatering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasi();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            uri = data.getData();
            img_catering.setImageURI(uri);
            caim_catering.setVisibility(View.VISIBLE);
            add_catering_img.setVisibility(View.GONE);
            check = "ok";

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void validasi(){
        nama = catname.getText().toString();
        jalan = address.getText().toString();
        kota = city.getText().toString();
        deskripsi = desc.getText().toString();

        if(TextUtils.isEmpty(nama) || TextUtils.isEmpty(jalan) ||
            TextUtils.isEmpty(kota)){
            if(TextUtils.isEmpty(nama)){
                catname.setError("Nama tidak boleh kosong");
            }
            if(TextUtils.isEmpty(jalan)){
                address.setError("Jalan Harus Diisi");
            }
            if(TextUtils.isEmpty(kota)){
                city.setError("Kota Harus Diisi");
            }
        } else if (TextUtils.isEmpty(check)){
            Toast.makeText(BukaActivity.this, "Please add 1 image", Toast.LENGTH_LONG).show();
        } else {
            upload();
        }
    }

    private void upload(){
        Snackbar snackbar = Snackbar
                .make(buka_catering, "Mengupload Gambar", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        addcatering.setEnabled(false);
        back_cat.setEnabled(false);
        caim_catering.setEnabled(false);
        sf = FirebaseStorage.getInstance().getReference().child("Catering").child(nama+uri.getLastPathSegment());
        sf.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        sf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                link = uri.toString();
                                Snackbar snackbar = Snackbar
                                        .make(buka_catering, "Berhasil Mengupload Gambar", Snackbar.LENGTH_LONG);
                                snackbar.show();
                                System.out.println(link);
                                new addCatering().execute();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        addcatering.setEnabled(true);
                        back_cat.setEnabled(true);
                        caim_catering.setEnabled(true);
                        Toast.makeText(BukaActivity.this, exception.getCause().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });

    }

    private class addCatering extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Snackbar snackbar = Snackbar
                    .make(buka_catering, "Menyimpan Data", Snackbar.LENGTH_LONG);
            snackbar.show();

            email = um.getUser();

            url = cs.getUrl()+"postcatering.php";
        }
        @Override
        protected Void doInBackground(Void... voids) {
            PostHandler sh = new PostHandler();

            JSONObject data = new JSONObject();
            try {
                data.put("email", email);
                data.put("nama", nama);
                data.put("link", link);
                data.put("alamat", jalan);
                data.put("kota", kota);
                data.put("desc", deskripsi);
                data.put("total", 0);
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
                Snackbar snackbar = Snackbar
                        .make(buka_catering, "Berhasil Menambahkan Catering", Snackbar.LENGTH_LONG);
                snackbar.show();
                um.setLevel(2);
                Handler hh = new Handler();
                hh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(BukaActivity.this, ManageActivity.class));
                        finish();
                    }
                },500);
            }else {
                Snackbar snackbar = Snackbar
                        .make(buka_catering, "Gagal menambahkan, coba lagi nanti", Snackbar.LENGTH_LONG);
                snackbar.show();
                addcatering.setEnabled(true);
                back_cat.setEnabled(true);
                caim_catering.setEnabled(true);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(BukaActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
